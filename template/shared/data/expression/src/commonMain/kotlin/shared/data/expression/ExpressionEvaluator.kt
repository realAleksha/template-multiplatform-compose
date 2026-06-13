package shared.data.expression

import shared.data.common.DataSource

/**
 * Interface for evaluating mathematical and logical expressions.
 * Provides functionality to evaluate expressions as double or boolean values.
 */
interface ExpressionEvaluator : DataSource {

    /**
     * Evaluates a mathematical expression and returns the result as a double.
     *
     * @param expression The mathematical expression to evaluate.
     * @param args A map of variable names to values that can be referenced in the expression.
     * @return The result of the expression evaluation as a double.
     */
    fun evalDouble(expression: String, args: Map<String, Any> = emptyMap()): Double

    /**
     * Evaluates a logical expression and returns the result as a boolean.
     *
     * @param expression The logical expression to evaluate.
     * @param args A map of variable names to values that can be referenced in the expression.
     * @return The result of the expression evaluation as a boolean.
     */
    fun evalBoolean(expression: String, args: Map<String, Any> = emptyMap()): Boolean
}
