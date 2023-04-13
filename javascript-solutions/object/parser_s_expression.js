const parsePrefix = (source) => {
    source = source
        .replace(/[()]/g, bracket => " " + bracket + " ")
        .replace(/\s+/g, " ")
        .trim();
    const tokens = source.split(" ");
    const sourceLength = tokens.reduce((start, value, i) => {
        tokens[i] = { value, start, end: start + value.length };
        return tokens[i].end + 1;
    }, 0);
    tokens.push({ value: "EOF", start: sourceLength - 1, end: sourceLength, isLast: true });

    let tokenIndex = 0;
    const take = () => tokens[tokenIndex++];
    const expect = (expected, isUnexpected) => {
        const token = take();
        if (isUnexpected ? isUnexpected(token) : token.value !== expected) {
            throw new UnexpectedTokenParseError(expected, token, source);
        }
        return token;
    }

    const takeExpression = (isOperatorExpected) => {
        let currentToken;
        const argumentsStack = [];
        const isOperator = isOperatorExpected ?? (currentToken = take()).value === "(";

        const isExpressionEnd = () => {
            if (isOperator) {
                currentToken = expect(")", (token) => token.isLast);
                return currentToken.value === ")";
            }

            currentToken = take();
            return currentToken.isLast;
        }

        const tokenToLexeme = (token) => {
            const { value } = token;
            if (Variable.isName(value)) {
                return new Variable(value);
            } else if (Const.isNumber(value)) {
                return new Const(parseFloat(value));
            }
            throw new UnexpectedTokenParseError(
                "Variable name | Number", token, source
            );
        }

        const tokenToOperation = (token) => {
            const { value } = token;
            if (value in operators) {
                return operators[value];
            }
            throw new UnexpectedTokenParseError(
                "Operator sign", token, source
            );
        }

        if (!isOperator) {
            const firstToken = currentToken;
            if (!isExpressionEnd()) {
                throw new ParseError("Too much expression without operator call", source);
            }
            return tokenToLexeme(firstToken);
        }

        const ExpressionConstructor = tokenToOperation(take());
        while (!isExpressionEnd()) {
            argumentsStack.push(currentToken.value === "("
                ? takeExpression(true)
                : tokenToLexeme(currentToken)
            );
            argumentsStack[argumentsStack.length - 1].start = currentToken.start;
            argumentsStack[argumentsStack.length - 1].end = currentToken.end;
        }

        if (!ExpressionConstructor.isAnyArity && argumentsStack.length !== ExpressionConstructor.prototype.f.length) {
            if (argumentsStack.length === 0) {
                argumentsStack.push(currentToken);
            }
            throw new WrongArityParseError(ExpressionConstructor, argumentsStack, source);
        }

        return new ExpressionConstructor(...argumentsStack);
    }

    const result = takeExpression();
    expect("EOF", (token) => tokenIndex < tokens.length && !token.isLast);
    return result
}