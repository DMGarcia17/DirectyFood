package sv.edu.itca.santaana.directyfood;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class registro extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private EditText txtUser;
    private EditText txtPass;
    private EditText txtCompany;
    private EditText txtEmail;
    private EditText txtLocation;
    private EditText txtDescription;
    private CheckBox cbTerms;
    private Button btnRegister;

    private OnFragmentInteractionListener mListener;

    public registro() {
        // Required empty public constructor
    }

    public static registro newInstance(String param1, String param2) {
        registro fragment = new registro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtUser = (EditText) getView().findViewById(R.id.txtUser);
        txtPass = (EditText) getView().findViewById(R.id.txtPass);
        txtCompany = (EditText) getView().findViewById(R.id.txtCompany);
        txtEmail = (EditText) getView().findViewById(R.id.txtEmail);
        txtLocation = (EditText) getView().findViewById(R.id.txtLocation);
        txtDescription = (EditText) getView().findViewById(R.id.txtDescription);
        btnRegister = (Button) getView().findViewById(R.id.btnRegister);
        cbTerms = (CheckBox) getView().findViewById(R.id.cbTerms);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registro, container, false);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
