package shared.data.expression

import co.touchlab.stately.collections.ConcurrentMutableMap
import com.github.murzagalin.evaluator.Evaluator
import com.github.murzagalin.evaluator.ast.Expression

class BasicExpressionEvaluator : ExpressionEvaluator {

    private val evaluator = Evaluator()
    private val expressionCache = ConcurrentMutableMap<String, Expression>()

    override fun evalDouble(expression: String, args: Map<String, Any>): Double {
        val cached = expressionCache.computeIfAbsent(expression) { evaluator.preprocessExpression(expression) }
        return evaluator.evaluateDouble(cached, args)
    }

    override fun evalBoolean(expression: String, args: Map<String, Any>): Boolean {
        val cached = expressionCache.computeIfAbsent(expression) { evaluator.preprocessExpression(expression) }
        return evaluator.evaluateBoolean(cached, args)
    }
}