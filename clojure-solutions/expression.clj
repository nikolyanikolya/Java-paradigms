; --------------------------------- HW 10-----------------------------------------------------------
(defn operation [op] (fn [& args] (fn [m] (apply op (mapv (fn [f] (f m)) args)))))
(defn constant [x] (constantly x))
(defn variable [var] (fn [m] (m var)))
(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(defn divide [a b] (fn [m] (/ (a m) (double (b m)))))       ; (apply / [5.0 0.0]) throws exception
(defn negate [a] (subtract a))
(def pow (operation #(Math/pow %1 %2)))
(defn log-abs [a] (Math/log (Math/abs a)))
(def log (operation #(/ (log-abs %2) (double (log-abs %1)))))
(def operations {:-      subtract
                 :+      add
                 :*      multiply
                 :/      divide
                 :negate negate
                 :pow    pow
                 :log    log
                 })
(defn parseFunction [str] (let [
                                expr (if (string? str)
                                       (read-string str)
                                       str)
                                ]
                            (cond
                              (list? expr) (apply (operations (keyword (first expr))) (mapv parseFunction (rest expr)))
                              (number? expr) (constant expr)
                              (symbol? expr) (variable (name expr))
                              )
                            ))

; ------------------------------HW 11------------------------------------------------------------------
(require '[clojure.string :as str])
(defn proto-get
  "Returns object property respecting the prototype chain"
  ([obj key] (proto-get obj key nil))
  ([obj key default]
   (cond
     (contains? obj key) (obj key)
     (contains? obj :prototype) (proto-get (obj :prototype) key default)
     :else default)))

(defn proto-call
  "Calls object method respecting the prototype chain"
  [this key & args]
  (apply (proto-get this key) this args))

(defn field [key]
  "Creates field"
  (fn
    ([this] (proto-get this key))
    ([this def] (proto-get this key def))))

(defn method
  "Creates method"
  [key] (fn [this & args] (apply proto-call this key args)))

(defn bitFunc [f args] (Double/longBitsToDouble (apply f (mapv #(Double/doubleToLongBits %) args))))
(def Operators {
                "+"      (fn [& args] (apply + args))
                "-"      (fn [& args] (apply - args))
                "/"      (fn [a b] (/ a (double b)))
                "*"      (fn [& args] (apply * args))
                "negate" #(- %1)
                "log"    #(/ (log-abs %2) (double (log-abs %1)))
                "pow"    #(Math/pow %1 %2)
                "|"      (fn [& args] (bitFunc bit-or args))
                "^"      (fn [& args] (bitFunc bit-xor args))
                "&"      (fn [& args] (bitFunc bit-and args))
                })

(def evaluate (method :evaluate))

(def toString (method :toString))

(def toStringSuffix (method :toStringSuffix))

(def diff (method :diff))

(def args (field :args))

(def op (field :op))

(def getSecond #(second (args %1)))

(def getFirst #(first (args %1)))

(defn diffArgument [f this var] (diff (f this) var))

(def diffFirst #(diffArgument getFirst %1 %2))

(def diffSecond #(diffArgument getSecond %1 %2))

(def toStringFunc #(constantly (.toString %)))

(defn toTemplateString [self str1 str2 f] (apply str (vector "(" str1 (str/join " "
                                                                                (mapv (fn [this] (f this)) (args self))) str2 ")")))
(defn Constant [value]
  {
   :evaluate       (constantly value)
   :toString       (toStringFunc value)
   :toStringSuffix (toStringFunc value)
   :diff           (fn [_ _] (Constant 0))
   })

(def Euler (Constant (Math/E)))

(defn Variable [name]
  {
   :evaluate       (fn [_ vars] (vars (str/lower-case (str (first name)))))
   :toString       (constantly name)
   :toStringSuffix (constantly name)
   :diff           (fn [_ var] (if (= name var) (Constant 1) (Constant 0)))
   })

(def Expression
  {
   :evaluate       (fn [self vars] (apply (Operators (op self)) (mapv (fn [this]
                                                                        (evaluate this vars)) (args self))))
   :toString       (fn [self] (toTemplateString self (str (op self) " ") "" toString))

   :toStringSuffix (fn [self] (toTemplateString self "" (str " " (op self)) toStringSuffix))

   }
  )

(defn constructor
  "Defines constructor"
  [ctor]
  (fn [& args] (apply ctor {:prototype Expression} args)))

(def addOperation #(fn [this & args] (assoc this
                                       :args args
                                       :op %1
                                       :diff %2)))

; fn [[x y] [dx dy] (+ dx dy)
; fn [[x y] [dx dy] (+ (* dx y) (* x dy))
(def Add (constructor (addOperation "+"
                                    #(Add (diffFirst %1 %2)
                                          (diffSecond %1 %2)))))
(def Negate (constructor (addOperation "negate"
                                       #(Negate (diffFirst %1 %2)))))

(def Subtract (constructor (addOperation "-"
                                         #(Subtract (diffFirst %1 %2) (diffSecond %1 %2)))))
(def Multiply (constructor (addOperation "*"
                                         #(Add (Multiply (diffFirst %1 %2)
                                                         (getSecond %1))
                                               (Multiply (getFirst %1)
                                                         (diffSecond %1 %2))
                                               ))))
(def Square #(Multiply %1 %1))
(def Divide (constructor (addOperation "/"
                                       #(Divide
                                          (Subtract (Multiply (diffFirst %1 %2)
                                                              (getSecond %1))
                                                    (Multiply (getFirst %1)
                                                              (diffSecond %1 %2))
                                                    )
                                          (Square (getSecond %1))
                                          ))))

(def Log (constructor (addOperation "log" #(Divide (Subtract (Divide (Multiply (diffSecond %1 %2) (Log Euler (getFirst %1)))
                                                                     (getSecond %1)) (Divide (Multiply (diffFirst %1 %2)
                                                                                                       (Log Euler (getSecond %1))) (getFirst %1)))
                                                   (Square (Log Euler (getFirst %1)))))))

(def Pow (constructor (addOperation "pow" #(Multiply (Pow (getFirst %1) (getSecond %1)) (diff (Multiply (getSecond %1)
                                                                                                        (Log Euler (getFirst %1))) %2)))))
(def BitOr (constructor (addOperation "|" identity)))

(def BitXor (constructor (addOperation "^" identity)))

(def BitAnd (constructor (addOperation "&" identity)))

(def Constructors {
                   :+      Add
                   :-      Subtract
                   :*      Multiply
                   :/      Divide
                   :negate Negate
                   :log    Log
                   :pow    Pow
                   :|      BitOr
                   (keyword "^") BitXor
                   :&      BitAnd
                   }
  )

(defn parseObject [str] (let [
                              expr (if (string? str)
                                     (read-string str)
                                     str)
                              ]
                          (cond
                            (list? expr) (apply (Constructors (keyword (first expr))) (mapv parseObject (rest expr)))
                            (number? expr) (Constant expr)
                            (symbol? expr) (Variable (name expr))
                            )
                          ))
;----------------------------------------HW 12---------------------------------------------------------
(load-file "parser.clj")
(def *space (+char "\t\n\r "))
(def *ws (+ignore (+star *space)))
(def *digit (+char "0123456789"))
(defn +ignore-char [ch] (+ignore (+char ch)))
(def *number (+map read-string (+str
                                 (+seqf #(flatten %&)
                                        (+opt (+char "+-"))
                                        (+plus *digit)
                                        (+opt (+seq
                                                (+char ".")
                                                (+plus *digit)))
                                        ))))

(def *letters (+char "xyzXYZ"))
(def *vars (+str (+plus *letters)))
(def *ops (apply +or (mapv #(+str (apply +seq (mapv +char (str/split % #"")))) (keys Operators))))
(def *atom (+or
             (+seqf Constant
                    *ws
                    *number
                    *ws)
             (+seqf Variable
                    *ws
                    *vars
                    *ws)
             (+seqf (fn [args op] (apply (Constructors (keyword op)) args))
                    *ws
                    (+ignore-char "(")
                    *ws
                    (+plus (delay *atom))
                    *ws
                    *ops
                    *ws
                    (+ignore-char ")")
                    *ws
                    )
             ))
(defn parseObjectSuffix [s] ((+parser *atom) s))
