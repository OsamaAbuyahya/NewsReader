package os.abuyahya.newsreader.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import os.abuyahya.newsreader.R;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.viewmodel.ArticleViewModel;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private ArrayList<Article> list = new ArrayList<>();
    private Context context;
    private OnItemClicked mListener;
    ArticleViewModel viewModel;
    public interface OnItemClicked {
        void onItemClickListener(String url, String title);
    }

    public void setOnClickItemListener(OnItemClicked mListener){
        this.mListener = mListener;
    }

    public ArticleAdapter(Context context, ArticleViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtAuthor, txtComment;
        ImageView icNext;
        public ViewHolder(@NonNull View itemView, OnItemClicked mListener) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_title);
            txtAuthor = itemView.findViewById(R.id.txt_author);
            txtComment = itemView.findViewById(R.id.txt_comment);
            icNext = itemView.findViewById(R.id.ic_next);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = list.get(getAdapterPosition()).getUrl();
                    String title = list.get(getAdapterPosition()).getTitle();

                    mListener.onItemClickListener(url, title);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.design_article_item, parent, false);

        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtAuthor.setText(list.get(position).getAuthor());
        holder.txtComment.setText(String.valueOf(list.get(position).getNum_comments()));

        if (viewModel.isOffline(list.get(position).getTitle())){
            holder.icNext.setVisibility(View.VISIBLE);
        }
    }

    public void setList(ArrayList<Article> newList) {
        this.list.addAll(newList);
        notifyDataSetChanged();
        Log.d("ArticleAdapter", "Size Set= "+list.size());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

}
