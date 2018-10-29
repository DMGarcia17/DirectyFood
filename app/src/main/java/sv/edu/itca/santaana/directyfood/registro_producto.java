package sv.edu.itca.santaana.directyfood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class registro_producto extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private EditText txtRNProd;
    private EditText txtRPrice;
    private EditText txtRDescription;
    private Button btnRImage;
    private Bitmap bit;
    private ProgressDialog progress;
    private RequestQueue req;
    private VolleyS v;
    private StringRequest str;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vis = inflater.inflate(R.layout.fragment_registro_producto, container, false);
        txtRDescription = (EditText) vis.findViewById(R.id.txtRDescription);
        txtRNProd = (EditText) vis.findViewById(R.id.txtRNProd);
        txtRPrice = (EditText) vis.findViewById(R.id.txtRPrice);
        btnRImage = (Button) vis.findViewById(R.id.btnRImage);
        progress = new ProgressDialog(getContext());
        v = VolleyS.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        req = v.getRequestQueue();
        btnRImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(Intent.ACTION_GET_CONTENT);
                log.setType("image/png");
                startActivityForResult(Intent.createChooser(log, "Seleccione"), 10);
            }
        });

        return vis;
    }

    private String cItoS(Bitmap bitm) {
        ByteArrayOutputStream bte = new ByteArrayOutputStream();
        bitm.compress(Bitmap.CompressFormat.PNG, 100, bte);
        byte[] array = bte.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
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
        Objects.requireNonNull(getActivity()).setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        Objects.requireNonNull(getActivity()).setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        Objects.requireNonNull(getActivity()).setProgressBarIndeterminateVisibility(false);
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
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
    public static registro_producto newInstance(String param1, String param2) {
        registro_producto fragment = new registro_producto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public registro_producto() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}
