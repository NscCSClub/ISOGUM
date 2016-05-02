package nsccsclub.isogum;

import android.support.v4.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateFunctionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateFunctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFunctionFragment extends Fragment{
    public static final String LOG_CODE = "CreateFunctionFragment";

    private static final String ARG_FUNCTION = "function";
    private static final String ARG_NAME = "name";

    private String function;
    private String name;

    private OnFragmentInteractionListener listener;

    public CreateFunctionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFunctionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFunctionFragment newInstance(String param1, String param2) {
        CreateFunctionFragment fragment = new CreateFunctionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FUNCTION, param1);
        args.putString(ARG_NAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            function = getArguments().getString(ARG_FUNCTION);
            name = getArguments().getString(ARG_NAME);
            ((EditText)this.getActivity().findViewById(R.id.editText)).setText(function);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_function, container, false);




        View.OnClickListener onClickListener = new View.OnClickListener(){


            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                if (v != null){
                    EditText editText = (EditText) getActivity().getCurrentFocus();
                    int start = editText.getSelectionStart();
                    Editable editable = editText.getText();
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
                            editText.setSelection(editText.getSelectionStart() - 1);
                            break;
                        case (R.id.cos):
                            editable.insert(start,"cos()");
                            editText.setSelection(editText.getSelectionStart()-1);
                            break;
                        case (R.id.tan):
                            editable.insert(start,"tan()");
                            editText.setSelection(editText.getSelectionStart()-1);
                            break;
                        case (R.id.aSin):
                            editable.insert(start,"aSin()");
                            editText.setSelection(editText.getSelectionStart()-1);
                            break;
                        case (R.id.aCos):
                            editable.insert(start,"aCos()");
                            editText.setSelection(editText.getSelectionStart()-1);
                            break;
                        case (R.id.aTan):
                            editable.insert(start,"aTan()");
                            editText.setSelection(editText.getSelectionStart()-1);
                            break;
                        case (R.id.ln):
                            editable.insert(start, "ln()");
                            editText.setSelection(editText.getSelectionStart()-1);
                            break;
                        case (R.id.log):
                            editable.insert(start,"log()");
                            editText.setSelection(editText.getSelectionStart()-1);
                            break;
                        case (R.id.leftParen):
                            editable.insert(start, "(");
                            break;
                        case (R.id.rightParen):
                            editable.insert(start, ")");
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
                            editText.setSelection(smartSelectLeft(editable,start));
                            break;
                        case (R.id.right):
                            editText.setSelection(smartSelectRight(editable,start));
                            break;
                        case (R.id.del):
                            if (start!=0) {
                                editable.delete(start - 1, start);
                            }
                            break;
                        case (R.id.var):
                            listener.nameVariable();


                            break;
                    }
                    impliedMulitply(editable);
                }
            }
        };
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
        view.findViewById(R.id.neg).setOnClickListener(onClickListener);
        view.findViewById(R.id.sin).setOnClickListener(onClickListener);
        view.findViewById(R.id.cos).setOnClickListener(onClickListener);
        view.findViewById(R.id.tan).setOnClickListener(onClickListener);
        view.findViewById(R.id.aSin).setOnClickListener(onClickListener);
        view.findViewById(R.id.aCos).setOnClickListener(onClickListener);
        view.findViewById(R.id.aTan).setOnClickListener(onClickListener);
        view.findViewById(R.id.ln).setOnClickListener(onClickListener);
        view.findViewById(R.id.log).setOnClickListener(onClickListener);
        view.findViewById(R.id.leftParen).setOnClickListener(onClickListener);
        view.findViewById(R.id.rightParen).setOnClickListener(onClickListener);
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
        return view;
    }

    private void impliedMulitply(Editable editable) {
        String text = editable.toString();
        char ch;
        for (int i = text.length()-1; i >= 0; i-- ){
            ch = text.charAt(i);
//            Log.d(LOG_CODE,"testing : " + ch);
            if ((ch == '(')&& i > 0){
//                Log.d(LOG_CODE,"testing : " + ch);
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
//                Log.d(LOG_CODE,"found function! : " + ch);
                int start = findBegin(text,i);
                if (start!=-1){
//                    Log.d(LOG_CODE,"found beginning! " + ch);
                    if (start>0&&!isOperator(text.charAt(start-1))&&text.charAt(start-1)!='('){
                        editable.insert(start,"*");
                    }
                }
            }
        }
    }

    private int findBegin(String text, int i) {
        String test;
        if (i-2>=0){
            test = text.substring(i-2,i);
//            Log.d(LOG_CODE,"substring implied multiply length 2 " + test);
            if(test.compareTo("ln")==0){
                return i-2;
            }
        }
        if (i-3>=0){
            test = text.substring(i-3,i);
//            Log.d(LOG_CODE,"substring implied multiply length 3 " + test);
            if(test.compareTo("sin")==0||test.compareTo("cos")==0||test.compareTo("tan")==0
                    ||test.compareTo("log")==0){
                return i-3;
            }
        }
        if (i-4>=0){
            test = text.substring(i-4,i);
//            Log.d(LOG_CODE,"substring implied multiply length 4 " + test);
            if(test.compareTo("aSin")==0||test.compareTo("aCos")==0||test.compareTo("aTan")==0){
                return i-4;
            }
        }
        return -1;
    }

    private boolean definedFunction(String text, int i) {
        String test;
        if (i-2>=0){
            test = text.substring(i-2,i);
            //Log.d(LOG_CODE,"substring implied multiply length 2 " + test);
            if(test.compareTo("ln")==0){
//                Log.d(LOG_CODE,"it worked");
                return true;
            }
        }
        if (i-3>=0){
            test = text.substring(i-3,i);
//            Log.d(LOG_CODE,"substring implied multiply length 3 " + test);
            if(test.compareTo("sin")==0||test.compareTo("cos")==0||test.compareTo("tan")==0
                    ||test.compareTo("log")==0){
//                Log.d(LOG_CODE,"it worked");
                return true;
            }
        }
        if (i-4>=0){
            test = text.substring(i-4,i);
//            Log.d(LOG_CODE,"substring implied multiply length 4 " + test);
            if(test.compareTo("aSin")==0||test.compareTo("aCos")==0||test.compareTo("aTan")==0){
//                Log.d(LOG_CODE,"it worked");
                return true;
            }
        }

        return false;
    }

    private boolean isOperator(char ch) {
        if(ch == '+'|| ch == '-' || ch == '*'||ch =='/'||ch == '^'){
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

    public void addVar(String var){

        EditText destination = (EditText) this.getActivity().findViewById(R.id.editText);
        Editable editable = destination.getText();
        int start = destination.getSelectionStart();
        editable.insert(start, "[" + var + "]");
        impliedMulitply(editable);
    }

    private int smartSelectLeft(Editable editable, int start) {
        Log.d(LOG_CODE, "Smart Left Called   Start: "+start);
        Log.d(LOG_CODE,"String: "+editable.toString());
        int move = 0;

        if (start==0){
            return 0;
        }
        String text = editable.toString();
        char ch = text.charAt(start-1);
        Log.d(LOG_CODE, "Initial letter :" +ch);
        move--;
        if (isLeftOperator(ch)){
            Log.d(LOG_CODE, "1st char is operator, early exit. idx: " + (start+move));
            if (ch=='('){
                return smartSelectLeft(editable,start+move);
            }
            return start+move;
        }
        if (ch == ']' ){
            return smartSelectLeft(editable,start+move);
        }

        while(!isStopLeft(ch)) {
            Log.d(LOG_CODE, "loop test failed " + ch + " is not an operator");

            move--;
            if (Math.abs(move) == start) {
                Log.d(LOG_CODE, "Reached begining, early exit, 1st char: " + ch);
                return start+move;
            }

            ch = text.charAt(start + move);
            Log.d(LOG_CODE,"Not a place to stop, new char: " + ch);
        }

        Log.d(LOG_CODE, "Loop test passed " +ch + " is an operator.");
        if(isLeftOperator(ch)){
            Log.d(LOG_CODE, "Last char was operator, new idx: "+(start+move +1));
            return start+move +1;
        }

        Log.d(LOG_CODE, "Found stopping place final idx: " + (start + move));
        return start+move;
    }

    private boolean isLeftOperator(char ch) {
        if (ch=='*' || ch=='/' || ch=='-' || ch=='+' || ch=='^'|| ch =='('){
            return true;
        }
        return false;
    }

    private boolean isRightOperator(char ch) {
        if (ch=='*' || ch=='/' || ch=='-' || ch=='+' || ch=='^' || ch==')'){
            return true;
        }
        return false;
    }


    private boolean isStopRight(char ch) {
        if (ch==')' || ch=='('|| ch == '['||
                ch=='*' || ch=='/' || ch=='-' || ch=='+' || ch=='^'){
            return true;
        }
        return false;
    }
    private boolean isStopLeft(char ch) {
        if (ch==')' || ch=='('|| ch == ']'||
                ch=='*' || ch=='/' || ch=='-' || ch=='+' || ch=='^'){
            return true;
        }
        return false;
    }
    private int smartSelectRight(Editable editable, int start) {
        Log.d(LOG_CODE, "Smart select called start: " + start);
        Log.d(LOG_CODE, "String: "+ editable.toString());
        int move = 0;

        if (start==editable.toString().length()-1){
            Log.d(LOG_CODE,"Started at last index: " + start);
            return start+1;
        }
        else if(start == editable.toString().length()){
            return start;
        }
        String text = editable.toString();
        char ch = text.charAt(start);
        Log.d(LOG_CODE, "Initial char: " + ch);
        move++;
        if (isRightOperator(ch)){
            Log.d(LOG_CODE, "1st char is operator, early exit. idx: " + (start+move));
            return start+move;
        }
        if(ch =='['){
            return smartSelectRight(editable,start+1);
        }


        while(!isStopRight(ch)) {
            Log.d(LOG_CODE, "Loop test failed " + ch + " is not a stop char");

            if (start+move==text.length()-1) {
                Log.d(LOG_CODE, "Reached end in loop, final idx: " + (start+ move));
                return start + move+1;
            }
            move++;
            ch = text.charAt(start + move);
        }
        Log.d(LOG_CODE,"Loop test passsed "+ ch +" is a stop char");
        if(ch=='('||ch ==']'){
            return start+move+1;
        }
        Log.d(LOG_CODE, "final idx : " + (start+ move));
        return start+move;
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
    public interface OnFragmentInteractionListener {
        public void nameVariable();

    }

    private enum Type{
        OPERATOR,NUMBER,COMPOSITE,VARIABLE;
    }


}
