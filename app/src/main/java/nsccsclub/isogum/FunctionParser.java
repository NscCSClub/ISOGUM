package nsccsclub.isogum;

import android.util.Log;

import java.util.ArrayList;

/**
 * Parser to transform raw string input into acceptable form for storing in the database, contains
 * methods to check for valid string input.
 * Created by csconway on 4/30/2016.
 */
public class FunctionParser {

    /**
     * raw string function to parse
     */
    private String function;
    /**
     * idx for getting tokens and traversing the function
     */
    private  int idx;
    /**
     * Array of tokens, see type enum for details, outlines all possible types of parameters
     * for a valid function
     */
    private ArrayList<Token> tokens;

    /**
     * Code for using  the logcat in android monitor.
     */
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

    /**
     * gets the next token in the function, is of Type enum
     * @return The next token in the function
     */
    protected Token getNext(){
         Token token = tokens.get(idx);
         this.setIdx(idx + 1);
         return token;
    }

    /**
     * checks to see if there are more tokens to parse in the function
     * @return
     */
    protected boolean hasNext(){
        return (idx < tokens.size());
    }

    /**
     * gets a list of tokens to check for a function
     * @return List of tokens of the function.
     */
    private ArrayList<Token> getTokens(){
        ArrayList<Token> list = new ArrayList<Token>();
        int idx = 0, temp = 0;
        char ch;
        //iterate through the function
        while (idx < getFunction().length()){

            //get a character to test
            ch = getFunction().charAt(idx);
            if (ch =='('){
                //found a left paren
                list.add(new Token(Type.LEFT_PAREN,ch));
                idx++;
            }
            else if (ch == ')'){
                //found a right paren
                list.add(new Token(Type.RIGHT_PAREN,ch));
                idx++;
            }
            else if (isOperator(ch)){
                //found an operator
                list.add(new Token(Type.OPERATOR,ch));
                idx++;
            }
            else if (isNumber(ch)){
                //idx of start of number
                temp = idx;
                //takes care of length one integer
                boolean flag = false;
                if (idx < getFunction().length()-1) {
                    //our number is longer than length 1 keep going
                    idx++;
                    ch = getFunction().charAt(idx);
                    flag = true;
                }
                while ((isNumber(ch)||ch == '.')&& idx < getFunction().length()-1) {
                    //gets all remaining digits and iterates until it reaches one past the end.
                    idx++;
                    ch = getFunction().charAt(idx);
                }

                //boolean test prevents random null pointer exception
                //not sure where it came from
                if (getFunction().substring(temp, idx).compareTo("") != 0) {
                    //adds token to list
                    list.add(new Token(Type.NUMBER,
                            Double.parseDouble(getFunction().substring(temp, idx))));
                }
                //extra increment to handle one digit numbers
                if (!flag){
                    idx++;
                }
            }
            else if (ch =='['){
                //found a named anonymous varaiable
                temp = idx;
                if(idx< getFunction().length()-1){
                    //make sure the variable has a valid  name
                    idx++;
                    ch = getFunction().charAt(idx);
                }
                else {
                    //invalid list add undefined token to list
                    list.add(new Token(Type.UNDEFINED, getFunction().substring(temp, idx)));
                    idx++;
                }
                while(ch != ']' && idx < getFunction().length()-1){
                    //get the full valid name
                    idx ++;
                    ch = getFunction().charAt(idx);
                }
                idx++;
                if(ch == ']'){
                    //store it if valid
                    list.add(new Token(Type.VARIABLE, getFunction().substring(temp, idx)));
                }
                else {
                    //no closing paren, invalid
                    list.add(new Token(Type.UNDEFINED, getFunction().substring(temp, idx )));
                }

            }
            else if (isStartFunction(ch)){
                //checks  to see if char is a start of a predefined function
                if (isDefinedFunction(function,idx)){
                    //find a string representation of the function based on index
                    String string = whatString(whatType(function, idx));
                    //translate that into a token and add it
                    Token token = new Token(whatType(function,idx),string);
                    list.add(token);
                    idx += string.length();
                }
                else {
                    //user entered in something we do not recognize if we end here.
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

    /**
     * basically Type.toString()
     * @param type The type to find a string represenation of.
     * @return The string represenation of the Type Enum.
     */
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
            //unrecognized or bad type!
            function = "unf";
        }
        return function;
    }

    /**
     * Gives the type of a valid defined function e.g sin, cos ... .
     * @param function The function to parse.
     * @param idx The starting index of the function.
     * @return The valid type of the function.
     */
    private Type whatType(String function, int idx) {
        String test;
        //idterate through the function and search for length two functions
        if (idx+1 < function.length()){
            test = function.substring(idx,idx+2);
            if(test.compareTo("ln")==0){
                return Type.LN;
            }
        }
        //idterate through the function and search for length three functions
        if (idx+2 < function.length()){
            test = function.substring(idx,idx+3);
            if (test.compareTo("sin") == 0) {
                return Type.SIN;
            } else if (test.compareTo("cos") == 0) {
                return Type.COS;
            } else if (test.compareTo("tan") == 0) {
                return Type.TAN;
            } else if (test.compareTo("log") == 0) {
                return Type.LOG;
            }
        }

        //idterate through the function and search for length three functions
        if (idx+3 < function.length()){
            test = function.substring(idx,idx+4);
            if (test.compareTo("aSin") == 0) {
                return Type.ASIN;
            } else if (test.compareTo("aCos") == 0) {
                return Type.ACOS;
            } else if (test.compareTo("aTan") == 0) {
                return Type.ATAN;
            }
        }
        //something went wrong and this method was not fed a valid function.
        return Type.UNDEFINED;
    }

    /**
     * Tests a sequence of characacters in a given function string for a defined function, cos,
     * sin ect...
     * @param function The function to parse.
     * @param idx The index to start searching at.
     * @return True for valid function at index.
     */
    private boolean isDefinedFunction(String function, int idx) {
        String test;
        //check for lenght 2 functions.
        if (idx+1 < function.length()){
            test = function.substring(idx,idx+2);
            if(test.compareTo("ln")==0){
                return true;
            }
        }
        //check for lenght 3 functions.
        if (idx+2 < function.length()){
            test = function.substring(idx,idx+3);
            if(test.compareTo("sin")==0||test.compareTo("cos")==0||test.compareTo("tan")==0
                    ||test.compareTo("log")==0){
                return true;
            }
        }
        //check for lenght 4 functions.
        if (idx+3 < function.length()){
            test = function.substring(idx,idx+4);
            if(test.compareTo("aSin")==0||test.compareTo("aCos")==0||test.compareTo("aTan")==0){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks that a character is a valid start for a function.
     * @param ch The character to test
     * @return True for valid start, more validation needed.
     */
    private boolean isStartFunction(char ch) {
        if (ch == 's' || ch == 'c' || ch == 't' || ch == 'a' || ch == 'l'){
            return true;
        }
        return false;
    }

    /**
     * tests if a given letter is a valid number.
     * @param ch the letter to check
     * @return True if number.
     */
    private boolean isNumber(char ch) {
        try {
            Integer.parseInt(""+ch);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * checks if a given character is a mathematical operator.
     * @param ch letter to check.
     * @return True if is Operator.
     */
    private boolean isOperator(char ch) {
        if (ch == '*' || ch == '/' || ch == '+' || ch == '-' || ch == '^'){
            return true;
        }
        return false;
    }

    /**
     * Gives the function associated with this object.
     * @return The function.
     */
    private String getFunction() {
        return function;
    }

    /**
     * Sets the function associated with this object.
     * @param function The function.
     */
    public void setFunction(String function) {
        this.function = function;
        //resets the index
        this.setIdx(0);
        //gets all tokens associated with this object.
        this.tokens = getTokens();
    }

    /**
     * Gives the parsing index for this object.
     * @return The parsing index.
     */
    private int getIdx() {
        return idx;
    }

    /**
     * Sets the parsing index for this object.
     * @param idx The new parsing index.
     */
    private void setIdx(int idx) {
            this.idx = idx;

    }

    /**
     * Tests function for validity.
     * @return
     */
    public boolean isValid(){
        //token to check and store into.
        Token token=null;
        //default valid
        boolean test = true;
        //sets parsing index
        this.setIdx(0);
        //counter for number of parentheses in the function
        //positive for too many left parn
        //negative for too many right paren
        int paren =0;

        //iterate through every token in the list
        while (hasNext()){
            if(!test){
                //ealy exit for bad data
                return test;
            }
            token = getNext();
            if(idx ==1 &&(token.getType()==Type.OPERATOR||token.getType()==Type.RIGHT_PAREN)){
                //function can never start with either of these two characters.
                return false;
            }
            if(isFunction(token.getType())){
                //functions can only be followed by left parentheses
                test = test && getNextType() == Type.LEFT_PAREN;

            }
            else if (token.getType()==Type.NUMBER ||token.getType()==Type.VARIABLE){
                //numbers an variables can only be followed by operators, closing paren, and
                // end of function
                     test = test && (getNextType()==Type.OPERATOR || getNextType()==Type.RIGHT_PAREN
                     ||getNextType() == Type.NONE);

            }
            else if (token.getType()==Type.OPERATOR || token.getType() == Type.LEFT_PAREN){
                //operators, left parenthese can only be followed by numbers, left paren and
                // variables
                if(token.getType()==Type.LEFT_PAREN){
                    //increment paren counter
                    paren++;
                }
                test = test && (getNextType()==Type.LEFT_PAREN|| getNextType() == Type.NUMBER ||
                        getNextType()== Type.VARIABLE || isFunction(getNextType()));
            }
            else if ( token.getType() == Type.RIGHT_PAREN){
                //Right paren can only be followed by right paren, operators, and be the end of the
                // function.
                //decrement paren counter
                paren--;
                test = test &&(getNextType()==Type.RIGHT_PAREN||
                        getNextType()== Type.OPERATOR || getNextType() == Type.NONE);
            }
            else {
                //not recognized!
                test = false;
            }
            if(!test){
                //again early exit in case of bad input
                return test;
            }
        }
        //makes sure all parentheses balance
        return test && paren ==0;
    }

    /**
     * Checks if a given typ eis classified as a function.
     * @param type The type to check.
     * @return True for function.
     */
    private boolean isFunction(Type type) {
        if (type == Type.LN || type == Type.LOG || type == Type.SIN || type == Type.COS ||
                type == Type.TAN || type == Type.ASIN || type == Type.ACOS || type == Type.ATAN){
            return true;
        }
        return false;
    }

    /**
     * Gets the type off the next token in the tokens field. returns none if end of list.
     * @return The type of the next token.
     */
    private Type getNextType(){
        if (idx < tokens.size()){
            return tokens.get(idx ).getType();
        }
        else {
            return Type.NONE;
        }
    }


    /**
     * Token class for the function parser contains the type of the token and physical string
     * representation of the token.
     */
    private class Token{
        /**
         * The type of the token
         */
        private Type type;
        /**
         * The true value of the token, string, double, or integer.
         */
        private Object value;

        /**
         * Construct a token object.
         * @param type The type of mathematical operator this is.
         * @param value The actual string representation;
         */
        private Token(Type type, Object value){
            this.setType(type);
            this.setValue(value);
        }

        /**
         * Gives the type of the token.
         * @return The type of the token.
         */
        private Type getType() {
            return type;
        }

        /**
         * Sets the type of the token.
         * @param type The new type of the token.
         */
        private void setType(Type type) {
            this.type = type;
        }

        /**
         * Gives the value of the token.
         * @return The value of the token, double, integer, or string.
         */
        private Object getValue() {
            return value;
        }

        /**
         * Sets the value of the token.
         * @param value The new value of the token.
         */
         private void setValue(Object value) {
            this.value = value;
        }

        /**
         * The string representation of the token, for debugging purposes only.
         * @return The string representation of the token.
         */
        public String toString(){
            return "Token: "+ type.toString() +", " + value.toString();
        }
    }

    /**
     * Enum to distinguish differen types of tokens.
     */
    private enum Type{
        VARIABLE, NUMBER, OPERATOR, SIN,COS,TAN,ASIN, ACOS, ATAN,
        LOG, LN, LEFT_PAREN, RIGHT_PAREN, UNDEFINED, NONE;

    }
}
