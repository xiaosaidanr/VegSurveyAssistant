package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemYangdiBinding;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;

import java.util.List;

public class YangdiAdapter extends RecyclerView.Adapter<YangdiAdapter.YangdiViewHolder> {

    List<Yangdi> mYangdiList;

    @Nullable
    private final ItemClickCallback<Yangdi> mYangdiClickCallback;

    public YangdiAdapter(ItemClickCallback<Yangdi> callback) {
        mYangdiClickCallback = callback;
        setHasStableIds(true);
    }

    public void setYangdiList(final List<Yangdi> yangdiList) {
        if (mYangdiList == null) {
            mYangdiList = yangdiList;
            notifyItemRangeInserted(0, yangdiList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mYangdiList.size();
                }

                @Override
                public int getNewListSize() {
                    return yangdiList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mYangdiList.get(oldItemPosition).id ==
                            yangdiList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Yangdi newYangdi = yangdiList.get(newItemPosition);
                    Yangdi oldYangdi = mYangdiList.get(oldItemPosition);
                    return newYangdi.yangdiCode.equals(oldYangdi.yangdiCode);
                }
            });
            mYangdiList = yangdiList;
//            notifyDataSetChanged();
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public YangdiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemYangdiBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_yangdi,
                        viewGroup, false);
        binding.setClickCallback(mYangdiClickCallback);
        return new YangdiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull YangdiViewHolder yangdiViewHolder, int i) {
        yangdiViewHolder.binding.setYangdi(mYangdiList.get(i));
        yangdiViewHolder.binding.executePendingBindings();
    }

    public void deleteItemByPosition(int position){
        mYangdiList.remove(position);
    }

    @Override
    public long getItemId(int position) {
        return mYangdiList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return mYangdiList == null ? 0 : mYangdiList.size();
    }

    static class YangdiViewHolder extends RecyclerView.ViewHolder {

        final ItemYangdiBinding binding;

        public YangdiViewHolder(ItemYangdiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
