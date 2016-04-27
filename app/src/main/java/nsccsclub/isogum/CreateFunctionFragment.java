package nsccsclub.isogum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateFunctionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateFunctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFunctionFragment extends Fragment {

    private static final String ARG_FUNCTION = "function";
    private static final String ARG_NAME = "name";

    private String function;
    private String name;

    private OnFragmentInteractionListener mListener;

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
    public static CreateFunctionFragment newInstance(String param1, String param2, EditText functionEdit) {
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
        }


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


                    }
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_function, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onTextButton(View view){

    }

    public void onVariableButton(View view){

    }

    public void onActionButton(View view){

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
