package com.example.kanbanboard.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kanbanboard.repository.CardRepository;
import com.example.kanbanboard.data.Card;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository repository;
    private LiveData<List<Card>> allCards1;
    private LiveData<List<Card>> allCards2;
    private LiveData<List<Card>> allCards3;
    private LiveData<Integer> a;
    private LiveData<Double> b;

    private LiveData<List<Card>> allCards4;




    public CardViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
            a= repository.b();
        allCards1=repository.getDataOnId();
        allCards2=repository.getDataOnId1();
        allCards3=repository.getDataOnId2();
        allCards4=repository.getDataOnId3();

    }

    public void insert (Card card){
        repository.insert(card);
    }
    public void update(Card card){
        repository.update(card);
    }
    public void updateColumn(int a, int b){
        repository.updateColumn(a,b);
    }
    public void deleteCard(int a){
        repository.deleteCard(a);
    }
    public void deleteAllCard(){
        repository.deleteAllCards();
    }
    public LiveData<List<Card>> getDataOnId ()
    {
        return allCards1;
    }
    public LiveData<Integer> a ()
    {
        return a;
    }
    public LiveData<Double> b (int a)
    {   b = repository.c(a);
        return b;
    }

    public LiveData<List<Card>> getDataOnId1 ()
    {
        return allCards2;

    }    public LiveData<List<Card>> getDataOnId2 ()
    {
        return allCards3;
    }
    public LiveData<List<Card>> getDataOnId3 ()
    {
        return allCards4;
    }

}
