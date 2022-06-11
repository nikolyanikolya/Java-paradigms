const Operators = {
                   "-" : (a, b) => a - b,
                   "+" : (a, b) => a + b,
                   "*" : (a, b) => a * b,
                   "/" : (a, b) =>  a / b,
                   "sinh" : x => Math.sinh(x),
                   "cosh" : x => Math.cosh(x),
                   "neg" : x => -x
                  }
const cnst = value => () => value;
const pi = cnst(Math.PI);
const e = cnst(Math.E);
const getFunction = op => Operators[op]
const operation = op => (...ops) => (x, y, z) => (getFunction(op))(...(ops.map(func => func(x, y, z))));
const variable = name => (x, y, z) => {
    switch (name){
        case 'x':
            return x;
        case 'y':
            return y;
        case 'z':
            return z;
    }
}
const negate = operation("neg")
const multiply = operation("*");
const add = operation("+");
const subtract = operation("-");
const divide = operation("/");
const sinh = operation("sinh");
const cosh = operation("cosh");
