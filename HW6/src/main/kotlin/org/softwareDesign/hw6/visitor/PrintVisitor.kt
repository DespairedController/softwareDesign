package org.softwareDesign.hw6.visitor

import org.softwareDesign.hw6.token.BraceToken
import org.softwareDesign.hw6.token.NumberToken
import org.softwareDesign.hw6.token.OperationToken
import java.io.PrintStream

class PrintVisitor(private val outputStream: PrintStream = System.out) : TokenVisitor {
    override fun visit(token: NumberToken) {
        outputStream.print("NUMBER(${token.value}) ")
    }

    override fun visit(token: BraceToken) {
        outputStream.print(if (token == BraceToken.LEFT) "(" else ")")
    }

    override fun visit(token: OperationToken) {
        outputStream.print("$token ")
    }
}