package nsccsclub.isogum;

import java.util.ArrayList;

/**
 * Created by csconway on 4/30/2016.
 */
public class FunctionParser {

    private String function;
    private  int idx;
    private ArrayList<Token> tokens;

    /**
     * Default constructor function needs to be set
     */
    public FunctionParser(){
        this.setFunction("");
        this.setIdx(0);
        this.tokens = null;
    }

    /**
     * Constructs a parser with a given function to parse
     */
    public FunctionParser(String function){
        this.setFunction(function);
    }

    public Token getNext(){
        Token token = tokens.get(idx);
        this.setIdx(idx+1);
        return token;
    }

    public Token getPrev(){
        this.setIdx(idx-1);
        Token token = tokens.get(idx);
        return token;
    }

    public  boolean hasNext(){
        return idx == function.length();
    }

    private ArrayList<Token> getTokens(){
        ArrayList<Token> list = new ArrayList<Token>();
        int idx = 0, temp = 0;
        char ch;
        while (idx < getFunction().length()){
            ch = getFunction().charAt(idx);
            if (ch =='('){
                list.add(new Token(Type.LEFT_PAREN,ch));
                idx++;
            }
            else if (ch == ')'){
                list.add(new Token(Type.RIGHT_PAREN,ch));
                idx++;
            }
            else if (isOperator(ch)){
                list.add(new Token(Type.OPERATOR,ch));
                idx++;
            }
            else if (isNumber(ch)){
                temp = idx;
                if (idx < getFunction().length()-1) {
                    idx++;
                    ch = getFunction().charAt(idx);
                }
                while ((isNumber(ch)||ch == '.')&& idx < getFunction().length()-1){
                    idx++;
                    ch = getFunction().charAt(idx);
                }
                list.add(new Token(Type.NUMBER,
                        Double.parseDouble(getFunction().substring(temp, idx))));
                idx++;
            }
            else if (ch =='['){
                temp = idx;
                if(idx< getFunction().length()-1){
                    idx++;
                    ch = getFunction().charAt(idx);
                }
                while(ch != ']' && idx < getFunction().length()-1){
                    idx ++;
                    ch = getFunction().charAt(idx);
                }
                idx++;
                if (idx < getFunction().length()){
                    if(ch == ']'){
                        list.add(new Token(Type.VARIABLE, getFunction().substring(temp, idx + 1)));
                    }
                    else {
                        list.add(new Token(Type.UNDEFINED, getFunction().substring(temp, idx + 1)));
                    }
                }
                else {
                    list.add(new Token(Type.UNDEFINED, getFunction().substring(temp, idx)));
                }

            }
            else if (isStartFunction(ch)){
                if (isDefinedFunction(function,idx)){
                    list.add(new Token(whatType(function,idx),whatString(whatType(function, idx))));
                }
                else {
                    list.add(new Token(Type.UNDEFINED, ""));
                }

                idx++;

            }
            else {
                list.add(new Token(Type.UNDEFINED, ch));
            }
        }


        return list;
    }

    private String whatString(Type type) {
        String function;
        if (type == Type.SIN){
            function = "sin";
        }
        else if (type == Type.COS){
            function = "cos";
        }
        else if (type == Type.TAN){
            function = "tan";
        }
        else if (type == Type.ASIN){
            function = "aSin";
        }
        else if (type == Type.ACOS){
            function = "aCos";
        }
        else if (type == Type.ATAN){
            function = "aTan";
        }
        else if (type == Type.LOG){
            function = "log";
        }
        else if (type == Type.LN){
            function = "ln";
        }
        else {
            function = "unf";
        }
        return function;
    }

