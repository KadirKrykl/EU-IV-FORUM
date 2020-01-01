package com.example.eu_iv_forum.Forum;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class ForumCateId {
    @Exclude
    public String ForumCateId;

    public <T extends ForumCateId> T withId(@NonNull final String id) {
        this.ForumCateId = id;
        return (T) this;
    }
}
