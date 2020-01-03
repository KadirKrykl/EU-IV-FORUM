package com.example.eu_iv_forum.Province;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class CityId {

    @Exclude
    public String CityId;

    public <T extends CityId> T withId(@NonNull final String id) {
        this.CityId = id;
        return (T) this;
    }
}
