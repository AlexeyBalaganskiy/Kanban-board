
package com.example.kanbanboard.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kanbanboard.R;
import com.example.kanbanboard.data.Card;
import com.woxthebox.draglistview.BoardView;
import com.woxthebox.draglistview.DragItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends  DragItemAdapter<Card, ItemAdapter.ViewHolder> {

    private int mGrabHandleId = R.id.item_layout;
    private boolean mDragOnLongPress =true;
    private OnItemClickListener listener;

    private List<Card> cards = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int mLayoutId = R.layout.column_item;
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String actor = mItemList.get(position).getActor();
        String title = mItemList.get(position).getTitle();
        int idCard = mItemList.get(position).getId_card();
        double cost = mItemList.get(position).getCost();
        @SuppressLint("SimpleDateFormat") String dateCreate = new SimpleDateFormat("dd.MM").format(mItemList.get(position).getDate());

        holder.mTextTitle.setText(title);
        holder.mTextActor.setText(actor);
        holder.mTextId.setText("#"+ idCard);
        holder.mTextCost.setText("Трудозатраты: "+ cost + " ч.");
        holder.mTextDate.setText(dateCreate);
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).getId_card();
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        setItemList(cards);
        notifyDataSetChanged();
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        TextView mTextId;
        TextView mTextActor;
        TextView mTextTitle;
        TextView mTextDate;
        TextView mTextCost;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            mTextActor = itemView.findViewById(R.id.actor);
            mTextTitle = itemView.findViewById(R.id.title_text);
            mTextId = itemView.findViewById(R.id.id_card);
            mTextDate = itemView.findViewById(R.id.date);
            mTextCost = itemView.findViewById(R.id.cost);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener != null && position != BoardView.NO_ID) {
                    listener.onItemClick(cards.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Card card);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
