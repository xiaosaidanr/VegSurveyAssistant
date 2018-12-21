package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemSpeciesBinding;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.SpeciesMainInfo;

import java.util.List;

public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.SpeciesViewHolder> {

    List<SpeciesMainInfo> mDataList;

    @Nullable
    private final ItemClickCallback<SpeciesMainInfo> mClickCallback;

    public SpeciesAdapter(ItemClickCallback<SpeciesMainInfo> callback) {
        mClickCallback = callback;
        setHasStableIds(true);
    }

    public void setDataList(final List<SpeciesMainInfo> dataList) {
        if (mDataList == null) {
            mDataList = dataList;
            notifyItemRangeInserted(0, mDataList.size());
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
                    SpeciesMainInfo newData = dataList.get(newItemPosition);
                    SpeciesMainInfo oldData = mDataList.get(oldItemPosition);
                    return newData.id == oldData.id
                            && newData.speciesId.equals(oldData.speciesId);
                }
            });
            mDataList = dataList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSpeciesBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_species,
                        viewGroup, false);
        binding.setClickCallback(mClickCallback);
        return new SpeciesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder viewHolder, int i) {
        viewHolder.binding.setSpeciesData(mDataList.get(i));
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    static class SpeciesViewHolder extends RecyclerView.ViewHolder {

        final ItemSpeciesBinding binding;

        public SpeciesViewHolder(ItemSpeciesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
