package org.softwareDesign.hw6.visitor

import org.softwareDesign.hw6.token.BraceToken
import org.softwareDesign.hw6.token.NumberToken
import org.softwareDesign.hw6.token.OperationToken

class CalcVisitor : TokenVisitor {
    private val stack = mutableListOf<Int>()

    override fun visit(token: NumberToken) {
        stack.add(token.value)
    }

    override fun visit(token: BraceToken) {
        throw IllegalArgumentException("Not a RPN Token given")
    }

    override fun visit(token: OperationToken) {
        val l = getOperand()
        val r = getOperand()

        when (token) {
            OperationToken.PLUS -> stack.add(l + r)
            OperationToken.MINUS -> stack.add(l - r)
            OperationToken.MUL -> stack.add(l * r)
            OperationToken.DIV -> stack.add(l / r)
        }
    }

    private fun getOperand(): Int {
        return stack.removeLastOrNull() ?: throw IllegalArgumentException("Not enough arguments for this operation")
    }

    fun getResult(): Int = stack.last()
}