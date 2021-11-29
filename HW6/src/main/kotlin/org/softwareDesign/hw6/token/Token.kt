package org.softwareDesign.hw6.token

import org.softwareDesign.hw6.visitor.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}

enum class OperationToken(val priority: Int) : Token {
    PLUS(0),
    MINUS(0),
    MUL(1),
    DIV(1);

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class NumberToken(val value: Int) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

enum class BraceToken : Token {
    LEFT,
    RIGHT;

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}
