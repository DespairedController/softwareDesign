package org.softwareDesign.hw6.tokenizer

import org.softwareDesign.hw6.token.NumberToken
import org.softwareDesign.hw6.token.Token

interface TokenizerState

class TokenizerStateStart : TokenizerState

class TokenizerStateNumber(c: Char) : TokenizerState {
    var number = "$c"

    fun consume(c: Char) {
        number += c
    }

    fun createToken(): Token {
        return NumberToken(number.toInt())
    }
}

class TokenizerStateError : TokenizerState

class TokenizerStateEOF : TokenizerState
