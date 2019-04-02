package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemSampleplotBinding;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;

import java.util.List;

public class SampleplotAdapter extends RecyclerView.Adapter<SampleplotAdapter.PlotViewHolder> {

    List<PlotMainInfo> mDataList;

    @Nullable
    private final ItemClickCallback<PlotMainInfo> mClickCallback;

    public SampleplotAdapter(ItemClickCallback<PlotMainInfo> callback) {
        mClickCallback = callback;
        setHasStableIds(true);
    }

    public void setDataList(final List<PlotMainInfo> dataList) {
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
                    return mDataList.get(oldItemPosition).plotId.equals(dataList.get(newItemPosition).plotId);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PlotMainInfo newData = dataList.get(newItemPosition);
                    PlotMainInfo oldData = mDataList.get(oldItemPosition);
                    return newData.code.equals(oldData.code);
                }
            });
            mDataList = dataList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public PlotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSampleplotBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_sampleplot,
                        viewGroup, false);
        binding.setClickCallback(mClickCallback);
        return new PlotViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlotViewHolder viewHolder, int i) {
        viewHolder.binding.setPlotData(mDataList.get(i));
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


    static class PlotViewHolder extends RecyclerView.ViewHolder {

        final ItemSampleplotBinding binding;

        public PlotViewHolder(ItemSampleplotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
