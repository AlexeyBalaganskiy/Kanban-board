package com.example.kanbanboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kanbanboard.adapter.CommentAdapter;
import com.example.kanbanboard.data.Comment;
import com.example.kanbanboard.viewModel.CardViewModel;
import com.example.kanbanboard.viewModel.CommentViewModel;

import java.util.Date;

public class AddCardActivity extends AppCompatActivity {
    public static final String EXTRA_ACTOR = "ACTOR";
    public static final String EXTRA_DESCRIPTION = "DESCRIPTION";
    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_ID_COLUMN = "ID_COLUMN";
    public static final String EXTRA_COST = "COST";
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_DATE = "DATE";
    private int idColumn;
    private int parameterForMenu =0;
    private EditText editTextActor;
    private EditText editTextDescription;
    private EditText editTextCost;
    private EditText editTextComment;
    private EditText editTextTitle;

    private CardViewModel cardViewModel;
    private CommentViewModel commentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        editTextActor = findViewById(R.id.actor_new);
        editTextDescription = findViewById(R.id.description_new);
        editTextCost = findViewById(R.id.cost_new);
        editTextComment = findViewById(R.id.comment);
        editTextTitle = findViewById(R.id.title);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ImageButton button = findViewById(R.id.send);
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Просмотр карточки");
            parameterForMenu =1;
            modeEditText(editTextActor,false,0);
            modeEditText(editTextDescription,false,0);
            modeEditText(editTextCost,false,0);
            modeEditText(editTextTitle,false,0);
            idColumn = intent.getIntExtra(EXTRA_ID_COLUMN,-1);
            editTextActor.setText(intent.getStringExtra(EXTRA_ACTOR));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextCost.setText(Double.toString(intent.getDoubleExtra(EXTRA_COST,-1)));
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));

        }
        else {
            setTitle("Создание карточки");
            parameterForMenu =0;
            button.setVisibility(View.INVISIBLE);
            editTextComment.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        final CommentAdapter adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);
        commentViewModel.getAllCards(getIntent().getIntExtra(EXTRA_ID,-1)).observe(this, comments -> adapter.setComments(comments));

        button.setOnClickListener(v -> {
            String comment = editTextComment.getText().toString();
            editTextComment.setText("");
            Date date = new Date();
            date.getTime();
            Comment comment1 = new Comment(getIntent().getIntExtra(EXTRA_ID,-1),comment,date);
            commentViewModel.insert(comment1);
        });
    }
    public void modeEditText(EditText text,boolean value,int parameter)
    {   if (parameter==0) { text.setFocusable(value);
    }else
    {
        text.setFocusableInTouchMode(value);
    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (parameterForMenu ==1) {
            menuInflater.inflate(R.menu.add_card_menu, menu);
            return true;
        } else
        {
            menuInflater.inflate(R.menu.add_card_menu_create, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_card:
                saveCard();
                return true;

            case R.id.edit_card:
                editText();
                //saveCard();
                return true;

            case R.id.delete_card:
                deleteCard();
                return true;
            case R.id.help:
                Toast.makeText(this, "Необходима помощь в решении задачи №" + getIntent().getIntExtra(EXTRA_ID,-1), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editText() {
       modeEditText(editTextActor,true,1);
       modeEditText(editTextDescription,true,1);
       modeEditText(editTextCost,true,1);
       modeEditText(editTextTitle,true,1);
        setTitle("Редактирование карточки");
    }

    private void deleteCard(){

        commentViewModel.deleteComment(getIntent().getIntExtra(EXTRA_ID,-1));
        cardViewModel.deleteCard(getIntent().getIntExtra(EXTRA_ID,-1));
        finish();
    }
    private void saveCard() {

        String actor = editTextActor.getText().toString();
        String description = editTextDescription.getText().toString();
        String cost = editTextCost.getText().toString();
        String title = editTextTitle.getText().toString();
        String date = getIntent().getStringExtra(EXTRA_DATE);
        if (actor.trim().isEmpty()||title.trim().isEmpty()||cost.trim().isEmpty()||description.trim().isEmpty()){
            Toast.makeText(this,"Введите данные",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ACTOR,actor);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_ID_COLUMN,idColumn);
        data.putExtra(EXTRA_COST,cost);
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DATE,date);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1)
        {
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,data);
        finish();
    }
}
