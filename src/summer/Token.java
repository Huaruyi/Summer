package summer;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/6/29 16:18
 */
public abstract class Token {
    /**
     * end of file
     */
    public static final Token EOF = new Token(-1) {};
    /**
     * end of line
     */
    public static final String EOL = "\\n";
    /**
     * 行号
     */
    private int lineNumber;


    protected Token(int line) {
        this.lineNumber = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * 是否为标识符，即变量名、函数名、类名、运算符、括号等标点符号和保留字
     * @return
     */
    public boolean isIdentifier() {
        return false;
    }

    /**
     * 是否为整数字面量
     * @return
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * 是否为字符串字面量
     * @return
     */
    public boolean isString() {
        return false;
    }
    public int getNumber() {
        throw new SummerException("not number token");
    }
    public String getText() {
        return "";
    }
}
