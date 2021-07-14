package summer.ast;

import java.util.List;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 17:23
 */
public class PrimaryExpr extends ASTList{
    public PrimaryExpr(List<ASTree> list) {
        super(list);
    }
    public static ASTree create(List<ASTree> list) {
        return list.size() == 1 ? list.get(0) : new PrimaryExpr(list);
    }
}
