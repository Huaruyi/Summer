package summer.ast;

import summer.Token;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 17:33
 */
public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) {
        super(t);
    }
    public String value() {
        return token().getText();
    }
}
