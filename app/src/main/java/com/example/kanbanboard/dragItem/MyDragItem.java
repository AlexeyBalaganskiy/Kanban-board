package com.example.kanbanboard.dragItem;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.kanbanboard.R;
import com.woxthebox.draglistview.DragItem;

public class MyDragItem extends DragItem {
    public MyDragItem(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void onBindDragView(View clickedView, View dragView) {
        CharSequence textTitle = ((TextView) clickedView.findViewById(R.id.title_text)).getText();
        ((TextView) dragView.findViewById(R.id.title_text)).setText(textTitle);
        CharSequence textIdCard = ((TextView) clickedView.findViewById(R.id.id_card)).getText();
        ((TextView) dragView.findViewById(R.id.id_card)).setText(textIdCard);
        CharSequence textActor = ((TextView) clickedView.findViewById(R.id.actor)).getText();
        ((TextView) dragView.findViewById(R.id.actor)).setText(textActor);
        CharSequence textCost = ((TextView) clickedView.findViewById(R.id.cost)).getText();
        ((TextView) dragView.findViewById(R.id.cost)).setText(textCost);
        CharSequence textDate = ((TextView) clickedView.findViewById(R.id.date)).getText();
        ((TextView) dragView.findViewById(R.id.date)).setText(textDate);
        CardView dragCard = dragView.findViewById(R.id.card);
        CardView clickedCard = clickedView.findViewById(R.id.card);

        dragCard.setMaxCardElevation(40);
        dragCard.setCardElevation(clickedCard.getCardElevation());
        dragCard.setForeground(clickedView.getResources().getDrawable(R.drawable.card_view_drag_foreground));
    }

    @Override
    public void onMeasureDragView(View clickedView, View dragView) {
        CardView dragCard = dragView.findViewById(R.id.card);
        CardView clickedCard = clickedView.findViewById(R.id.card);
        int widthDiff = dragCard.getPaddingLeft() - clickedCard.getPaddingLeft() + dragCard.getPaddingRight() -
                clickedCard.getPaddingRight();
        int heightDiff = dragCard.getPaddingTop() - clickedCard.getPaddingTop() + dragCard.getPaddingBottom() -
                clickedCard.getPaddingBottom();
        int width = clickedView.getMeasuredWidth() + widthDiff;
        int height = clickedView.getMeasuredHeight() + heightDiff;
        dragView.setLayoutParams(new FrameLayout.LayoutParams(width, height));

        int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        dragView.measure(widthSpec, heightSpec);
    }

    @Override
    public void onStartDragAnimation(View dragView) {
        CardView dragCard = dragView.findViewById(R.id.card);
        ObjectAnimator anim = ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.getCardElevation(), 40);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();
    }

    @Override
    public void onEndDragAnimation(View dragView) {
        CardView dragCard = dragView.findViewById(R.id.card);
        ObjectAnimator anim = ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.getCardElevation(), 6);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();
    }
}
