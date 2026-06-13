package shared.data.expression

import kotlin.math.roundToInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BasicExpressionEvaluatorTest {

    private val expressionSource = BasicExpressionEvaluator()

    @Test
    fun `WHEN evaluate simple expression THEN return correct result`() {
        assertFalse(expressionSource.evalBoolean("1 == 2"))
        assertTrue(expressionSource.evalBoolean("1 == 1"))
        assertFalse(expressionSource.evalBoolean("!(1 == 1)"))
    }

    @Test
    fun `WHEN evaluate boolean expression THEN return correct result`() {
        val valueExp = "23 + 0.123 * (124 / -60.124)"

        val value = expressionSource.evalDouble(valueExp)
        assertEquals(23, value.roundToInt())

        assertFalse(expressionSource.evalBoolean("$valueExp > 100"))
        assertTrue(expressionSource.evalBoolean("($valueExp) < 23 && ($valueExp) > 22"))
    }
}