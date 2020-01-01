package com.example.eu_iv_forum.Forum;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class ForumTopicId {
    @Exclude
    public String ForumTopicId;

    public <T extends ForumTopicId> T withId(@NonNull final String id) {
        this.ForumTopicId = id;
        return (T) this;
    }
}
