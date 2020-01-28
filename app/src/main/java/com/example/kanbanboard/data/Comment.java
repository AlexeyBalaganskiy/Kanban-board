package com.example.kanbanboard.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Card.class,parentColumns = "id_card",childColumns = "id_card"))
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_card;
    private String comment;
    private Date date;

    public Comment(int id_card, String comment, Date date) {
        this.id_card = id_card;
        this.comment = comment;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
