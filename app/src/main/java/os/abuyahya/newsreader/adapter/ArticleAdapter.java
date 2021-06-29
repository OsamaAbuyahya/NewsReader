package os.abuyahya.newsreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import os.abuyahya.newsreader.R;
import os.abuyahya.newsreader.model.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private ArrayList<Article> list = new ArrayList<>();
    private Context context;
    private OnItemClicked mListener;

    public interface OnItemClicked {
        void onItemClickListener(String url);
    }

    public void setOnClickItemListener(OnItemClicked mListener){
        this.mListener = mListener;
    }

    public ArticleAdapter(Context context) {
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtAuthor, txtComment;
        public ViewHolder(@NonNull View itemView, OnItemClicked mListener) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_title);
            txtAuthor = itemView.findViewById(R.id.txt_author);
            txtComment = itemView.findViewById(R.id.txt_comment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickListener(list.get(getAdapterPosition()).getUrl());
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
    }

    public void setList(ArrayList<Article> newList) {
//        int index = list.size();
        this.list.addAll(newList);
        notifyDataSetChanged();
//        notifyItemRangeChanged(index, newList.size());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

}
