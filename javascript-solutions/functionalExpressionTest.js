include("functionalExpression.js")

// ((x * y) - (2 * z)) + var1
let expression = add(
    subtract(
        multiply(
            variable("x"),
            variable("y"),
        ),
        multiply(
            cnst(2),
            variable("z")
        )
    ),
    variable("var1")
)

for (let x = 0; x <= 10; x++) {
    println(expression(x, 1, 0, 2 * x))
}
