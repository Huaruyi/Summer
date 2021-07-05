package summer.ast;

import java.util.Iterator;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/6/29 16:28
 */
public abstract class ASTree implements Iterable<ASTree> {
    public abstract ASTree child(int i);
    public abstract int numChildren();
    public abstract Iterator<ASTree> children();
    public abstract String location();
    @Override
    public Iterator<ASTree> iterator() {
        return children();
    }
}
