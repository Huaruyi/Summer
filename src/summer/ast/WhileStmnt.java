package summer.ast;

import java.util.List;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 17:30
 */
public class WhileStmnt extends ASTList {
    public WhileStmnt(List<ASTree> list) {
        super(list);
    }

    public ASTree condition() {
        return child(0);
    }
    public ASTree body() {
        return child(1);
    }

    @Override
    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }
}
