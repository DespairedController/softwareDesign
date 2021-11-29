package org.softwareDesign.hw6.visitor

import org.softwareDesign.hw6.token.BraceToken
import org.softwareDesign.hw6.token.NumberToken
import org.softwareDesign.hw6.token.OperationToken
import org.softwareDesign.hw6.token.Token

class ParserVisitor : TokenVisitor {
    private val stack = mutableListOf<Token>()
    private val result = mutableListOf<Token>()

    override fun visit(token: NumberToken) {
        result.add(token)
    }

    override fun visit(token: BraceToken) {
        when (token) {
            BraceToken.LEFT -> stack.add(token)
            BraceToken.RIGHT -> {
                while (stack.isNotEmpty() && stack.last() != BraceToken.LEFT) {
                    result.add(stack.removeLast())
                }
                if (stack.isEmpty()) {
                    throw IllegalArgumentException("Wrong brackets balance: no opening brace.")
                }
                stack.removeLast()
            }
        }
    }

    override fun visit(token: OperationToken) {
        while (stack.isNotEmpty()) {
            val top = stack.last()
            if (top !is OperationToken) break
            if (top.priority > token.priority) {
                result.add(stack.removeLast())
            } else break
        }
        stack.add(token)
    }

    fun getResult() : List<Token> {
        while (stack.isNotEmpty()) {
            result.add(stack.removeLast())
        }
        return result
    }
}
