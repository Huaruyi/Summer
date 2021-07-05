package summer;

import summer.ast.ASTree;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/6/29 16:25
 */
public class SummerException  extends RuntimeException{
    public SummerException(String msg) {
        super(msg);
    }
    public SummerException(String msg, ASTree t) {
        super(msg + " " + t.location());
    }
}
