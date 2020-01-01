package com.example.eu_iv_forum.Forum;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eu_iv_forum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class ForumTopicAdapter extends RecyclerView.Adapter<ForumTopicAdapter.ViewHolder>{

    public List<ForumTopic> topic_list;
    private final ForumSubjectFragment.OnTopicInteractionListener mListener;
    public Context context;
    private final String cateId;

    private FirebaseFirestore firebaseFirestore;

    public ForumTopicAdapter(List<ForumTopic> topic_list,ForumSubjectFragment.OnTopicInteractionListener mListener,String cateId) {
        this.topic_list = topic_list;
        this.mListener=mListener;
        this.cateId=cateId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_topic_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String forumTopicId=topic_list.get(position).ForumTopicId;
        String title_data = topic_list.get(holder.getAdapterPosition()).getTitle();
        holder.setTitleText(title_data);
        String user_id_data= topic_list.get(holder.getAdapterPosition()).getUser_id();
        Log.d("user id", user_id_data);
        firebaseFirestore.collection("Users").document(user_id_data).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");
                    holder.setUserData(userName);


                } else {

                    //Firebase Exception

                }
            }
        });


        try {
            long millSecs = topic_list.get(holder.getAdapterPosition()).getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millSecs)).toString();
            holder.setTime(dateString);
        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (null != mListener) {
                Log.d("click", "onClick:Topic ");
                mListener.onTopicSelected(forumTopicId,cateId);
            }
            }
        });

    }

    @Override
    public int getItemCount() {return topic_list.size();}

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

        public void setUserData(String userData){
            userView = mView.findViewById(R.id.topic_created_whom);
            userView.setText(userData);
        }

    }
}
