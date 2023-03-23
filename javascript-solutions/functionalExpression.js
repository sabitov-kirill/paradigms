/*=============================================
 * Operators
 *=============================================*/

const variable = (name) => (x, y, z) => ({ x, y, z })[name];

const cnst = value => () => value;
const one = cnst(1);
const two = cnst(2);
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const operator = f => (...expressions) => (x, y, z) => f(...expressions.map(expression => expression(x, y, z)));

const arrayIndexByRelation = (binaryRelation, size) => operator((...array) => array.slice(0, size).reduce(
    (bestIndex, value, index) => binaryRelation(value, array[bestIndex]) ? bestIndex : index, -1
));
const argMin3 = arrayIndexByRelation((x, y) => x >= y, 3);
const argMax3 = arrayIndexByRelation((x, y) => x <= y, 3);
const argMin5 = arrayIndexByRelation((x, y) => x >= y, 5);
const argMax5 = arrayIndexByRelation((x, y) => x <= y, 5);

const madd = operator((x, y, z) => x * y + z);
const add = operator((x, y) => x + y);
const subtract = operator((x, y) => x - y);
const multiply = operator((x, y) => x * y);
const divide = operator((x, y) => x / y);

const negate = operator(x => -x);
const floor = operator(Math.floor);
const ceil = operator(Math.ceil);
const sin = operator(Math.sin);
const sinh = operator(Math.sinh);
const cos = operator(Math.cos);
const cosh = operator(Math.cosh);

/*=============================================
 * Expression parser
 *=============================================*/

let parse = (source) => {
    const cnstAliases = { one, two };
    const variableNames = ['x', 'y', 'z'];
    const operators = {
        'argMin5': { operation: argMin5,  arity: 5 },
        'argMax5': { operation: argMax5,  arity: 5 },
        'argMin3': { operation: argMin3,  arity: 3 },
        'argMax3': { operation: argMax3,  arity: 3 },
        '*+':      { operation: madd,     arity: 3 },
        '+':       { operation: add,      arity: 2 },
        '-':       { operation: subtract, arity: 2 },
        '*':       { operation: multiply, arity: 2 },
        '/':       { operation: divide,   arity: 2 },
        'negate':  { operation: negate,   arity: 1 },
        '_':       { operation: floor,    arity: 1 },
        '^':       { operation: ceil,     arity: 1 },
        'sin':     { operation: sin,      arity: 1 },
        'sinh':    { operation: sinh,      arity: 1 },
        'cosh':    { operation: cosh,      arity: 1 },
        'cos':     { operation: cos,      arity: 1 },
    };

    let stack = [];
    source.trim().split(/\s+/).forEach(token => {
        let result;
        if (token in operators) {
            result = operators[token].operation(...stack.splice(
                stack.length - operators[token].arity,
                operators[token].arity
            ));
        } else if (variableNames.includes(token)) {
            result = variable(token);
        } else {
            result = token in cnstAliases ?
                cnstAliases[token] :
                cnst(parseFloat(token));
        }

        stack.push(result);
    });

    return stack[0];
}
