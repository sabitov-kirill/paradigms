/*=============================================
 * Operators
 *=============================================*/

let cnst = value => () => value;
let variable = (name) => (x, y, z) => ({ x, y, z })[name];
let operator = f => (...operators) => (x, y, z) => f(...operators.map(op => op(x, y, z)));

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
    const binaryOperators = {
        '+': add,
        '-': subtract,
        '*': multiply,
        '/': divide
    };
    const unaryOperators = {
        'negate': negate
    };

    let stack = [];
    source.trim().split(/\s+/).forEach(token => {
        let result;
        if (token in binaryOperators) {
            let last = stack.pop(), first = stack.pop();
            result = binaryOperators[token](first, last);
        } else if (token in unaryOperators) {
            result = unaryOperators[token](stack.pop());
        } else if (variableNames.includes(token)) {
            result = variable(token);
        } else {
            result = cnst(parseInt(token));
        }

        stack.push(result);
    })

    return stack[0];
}

parse("x x 2 - * x * 1 +")
