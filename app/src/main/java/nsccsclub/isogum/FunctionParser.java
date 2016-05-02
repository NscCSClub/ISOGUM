package nsccsclub.isogum;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by csconway on 4/30/2016.
 */
public class FunctionParser {

    private String function;
    private  int idx;
    private ArrayList<Token> tokens;
    public final String LOG_CODE = "FunctionParser";

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
         this.setIdx(idx + 1);
         return token;
    }

    public  boolean hasNext(){
        return (idx < tokens.size());
    }

    private ArrayList<Token> getTokens(){
        ArrayList<Token> list = new ArrayList<Token>();
        int idx = 0, temp = 0;
        char ch;
        while (idx < getFunction().length()){

            ch = getFunction().charAt(idx);
            Log.d(LOG_CODE, "loop idx :" + idx);
            Log.d(LOG_CODE, "loop char :" + ch);
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
                Log.d(LOG_CODE, "initial number : " + ch);
                boolean flag = false;
                if (idx < getFunction().length()-1) {
                    idx++;
                    ch = getFunction().charAt(idx);
                    Log.d(LOG_CODE, "2nd char : " +ch);
                    flag = true;
                }
                while ((isNumber(ch)||ch == '.')&& idx < getFunction().length()-1){
                    idx++;
                    ch = getFunction().charAt(idx);

                    Log.d(LOG_CODE, "end number char : " + ch);
                }


                if (getFunction().substring(temp, idx).compareTo("") != 0) {
                    Log.d(LOG_CODE, "adding number : " +getFunction().substring(temp, idx));
                    list.add(new Token(Type.NUMBER,
                            Double.parseDouble(getFunction().substring(temp, idx))));
                }
                if (!flag){
                    idx++;
                }
            }
            else if (ch =='['){
                Log.d(LOG_CODE,"Potential variable!");
                temp = idx;
                if(idx< getFunction().length()-1){
                    Log.d(LOG_CODE,"1st char of variable should always excecute");
                    idx++;
                    ch = getFunction().charAt(idx);
                }
                else {
                    Log.d(LOG_CODE,"empty variable");
                    list.add(new Token(Type.UNDEFINED, getFunction().substring(temp, idx)));
                    idx++;
                }
                while(ch != ']' && idx < getFunction().length()-1){
                    Log.d(LOG_CODE,"variable name letter : " +ch);
                    idx ++;
                    ch = getFunction().charAt(idx);
                }
                idx++;
                Log.d(LOG_CODE, "ending character ; " + ch);
                if(ch == ']'){
                    list.add(new Token(Type.VARIABLE, getFunction().substring(temp, idx)));
                }
                else {
                    Log.d(LOG_CODE,"invalid ending char");
                    list.add(new Token(Type.UNDEFINED, getFunction().substring(temp, idx )));
                }

            }
            else if (isStartFunction(ch)){
                if (isDefinedFunction(function,idx)){
                    String string = whatString(whatType(function, idx));
                    Token token = new Token(whatType(function,idx),string);
                    Log.d(LOG_CODE, "Added function! type: " + token.getType().toString() + " value : " + token.getValue().toString());
                    list.add(token);
                    idx += string.length();
                }
                else {
                    list.add(new Token(Type.UNDEFINED, ""));
                    idx++;
                }



            }
            else {
                list.add(new Token(Type.UNDEFINED, ch));
                idx++;
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
        if (idx+1 < function.length()){
            test = function.substring(idx,idx+2);
            Log.d(LOG_CODE,"type test length 2 " + test);
            if(test.compareTo("ln")==0){
                Log.d(LOG_CODE,"it worked" + Type.LN.toString());
                return Type.LN;
            }
        }
        if (idx+2 < function.length()){
            test = function.substring(idx,idx+3);
            Log.d(LOG_CODE,"Type test length 3 " + test);
            if (test.compareTo("sin") == 0) {
                Log.d(LOG_CODE,"it worked sin");
                return Type.SIN;
            } else if (test.compareTo("cos") == 0) {
                Log.d(LOG_CODE,"it worked cos");
                return Type.COS;
            } else if (test.compareTo("tan") == 0) {
                Log.d(LOG_CODE,"it worked tan");
                return Type.TAN;
            } else if (test.compareTo("log") == 0) {
                Log.d(LOG_CODE,"it worked log");
                return Type.LOG;
            }
        }
        if (idx+3 < function.length()){
            test = function.substring(idx,idx+4);
            Log.d(LOG_CODE,"type test length 4 " + test);
            if (test.compareTo("aSin") == 0) {
                Log.d(LOG_CODE,"it worked aSin");
                return Type.ASIN;
            } else if (test.compareTo("aCos") == 0) {
                Log.d(LOG_CODE,"it worked aCos");
                return Type.ACOS;
            } else if (test.compareTo("aTan") == 0) {
                Log.d(LOG_CODE,"it worked aTan");
                return Type.ATAN;
            }
        }
        Log.d(LOG_CODE, "type test failed");
        return Type.UNDEFINED;
    }

    private boolean isDefinedFunction(String function, int idx) {

        String test;
        if (idx+1 < function.length()){
            test = function.substring(idx,idx+2);
            Log.d(LOG_CODE,"testing function length 2: " + test);
            if(test.compareTo("ln")==0){
                Log.d(LOG_CODE,"it worked : " + test);
                return true;
            }
        }
        if (idx+2 < function.length()){
            test = function.substring(idx,idx+3);
            Log.d(LOG_CODE,"testing function length 3 : " + test);
            if(test.compareTo("sin")==0||test.compareTo("cos")==0||test.compareTo("tan")==0
                    ||test.compareTo("log")==0){
                Log.d(LOG_CODE,"it worked : " + test);
                return true;
            }
        }
        if (idx+3 < function.length()){
            test = function.substring(idx,idx+4);
            Log.d(LOG_CODE,"testing function length 4 " + test);
            if(test.compareTo("aSin")==0||test.compareTo("aCos")==0||test.compareTo("aTan")==0){
                Log.d(LOG_CODE,"it worked : " + test);
                return true;
            }
        }

        return false;
    }

    private boolean isStartFunction(char ch) {
        Log.d(LOG_CODE,"checking if start of function :" + ch);
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
        if(idx >=-1 && idx < function.length()) {
            this.idx = idx;
        }
    }

    public boolean isValid(){
        Token token=null;
        boolean test = true;
        this.setIdx(0);
        Log.d(LOG_CODE, "validity: ");


        while (hasNext()){
            Log.d(LOG_CODE,"idx : " + idx);

            token = getNext();
            if(idx ==0 &&(token.getType()==Type.OPERATOR||token.getType()==Type.RIGHT_PAREN)){
                return false;
            }
            Log.d(LOG_CODE, "testing valididty " + token.toString());
            Log.d(LOG_CODE,"testing next type : "  + getNextType().toString());
            if(isFunction(token.getType())){
                Log.d(LOG_CODE,"testing function next type : "  + getNextType().toString());

                test = test && getNextType() == Type.LEFT_PAREN;

            }
            else if (token.getType()==Type.NUMBER ||token.getType()==Type.VARIABLE){
                Log.d(LOG_CODE,"testing number next type : "  + getNextType().toString());
                Log.d(LOG_CODE,"idx come on: " + idx );
                     test = test && (getNextType()==Type.OPERATOR || getNextType()==Type.RIGHT_PAREN
                     ||getNextType() == Type.NONE);
                Log.d(LOG_CODE,"idx come on: " + idx );
            }
            else if (token.getType()==Type.OPERATOR || token.getType() == Type.LEFT_PAREN){
                Log.d(LOG_CODE,"testing operateor or left paren next type : "  + getNextType().toString());
                test = test && (getNextType()==Type.LEFT_PAREN|| getNextType() == Type.NUMBER ||
                        getNextType()== Type.VARIABLE || isFunction(getNextType()));
            }
            else if ( token.getType() == Type.RIGHT_PAREN){
                Log.d(LOG_CODE,"testing right paren next type : "  + getNextType().toString());
                test = test &&(getNextType()==Type.RIGHT_PAREN||
                        getNextType()== Type.OPERATOR || getNextType() == Type.NONE);
            }
            else if ( token.getType() == Type.LEFT_PAREN){
                Log.d(LOG_CODE,"testing left paren next type : "  + getNextType().toString());
                test = test &&(getNextType()==Type.LEFT_PAREN|| getNextType() == Type.NUMBER||
                        getNextType()== Type.VARIABLE || isFunction(getNextType()) );
            }
            else {
                test = false;
            }
            if(!test){


                return test;
            }
        }

        return test;
    }

    private boolean isFunction(Type type) {
        if (type == Type.LN || type == Type.LOG || type == Type.SIN || type == Type.COS ||
                type == Type.TAN || type == Type.ASIN || type == Type.ACOS || type == Type.ATAN){
            return true;
        }
        return false;
    }

    private Type getNextType(){
        if (idx < tokens.size()-1){
            return tokens.get(idx ).getType();
        }
        else {
            return Type.NONE;
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
        LOG, LN, LEFT_PAREN, RIGHT_PAREN, UNDEFINED, NONE;

    }
}
