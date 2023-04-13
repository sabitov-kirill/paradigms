const parse = (source) => {
    return source.trim().split(/\s+/).reduce((stack, token) => {
        const {constructor, arity, tokenType} = token in operators ? {
            constructor: operators[token], arity: operators[token].prototype.f.length, tokenType: 'operator'
        } : templatedOperators.reduce((result, [regex, constructor]) => {
            const match = token.match(regex);
            return match === null || result.found ? result : {
                found: true, tokenType: 'templatedOperator', constructor, arity: parseInt(match[1])
            };
        }, {found: false});
        if (tokenType === 'operator') {
            stack.push(new constructor(...stack.splice(-arity)));
        } else if (tokenType === 'templatedOperator') {
            stack.push(new constructor(arity, ...stack.splice(-arity)));
        } else if (Variable.isName(token)) {
            stack.push(new Variable(token));
        } else {
            stack.push(new Const(parseFloat(token)));
        }
        return stack;
    }, [])[0];
}
