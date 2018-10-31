package sv.edu.itca.santaana.directyfood;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductsAdap extends ArrayAdapter<Products> {

    public ProductsAdap(Context context, List<Products> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        ProductsHolder prod = null;
        if(listItem !=null){
            prod = (ProductsHolder) listItem.getTag();
        }else{
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_rc, parent, false);
            prod = new ProductsHolder(listItem);
        }

        Products p = getItem(position);
        if(p!=null){
            prod.txtItTittle.setText(p.getNombre());
            prod.txtItDesc.setText(p.getDesc());
            prod.txtItPrice.setText(p.getPrecio());
        }


        return listItem;
    }

    public class ProductsHolder extends RecyclerView.ViewHolder {
        TextView txtItTittle, txtItDesc, txtItPrice;

        public ProductsHolder(View itemView) {
            super(itemView);
            txtItDesc = itemView.findViewById(R.id.tvItDesc);
            txtItPrice = itemView.findViewById(R.id.tvItPrice);
            txtItTittle = itemView.findViewById(R.id.tvItTittle);

        }
    }
}
