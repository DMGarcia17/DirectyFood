package sv.edu.itca.santaana.directyfood;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class func {

    private Bitmap bit;
    private ProgressDialog progress;
    private RequestQueue req;
    private VolleyS v;
    private StringRequest str;
    private FragmentActivity frag;

    public func(VolleyS volleyS, RequestQueue requestQueue, StringRequest stringRequest, FragmentActivity f) {
        v = volleyS;
        req = v.getRequestQueue();
        frag = f;
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
            this.onPreStartConnection();
            req.add(request);
        }
    }

    public void onPreStartConnection() {
        frag.setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        frag.setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        frag.setProgressBarIndeterminateVisibility(false);
        Toast.makeText(frag, error, Toast.LENGTH_LONG).show();
    }
}
