package com.ahmedM.mylibrary.Authors;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Authors {
    @Column(name = "author_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    public Authors(int id, String name, String avatar) {
        this.authorId = id;
        this.name = name;
        this.avatar = avatar;
    }

    public Authors() {
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
