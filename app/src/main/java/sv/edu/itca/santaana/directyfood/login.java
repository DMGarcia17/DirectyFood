package sv.edu.itca.santaana.directyfood;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class login extends Fragment implements Listener<JSONObject>, Response.ErrorListener {

    private ProgressDialog progress;
    private RequestQueue req;
    private VolleyS v;
    private JsonObjectRequest jsreq;
    private StringRequest str;
    private EditText txtLUser;
    private EditText txtLPass;
    private Button btnLogin;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public login() {
        // Required empty public constructor
    }

    public static login newInstance(String param1, String param2) {
        login fragment = new login();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_login, container, false);
        txtLUser = (EditText) vista.findViewById(R.id.txtLUser);
        txtLPass = (EditText) vista.findViewById(R.id.txtLPass);
        btnLogin = (Button) vista.findViewById(R.id.btnLogin);
        v = VolleyS.getInstance(getActivity().getApplicationContext());
        progress = new ProgressDialog(getContext());
        req = v.getRequestQueue();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarLogin();
            }
        });
        return vista;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        onConnectionFailed(error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        onConnectionFinished();
        JSONArray arr = response.optJSONArray("usuario");
        try {
            JSONObject json = arr.getJSONObject(0);
            if (json.optString("username").equals(txtLUser.getText().toString())) {
                Toast.makeText(getContext(), "Usuario Valido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Usuario erroneo", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void comprobarLogin() {
        str = new StringRequest(Request.Method.POST, getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] res = response.split("@");
                onConnectionFinished();
                String result = res[0].trim();
                if (result.equalsIgnoreCase("true")) {
                    Toast.makeText(getContext(), "Usuario correcto",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), result,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                onConnectionFailed(error.toString());
                txtLPass.setText(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String user = txtLUser.getText().toString();
                String pass = txtLPass.getText().toString();
                String key = "AJZfpodVtaCFQO5TKpV8PE7qLlKiAbNglPeNhoiudyD3LsEE2RlFq6pe";

                Map<String, String> param = new HashMap<>();
                param.put("user", user);
                param.put("passwd", pass);
                param.put("key", key);
                param.put("act", "3");

                return param;
            }
        };
//        jsreq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        addToQueue(str);
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
        progress.setMessage("Cargando...");
        progress.show();
    }

    public void onConnectionFinished() {
        progress.hide();
        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        progress.hide();
        getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }
}
