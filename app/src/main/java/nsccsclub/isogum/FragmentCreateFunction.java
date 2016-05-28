package nsccsclub.isogum;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCreateFunction.OnFunctionFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCreateFunction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateFunction extends Fragment{
    /**
     * log code for using logcat
     */
    public static final String LOG_CODE = "FragmentCreateFunction";

    /**
     * Argument for updating an existing function needs to be implemented
     */
    private static final String ARG_FUNCTION = "function";
    /**
     * 2nd argument for updating an existing function
     */
    private static final String ARG_NAME = "name";

    /**
     *
     */
    private String function;
    private String name;

    private EditText editText;

    private OnFunctionFragmentInteractionListener listener;

    public FragmentCreateFunction() {
        // Required empty public constructor
    }

    /**
     * Creates the fragment with additionap parameters
     *
     * @param param1 The existing function to edit.
     * @param param2 name of the function for saving data.
     * @return A new instance of fragment FragmentCreateFunction.
     */
    public static FragmentCreateFunction newInstance(String param1, String param2) {
        //create a new instance of the fragment with arguments will be used w the edit function
        // fragment
        FragmentCreateFunction fragment = new FragmentCreateFunction();
        Bundle args = new Bundle();
        args.putString(ARG_FUNCTION, param1);
        args.putString(ARG_NAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    /**
     * Creates a the fragment and sets arguments
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            function = getArguments().getString(ARG_FUNCTION);
            name = getArguments().getString(ARG_NAME);
            ((EditText)this.getActivity().findViewById(R.id.editText)).setText(function);
        }
    }

    @Override
    /**
     * Creates the view and hooks up listeners with the parent activity. All keys are hooked up
     * in this method
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_function, container, false);
        View.OnClickListener onClickListener = new View.OnClickListener(){


            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                if (v != null){
                    //edit text is the only focusable object in the activity so we should be okay.
                    //Initialize edit text here
                    setEditText((EditText) getActivity().getCurrentFocus());
                    int start = getEditText().getSelectionStart();
                    Editable editable = getEditText().getText();
                    int id = v.getId();
                    switch (id){
                        case (R.id.num0):
                            editable.insert(start,"0");
                            break;
                        case (R.id.num1):
                            editable.insert(start,"1");
                            break;
                        case (R.id.num2):
                            editable.insert(start,"2");
                            break;
                        case (R.id.num3):
                            editable.insert(start,"3");
                            break;
                        case (R.id.num4):
                            editable.insert(start,"4");
                            break;
                        case (R.id.num5):
                            editable.insert(start,"5");
                            break;
                        case (R.id.num6):
                            editable.insert(start,"6");
                            break;
                        case (R.id.num7):
                            editable.insert(start,"7");
                            break;
                        case (R.id.num8):
                            editable.insert(start,"8");
                            break;
                        case (R.id.num9):
                            editable.insert(start,"9");
                            break;
                        case (R.id.point):
                            editable.insert(start,".");
                            break;
                        case (R.id.neg):
                            editable.insert(start,"-");
                            break;
                        case (R.id.sin):
                            editable.insert(start,"sin()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.cos):
                            editable.insert(start,"cos()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.tan):
                            editable.insert(start,"tan()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.aSin):
                            editable.insert(start,"aSin()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.aCos):
                            editable.insert(start,"aCos()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.aTan):
                            editable.insert(start,"aTan()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.ln):
                            editable.insert(start, "ln()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.log):
                            editable.insert(start,"log()");
                            getEditText().setSelection(getEditText().getSelectionStart() - 1);
                            break;
                        case (R.id.Paren):
                            editable.insert(start, "()");
                            getEditText().setSelection(getEditText().getSelectionStart()-1);
                            break;
                        case (R.id.div):
                            editable.insert(start,"/");
                            break;
                        case (R.id.times):
                            editable.insert(start,"*");
                            break;
                        case (R.id.minus):
                            editable.insert(start,"-");
                            break;
                        case (R.id.plus):
                            editable.insert(start,"+");
                            break;
                        case (R.id.pow):
                            editable.insert(start,"^");
                            break;
                        case (R.id.e):
                            editable.insert(start,"[e]");
                            break;
                        case (R.id.pi):
                            editable.insert(start,"[pi]");
                            break;
                        case (R.id.left):
                            getEditText().setSelection(smartSelectLeft(editable, start));
                            break;
                        case (R.id.right):
                            getEditText().setSelection(smartSelectRight(editable, start));
                            break;
                        case (R.id.del):
                            if (start!=0) {
                                editable.delete(start - 1, start);
                            }
                            break;
                        case (R.id.var):
                            listener.nameVariable();
                            break;
                        case (R.id.save):
                            impliedMulitply(editable);

                            listener.saveFunction(getEditText());
                            break;
                        case (R.id.cancel):
                            listener.cancelFunction();
                            break;
                    }

                }
            }
        };
        //hookup keys here
        View.OnTouchListener onTouchListener= new View.OnTouchListener(){


            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v     The view the touch event has been dispatched to.
             * @param event The MotionEvent object containing full information about
             *              the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        };
        view.findViewById(R.id.editText).setOnTouchListener(onTouchListener);
        view.findViewById(R.id.num0).setOnClickListener(onClickListener);
        view.findViewById(R.id.num1).setOnClickListener(onClickListener);
        view.findViewById(R.id.num2).setOnClickListener(onClickListener);
        view.findViewById(R.id.num3).setOnClickListener(onClickListener);
        view.findViewById(R.id.num4).setOnClickListener(onClickListener);
        view.findViewById(R.id.num5).setOnClickListener(onClickListener);
        view.findViewById(R.id.num6).setOnClickListener(onClickListener);
        view.findViewById(R.id.num7).setOnClickListener(onClickListener);
        view.findViewById(R.id.num8).setOnClickListener(onClickListener);
        view.findViewById(R.id.num9).setOnClickListener(onClickListener);
        view.findViewById(R.id.point).setOnClickListener(onClickListener);
        view.findViewById(R.id.sin).setOnClickListener(onClickListener);
        view.findViewById(R.id.cos).setOnClickListener(onClickListener);
        view.findViewById(R.id.tan).setOnClickListener(onClickListener);
        view.findViewById(R.id.aSin).setOnClickListener(onClickListener);
        view.findViewById(R.id.aCos).setOnClickListener(onClickListener);
        view.findViewById(R.id.aTan).setOnClickListener(onClickListener);
        view.findViewById(R.id.ln).setOnClickListener(onClickListener);
        view.findViewById(R.id.log).setOnClickListener(onClickListener);
        view.findViewById(R.id.Paren).setOnClickListener(onClickListener);
        view.findViewById(R.id.div).setOnClickListener(onClickListener);
        view.findViewById(R.id.times).setOnClickListener(onClickListener);
        view.findViewById(R.id.minus).setOnClickListener(onClickListener);
        view.findViewById(R.id.plus).setOnClickListener(onClickListener);
        view.findViewById(R.id.pow).setOnClickListener(onClickListener);
        view.findViewById(R.id.e).setOnClickListener(onClickListener);
        view.findViewById(R.id.pi).setOnClickListener(onClickListener);
        view.findViewById(R.id.left).setOnClickListener(onClickListener);
        view.findViewById(R.id.right).setOnClickListener(onClickListener);
        view.findViewById(R.id.del).setOnClickListener(onClickListener);
        view.findViewById(R.id.var).setOnClickListener(onClickListener);
        view.findViewById(R.id.save).setOnClickListener(onClickListener);
        view.findViewById(R.id.cancel).setOnClickListener(onClickListener);

        editText = (EditText) view.findViewById(R.id.editText);

        return view;
    }

    /**
     * puts multiplication symbols in all unused operator spaces in a string.
     * @param editable The editable text from a an editText field.
     */
    private void impliedMulitply(Editable editable) {
        String text = editable.toString();
        char ch;
    //iteratre trhough the function
        for (int i = text.length()-1; i >= 0; i-- ){
            ch = text.charAt(i);
            //find character and see if there is a gap where we need an implied multiply
            if ((ch == '(')&& i > 0){
                if (!(isOperator(text.charAt(i-1)))&&!(definedFunction(text,i))&&text.charAt(i-1)!='('){
                    editable.insert(i,"*");
                }
            }
            if (ch =='['&& i>0){
                if (!isOperator(text.charAt(i-1))&& text.charAt(i-1)!='('){
                    editable.insert(i,"*");
                }
            }
            if (isNumber(ch)&&i>0){
                if (!isOperator(text.charAt(i-1))&&!isNumber(text.charAt(i-1))&&
                        text.charAt(i-1)!='(' && text.charAt(i-1)!='.'){
                    editable.insert(i,"*");
                }
            }
            if (definedFunction(text,i)){
                //searches ahead of cursor position and finds out fia defined function exists
                int start = findBegin(text,i);
                if (start!=-1){
                    if (start>0&&!isOperator(text.charAt(start-1))&&text.charAt(start-1)!='('){
                        editable.insert(start,"*");
                    }
                }
            }
        }
    }

    /**
     * finds the begginof a function from an index, must be defined.
     * @param text The function to parse.
     * @param i the index to start at.
     * @return The index of the start of the function, -1 if not found.
     */
    private int findBegin(String text, int i) {
        String test;
        //checks for fucntion sof length two.
        if (i-2>=0){
            test = text.substring(i-2,i);
            if(test.compareTo("ln")==0){
                return i-2;
            }
        }
        //checks for function of length 3.
        if (i-3>=0){
            test = text.substring(i-3,i);
            if(test.compareTo("sin")==0||test.compareTo("cos")==0||test.compareTo("tan")==0
                    ||test.compareTo("log")==0){
                return i-3;
            }
        }
        //checks for function of length 4.
        if (i-4>=0){
            test = text.substring(i-4,i);
            if(test.compareTo("aSin")==0||test.compareTo("aCos")==0||test.compareTo("aTan")==0){
                return i-4;
            }
        }
        //we didnt find it.
        return -1;
    }

    /**
     * checks if there is defined function at the start of a given index in a string
     * @param text The string to check.
     * @param i The index to search at.
     * @return True if defined function exists
     */
    private boolean definedFunction(String text, int i) {
        String test;
        //checks for function of length 2
        if (i-2>=0){
            test = text.substring(i-2,i);
            if(test.compareTo("ln")==0){
                return true;
            }
        }
        //checks for function of length 3.
        if (i-3>=0){
            test = text.substring(i-3,i);
            if(test.compareTo("sin")==0||test.compareTo("cos")==0||test.compareTo("tan")==0
                    ||test.compareTo("log")==0){
                return true;
            }
        }
        //checks for function of length f=4.
        if (i-4>=0){
            test = text.substring(i-4,i);
            if(test.compareTo("aSin")==0||test.compareTo("aCos")==0||test.compareTo("aTan")==0){
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a given character is a mathematical operator.
     * @param ch The char to check.
     * @return True if mathematical operator.
     */
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    /**
     * Checks if a character is a number.
     * @param ch The character to check.
     * @return True if the char is a number.
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
     * Adds a variable to the edittext windwon in the activity.
     * @param var The variable to add.
     */
    public void addVar(String var){

        EditText destination = (EditText) this.getActivity().findViewById(R.id.editText);
        Editable editable = destination.getText();
        int start = destination.getSelectionStart();
        editable.insert(start, "[" + var + "]");
//        impliedMulitply(editable);
    }

    /**
     * Moves the cursor to the left to the next term.
     * @param editable The edit text function to move around in.
     * @param start The index we are currently at.
     * @return The index of the next term to the left.
     */
    private int smartSelectLeft(Editable editable, int start) {
//        Log.d(LOG_CODE, "Smart Left Called   Start: "+start);
//        Log.d(LOG_CODE,"String: "+editable.toString());
        int move = 0;

        if (start==0){
            return 0;
        }
        String text = editable.toString();
        //moves one char over.
        char ch = text.charAt(start-1);
//        Log.d(LOG_CODE, "Initial letter :" +ch);
        move--;
        //if its an operator stop here
        if (isLeftOperator(ch)){

            return start+move;
        }
        //if we are next to a variable we should skip to the end
        if (ch == ']' ){
            return smartSelectLeft(editable,start+move);
        }

        //searches the string for the next stopping criterion
        while(!isStopLeft(ch)) {
//            Log.d(LOG_CODE, "loop test failed " + ch + " is not an operator");
            //move to the left
            move--;
            if (Math.abs(move) == start) {
                //we have reached the beggining of the string we can stop
//                Log.d(LOG_CODE, "Reached begining, early exit, 1st char: " + ch);
                return start+move;
            }
            //increment the character to the next one
            ch = text.charAt(start + move);
//            Log.d(LOG_CODE,"Not a place to stop, new char: " + ch);
        }
//        Log.d(LOG_CODE, "Loop test passed " +ch + " is an operator.");
        if(isLeftOperator(ch)){
            //if we reached an operator we need to move back one
//            Log.d(LOG_CODE, "Last char was operator, new idx: "+(start+move +1));
            return start+move +1;
        }

//        Log.d(LOG_CODE, "Found stopping place final idx: " + (start + move));
        return start+move;
    }

    /**
     * Checks to see if the current character is an operator for  the smart select left funciton
     * that we need to move back from.
     * @param ch The char to check.
     * @return True if we need to move back
     */
    private boolean isLeftOperator(char ch) {
        return ch == '*' || ch == '/' || ch == '-' || ch == '+' || ch == '^' || ch == '(' || ch == ')';
    }

    /**
     * Checks to see if the current character is an operator for  the smart select Right funciton
     * that we need to move back from.
     * @param ch The char to check.
     * @return True if we need to move back
     */
    private boolean isRightOperator(char ch) {
        return ch == '*' || ch == '/' || ch == '-' || ch == '+' || ch == '^' || ch == ')' || ch == '(';
    }


    /**
     * Checks if the current char is a place for the smart select right function to stop at.
     * @param ch The char to check.
     * @return True if we need to stop.
     */
    private boolean isStopRight(char ch) {
        return ch == ')' || ch == '(' || ch == '[' ||
                ch == '*' || ch == '/' || ch == '-' || ch == '+' || ch == '^';
    }

    /**
     * Checks if the current char is a place for the smart select left function to stop at.
     * @param ch The char to check.
     * @return True if we need to stop.
     */
    private boolean isStopLeft(char ch) {
        return ch == ')' || ch == '(' || ch == ']' ||
                ch == '*' || ch == '/' || ch == '-' || ch == '+' || ch == '^';
    }

    /**
     * Moves the cursor to the right to the next term.
     * @param editable The edit text function to move around in.
     * @param start The index we are currently at.
     * @return The index of the next term to the right.
     */
    private int smartSelectRight(Editable editable, int start) {
//        Log.d(LOG_CODE, "Smart : "+ editable.toString());
        int move = 0;

        if (start==editable.toString().length()-1){
//            Log.d(LOG_CODE,"Started at last index: " + start);
            //reached 2nd to the end of the function, move righ one
            return start+1;
        }
        else if(start == editable.toString().length()){
            //reached end of function do not scroll
            return start;
        }
        //get the initial char and move one to the right.
        String text = editable.toString();
        char ch = text.charAt(start);
//        Log.d(LOG_CODE, "Initial char: " + ch);
        move++;
        if (isRightOperator(ch)){
//            Log.d(LOG_CODE, "1st char is operator, early exit. idx: " + (start+move));
            //1st char is an operator we can stop
            return start+move;
        }
        if(ch =='['){
            //if we are at the start of a variable we should skip through it's contents.
            return smartSelectRight(editable, start + 1);
        }


        while(!isStopRight(ch)) {
            //parse the string to the right until we reach a stopping character or the end of String
//            Log.d(LOG_CODE, "Loop test failed " + ch + " is not a stop char");

            if (start+move==text.length()-1) {
                //we have reached the end, we can stop
//                Log.d(LOG_CODE, "Reached end in loop, final idx: " + (start+ move));
                return start + move+1;
            }
            move++;
            ch = text.charAt(start + move);
        }
//        Log.d(LOG_CODE,"Loop test passsed "+ ch +" is a stop char");
        if(ch ==']'){
            // we can iterate one more in each of these special cases
            //this is a bug fix
            return start+move+1;
        }
//        Log.d(LOG_CODE, "final idx : " + (start+ move));
        return start+move;
    }


    public void setText(String text){
        editText.setText(text);
    }



    @Override
    /**
     * makes sure the host activity has implemented the proper interfaces.
     */
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFunctionFragmentInteractionListener) {
            listener = (OnFunctionFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    //kills all listeners
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private EditText getEditText() {
        return editText;
    }

    private void setEditText(EditText editText) {
        this.editText = editText;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFunctionFragmentInteractionListener {
        /**
         * Gets a variable name, checks for uniqueness and sets it in the edit text window.
         */
        void nameVariable();

        /**
         * checks the function for valididty, displays an error message if not valid, if valid it
         * stores it in the database, then returns the activity to th last screen
         */
        void saveFunction(EditText editText);

        /**
         * cancel function, returns to the last activity.
         */
        void cancelFunction();



    }
}
