package com.example.kanbanboard.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kanbanboard.data.Card;
import com.example.kanbanboard.data.Comment;
import com.example.kanbanboard.repository.CardRepository;
import com.example.kanbanboard.repository.CommentRepository;

import java.util.List;

public class CommentViewModel extends AndroidViewModel {
    private CommentRepository commentRepository;
    private LiveData<List<Comment>> allComment;
    public CommentViewModel(@NonNull Application application) {
        super(application);
        commentRepository = new CommentRepository(application);
    }
    public LiveData<List<Comment>> getAllCards (int id)
    {
        allComment = commentRepository.getAllComment(id);
        return allComment;
    }
    public void deleteComment(int a){
        commentRepository.deleteComment(a);
    }
    public void insert (Comment comment){
        commentRepository.insert(comment);
    }
    public void deleteAllComment(){
        commentRepository.deleteAllComment();
    }
}
