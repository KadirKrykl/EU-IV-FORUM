package com.example.eu_iv_forum.Forum;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eu_iv_forum.R;

import java.util.List;

public class ForumCateAdapter extends RecyclerView.Adapter<ForumCateAdapter.ViewHolder> {

    public List<ForumCategory> cate_list;
    private final ForumCategorizeFragment.OnCateListInteractionListener mListener;


    public ForumCateAdapter(List<ForumCategory> cate_list,ForumCategorizeFragment.OnCateListInteractionListener mListener){
        this.cate_list=cate_list;
        this.mListener=mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_cate_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final String forumCategoryId=cate_list.get(position).ForumCateId;
        String desc_data = cate_list.get(holder.getAdapterPosition()).getDescription();
        holder.setDescText(desc_data);
        String title_data = cate_list.get(holder.getAdapterPosition()).getTitle();
        holder.setTitleText(title_data);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Log.d("click", "onClick: ");
                    mListener.onCateSelected(forumCategoryId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cate_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView descView;
        private TextView titleView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setDescText(String descText){
            descView = mView.findViewById(R.id.cate_desc);
            descView.setText(descText);
        }
        public void setTitleText(String titleText){
            titleView = mView.findViewById(R.id.cate_title);
            titleView.setText(titleText);
        }

    }

}
