package summer.ast;

import java.util.List;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 17:25
 */
public class NegativeExpr extends ASTList{
    public NegativeExpr(List<ASTree> list) {
        super(list);
    }
    public ASTree operand() {
        return child(0);
    }

    @Override
    public String toString() {
        return "-" + operand();
    }
}
