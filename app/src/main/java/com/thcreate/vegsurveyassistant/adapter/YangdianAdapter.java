package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.YangdianItemBinding;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.util.List;

public class YangdianAdapter extends RecyclerView.Adapter<YangdianAdapter.YangdianViewHolder> {


    List<Yangdian> mYangdianList;

    public YangdianAdapter() {
        setHasStableIds(true);
    }

    public void setYangdianList(final List<Yangdian> yangdianList) {
        if (mYangdianList == null) {
            mYangdianList = yangdianList;
            notifyItemRangeInserted(0, yangdianList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mYangdianList.size();
                }

                @Override
                public int getNewListSize() {
                    return yangdianList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mYangdianList.get(oldItemPosition).id ==
                            yangdianList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Yangdian newYangdian = yangdianList.get(newItemPosition);
                    Yangdian oldYangdian = mYangdianList.get(oldItemPosition);
                    return newYangdian.id == oldYangdian.id
//                            && Objects.equals(newYangdian.getDescription(), oldYangdian.getDescription())
//                            && Objects.equals(newProduct.getName(), oldProduct.getName())
                            && newYangdian.yangdianCode.equals(oldYangdian.yangdianCode);
                }
            });
            mYangdianList = yangdianList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public YangdianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        YangdianItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.yangdian_item,
                        viewGroup, false);
        return new YangdianViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull YangdianViewHolder yangdianViewHolder, int i) {
        yangdianViewHolder.binding.setYangdian(mYangdianList.get(i));
        yangdianViewHolder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return mYangdianList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return mYangdianList == null ? 0 : mYangdianList.size();
    }

    static class YangdianViewHolder extends RecyclerView.ViewHolder {

        final YangdianItemBinding binding;

        public YangdianViewHolder(YangdianItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
