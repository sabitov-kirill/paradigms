include("functionalExpression.js")

let expression = add(
    subtract(
        multiply(
            variable("x"),
            variable("x"),
        ),
        multiply(
            cnst(2),
            variable("x")
        )
    ),
    cnst(1)
)

for (let x = 0; x <= 10; x++) {
    println(expression(x, 0, 0))
}
