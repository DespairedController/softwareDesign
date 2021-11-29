package org.softwareDesign.hw6.tokenizer

import org.softwareDesign.hw6.token.BraceToken
import org.softwareDesign.hw6.token.OperationToken
import org.softwareDesign.hw6.token.Token


class Tokenizer {
    private var state: TokenizerState = TokenizerStateStart()
    private val tokens = mutableListOf<Token>()

    fun consume(c: Char) {
        when (val currentState = state) {
            is TokenizerStateStart -> {
                if (c.isDigit()) {
                    state = TokenizerStateNumber(c)
                    return
                } else if (c.isWhitespace()) {
                    return
                }
                when (c) {
                    '(' -> tokens.add(BraceToken.LEFT)
                    ')' -> tokens.add(BraceToken.RIGHT)
                    '+' -> tokens.add(OperationToken.PLUS)
                    '-' -> tokens.add(OperationToken.MINUS)
                    '*' -> tokens.add(OperationToken.MUL)
                    '/' -> tokens.add(OperationToken.DIV)
                    Char.MAX_VALUE -> state = TokenizerStateEOF()
                    else -> {
                        state = TokenizerStateError()
                        consume(c)
                    }
                }
            }
            is TokenizerStateNumber -> {
                if (c.isDigit()) {
                    currentState.consume(c)
                    return
                }
                tokens.add(currentState.createToken())
                state = TokenizerStateStart()
                consume(c)
            }
            is TokenizerStateError -> {
                throwIllegalState("Error")
            }
            is TokenizerStateEOF -> {
                throwIllegalState("EOF")
            }
        }
    }

    fun getTokens() = tokens

    private fun throwIllegalState(state: String) {
        throw IllegalStateException("Tokenizer in $state state. No more char consumption possible.")
    }
}