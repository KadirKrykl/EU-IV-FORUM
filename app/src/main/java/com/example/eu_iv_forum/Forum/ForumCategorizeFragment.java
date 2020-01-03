package com.example.eu_iv_forum.Forum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class ForumCategorizeFragment extends Fragment {

    private RecyclerView forumCateListView;
    private List<ForumCategory> cate_list;
    private FirebaseFirestore firebaseFirestore;
    private ForumCateAdapter forumCateAdapter;
    private OnCateListInteractionListener mListener;

    public ForumCategorizeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_forum_categorize, container, false);
        cate_list = new ArrayList<>();
        forumCateListView = view.findViewById(R.id.forum_cate_list);

        forumCateAdapter=new ForumCateAdapter(cate_list,mListener);
        forumCateListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        forumCateListView.setAdapter(forumCateAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Forum").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String forumCategoryId=doc.getDocument().getId();
                        ForumCategory forumCate=doc.getDocument().toObject(ForumCategory.class).withId(forumCategoryId);
                        cate_list.add(forumCate);
                        forumCateAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCateListInteractionListener) {
            mListener = (OnCateListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNoteListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCateListInteractionListener {
        void onCateSelected(String item);
    }
}
