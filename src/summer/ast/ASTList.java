package summer.ast;

import java.util.Iterator;
import java.util.List;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 13:18
 */
public class ASTList extends ASTree{
    protected List<ASTree> children;

    public ASTList(List<ASTree> list) {
        this.children = list;
    }

    @Override
    public ASTree child(int i) {
        return children.get(i);
    }

    @Override
    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<ASTree> children() {
        return children.iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        String sep = "";
        for (ASTree t : children) {
            builder.append(sep);
            sep = " ";
            builder.append(t.toString());
        }
        return builder.append(')').toString();
    }

    @Override
    public String location() {
        for (ASTree t : children) {
            String s = t.location();
            if (s != null) {
                return s;
            }
        }
        return null;
    }
}
