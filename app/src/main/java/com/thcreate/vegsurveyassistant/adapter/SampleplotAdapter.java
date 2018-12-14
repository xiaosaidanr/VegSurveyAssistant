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

import java.util.List;

public class SampleplotAdapter extends RecyclerView.Adapter<SampleplotAdapter.PlotViewHolder> {

    List<SampleplotEntity> mDataList;

    @Nullable
    private final ItemClickCallback<SampleplotEntity> mClickCallback;

    public SampleplotAdapter(ItemClickCallback<SampleplotEntity> callback) {
        mClickCallback = callback;
        setHasStableIds(true);
    }

    public void setDataList(final List<SampleplotEntity> dataList) {
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
                    SampleplotEntity newData = dataList.get(newItemPosition);
                    SampleplotEntity oldData = mDataList.get(oldItemPosition);
                    return newData.plotId.equals(oldData.plotId);
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
