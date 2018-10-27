package sv.edu.itca.santaana.directyfood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.Map;

public class registro extends Fragment{
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
    private String path;
    StringRequest str;

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            Uri uri = data.getData();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_registro, container, false);
        txtUser = (EditText) vista.findViewById(R.id.txtUser);
        txtPass = (EditText) vista.findViewById(R.id.txtPass);
        txtCompany = (EditText) vista.findViewById(R.id.txtCompany);
        txtEmail = (EditText) vista.findViewById(R.id.txtEmail);
        txtLocation = (EditText) vista.findViewById(R.id.txtLocation);
        txtDescription = (EditText) vista.findViewById(R.id.txtDescription);
        btnRegister = (Button) vista.findViewById(R.id.btnRegister);
        cbTerms = (CheckBox) vista.findViewById(R.id.cbTerms);
        Button btnLogo = (Button) vista.findViewById(R.id.btnLogo);
        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                log.setType("images/png");
                startActivityForResult(Intent.createChooser(log, "Seleccione"), 10);
            }
        });



        cbTerms.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbTerms.isChecked()){
                    cargarwebservice();

                }
            }
        });

        return vista;

    }

    private void cargarwebservice() {
        String url = "https://foodapp-android.azurewebsites.net/index.php";
//        String key = "AJZfpodVtaCFQO5TKpV8PE7qLlKiAbNglPeNhoiudyD3LsEE2RlFq6pe";
//        String params = "data="+txtUser.getText().toString()
//                +"||"+txtPass.getText().toString()+"||"+txtCompany.getText().toString()+
//                "||"+txtLocation.getText().toString()+"||"+path+"||"+txtDescription.getText().toString();
        str = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
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
