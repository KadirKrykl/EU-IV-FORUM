package com.example.eu_iv_forum.Forum;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_iv_forum.R;

import java.util.Date;
import java.util.List;

public class ForumTopicAdapter extends RecyclerView.Adapter<ForumTopicAdapter.ViewHolder>{

    public List<ForumTopic> topic_list;
    public Context context;

    public ForumTopicAdapter(List<ForumTopic> topic_list) {
        this.topic_list = topic_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_topic_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title_data = topic_list.get(holder.getAdapterPosition()).getTitle();
        holder.setTitleText(title_data);
        String user_id_data= topic_list.get(holder.getAdapterPosition()).getUser_id();
        // User will return
        try {
            long millSecs = topic_list.get(holder.getAdapterPosition()).getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millSecs)).toString();
            holder.setTime(dateString);
        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return topic_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView titleView;
        private TextView userView;
        private TextView createdTimeView;
        private TextView repliesView;
        private TextView lastRepliesView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitleText(String titleText){
            titleView = mView.findViewById(R.id.topic_title);
            titleView.setText(titleText);
        }

        public void setTime(String date) {
            createdTimeView = mView.findViewById(R.id.topic_created_time);
            createdTimeView.setText(date);
        }

    }
}
