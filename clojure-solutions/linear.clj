(def apply-to-matrix #(apply mapv %1 %2))
(defn apply-to-vectors [op] (fn [& args] (apply-to-matrix op args)))
(defn get-applier [op]
  (fn [& args]
    (if (vector? (first args))
      (apply mapv (get-applier op) args)
      (reduce op args))))
(def s+ (get-applier +))
(def s- (get-applier -))
(def s* (get-applier *))
(def sd (get-applier /))
(def v+ (apply-to-vectors +))
(def v* (apply-to-vectors *))
(def v- (apply-to-vectors -))
(def vd (apply-to-vectors /))
(defn v*s [v s] (mapv #(* % s) v))
(defn scalar [v1 v2] (reduce + (v* v1 v2)))
(def m+ (apply-to-vectors v+))
(def m* (apply-to-vectors v*))
(def m- (apply-to-vectors v-))
(def md (apply-to-vectors vd))
(defn m*s [m s] (apply-to-matrix #(v*s % s) (vector m)))
(defn m*v [m v] (apply-to-matrix #(scalar % v) (vector m)))
(defn transpose [m] (apply-to-matrix vector m))
(defn m*m [m1 m2] (transpose (apply-to-matrix #(m*v m1 %) (vector (transpose m2)))))
(defn vect [v1 v2] (letfn [(make-vec-multiply-matrix [v]
                             (let [error-message (str "vector with three coordinates expected")
                                   a3 (nth v 2 error-message)
                                   a2 (nth v 1 error-message)
                                   a1 (nth v 0 error-message)]
                               (vector [0 (- a3) a2]
                                       [a3 0 (- a1)]
                                       [(- a2) a1 0])))]
          (m*v (make-vec-multiply-matrix v1) v2)))