package com.thcreate.vegsurveyassistant.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerViewSwipeDismissController extends ItemTouchHelper.SimpleCallback {

    private Drawable mBackground;
    private Drawable mIcon;
    private final int mIconMargin = 50;
    private OnDeleteCallback mCallback = null;

    public RecyclerViewSwipeDismissController(int dragDirs, int swipeDirs, Drawable icon) {
        super(dragDirs, swipeDirs);
        mIcon = icon;
        mBackground = new ColorDrawable(Color.RED);
        mIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
    }

    public void setOnDeleteCallback(OnDeleteCallback callback){
        mCallback = callback;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int id = (int)viewHolder.getItemId();
        int position = viewHolder.getAdapterPosition();
        if (mCallback != null){
            mCallback.onDelete(id, position);
        }
    }
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        // not sure why, but this method get's called for viewholder that are already swiped away
        if (viewHolder.getAdapterPosition() == -1) {
            // not interested in those
            return;
        }
        mBackground.setBounds(itemView.getRight() + (int)dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        mBackground.draw(c);
        int itemHeight = itemView.getBottom() - itemView.getTop();
        int intrinsicWidth = mIcon.getIntrinsicWidth();
        int intrinsicHeight = mIcon.getIntrinsicWidth();
        int iconLeft = 0;
        int iconRight = 0;
        int iconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
        int iconBottom = iconTop + intrinsicHeight;
        if (dX < 0) {
            iconLeft = itemView.getRight() - mIconMargin - intrinsicWidth;
            iconRight = itemView.getRight() - mIconMargin;
        } else {
            iconLeft = itemView.getLeft() + mIconMargin;
            iconRight = itemView.getLeft() + mIconMargin + intrinsicWidth;
        }
        mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        mIcon.draw(c);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface OnDeleteCallback{
        void onDelete(int id, int position);
    }

}
