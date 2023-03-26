/*=============================================
 * Expression classes declaration
 *=============================================*/

function Const(value) { this.value = value }
Const.prototype = {
    evaluate: function() { return this.value; },
    toString: function() { return this.value.toString(); },
    diff: function() { return zero; },
}
const zero = new Const(0);
const one = new Const(1);
const pi = new Const(Math.PI);
const e = new Const(Math.E);

function Variable(name) {
    this.name = name;
    this.argumentsNameIndex = ({
        [name]: 2 + parseInt((name.match(/var(\d+)/) ?? [])[1]),
        x: 0, y: 1, z: 2
    })[name];
}
Variable.isName = function(name) { return /x|y|z|var(\d+)/.test(name)}
Variable.prototype = {
    evaluate: function (...args) { return args[this.argumentsNameIndex]; },
    toString: function () { return this.name; },
    diff: function(diffVariableName) {
        return diffVariableName === this.name ? one : zero;
    }
}

function Operator(...expressions) {
    this.expressions = expressions;
}
Operator.prototype = {
    f: x => x,
    sign: "",
    evaluate: function (...args) {
        return this.f(...this.expressions.map(expr => expr.evaluate(...args)));
    },
    toString: function () {
        return this.expressions.reduce((s, expr) => s + expr.toString() + " ", "") + this.sign;
    },
    diff: function(diffVariableName) {
        return new this.constructor(...this.expressions.map(expr => expr.diff(diffVariableName)));
    },
}

const constructOperator = (sign, f, diff) => {
    function ConstructedOperator(...expressions) { Operator.call(this, ...expressions); }
    ConstructedOperator.prototype = {
        ...Operator.prototype,
        constructor: ConstructedOperator,
        sign:        sign,
        f:           f,
    };
    if (diff != null) {
        ConstructedOperator.prototype.diff = diff;
    }

    return ConstructedOperator;
}

const constructOperatorN = (sign, f, diff) => {
    function ConstructedOperatorN(n, ...expressions) {
        Operator.call(this, ...expressions.slice(0, n));
        this.sign = sign + n;
    }
    ConstructedOperatorN.prototype = {
        ...Operator.prototype,
        constructor: ConstructedOperatorN,
        f: f,
    };
    if (diff != null) {
        ConstructedOperatorN.prototype.diff = diff;
    }

    const constructConcreteNOperatorN = n => {
        function ConstructedSumrecN(...expressions) {
            ConstructedOperatorN.call(this, n, ...expressions);
        }
        ConstructedSumrecN.prototype = { ...ConstructedOperatorN.prototype };
        return ConstructedSumrecN;
    }

    return { ConstructedOperatorN, constructConcreteNOperatorN };
}

const Add = constructOperator("+", (x, y) => x + y);
const Subtract = constructOperator("-", (x, y) => x - y);
const Multiply = constructOperator("*", (x, y) => x * y, function (diffVariableName) {
    return new Add(
        new Multiply(this.expressions[0].diff(diffVariableName), this.expressions[1]),
        new Multiply(this.expressions[1].diff(diffVariableName), this.expressions[0]),
    );
});
const Divide = constructOperator("/", (x, y) => x / y, function (diffVariableName) {
    return new Divide(new Subtract(
        new Multiply(this.expressions[0].diff(diffVariableName), this.expressions[1]),
        new Multiply(this.expressions[1].diff(diffVariableName), this.expressions[0]),
    ), new Multiply(this.expressions[1], this.expressions[1]));
});
const Negate = constructOperator("negate", x => -x);

const { ConstructedOperatorN: SumrecN, constructConcreteNOperatorN: constructSumrecN } = constructOperatorN(
    "sumrec", (...args) => args.reduce((sum, arg) => sum + 1 / arg, 0), function (diffVariableName) {
        return this.expressions.reduce((result, current) => new Add(
            result, new Negate(new Divide(
                current.diff(diffVariableName),
                new Multiply(current, current)
            ))
        ), new Const(0));
    }
);

const Sumrec2 = constructSumrecN(2);
const Sumrec3 = constructSumrecN(3);
const Sumrec4 = constructSumrecN(4);
const Sumrec5 = constructSumrecN(5);

const { ConstructedOperatorN: HMeanN, constructConcreteNOperatorN: constructHMeanN } = constructOperatorN(
    "hmean",(...args) => args.length / SumrecN.prototype.f(...args), function (diffVariableName) {
        const sumrecN = new SumrecN(this.expressions.length, ...this.expressions);
        return new Divide(
            new Multiply(new Const(-this.expressions.length), SumrecN.prototype.diff.call(this, diffVariableName)),
            new Multiply(sumrecN, sumrecN)
        );
    }
);

const HMean2 = constructHMeanN(2);
const HMean3 = constructHMeanN(3);
const HMean4 = constructHMeanN(4);
const HMean5 = constructHMeanN(5);

/*=============================================
 * Expression parser
 *=============================================*/

const parse = (source) => {
    const operators = Object.fromEntries([Add, Subtract, Multiply, Divide, Negate].map(op => [op.prototype.sign, op]));
    const templatedOperators = [ [ /sumrec(\d+)/, SumrecN ], [ /hmean(\d+)/, HMeanN] ];
    return source.trim().split(/\s+/).reduce((stack, token) => {
        const { constructor, arity, tokenType } = token in operators ? {
            constructor: operators[token], arity: operators[token].prototype.f.length, tokenType: 'operator'
        } : templatedOperators.reduce((result, [ regex, constructor ]) => {
            const match = token.match(regex);
            return match === null || result.found ? result : {
                found: true, tokenType: 'templatedOperator', constructor, arity: parseInt(match[1])
            };
        }, { found : false });
        if (tokenType === 'operator') {
            stack.push(new constructor(...stack.splice(stack.length - arity, arity)));
        } else if (tokenType === 'templatedOperator') {
            stack.push(new constructor(arity, ...stack.splice(stack.length - arity, arity)));
        } else if (Variable.isName(token)) {
            stack.push(new Variable(token));
        } else {
            stack.push(new Const(parseFloat(token)));
        }
        return stack;
    }, [])[0];
}
