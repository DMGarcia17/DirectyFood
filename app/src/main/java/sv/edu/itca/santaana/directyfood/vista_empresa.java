package sv.edu.itca.santaana.directyfood;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class vista_empresa extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tvNCompany, tvUbicacion, tvCompaDesc, tvTel;
    private ImageView imgLog;
    private String mParam1, img;
    private String mParam2;
    private String id;
    RequestQueue req;
    JsonObjectRequest json;

    public vista_empresa() {
        // Required empty public constructor
    }

    public static vista_empresa newInstance(String param1, String param2) {
        vista_empresa fragment = new vista_empresa();
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
            id = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.vista_empresa, container, false);
        tvNCompany = v.findViewById(R.id.tvNCompany);
        tvCompaDesc = v.findViewById(R.id.tvCompaDesc);
        tvUbicacion = v.findViewById(R.id.tvUbicacion);
        tvTel = v.findViewById(R.id.tvTel);
        imgLog =v.findViewById(R.id.imgLog);
        req = Volley.newRequestQueue(getContext());
        carga();

        return v;
    }

    private void carga() {
        String url = getResources().getString(R.string.url);
        url = url + "api.php?act=5&key=AJZfpodVtaCFQO5TKpV8PE7qLlKiAbNglPeNhoiudyD3LsEE2RlFq6pe&filtro="+id;
        json = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        req.add(json);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("products");
        try {
            JSONObject obj = jsonArray.getJSONObject(0);
            tvNCompany.setText(obj.optString("c_name"));
            tvCompaDesc.setText(obj.optString("description"));
            tvTel.setText("$ "+obj.optString("tel"));
            img = "http://192.168.43.223/Directy/".concat(obj.optString("logo"));
            cargaImg();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void cargaImg() {
        img = img.replace(" ", "%20");
        ImageRequest imgR = new ImageRequest(img, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgLog.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        req.add(imgR);
    }
}
