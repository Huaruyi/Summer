package summer.ast;

import summer.Token;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/7/2 11:22
 */
public class ASTLeaf extends ASTree{
    private static ArrayList<ASTree> empty = new ArrayList<>();
    protected Token token;
    public ASTLeaf(Token t) {
        this.token = t;
    }
    @Override
    public ASTree child(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int numChildren() {
        return 0;
    }

    @Override
    public Iterator<ASTree> children() {
        return empty.iterator();
    }

    @Override
    public String location() {
        return "at line " + token.getLineNumber();
    }

    @Override
    public String toString() {
        return token.getText();
    }

    public Token token() {
        return token;
    }
}
