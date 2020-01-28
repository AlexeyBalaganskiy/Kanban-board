package com.example.kanbanboard.dragItem;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kanbanboard.R;
import com.woxthebox.draglistview.DragItem;

public class MyColumnDragItem extends DragItem {
    public MyColumnDragItem(Context context, int layoutId) {
        super(context, layoutId);
        setSnapToTouch(false);
    }

    @Override
    public void onBindDragView(View clickedView, View dragView) {
        LinearLayout clickedLayout = (LinearLayout) clickedView;
        View clickedHeader = clickedLayout.getChildAt(0);
        RecyclerView clickedRecyclerView = (RecyclerView) clickedLayout.getChildAt(1);

        View dragHeader = dragView.findViewById(R.id.drag_header);
        ScrollView dragScrollView = dragView.findViewById(R.id.drag_scroll_view);
        LinearLayout dragLayout = dragView.findViewById(R.id.drag_list);
        dragLayout.removeAllViews();

        ((TextView) dragHeader.findViewById(R.id.name_column)).setText(((TextView) clickedHeader.findViewById(R.id.name_column)).getText());
        ((TextView) dragHeader.findViewById(R.id.item_count)).setText(((TextView) clickedHeader.findViewById(R.id.item_count)).getText());
        for (int i = 0; i < clickedRecyclerView.getChildCount(); i++) {
            View view = View.inflate(dragView.getContext(), R.layout.column_item, null);
           // ((TextView) view.findViewById(R.id.title_text)).setText(((TextView) clickedRecyclerView.getChildAt(i).findViewById(R.id.title_text)).getText());
            dragLayout.addView(view);

            if (i == 0) {
                dragScrollView.setScrollY(-clickedRecyclerView.getChildAt(i).getTop());
            }
        }

        dragView.setPivotY(0);
        dragView.setPivotX(clickedView.getMeasuredWidth() / 2);
    }

    @Override
    public void onStartDragAnimation(View dragView) {
        super.onStartDragAnimation(dragView);
        dragView.animate().scaleX(0.9f).scaleY(0.9f).start();
    }

    @Override
    public void onEndDragAnimation(View dragView) {
        super.onEndDragAnimation(dragView);
        dragView.animate().scaleX(1).scaleY(1).start();
    }
}