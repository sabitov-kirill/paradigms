/*=============================================
 * Operators
 *=============================================*/

let cnst = value => () => value;
let variable = (name) => (x, y, z) => ({ x, y, z })[name];

let operator = f => (...expressions) => (x, y, z) => f(...expressions.map(expression => expression(x, y, z)));
let add = operator((x, y) => x + y);
let subtract = operator((x, y) => x - y);
let multiply = operator((x, y) => x * y);
let divide = operator((x, y) => x / y);
let negate = operator(x => -x);

/*=============================================
 * Expression parser
 *=============================================*/

let parse = (source) => {
    const variableNames = ['x', 'y', 'z'];
    const operators = {
        '+':      { operation: add,      arity: 2 },
        '-':      { operation: subtract, arity: 2 },
        '*':      { operation: multiply, arity: 2 },
        '/':      { operation: divide,   arity: 2 },
        'negate': { operation: negate,   arity: 1 },
    };

    let stack = [];
    source.trim().split(/\s+/).forEach(token => {
        let result;
        if (token in operators) {
            result = operator.operation(...stack.splice(
                stack.length - operators[token].arity,
                operators[token].arity
            ));
        } else if (variableNames.includes(token)) {
            result = variable(token);
        } else {
            result = cnst(parseInt(token));
        }

        stack.push(result);
    });

    return stack[0];
}
