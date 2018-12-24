package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemPictureBinding;
import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;

import java.util.List;

public class PictureAdapter<T extends PictureEntity> extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    List<T> mDataList;

    @Nullable
    private final ItemClickCallback<T> mClickCallback;

    public PictureAdapter(ItemClickCallback<T> callback) {
        mClickCallback = callback;
        setHasStableIds(true);
    }

    public void setDataList(final List<T> dataList) {
        if (mDataList == null) {
            mDataList = dataList;
            notifyItemRangeInserted(0, dataList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mDataList.size();
                }

                @Override
                public int getNewListSize() {
                    return dataList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mDataList.get(oldItemPosition).id ==
                            dataList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    T newData = dataList.get(newItemPosition);
                    T oldData = mDataList.get(oldItemPosition);
                    return newData.pictureId.equals(oldData.pictureId);
                }
            });
            mDataList = dataList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPictureBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_picture,
                        viewGroup, false);
        binding.setClickCallback(mClickCallback);
        return new PictureViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder viewHolder, int i) {
        viewHolder.binding.setPicture(mDataList.get(i));
        viewHolder.binding.executePendingBindings();
    }

    public void deleteItemByPosition(int position){
        mDataList.remove(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class PictureViewHolder extends RecyclerView.ViewHolder {

        final ItemPictureBinding binding;

        public PictureViewHolder(ItemPictureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
