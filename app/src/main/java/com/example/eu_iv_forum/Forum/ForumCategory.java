package com.example.eu_iv_forum.Forum;


public class ForumCategory extends ForumCateId{

    public String title;
    public String description;

    public ForumCategory() {}

    public ForumCategory(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
