package com.example.kanbanboard.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.kanbanboard.data.AppDatabase;
import com.example.kanbanboard.data.Comment;
import com.example.kanbanboard.data.CommentDao;

import java.util.List;

public class CommentRepository {
    private CommentDao commentDao;
    private LiveData<List<Comment>> allcomment;
    public CommentRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        commentDao = database.commentDao();
    }
    public LiveData<List<Comment>> getAllComment(int id) {
        allcomment = commentDao.getCommentOnId(id);
        return allcomment;
    }
    public  void insert (Comment comment) {
        new CommentRepository.InsertCommentAsyncTask(commentDao).execute(comment);

    }
    public  void deleteComment (int a) {
        new CommentRepository.DeleteCommentByIdAsyncTask(commentDao,a).execute();
    }
    public  void deleteAllComment() {
        new CommentRepository.DeleteAllCommentAsyncTask(commentDao).execute();

    }
    private static class InsertCommentAsyncTask extends AsyncTask<Comment,Void,Void>
    {
        private CommentDao commentDao;
        private InsertCommentAsyncTask(CommentDao commentDao)
        {
            this.commentDao=commentDao;
        }
        @Override
        protected Void doInBackground(Comment...comments)
        {
            commentDao.insert(comments[0]);
            return null;
        }
    }
    private static class DeleteCommentByIdAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private CommentDao commentDao;

        private int id;
        public DeleteCommentByIdAsyncTask (CommentDao commentDao, int id)
        {this.commentDao=commentDao;

            this.id=id;
        }

        @Override
        protected Void doInBackground(Void...voids)
        {

            commentDao.deleteComments(id);
            return null;
        }
    }
    private static class DeleteAllCommentAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private CommentDao commentDao;
        private DeleteAllCommentAsyncTask(CommentDao commentDao)
        {
            this.commentDao=commentDao;
        }
        @Override
        protected Void doInBackground(Void...voids)
        {
            commentDao.deleteAllComment();
            return null;
        }
    }
}
