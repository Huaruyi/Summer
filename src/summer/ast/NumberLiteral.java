package summer.ast;

import summer.Token;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 13:27
 */
public class NumberLiteral extends ASTLeaf{
    public NumberLiteral(Token t) {
        super(t);
    }
    public int value() {
        return token().getNumber();
    }
}
