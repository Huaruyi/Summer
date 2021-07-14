package test;

import summer.*;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/6/30 14:25
 */
public class LexerRunner {
    public static void main(String[] args) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        for (Token token; (token = lexer.read()) != Token.EOF;) {
            System.out.println("=> " + token.getText());
        }
    }
}
