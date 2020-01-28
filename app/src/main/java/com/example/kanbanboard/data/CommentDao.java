package com.example.kanbanboard.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentDao {

    @Insert
    void insert(Comment comment);

    @Query("SELECT * FROM Comment WHERE id_card = :id")
    LiveData<List<Comment>> getCommentOnId(int id);
    @Query("DELETE FROM Comment WHERE id_card=:id")
    void deleteComments(int id);
    @Query("DELETE FROM Comment")
    void deleteAllComment();
}
