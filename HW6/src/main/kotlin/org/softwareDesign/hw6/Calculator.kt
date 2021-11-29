package org.softwareDesign.hw6

import org.softwareDesign.hw6.token.Token
import org.softwareDesign.hw6.tokenizer.Tokenizer
import org.softwareDesign.hw6.visitor.CalcVisitor
import org.softwareDesign.hw6.visitor.ParserVisitor
import org.softwareDesign.hw6.visitor.PrintVisitor
import org.softwareDesign.hw6.visitor.TokenVisitor
import java.io.IOException
import java.io.InputStream

class Calculator(private val inStream: InputStream = System.`in`) {
    fun run() {
        val tokens = tokenize()
        printTokens(tokens)
        println()

        val parserVisitor = ParserVisitor()
        visitTokens(tokens, parserVisitor)
        val rpnTokens = parserVisitor.getResult()
        printTokens(rpnTokens)
        println()

        val calcVisitor = CalcVisitor()
        visitTokens(rpnTokens, calcVisitor)
        println("Result: ${calcVisitor.getResult()}")
        println()
    }

    private fun printTokens(tokens: List<Token>) = visitTokens(tokens, PrintVisitor())

    private fun visitTokens(tokens: List<Token>, visitor: TokenVisitor) {
        tokens.forEach { it.accept(visitor) }
    }

    private fun tokenize(): List<Token> {
        val tokenizer = Tokenizer()
        try {
            var next = 0
            while (next != -1) {
                next = inStream.read()
                tokenizer.consume(next.toChar())
            }
        } catch (e: IOException) {
            throw e
        }
        return tokenizer.getTokens()
    }
}

fun main() {
    val app = Calculator()
    app.run()
}