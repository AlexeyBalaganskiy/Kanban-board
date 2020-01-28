package com.example.kanbanboard.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CardDao {

    @Insert
    void insert(Card card);
    @Update
    void update(Card card);

    @Query("DELETE FROM card_table WHERE id_card=:id")
    void deleteCard(int id);

    @Query("DELETE FROM card_table")
    void deleteAllCard();

    @Query("UPDATE card_table SET id_column = :id_column WHERE id_card = :id")
    void updateColumn(int id, int id_column);

    @Query("SELECT * FROM card_table WHERE id_column = :id")
    LiveData<List<Card>> getDataOnId(int id);

    @Query("SELECT SUM(cost) FROM card_table WHERE id_column=:id")
    LiveData<Double> sumCost(int id);

    @Query("SELECT MAX(id_card) FROM card_table")
    LiveData<Integer> a();

}
