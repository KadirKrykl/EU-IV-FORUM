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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class ForumReplyAdapter  extends RecyclerView.Adapter<ForumReplyAdapter.ViewHolder>{

    public List<ForumReply> reply_list;
    public Context context;
    private FirebaseFirestore firebaseFirestore;

    public ForumReplyAdapter(List<ForumReply> reply_list) {
        this.reply_list = reply_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_reply_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ForumReplyAdapter.ViewHolder holder, int position) {
        String reply_data = reply_list.get(holder.getAdapterPosition()).getReply();
        holder.setReplyText(reply_data);
        String user_id_data= reply_list.get(holder.getAdapterPosition()).getUser_id();
        firebaseFirestore.collection("Users").document(user_id_data).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");
                    holder.setUserText(userName);


                } else {

                    //Firebase Exception

                }
            }
        });
        try {
            long millSecs = reply_list.get(holder.getAdapterPosition()).getTime_stamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy  H:m", new Date(millSecs)).toString();
            holder.setTimeText(dateString);
        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return reply_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView replyView;
        private TextView userView;
        private TextView createdTimeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setReplyText(String replyText){
            replyView = mView.findViewById(R.id.reply_content_txt);
            replyView.setText(replyText);
        }

        public void setUserText(String userText){
            userView = mView.findViewById(R.id.reply_owner_txt);
            userView.setText("by "+userText);
        }

        public void setTimeText(String timeText){
            createdTimeView = mView.findViewById(R.id.reply_created_txt);
            createdTimeView.setText(" : "+timeText);
        }
    }
}
