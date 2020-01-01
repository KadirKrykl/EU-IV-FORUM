package com.example.eu_iv_forum.Forum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eu_iv_forum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumTopicSingleFragment extends Fragment {

    String topic_id;
    String cate_id;
    ForumTopic topic;
    private FirebaseFirestore firebaseFirestore;

    TextView topic_title;
    TextView topic_content;
    TextView topic_owner;
    TextView topic_created;

    public ForumTopicSingleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        topic_id=getArguments().get("topic_id").toString();
        cate_id=getArguments().get("cate_id").toString();
        firebaseFirestore = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.fragment_forum_topic_single, container, false);

        topic_title = view.findViewById(R.id.topic_title_txt);
        topic_content = view.findViewById(R.id.topic_content_txt);
        topic_owner = view.findViewById(R.id.topic_owner_txt);
        topic_created = view.findViewById(R.id.topic_created_txt);



        firebaseFirestore.collection("Forum/"+cate_id+"/Topics").document(topic_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String forumTopicId = document.getId();
                        ForumTopic forumTopic = document.toObject(ForumTopic.class).withId(forumTopicId);
                        topic_title.setText(forumTopic.title);
                        topic_content.setText(forumTopic.content);
                        String user_id = forumTopic.user_id;
                        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                String userName=task.getResult().getString("name");
                                topic_owner.setText("by " + userName);
                            } else {

                                //Firebase Exception

                            }
                            }
                        });
                        try {
                            long millSecs = forumTopic.getTimestamp().getTime();
                            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millSecs)).toString();
                            topic_created.setText(": "+dateString);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
