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
 * {@link FragmentCreateVariable.OnVariableFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCreateVariable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateVariable extends Fragment {
    /**
     * log code for using logcat
     */
    public static final String LOG_CODE = "FragmentCreateVariable";

    /**
     * Argument for updating an existing variable needs to be implemented
     */
    private static final String ARG_VALUE = "value";
    /**
     * 2nd argument for updating an existing function
     */
    private static final String ARG_NAME = "name";
    /**
     * 3rd argument for updating an existing variable
     */
    private static final String ARG_UNCERTAINTY = "uncertainty";

    /**
     *
     */
    private String value;
    private String name;
    private String uncertainty;

    private EditText value_field;
    private EditText uncertaindy_field;

    private OnVariableFragmentInteractionListener listener;

    public FragmentCreateVariable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 The existing value to edit
     * @param param2 The name of the function for saving data.
     * @return A new instance of fragment FragmentCreateVariable.
     */
    public static FragmentCreateVariable newInstance(String param1, String param2, String param3) {
        FragmentCreateVariable fragment = new FragmentCreateVariable();
        Bundle args = new Bundle();
        args.putString(ARG_VALUE, param1);
        args.putString(ARG_NAME, param2);
        args.putString(ARG_UNCERTAINTY, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    /**
     * Creates the variable and sets arguments if neccessary.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = getArguments().getString(ARG_VALUE);
            name = getArguments().getString(ARG_NAME);
            uncertainty = getArguments().getString(ARG_UNCERTAINTY);
            ((EditText)this.getActivity().findViewById(R.id.editText)).setText(value);
        }
    }

    @Override
    /**
     * Creates the view and hooks up the listeners with the parent activity. All key
     * actions are handled in this method.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_variable, container, false);
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
                    EditText current = ((EditText) getActivity().getCurrentFocus());
                    int start = current.getSelectionStart();
                    Editable editable = current.getText();
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
                        case (R.id.del):
                            if (start!=0) {
                                editable.delete(start - 1, start);
                            }
                            break;
                        case (R.id.left):
                            if(start>0){
                                current.setSelection(start-1);
                            }
                            break;
                        case (R.id.right):
                            if(start<editable.length()){
                                current.setSelection(start+1);
                            }
                            break;

                        case (R.id.save):
                            listener.saveVariable(((EditText)view.findViewById(R.id.editText))
                                    .getText().toString(),
                                    ((EditText)view.findViewById(R.id.editText))
                                            .getText().toString());
                            break;
                        case (R.id.cancel):
                            listener.cancelVariable();
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
                v.requestFocus();
                return true;
            }
        };
        view.findViewById(R.id.editText).setOnTouchListener(onTouchListener);
        view.findViewById(R.id.editText).setFocusable(true);
        view.findViewById(R.id.editText3).setOnTouchListener(onTouchListener);
        view.findViewById(R.id.editText3).setFocusable(true);
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
        view.findViewById(R.id.left).setOnClickListener(onClickListener);
        view.findViewById(R.id.right).setOnClickListener(onClickListener);
        view.findViewById(R.id.del).setOnClickListener(onClickListener);
        view.findViewById(R.id.save).setOnClickListener(onClickListener);
        view.findViewById(R.id.cancel).setOnClickListener(onClickListener);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVariableFragmentInteractionListener) {
            listener = (OnVariableFragmentInteractionListener) context;
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
    public interface OnVariableFragmentInteractionListener {
        /**
         * checks the variable for valididty, displays an error message if not valid, if valid it
         * stores it in the database, then returns the activity to th last screen
         */
        void saveVariable(String value, String uncertainty);

        /**
         * cancel variable, returns to the last activity.
         */
        void cancelVariable();
    }
}
