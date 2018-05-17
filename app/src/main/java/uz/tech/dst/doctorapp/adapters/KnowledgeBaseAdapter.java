package uz.tech.dst.doctorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.ArticleInfoActivity;
import uz.tech.dst.doctorapp.R;
import uz.tech.dst.doctorapp.models.Article;

/**
 * Created by Fz_Designs on 3/30/2017.
 */

public class KnowledgeBaseAdapter extends RecyclerView.Adapter<KnowledgeBaseAdapter.ViewHolder> {

    private ArrayList<Article> data;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView articleName, articleType, authorName;
        private LinearLayout linear;


        public ViewHolder(View v) {
            super(v);
            articleName = (TextView) v.findViewById(R.id.articleName);
            articleType = (TextView) v.findViewById(R.id.articleType);
            authorName = (TextView)v.findViewById(R.id.authorName);
            linear = (LinearLayout) v.findViewById(R.id.linear);
        }
    }

    public KnowledgeBaseAdapter(Context cont, ArrayList<Article> dataSet) {
        data = dataSet;
        context = cont;
    }

    @Override
    public KnowledgeBaseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_knowledge_base, viewGroup, false);

        return new KnowledgeBaseAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final KnowledgeBaseAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.articleName.setText(data.get(position).name);
        viewHolder.articleType.setText(data.get(position).type);
        viewHolder.authorName.setText(data.get(position).authorName);

        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.linear.animate().translationZ(10).setDuration(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.linear.animate().translationZ(2).setDuration(300);
                        Intent intent = new Intent(context, ArticleInfoActivity.class);
                        intent.putExtra("name", data.get(position).name);
                        intent.putExtra("info", data.get(position).info);
                        context.startActivity(intent);
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
