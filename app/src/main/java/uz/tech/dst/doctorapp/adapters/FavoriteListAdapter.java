package uz.tech.dst.doctorapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.R;
import uz.tech.dst.doctorapp.models.Favorite;

/**
 * Created by Fz_Designs on 3/26/2017.
 */

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {

    private ArrayList<Favorite> data;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, type;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            type = (TextView) v.findViewById(R.id.type);

        }
    }

    public FavoriteListAdapter(Context cont, ArrayList<Favorite> dataSet) {
        data = dataSet;
        context = cont;
    }

    @Override
    public FavoriteListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_favorite_list, viewGroup, false);
        return new FavoriteListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FavoriteListAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(data.get(position).name);
        viewHolder.type.setText(data.get(position).type);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

