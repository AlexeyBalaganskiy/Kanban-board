package com.example.kanbanboard.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanbanboard.R;
import com.example.kanbanboard.data.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<Comment> comments = new ArrayList<>();
    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item,parent,false);
        return new CommentHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
            Comment comment = comments.get(position);
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(comment.getDate());
            holder.textViewComment.setText(comment.getComment());
            holder.textViewDate.setText(timeStamp);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView textViewComment;
        CommentHolder(@NonNull View itemView) {
            super(itemView);
            textViewComment =itemView.findViewById(R.id.comment);
            textViewDate =itemView.findViewById(R.id.date_comment);
        }
    }


}
