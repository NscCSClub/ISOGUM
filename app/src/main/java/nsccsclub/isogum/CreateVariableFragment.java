package nsccsclub.isogum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateVariableFragment.OnVariableFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateVariableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateVariableFragment extends Fragment {
    /**
     * log code for using logcat
     */
    public static final String LOG_CODE = "CreateVariableFragment";

    /**
     * Argument for updating an existing variable needs to be implemented
     */
    private static final String ARG_VALUE = "value";
    /**
     * 2nd argument for updating an existing function
     */
    private static final String ARG_NAME = "name";

    /**
     *
     */
    private String value;
    private String name;

    private EditText editText;

    private OnVariableFragmentInteractionListener listener;

    public CreateVariableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateVariableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateVariableFragment newInstance(String param1, String param2) {
        CreateVariableFragment fragment = new CreateVariableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VALUE, param1);
        args.putString(ARG_NAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = getArguments().getString(ARG_VALUE);
            name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_variable, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