    private Type whatType(String function, int idx) {

        String test;
        if (idx+2 < function.length()){
            test = function.substring(idx,idx+3);
            //Log.d(LOG_CODE,"substring implied multiply length 2 " + test);
            if(test.compareTo("ln")==0){
//                Log.d(LOG_CODE,"it worked");
                return Type.LN;
            }
        }
        if (idx+3 < function.length()){
            test = function.substring(idx,idx+4);
//            Log.d(LOG_CODE,"substring implied multiply length 3 " + test);
            if (test.compareTo("sin") == 0) {
//                Log.d(LOG_CODE,"it worked");
                return Type.SIN;
            } else if (test.compareTo("cos") == 0) {
//                Log.d(LOG_CODE,"it worked");
                return Type.COS;
            } else if (test.compareTo("tan") == 0) {
//                Log.d(LOG_CODE,"it worked");
                return Type.TAN;
            } else if (test.compareTo("log") == 0) {
//                Log.d(LOG_CODE,"it worked");
                return Type.LOG;
            }
        }
        if (idx+4 < function.length()){
            test = function.substring(idx,idx+5);
//            Log.d(LOG_CODE,"substring implied multiply length 4 " + test);
            if (test.compareTo("aSin") == 0) {
//                Log.d(LOG_CODE,"it worked");
                return Type.ASIN;
            } else if (test.compareTo("aCos") == 0) {
//                Log.d(LOG_CODE,"it worked");
                return Type.ACOS;
            } else if (test.compareTo("aTan") == 0) {
//                Log.d(LOG_CODE,"it worked");
                return Type.ATAN;
            }
        }
        return Type.UNDEFINED;
    }

    private boolean isDefinedFunction(String function, int idx) {

        String test;
        if (idx+2 < function.length()){
            test = function.substring(idx,idx+3);
            //Log.d(LOG_CODE,"substring implied multiply length 2 " + test);
            if(test.compareTo("ln")==0){
//                Log.d(LOG_CODE,"it worked");
                return true;
            }
        }
        if (idx+3 < function.length()){
            test = function.substring(idx,idx+4);
//            Log.d(LOG_CODE,"substring implied multiply length 3 " + test);
            if(test.compareTo("sin")==0||test.compareTo("cos")==0||test.compareTo("tan")==0
                    ||test.compareTo("log")==0){
//                Log.d(LOG_CODE,"it worked");
                return true;
            }
        }
        if (idx+4 < function.length()){
            test = function.substring(idx,idx+5);
//            Log.d(LOG_CODE,"substring implied multiply length 4 " + test);
            if(test.compareTo("aSin")==0||test.compareTo("aCos")==0||test.compareTo("aTan")==0){
//                Log.d(LOG_CODE,"it worked");
                return true;
            }
        }

        return false;
    }

    private boolean isStartFunction(char ch) {
        if (ch == 's' || ch == 'c' || ch == 't' || ch == 'a' || ch == 'l'){
            return true;
        }
        return false;
    }

    private boolean isNumber(char ch) {
        try {
            Integer.parseInt(""+ch);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean isOperator(char ch) {

        if (ch == '*' || ch == '/' || ch == '+' || ch == '-' || ch == '^'){
            return true;
        }

        return false;
    }

    /**
     * The function to parse.
     */
    private String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
        this.setIdx(0);
        this.tokens = getTokens();
    }

    private int getIdx() {
        return idx;
    }

    private void setIdx(int idx) {
        if(idx >=0 && idx < function.length()) {
            this.idx = idx;
        }
    }

    public class Token{
        private Type type;
        private Object value;

        /**
         * Construct a token object.
         * @param type The type of mathematical operator this is.
         * @param value The actual string representation;
         */
        public Token(Type type, Object value){
            this.setType(type);
            this.setValue(value);
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String toString(){
            return "Token: "+ type.toString() +", " + value.toString();
        }
    }

    public enum Type{
        VARIABLE, NUMBER, OPERATOR, SIN,COS,TAN,ASIN, ACOS, ATAN,
        LOG, LN, LEFT_PAREN, RIGHT_PAREN, UNDEFINED;

    }
}
