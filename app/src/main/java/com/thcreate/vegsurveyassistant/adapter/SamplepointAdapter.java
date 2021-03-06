package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemSamplepointBinding;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PointMainInfo;

import java.util.List;

public class SamplepointAdapter extends RecyclerView.Adapter<SamplepointAdapter.PointViewHolder> {


    List<PointMainInfo> mDataList;

    @Nullable
    private final ItemClickCallback<PointMainInfo> mClickCallback;

    public SamplepointAdapter(ItemClickCallback<PointMainInfo> callback) {
        mClickCallback = callback;
        setHasStableIds(true);
    }

    public void setDataList(final List<PointMainInfo> dataList) {
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
                    return mDataList.get(oldItemPosition).pointId.equals(dataList.get(newItemPosition).pointId);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PointMainInfo newData = dataList.get(newItemPosition);
                    PointMainInfo oldData = mDataList.get(oldItemPosition);
                    return arePointMainInfoContentsTheSame(oldData, newData);
                }
            });
            mDataList = dataList;
            result.dispatchUpdatesTo(this);
        }
    }

    private boolean arePointMainInfoContentsTheSame(PointMainInfo oldData, PointMainInfo newData){
        String oldDataString = String.format("%s%s%s%s%s",
                String.valueOf(oldData.code),
                String.valueOf(oldData.lat),
                String.valueOf(oldData.lng),
                String.valueOf(oldData.investigatedAt),
                String.valueOf(oldData.investigatorName)
        );
        String newDataString = String.format("%s%s%s%s%s",
                String.valueOf(newData.code),
                String.valueOf(newData.lat),
                String.valueOf(newData.lng),
                String.valueOf(newData.investigatedAt),
                String.valueOf(newData.investigatorName)
        );
        return oldDataString.equals(newDataString);
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSamplepointBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_samplepoint,
                        viewGroup, false);
        binding.setClickCallback(mClickCallback);
        return new PointViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder viewHolder, int i) {
        viewHolder.binding.setPointData(mDataList.get(i));
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

    static class PointViewHolder extends RecyclerView.ViewHolder {

        final ItemSamplepointBinding binding;

        public PointViewHolder(ItemSamplepointBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
