package sv.edu.itca.santaana.directyfood;

import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class vista_producto extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2, Company, img;
    private TextView tvNombre, tvPrecio, tvDescri;
    private Button btnInfoC;
    private ImageView imgFotografia, imgLogo;
    private JsonObjectRequest json;
    private RequestQueue req;
    String llave;

    public vista_producto() {
        // Required empty public constructor
    }

    public static vista_producto newInstance(String param1, String param2) {
        vista_producto fragment = new vista_producto();
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
            llave = getArguments().getString("pos");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vista_producto, container, false);
        tvNombre = v.findViewById(R.id.tvNombre);
        tvDescri = v.findViewById(R.id.tvDescri);
        tvPrecio = v.findViewById(R.id.tvPrecio);
        btnInfoC = v.findViewById(R.id.btnInfoC);
        imgFotografia = v.findViewById(R.id.imgFotografia);
        imgLogo = v.findViewById(R.id.imgLog);
        req = Volley.newRequestQueue(getContext());
        carga();
        btnInfoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vista_empresa in = new vista_empresa();
                Bundle args = new Bundle();
                args.putString("id", Company);
                in.setArguments(args);
                FragmentTransaction frag = getFragmentManager().beginTransaction();
                frag.replace(R.id.content_main, in);
                frag.addToBackStack(null);
                frag.commit();
            }
        });
        return v;
    }

    private void carga() {
        String url = getResources().getString(R.string.url);
        url = url + "api.php?act=5&key=AJZfpodVtaCFQO5TKpV8PE7qLlKiAbNglPeNhoiudyD3LsEE2RlFq6pe&filtro="+llave;
        json = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        req.add(json);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("products");
        try {
            JSONObject obj = jsonArray.getJSONObject(0);
            tvNombre.setText(obj.optString("name"));
            tvDescri.setText(obj.optString("description"));
            tvPrecio.setText("$ "+obj.optString("price"));
            Company = obj.optString("company");
            img = "http://192.168.43.223/Directy/".concat(obj.optString("img"));
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
                imgFotografia.setImageBitmap(response);
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
