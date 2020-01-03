package com.example.eu_iv_forum.Forum;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eu_iv_forum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumTopicSingleFragment extends Fragment {

    private String topic_id;
    private String cate_id;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private TextView topic_title;
    private TextView topic_content;
    private TextView topic_owner;
    private TextView topic_created;

    private ImageButton btn_back;
    private ImageButton btn_reply;

    private EditText txt_reply;

    private String current_user_id;

    private RecyclerView forumReplyListView;
    private List<ForumReply> reply_list;
    ForumReplyAdapter forumReplyAdapter;

    public ForumTopicSingleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        topic_id=getArguments().get("topic_id").toString();
        cate_id=getArguments().get("cate_id").toString();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_forum_topic_single, container, false);

        topic_title = view.findViewById(R.id.topic_title_txt);
        topic_content = view.findViewById(R.id.topic_content_txt);
        topic_owner = view.findViewById(R.id.topic_owner_txt);
        topic_created = view.findViewById(R.id.topic_created_txt);

        btn_back = view.findViewById(R.id.btn_backward);



        //++++++++++++
        //CREATE REPLY
        //++++++++++++
        btn_reply = view.findViewById(R.id.reply_button);
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        txt_reply = view.findViewById(R.id.topic_reply_txt);

        btn_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = txt_reply.getText().toString();
                if (!TextUtils.isEmpty(reply)) {
                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("reply", reply);
                    postMap.put("user_id", current_user_id);
                    postMap.put("time_stamp", FieldValue.serverTimestamp());
                    firebaseFirestore.collection("Forum/"+cate_id+"/Topics/"+topic_id+"/Reply").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Reply was created", Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(getActivity(), ForumsActivity.class);
                            Bundle b = new Bundle();
                            b.putString("cate_id", cate_id);
                            b.putString("topic_id", topic_id);
                            mainIntent.putExtras(b);
                            startActivity(mainIntent);
                        }
                        }
                    });
                }
            }
        });

        //------------
        //CREATE REPLY
        //------------


        reply_list=new ArrayList<>();
        forumReplyListView=view.findViewById(R.id.reply_list_view);
        forumReplyAdapter=new ForumReplyAdapter(reply_list);
        forumReplyListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        forumReplyListView.setAdapter(forumReplyAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forumIntent = new Intent(getActivity(), ForumsActivity.class);
                Bundle b = new Bundle();
                b.putString("cate_id", cate_id);
                forumIntent.putExtras(b);
                startActivity(forumIntent);
            }
        });



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
                            String dateString = DateFormat.format("MM/dd/yyyy  H:m", new Date(millSecs)).toString();
                            topic_created.setText(": "+dateString);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        firebaseFirestore.collection("Forum/"+cate_id+"/Topics/"+topic_id+"/Reply").orderBy("time_stamp", Query.Direction.ASCENDING).addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d("REPLY", Integer.toString(queryDocumentSnapshots.size()));
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){
                            //String forumReplyId=doc.getDocument().getId();
                            ForumReply forumTopic=doc.getDocument().toObject(ForumReply.class);
                            reply_list.add(forumTopic);
                            forumReplyAdapter.notifyDataSetChanged();
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
