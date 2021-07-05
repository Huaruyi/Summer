package summer.ast;

import java.util.List;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 13:30
 */
public class BinaryExpr extends ASTList{
    public BinaryExpr(List<ASTree> list) {
        super(list);
    }
    public ASTree left() {
        return child(0);
    }
    public String operator() {
        return ((ASTLeaf) child(1)).token().getText();
    }
    public ASTree right() {
        return child(2);
    }
}
