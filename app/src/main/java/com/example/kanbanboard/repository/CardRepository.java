package com.example.kanbanboard.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.kanbanboard.data.AppDatabase;
import com.example.kanbanboard.data.Card;
import com.example.kanbanboard.data.CardDao;

import java.util.List;

public class CardRepository {
    private CardDao cardDao;
    private LiveData<List<Card>> allcards1;
    private LiveData<List<Card>> allcards2;
    private LiveData<List<Card>> allcards3;
    private LiveData<List<Card>> allcards4;
    private LiveData<Integer> test;
    private LiveData<Double> test1;

    public CardRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        cardDao = database.cardDao();
        test= cardDao.a();

        allcards1 = cardDao.getDataOnId(0);
        allcards2 = cardDao.getDataOnId(1);

        allcards3 = cardDao.getDataOnId(2);
        allcards4 = cardDao.getDataOnId(3);

    }
    public  void insert (Card card) {
        new InsertCardAsyncTask(cardDao).execute(card);

    }
    public  void update (Card card) {
        new UpdateCardAsyncTask(cardDao).execute(card);

    }
    public  void updateColumn (int a, int b) {
        new UpdateColumnCardAsyncTask(cardDao,a,b).execute();
    }
    public  void deleteCard (int a) {
        new CardRepository.DeleteCardByIdAsyncTask(cardDao,a).execute();
    }
    public  void deleteAllCards() {
        new DeleteAllCardsAsyncTask(cardDao).execute();

    }
    public LiveData<Integer> b()
    {
        return test;
    }
    public LiveData<Double> c(int a)
    {   test1 = cardDao.sumCost(a);
        return test1;
    }
    public LiveData<List<Card>> getDataOnId() {

        return allcards1;
    }
    public LiveData<List<Card>> getDataOnId1() {

        return allcards2;
    }
    public LiveData<List<Card>> getDataOnId2() {

        return allcards3;
    }
    public LiveData<List<Card>> getDataOnId3() {

        return allcards4;
    }

    private static class InsertCardAsyncTask extends AsyncTask<Card,Void,Void>
    {
        private CardDao cardDao;
        private InsertCardAsyncTask(CardDao cardDao)
        {
            this.cardDao=cardDao;
        }
        @Override
        protected Void doInBackground(Card...cards)
        {
            cardDao.insert(cards[0]);
            return null;
        }
    }

    private static class DeleteAllCardsAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private CardDao cardDao;
        private DeleteAllCardsAsyncTask(CardDao cardDao)
        {
            this.cardDao=cardDao;
        }
        @Override
        protected Void doInBackground(Void...voids)
        {
            cardDao.deleteAllCard();
            return null;
        }
    }

    private static class UpdateCardAsyncTask extends AsyncTask<Card,Void,Void>
    {
        private CardDao cardDao;
        private UpdateCardAsyncTask(CardDao cardDao)
        {
            this.cardDao=cardDao;
        }
        @Override
        protected Void doInBackground(Card...cards)
        {
            cardDao.update(cards[0]);
            return null;
        }
    }
    private static class UpdateColumnCardAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private CardDao cardDao;
        private int id_column;
        private int id;
        public UpdateColumnCardAsyncTask (CardDao cardDao, int id,int id_column)
        {
            this.cardDao=cardDao;
            this.id_column=id_column;
            this.id=id;
        }

        @Override
        protected Void doInBackground(Void...voids)
        {
            cardDao.updateColumn(id,id_column);
            return null;
        }
    }
    private static class DeleteCardByIdAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private CardDao cardDao;

        private int id;
        public DeleteCardByIdAsyncTask (CardDao cardDao, int id)
        {
            this.cardDao=cardDao;
            this.id=id;
        }

        @Override
        protected Void doInBackground(Void...voids)
        {

            cardDao.deleteCard(id);
            return null;
        }
    }
}
