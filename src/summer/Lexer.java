package summer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hua Ruyi
 * @version 1.0
 * @date 2021/6/29 16:31
 */
public class Lexer {
    /**
     * 整型字面量： [0-9]+  从0~9中取出一到多个数字，构成一个整型字面量
     * 字符串字面量："(\\"|\\\\|\\n|[^"])*"  这是一个"(pat)*"形式的模式，双引号内是一个与pat重复出现至少0次的结果匹配的字符串
     *              其中模式pat与\"、\\、\\n或除"之外任意一个字符匹配
     * 标识符：[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\|\||\p{Punct} 至少一个字母、数字或下划线，且首字符不能为数字
     *        \p{Punct}表示与任意一个符号字符匹配    该表达式也会匹配 ==  <=  >= && ||
     * \s*((//.*)|(pat1)|(pat2)|pat3)?：其中pat1是与整型字面量匹配的正则表达式，pat2与字符串字面量匹配，pat3则与标识符匹配。
     *                                  起始的\s与空字符匹配，\s*与0到多个空字符匹配。模式//.*匹配由//开始的任意长度的字符串，用于匹配代码注释
     *                                  因此上述正则表达式能匹配任意个空白符以及连在其后的注释、整型字面量、字符串字面量或标识符。又因为它以?结尾
     *                                  ，所以仅由任意多个空白符组成的字符串也能与该模式匹配。
     * 匹配时只需要判断检查哪一个括号对应的不是null，就能知道行首出现的是哪种类型的单词（token）。之后再继续用正则表达式匹配剩余部分，
     *      就能得到下一个单词。不断重复该过程，词法分析器就能获得有源代码分割而得的所有单词。
     */
    public static String regexPat = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
            + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
    private Pattern pattern = Pattern.compile(regexPat);
    private ArrayList<Token> queue = new ArrayList<>();
    private boolean hasMore;
    private LineNumberReader reader;

    public Lexer(Reader r) {
        this.hasMore = true;
        this.reader = new LineNumberReader(r);
    }

    /**
     * 从源代码头部开始逐一获取单词。
     * @return 调用read()时将返回一个新的单词（token）。如果所有单词都已读取，read()方法将返回Token.EOF，表示程序结束。
     * @throws ParseException 解析异常
     */
    public Token read() throws ParseException {
        if (fillQueue(0)) {
            return queue.remove(0);
        } else {
            return Token.EOF;
        }
    }

    /**
     * 用于预读取。通过调用peek()方法，词法分析器就能事先知道在调用read()方法时将会获得什么单词（token），
     * 例如，peek(1)所返回的单词与调用read方法两次后返回的单词（token）相同。
     * @param i
     * @return peek(i)方法将返回read()方法即将返回的单词之后的第i个单词（token），如果参数i为0，则返回与read()方法相同的结果。
     *         如果所有单词都已读取，peek()方法将返回Token.EOF，表示程序结束。
     *
     * @throws ParseException
     */
    public Token peek(int i) throws ParseException {
        if (fillQueue(i)) {
            return queue.remove(i);
        } else {
            return Token.EOF;
        }
    }
    private boolean fillQueue(int i) throws ParseException {
        while (i >= queue.size()) {
            if (hasMore) {
                readLine();
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 从每一行中读取单词。由于正则表达式已经事先编译为Pattern对象，因此能调用matcher方法来获得一个用于实际检查匹配的Matcher对象。
     * 词法分析器一边通过region方法限定该对象检查匹配的范围，一边通过lookingAt方法在检查范围内进行正则表达式匹配。之后，词法分析器
     * 将使用group方法来获取与各个括号对应的子字符串。end方法用于获取匹配部分的结束位置，词法分析器将从那里开始继续执行下一次
     * lookingAt方法调用，直到该代码行中不再含有单词。
     * @throws ParseException
     */
    private void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw  new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        int lineNo = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                addToken(lineNo, matcher);
                pos = matcher.end();
            } else {
                throw new ParseException("bad token at line " + lineNo);
            }
        }
        queue.add(new IdToken(lineNo, Token.EOL));
    }
    protected void addToken(int lineNo, Matcher matcher) {
        String m = matcher.group(1);
        //if not a place
        if (m != null) {
            //if not a comment
            if (matcher.group(2) == null) {
                Token token;
                if (matcher.group(3) != null) {
                    token = new NumToken(lineNo, Integer.parseInt(m));
                } else if(matcher.group(4) != null) {
                    token = new StrToken(lineNo, toStringLiteral(m));
                } else {
                    token = new IdToken(lineNo, m);
                }
                queue.add(token);
            }
        }
    }
    protected String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\') {
                    c = s.charAt(++i);
                } else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
    protected static class NumToken extends Token {
        private int value;

        protected NumToken(int line, int v) {
            super(line);
            this.value = v;
        }

        @Override
        public boolean isNumber() {
            return true;
        }
        @Override
        public String getText() {
            return Integer.toString(value);
        }
        @Override
        public int getNumber() {
            return value;
        }
    }
    public static class IdToken extends Token {
        private String text;

        protected IdToken(int line, String id) {
            super(line);
            this.text = id;
        }
        @Override
        public boolean isIdentifier() {
            return true;
        }
        @Override
        public String getText() {
            return text;
        }
    }
    protected static class StrToken extends Token {
        private String literal;

        protected StrToken(int line, String str) {
            super(line);
            this.literal = str;
        }
        @Override
        public boolean isString() {
            return true;
        }
        @Override
        public String getText() {
            return literal;
        }
    }
}
