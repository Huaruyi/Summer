package summer;

import javax.swing.text.MaskFormatter;
import java.io.IOException;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/6/29 17:11
 */
public class ParseException extends Exception{
    public ParseException(Token t) {
        this("", t);
    }
    public ParseException(String msg, Token t) {
        super("syntax error around " + location(t) + ". " + msg);
    }
    private static String location(Token t) {
        if (t == Token.EOF) {
            return "the last line";
        } else {
            return "\"" + t.getText() + "\" at line " + t.getLineNumber();
        }
    }
    public ParseException(IOException e) {
        super(e);
    }
    public ParseException(String msg) {
        super(msg);
    }
}
