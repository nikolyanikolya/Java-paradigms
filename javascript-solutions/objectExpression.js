"use strict"
const Operators = {
    "-": [(a, b) => a - b, 2],
    "+": [(a, b) => a + b, 2],
    "*": [(a, b) => a * b, 2],
    "/": [(a, b) => a / b, 2],
    "negate": [x => -x, 1],
    "sinh": [x => Math.sinh(x), 1],
    "cosh": [x => Math.cosh(x), 1],
    "mean": [(...args) => args.reduce((a, b) => a + b) / args.length, Number.MAX_SAFE_INTEGER],
    "var": [(...args) => args.map(n => n * n).reduce((sum, n) => sum + n) / args.length
        - (args.reduce((a, b) => a + b) / args.length) ** 2, Number.MAX_SAFE_INTEGER]

}

function Const(value) {
    this.value = value;
}

Const.prototype.evaluate = function () {
    return this.value;
}

Const.prototype.toString = function () {
    return this.value.toString();
}
Const.prototype.prefix = Const.prototype.toString;

function Variable(value) {
    this.name = value;
}

Variable.prototype.evaluate = function (x, y, z) {
    switch (this.name) {
        case 'x':
            return x;
        case 'y':
            return y;
        case 'z':
            return z;
    }
}

Variable.prototype.toString = function () {
    return this.name;
}
Variable.prototype.prefix = Variable.prototype.toString;

let Expression = function (op, args) {
    this.getFunction = Operators[op][0]
    this.args = args;
    this.op = op;
}
Expression.prototype.evaluate = function (x, y, z) {
    return this.getFunction(...(this.args.map(fun => fun.evaluate(x, y, z))))
}
Expression.prototype.toString = function () {
    return this.args.join(" ") + " " + this.op;
}
Expression.prototype.prefix = function () {
    return "(" + this.op + " " + this.args.map(arg => arg.prefix()).join(" ") + ")";
}
let Operation = function (op) {
    let Operator = function (...args) {
        return new Expression(op, args);
    }
    Operator.prototype = Object.create(Expression.prototype);
    return Operator;
}

const Negate = Operation("negate")
const Multiply = Operation("*");
const Add = Operation("+");
const Subtract = Operation("-");
const Divide = Operation("/");
const Sinh = Operation("sinh");
const Cosh = Operation("cosh");
const Mean = Operation("mean");
const Var = Operation("var");

const parse = expr => {
    let words = expr.trim().split(' ');
    let stack = [];
    for (let i = 0; i < words.length; i++) {
        let word = words[i].trim();
        if (word === "") {
            continue;
        }
        if (word in Operators) {
            let args = [];
            for (let i = 0; i < Operators[word][1]; i++) {
                args.unshift(stack.shift());
            }
            stack.unshift(new (Operation(word))(...args));
        } else if (word === 'x' || word === 'y' || word === 'z') {
            stack.unshift(new Variable(word));
        } else {
            stack.unshift(new Const(Number.parseInt(word)))
        }
    }
    return stack.shift()
}

class ParseError extends Error {
    constructor(message, pos, prefix) {
        super("Character on pos: " + (pos + 1) + " in " + prefix + " " + message);
        this.name = "ParseError";
    }
}

class BaseParser {
    #s;
    #pos;  // private fields
    constructor(s) {
        this.#s = s;
        this.#pos = 0;
    }

    next() {
        return this.#s[this.#pos++];
    }

    error(message) {
        return new ParseError(message, this.#pos, this.#s.substring(0, this.#pos));
    }

    isDigit = function (x) {
        return /^\d$/.test(x);
    }

    currentCharacter() {
        return this.#s[this.#pos];
    }

    expect(ch) {
        let currentChar = this.currentCharacter();
        if (currentChar !== ch) {
            if (ch === ')') {
                throw this.error("Unopened parenthesis");
            } else if (currentChar === ')') {
                throw this.error("Unclosed parenthesis");
            }
            throw this.error("Expected " + (ch === undefined ? "end of expression"
                : "'" + ch + "'" + ", found '" + currentChar + "'"));
        }
        this.next();
    }

    skipWhitespace() {
        while (this.currentCharacter() !== undefined &&
        this.currentCharacter().trim() === '') {
            this.next();
        }
    }

    isSeparator() {
        let currentChar = this.currentCharacter();
        return currentChar === undefined || currentChar.trim() === ''
            || currentChar === ')' || currentChar === '(';
    }

    parseFunctionName() {
        this.skipWhitespace();
        let functionName = '';
        while (!this.isSeparator()) {
            functionName += this.next();
        }
        this.skipWhitespace();
        return functionName;
    }
}

class Parser {
    static #VARIABLES = ['x', 'y', 'z'];
    static #OPERATORS = {
        '+': [Add, 2],
        '*': [Multiply, 2],
        '/': [Divide, 2],
        'negate': [Negate, 1],
        '-': [Subtract, 2],
        'sinh': [Sinh, 1],
        'cosh': [Cosh, 1],
        'mean': [Mean, Number.MAX_SAFE_INTEGER],
        'var': [Var, Number.MAX_SAFE_INTEGER]
    }

    #parseOperator(expr) {
        expr.skipWhitespace();
        expr.expect('(');
        expr.skipWhitespace();
        let token = expr.parseFunctionName();
        let operator = Parser.#OPERATORS[token];
        if (operator === undefined) {
            throw expr.error("Invalid operator " + token + ". ");
        }
        let args = []
        for (let i = 0; expr.currentCharacter() !== ")"; i++) {
            expr.skipWhitespace();
            args.push(this.#parseNumber(expr));
            expr.skipWhitespace();
        }
        if (args.length !== operator[1] && operator[1] !== Number.MAX_SAFE_INTEGER) {
            throw expr.error("Invalid arguments for operator " + token + ". ");
        }
        expr.skipWhitespace();
        expr.expect(')');
        expr.skipWhitespace();
        return operator[0](...args);
    }

    #parseNumber(expr) {
        expr.skipWhitespace();
        let currentChar = expr.currentCharacter();
        if (expr.isDigit(currentChar) || currentChar === '-') {
            let digit = currentChar;
            expr.next();
            while (expr.isDigit(expr.currentCharacter())) {
                digit += expr.next();
            }
            let number = parseFloat(digit)
            if (isNaN(number)) {
                throw expr.error("Invalid digit " + digit + ". ");
            }
            return new Const(number);
        }
        if (Parser.#VARIABLES.indexOf(currentChar) !== -1) {
            expr.next();
            return new Variable(currentChar);
        }
        return this.#parseOperator(expr);
    }

    parsePrefix(expr) {
        let baseParser = new BaseParser(expr)
        let parsed = this.#parseNumber(baseParser);
        baseParser.skipWhitespace();
        baseParser.expect(undefined);
        return parsed;
    }
}

let parser = new Parser();
const parsePrefix = parser.parsePrefix.bind(parser);
