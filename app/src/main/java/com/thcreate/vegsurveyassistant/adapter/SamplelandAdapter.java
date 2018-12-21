package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemSamplelandBinding;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;

import java.util.List;

public class SamplelandAdapter extends RecyclerView.Adapter<SamplelandAdapter.LandViewHolder> {

    List<LandMainInfo> mDataList;

    @Nullable
    private final ItemClickCallback<LandMainInfo> mClickCallback;

    public SamplelandAdapter(ItemClickCallback<LandMainInfo> callback) {
        mClickCallback = callback;
        setHasStableIds(true);
    }

    public void setDataList(final List<LandMainInfo> dataList) {
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
                    LandMainInfo newData = dataList.get(newItemPosition);
                    LandMainInfo oldData = mDataList.get(oldItemPosition);
                    return newData.landId.equals(oldData.landId);
                }
            });
            mDataList = dataList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public LandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSamplelandBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_sampleland,
                        viewGroup, false);
        binding.setClickCallback(mClickCallback);
        return new LandViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LandViewHolder viewHolder, int i) {
        viewHolder.binding.setLandData(mDataList.get(i));
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

    static class LandViewHolder extends RecyclerView.ViewHolder {

        final ItemSamplelandBinding binding;

        public LandViewHolder(ItemSamplelandBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
