package org.softwareDesign.hw6.visitor

import org.softwareDesign.hw6.token.BraceToken
import org.softwareDesign.hw6.token.NumberToken
import org.softwareDesign.hw6.token.OperationToken

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: BraceToken)
    fun visit(token: OperationToken)
}
