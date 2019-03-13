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
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;
import java.util.Map;

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
                    return mDataList.get(oldItemPosition).landId.equals(dataList.get(newItemPosition).landId);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    LandMainInfo newData = dataList.get(newItemPosition);
                    LandMainInfo oldData = mDataList.get(oldItemPosition);
                    return areLandMainInfoContentsTheSame(oldData, newData);
                }
            });
            mDataList = dataList;
            result.dispatchUpdatesTo(this);
        }
    }

    private boolean areLandMainInfoContentsTheSame(LandMainInfo oldData, LandMainInfo newData){
        String oldDataString = String.format("%s%s%s%s",
                String.valueOf(oldData.code),
                String.valueOf(oldData.lat),
                String.valueOf(oldData.lng),
                String.valueOf(oldData.type)
        );
        String newDataString = String.format("%s%s%s%s",
                String.valueOf(newData.code),
                String.valueOf(newData.lat),
                String.valueOf(newData.lng),
                String.valueOf(newData.type)
        );
        return oldDataString.equals(newDataString);
    }

    @NonNull
    @Override
    public LandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSamplelandBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_sampleland,
                        viewGroup, false);
        binding.setClickCallback(mClickCallback);
        binding.setLandTypeMap(Macro.LAND_TYPE_MAP);
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
