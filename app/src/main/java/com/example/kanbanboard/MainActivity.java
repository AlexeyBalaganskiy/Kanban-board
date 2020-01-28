package com.example.kanbanboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kanbanboard.adapter.ItemAdapter;
import com.example.kanbanboard.data.Card;
import com.example.kanbanboard.dragItem.MyColumnDragItem;
import com.example.kanbanboard.dragItem.MyDragItem;
import com.example.kanbanboard.viewModel.CardViewModel;
import com.example.kanbanboard.viewModel.CommentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.woxthebox.draglistview.BoardView;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CARD_REQUEST = 1;
    public static final int EDIT_CARD_REQUEST = 2;
    int maxId =0;
    double sumOldCost;
    double sumNewCost;
    private CardViewModel cardViewModel;
    private CommentViewModel commentViewModel;
    private BoardView mBoardView;
    int idCardForUpdate = 0;
    String [] nameColumn= new String[4];

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        for (int i = 0; i < 4; i++) {
            nameColumn[i]= Column.values()[i].toString();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
             Intent intent = new Intent(MainActivity.this,AddCardActivity.class);
             startActivityForResult(intent,ADD_CARD_REQUEST);
        });
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);

        mBoardView = findViewById(R.id.board_view);
        mBoardView.setSnapToColumnsWhenScrolling(true);
        mBoardView.setSnapToColumnWhenDragging(true);
        mBoardView.setSnapDragItemToTouch(true);
        mBoardView.setSnapToColumnInLandscape(false);
        mBoardView.setColumnSnapPosition(BoardView.ColumnSnapPosition.CENTER);
        mBoardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onItemDragStarted(int column, int row) {

                final ItemAdapter adapter = new ItemAdapter();
                if (column == 0) {
                    cardViewModel.getDataOnId().observe(MainActivity.this, adapter::setCards);
                } else if (column == 1)
                {
                    cardViewModel.getDataOnId1().observe(MainActivity.this, adapter::setCards);
                } else if (column == 2)
                {
                    cardViewModel.getDataOnId2().observe(MainActivity.this, adapter::setCards);
                }
                else if (column == 3)
                {
                    cardViewModel.getDataOnId3().observe(MainActivity.this, adapter::setCards);
                }
                idCardForUpdate=(int)adapter.getUniqueItemId(row);
            }

            @Override
            public void onItemDragEnded(int fromColumn, int fromRow, int toColumn, int toRow) {
                cardViewModel.updateColumn(idCardForUpdate,toColumn);
            }

            @Override
            public void onItemChangedPosition(int oldColumn, int oldRow, int newColumn, int newRow) {
            }

            @Override
            public void onItemChangedColumn(int oldColumn, int newColumn) {
                recalculationItem(oldColumn, newColumn);
            }

            @Override
            public void onFocusedColumnChanged(int oldColumn, int newColumn) {

            }
        });
        mBoardView.setBoardCallback(new BoardView.BoardCallback() {
            @Override
            public boolean canDragItemAtPosition(int column, int dragPosition) {
                return true;
            }

            @Override
            public boolean canDropItemAtPosition(int oldColumn, int oldRow, int newColumn, int newRow) {
                return true;

            }
        });
        resetBoard();
        cardViewModel.a().observe(this, integer -> {
             if (integer!=null) {
                 final int a = integer;
                 getMaxId(a);
             }
             else
             {
                 getMaxId(0);
             }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CARD_REQUEST && resultCode == RESULT_OK) {
            String actor = data.getStringExtra(AddCardActivity.EXTRA_ACTOR);
            String description = data.getStringExtra(AddCardActivity.EXTRA_DESCRIPTION);
            String cost = data.getStringExtra(AddCardActivity.EXTRA_COST);
            String sitle = data.getStringExtra(AddCardActivity.EXTRA_TITLE);
            Date date = new Date();
            date.getTime();
            System.out.println(date);
            Card card = new Card(actor, description);
            System.out.println(date);
            card.setDate(date);
            card.setId_column(0);
            card.setId_card(maxId +1);
            card.setTitle(sitle);
            card.setCost(Double.parseDouble(cost));
            cardViewModel.insert(card);
        }
        else if (requestCode==EDIT_CARD_REQUEST &&resultCode==RESULT_OK)
        {
            int id = data.getIntExtra(AddCardActivity.EXTRA_ID,-1);
            int idColumn = data.getIntExtra(AddCardActivity.EXTRA_ID_COLUMN,-1);
            if (id == -1)
            {
                return;
            }
            String actor = data.getStringExtra(AddCardActivity.EXTRA_ACTOR);
            String description = data.getStringExtra(AddCardActivity.EXTRA_DESCRIPTION);
            Double cost = Double.valueOf(data.getStringExtra(AddCardActivity.EXTRA_COST));
            String title = data.getStringExtra(AddCardActivity.EXTRA_TITLE);
            Card card = new Card(actor,description);
            card.setId_card(id);
            card.setCost(cost);
            card.setId_column(idColumn);
            card.setTitle(title);
            String date = data.getStringExtra(AddCardActivity.EXTRA_DATE);
            Date date1 = new Date();
            DateFormat format = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
            try {
                date1 = format.parse(date);
                System.out.println(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            card.setDate(date1);

            cardViewModel.update(card);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void getMaxId(int id){
         maxId =id;
    }
    public void getSumCostOldColumn(double sum){
        sumOldCost =sum;

    }public void getSumCostNewColumn(double sum){
        sumNewCost =sum;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_board:
                mBoardView.clearBoard();
                commentViewModel.deleteAllComment();
                cardViewModel.deleteAllCard();
                resetBoard();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
    private void resetBoard() {
        mBoardView.setCustomDragItem(new MyDragItem(this, R.layout.column_item));

            addColumn();
    }
    public void pushCard(Card card){
        Intent intent = new Intent(MainActivity.this,AddCardActivity.class);
        intent.putExtra(AddCardActivity.EXTRA_ID,card.getId_card());
        intent.putExtra(AddCardActivity.EXTRA_ID_COLUMN,card.getId_column());
        intent.putExtra(AddCardActivity.EXTRA_ACTOR,card.getActor());
        intent.putExtra(AddCardActivity.EXTRA_DESCRIPTION,card.getDescription());
        intent.putExtra(AddCardActivity.EXTRA_COST,card.getCost());
        intent.putExtra(AddCardActivity.EXTRA_TITLE,card.getTitle());
        @SuppressLint("SimpleDateFormat") Format formatter = new SimpleDateFormat("dd.MM");
        String s = formatter.format(card.getDate());
        intent.putExtra(AddCardActivity.EXTRA_DATE,s);
        startActivityForResult(intent,EDIT_CARD_REQUEST);
    }
    public void addColumn() {
        final ItemAdapter adapter = new ItemAdapter();
        maxId =0;
        final View header = View.inflate(this, R.layout.column_header, null);
        ((TextView) header.findViewById(R.id.name_column)).setText(nameColumn[0]);

        ((TextView) header.findViewById(R.id.item_count)).setText(Double.toString(sumOldCost));
        mBoardView.setCustomColumnDragItem(new MyColumnDragItem(MainActivity.this, R.layout.column_drag_layout));
        mBoardView.addColumn(adapter, header, header, false);
        mBoardView.getRecyclerView(0).setLayoutManager(new LinearLayoutManager(this));
        mBoardView.getRecyclerView(0).setAdapter(adapter);
        cardViewModel.getDataOnId().observe(this, cards -> {
            adapter.setCards(cards);
            recalculationItem(0,0);
                }
        );
        adapter.setOnItemClickListener(this::pushCard);
        addColumn1();
    }
    public void addColumn1() {
        final ItemAdapter adapter = new ItemAdapter();
        final View header = View.inflate(this, R.layout.column_header, null);
        ((TextView) header.findViewById(R.id.name_column)).setText(nameColumn[1]);
        ((TextView) header.findViewById(R.id.item_count)).setText("");
        mBoardView.setCustomColumnDragItem(new MyColumnDragItem(MainActivity.this, R.layout.column_drag_layout));
        mBoardView.addColumn(adapter, header, header, false);
        cardViewModel.getDataOnId1().observe(this, cards -> {
                    adapter.setCards(cards);
                    recalculationItem(1,1);
                }
        );
        adapter.setOnItemClickListener(this::pushCard);
        addColumn2();

    }
    public void addColumn2() {
        final ItemAdapter adapter = new ItemAdapter();
        final View header = View.inflate(this, R.layout.column_header, null);
        ((TextView) header.findViewById(R.id.name_column)).setText(nameColumn[2]);
        ((TextView) header.findViewById(R.id.item_count)).setText("");
        mBoardView.setCustomColumnDragItem(new MyColumnDragItem(MainActivity.this, R.layout.column_drag_layout));
        mBoardView.addColumn(adapter, header, header, false);
        cardViewModel.getDataOnId2().observe(this, cards -> {
                    adapter.setCards(cards);
                    recalculationItem(2,2);
                }
        );
        adapter.setOnItemClickListener(this::pushCard);
        addColumn3();

    }
    public void addColumn3() {

        final ItemAdapter adapter = new ItemAdapter();
        final View header = View.inflate(this, R.layout.column_header, null);
        ((TextView) header.findViewById(R.id.name_column)).setText(nameColumn[3]);
        ((TextView) header.findViewById(R.id.item_count)).setText("");
        mBoardView.setCustomColumnDragItem(new MyColumnDragItem(MainActivity.this, R.layout.column_drag_layout));
        mBoardView.addColumn(adapter, header, header, false);
        cardViewModel.getDataOnId3().observe(this, cards -> {
                    adapter.setCards(cards);
                    recalculationItem(3,3);
                }
        );
        adapter.setOnItemClickListener(this::pushCard);
    }
        @SuppressLint("SetTextI18n")
        private void recalculationItem (int oldColumn, int newColumn){
            TextView itemCount1 = mBoardView.getHeaderView(oldColumn).findViewById(R.id.item_cost);
            TextView itemCount2 = mBoardView.getHeaderView(newColumn).findViewById(R.id.item_cost);
            TextView itemCount3 = mBoardView.getHeaderView(oldColumn).findViewById(R.id.item_count);
            TextView itemCount4 = mBoardView.getHeaderView(newColumn).findViewById(R.id.item_count);
            itemCount3.setText("(" + mBoardView.getAdapter(newColumn).getItemCount() + ")");
            itemCount4.setText("(" + mBoardView.getAdapter(newColumn).getItemCount() + ")");
            cardViewModel.b(oldColumn).observe(this, aDouble -> {
                if (aDouble!=null) {
                    getSumCostOldColumn(aDouble);

                    itemCount1.setText((sumOldCost)+" ч.");

                }
                else
                {
                    itemCount1.setText((0)+" ч.");
                }
            });
            cardViewModel.b(newColumn).observe(this, aDouble -> {
                if (aDouble!=null) {
                    getSumCostNewColumn(aDouble);
                    itemCount2.setText((sumNewCost)+" ч.");
                }
            });
        }
        enum Column {
            Новые,
            Выполняются,
            Проверяются,
            Сделаны
        }

    }

