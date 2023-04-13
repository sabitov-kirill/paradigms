function ParseError(message, parseSource) {
    this.message = (`${message} in expression\n\t${parseSource}`);
    this.name = "ParseError";
}

class UnderlinedParseError extends ParseError {
    constructor(message, {start, end}, parseSource) {
        super(message, parseSource + `\n\t${" ".repeat(start)}^${"~".repeat(end - start - 1)}`);
        this.name = "UnderlinedParseError";
    }
}

class WrongArityParseError extends UnderlinedParseError {
    constructor(ExpressionConstructor, argumentsStack, parseSource) {
        super(`Wrong number of arguments` +
            `(expected ${ExpressionConstructor.prototype.f.length}, got ${argumentsStack.length}) ` +
            `of operator "${ExpressionConstructor.prototype.sign}"`,
            { start: argumentsStack[0].start, end: argumentsStack[argumentsStack.length - 1].end },
            parseSource
        );
        this.name = "WrongArityParseError";
    }
}

class UnexpectedTokenParseError extends UnderlinedParseError {
    constructor(expectedValue, gotToken, source) {
        super(`Expected "${expectedValue}", got "${gotToken.value}"`, gotToken, source);
        this.name = "UnexpectedTokenParseError";
    }
}
