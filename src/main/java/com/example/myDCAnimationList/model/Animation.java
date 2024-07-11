package com.example.myDCAnimationList.model;

import jakarta.persistence.*;

@Entity
@Table(name = "animation")
public class Animation {
    @Id
    private Long id;
    private String title;
    private String year;
    private String rating;
    private String image_url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getImage_url() {
        return image_url;
    }
}
