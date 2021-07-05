package test;

import summer.*;
import summer.ast.ASTree;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 17:36
 */
public class ParserRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        BasicParser bp = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            ASTree ast = bp.parse(l);
            System.out.println("=> " + ast.toString());
        }
    }
}
