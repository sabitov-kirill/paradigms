/* Common expressions */

function Const(value) {
    this.value = value
}

Const.prototype = {
    evaluate: function () {
        return this.value;
    },
    diff: function () {
        return zero;
    },
    toString: function () {
        return this.value.toString();
    },
    prefix: function () {
        return this.toString();
    },
    postfix: function () {
        return this.toString();
    }
}
Const.isNumber = (str) => /^([+-]?(\d*\.)?\d+)$/.test(str);

const zero = new Const(0);
const one = new Const(1);
const pi = new Const(Math.PI);
const e = new Const(Math.E);

function Variable(name) {
    this.name = name;
    this.argumentsNameIndex = ({
        [name]: 2 + parseInt((name.match(/var(\d+)/) ?? [])[1]), x: 0, y: 1, z: 2
    })[name];
}
Variable.isName = (name) => /^(x|y|z|var(\d+))$/.test(name);
Variable.prototype = {
    evaluate: function (...args) {
        return args[this.argumentsNameIndex];
    },
    diff: function (diffVariableName) {
        return diffVariableName === this.name ? one : zero;
    },
    toString: function () {
        return this.name;
    },
    prefix: function () {
        return this.toString();
    },
    postfix: function () {
        return this.toString();
    }
}

/* Default operators */

function Operator(...expressions) {
    this.expressions = expressions;
}

Operator.prototype = {
    f: x => x,
    sign: "",
    evaluate: function (...args) {
        return this.f(...this.expressions.map(expr => expr.evaluate(...args)));
    },
    diff: function (diffVariableName) {
        return new this.constructor(...this.expressions.map(expr => expr.diff(diffVariableName)));
    },
    toString: function () {
        return this.expressions.join(' ') + ' ' + this.sign;
    },
    prefix: function () {
        return "(" + this.sign + ' ' + this.expressions.map(e => e.prefix()).join(' ') + ")";
    },
    postfix: function () {
        return "(" + this.expressions.map(e => e.prefix()).join(' ') + " " + this.sign + ")";
    },
}

const operators = {};
const constructOperator = (sign, f, diff) => {
    function ConstructedOperator(...expressions) {
        Operator.call(this, ...expressions);
    }

    ConstructedOperator.prototype = {...Operator.prototype, constructor: ConstructedOperator, sign, f};
    if (diff != null) {
        ConstructedOperator.prototype.diff = diff;
    }

    operators[sign] = ConstructedOperator;
    return ConstructedOperator;
}

const Add = constructOperator("+", (x, y) => x + y);
const Subtract = constructOperator("-", (x, y) => x - y);
const Multiply = constructOperator("*", (x, y) => x * y, function (diffVariableName) {
    return new Add(new Multiply(this.expressions[0].diff(diffVariableName), this.expressions[1]), new Multiply(this.expressions[1].diff(diffVariableName), this.expressions[0]),);
});
const Divide = constructOperator("/", (x, y) => x / y, function (diffVariableName) {
    return new Divide(new Subtract(new Multiply(this.expressions[0].diff(diffVariableName), this.expressions[1]), new Multiply(this.expressions[1].diff(diffVariableName), this.expressions[0]),), new Multiply(this.expressions[1], this.expressions[1]));
});
const Negate = constructOperator("negate", x => -x);
const ArcTan = constructOperator("atan", Math.atan, function (diffVariableName) {
    return new Divide(this.expressions[0].diff(diffVariableName), new Add(one, new Multiply(this.expressions[0], this.expressions[0])));
});
const ArcTan2 = constructOperator("atan2", Math.atan2, function (diffVariableName) {
    return new ArcTan(new Divide(this.expressions[0], this.expressions[1])).diff(diffVariableName);
});

const Sum = constructOperator("sum", (...args) => args.reduce((s, val) => s + val, 0));
const Avg = constructOperator("avg", (...args) => args.reduce((s, val) => s + val, 0) / args.length);
Sum.isAnyArity = Avg.isAnyArity = true;

/* Templated operators */

const templatedOperators = [];
const constructOperatorN = (sign, f, diff) => {
    function ConstructedOperatorN(n, ...expressions) {
        Operator.call(this, ...expressions.slice(0, n));
        this.sign = sign + n;
    }

    ConstructedOperatorN.prototype = {
        ...Operator.prototype, constructor: ConstructedOperatorN, f,
    };
    if (diff != null) {
        ConstructedOperatorN.prototype.diff = diff;
    }

    const constructConcreteNOperatorN = n => {
        function ConstructedOperatorConcreteN(...expressions) {
            ConstructedOperatorN.call(this, n, ...expressions);
        }

        ConstructedOperatorConcreteN.prototype = {...ConstructedOperatorN.prototype};
        return ConstructedOperatorConcreteN;
    }

    templatedOperators.push([new RegExp(sign + "(\\d+)"), ConstructedOperatorN]);
    return {ConstructedOperatorN, constructConcreteNOperatorN};
}

const {
    ConstructedOperatorN: SumrecN,
    constructConcreteNOperatorN: constructSumrecN
} = constructOperatorN("sumrec", (...args) => args.reduce((sum, arg) => sum + 1 / arg, 0), function (diffVariableName) {
    return this.expressions.reduce((result, current) => new Add(result, new Negate(new Divide(current.diff(diffVariableName), new Multiply(current, current)))), new Const(0));
});

const Sumrec2 = constructSumrecN(2);
const Sumrec3 = constructSumrecN(3);
const Sumrec4 = constructSumrecN(4);
const Sumrec5 = constructSumrecN(5);

const {
    ConstructedOperatorN: HMeanN,
    constructConcreteNOperatorN: constructHMeanN
} = constructOperatorN("hmean", (...args) => args.length / SumrecN.prototype.f(...args), function (diffVariableName) {
    const sumrecN = new SumrecN(this.expressions.length, ...this.expressions);
    return new Divide(new Multiply(new Const(-this.expressions.length), SumrecN.prototype.diff.call(this, diffVariableName)), new Multiply(sumrecN, sumrecN));
});

const HMean2 = constructHMeanN(2);
const HMean3 = constructHMeanN(3);
const HMean4 = constructHMeanN(4);
const HMean5 = constructHMeanN(5);