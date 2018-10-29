package sv.edu.itca.santaana.directyfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private ProgressDialog progress;
    private RequestQueue req;
    private VolleyS v;
    private StringRequest str;
    private Bitmap bit;

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
        if (requestCode == 10) {
            Uri uri = data.getData();
            try {
                bit = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (req == null)
                req = v.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            onPreStartConnection();
            req.add(request);
        }
    }

    public void onPreStartConnection() {
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
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
        progress = new ProgressDialog(getContext());
        v = VolleyS.getInstance(getActivity().getApplicationContext());
        req = v.getRequestQueue();
        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(Intent.ACTION_GET_CONTENT);
                log.setType("image/png");
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
                if (cbTerms.isChecked()) {
                    cargarwebservice();
                }
            }
        });

        return vista;

    }

    private void cargarwebservice() {
        str = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("true")) {
                    Toast.makeText(getContext(), "Se ha registrado exitosamente \nAhora usted puede iniciar sesión",
                            Toast.LENGTH_SHORT).show();
                    onConnectionFinished();
                } else {
                    Toast.makeText(getContext(), "No Se ha registrado exitosamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                onConnectionFailed(error.toString());
                txtDescription.setText(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String user = txtUser.getText().toString();
                String pass = txtPass.getText().toString();
                String key = "AJZfpodVtaCFQO5TKpV8PE7qLlKiAbNglPeNhoiudyD3LsEE2RlFq6pe";
                String company = txtCompany.getText().toString();
                String locat = txtLocation.getText().toString();
                String img = cItoS(bit);
                String descrip = txtDescription.getText().toString();

                Map<String, String> param = new HashMap<>();
                param.put("user", user);
                param.put("pass", pass);
                param.put("company", company);
                param.put("locat", locat);
                param.put("img", img);
                param.put("descrip", descrip);
                param.put("key", key);
                param.put("act", "2");

                return param;
            }
        };
        addToQueue(str);
    }

    private String cItoS(Bitmap bitm) {
        ByteArrayOutputStream bte = new ByteArrayOutputStream();
        bitm.compress(Bitmap.CompressFormat.PNG, 100, bte);
        byte[] array = bte.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
