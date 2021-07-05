package summer.ast;

import summer.Token;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 13:29
 */
public class Name extends ASTLeaf{
    public Name(Token t) {
        super(t);
    }
    public String name() {
        return token().getText();
    }
}
