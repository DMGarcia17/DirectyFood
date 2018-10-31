package sv.edu.itca.santaana.directyfood;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class inicio extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ArrayList<Products> list;
    private RequestQueue req;
    TextView tv;
    private JsonObjectRequest json;
    private ListView ltbProducts;


    public inicio() {
    }

    public static inicio newInstance(String param1, String param2) {
        inicio fragment = new inicio();
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
        View v = inflater.inflate(R.layout.fragment_inicio, container, false);
        list = new ArrayList<>();
        tv = v.findViewById(R.id.textView8);
        ltbProducts = v.findViewById(R.id.ltbProducts);
        req = Volley.newRequestQueue(getContext());
        cargarWebService();
        ltbProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return v;
    }

    private void cargarWebService() {
        String url = getResources().getString(R.string.url);
        url = url + "api.php?act=1&key=AJZfpodVtaCFQO5TKpV8PE7qLlKiAbNglPeNhoiudyD3LsEE2RlFq6pe";
        json = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        req.add(json);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        tv.setText(error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Products p = null;
        JSONArray jsonArray = response.optJSONArray("products");
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                p = new Products();
                JSONObject obj = jsonArray.getJSONObject(i);
                p.setNombre(obj.optString("name"));
                p.setDesc(obj.optString("description"));
                p.setPrecio("$"+obj.optString("price"));
                list.add(p);
            }
            ProductsAdap pro = new ProductsAdap(getContext(), list);
            ltbProducts.setAdapter(pro);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
