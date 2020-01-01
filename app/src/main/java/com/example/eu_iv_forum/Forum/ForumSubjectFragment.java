package com.example.eu_iv_forum.Forum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eu_iv_forum.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForumSubjectFragment extends Fragment {

    String cate_id;
    private RecyclerView forumTopicListView;
    private List<ForumTopic> topic_list;
    private FirebaseFirestore firebaseFirestore;
    private ForumTopicAdapter forumTopicAdapter;

    public ForumSubjectFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cate_id=getArguments().get("id").toString();
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_forum_subject, container, false);
        topic_list=new ArrayList<>();
        forumTopicListView=view.findViewById(R.id.topic_list_view);

        forumTopicAdapter=new ForumTopicAdapter(topic_list);
        forumTopicListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        forumTopicListView.setAdapter(forumTopicAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);

        firebaseFirestore.collection("Forum/"+cate_id+"/Topics").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d("DATA COUNT", Integer.toString(queryDocumentSnapshots.size()));
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){
                            ForumTopic forumTopic=doc.getDocument().toObject(ForumTopic.class);
                            topic_list.add(forumTopic);
                            forumTopicAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        return view;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
