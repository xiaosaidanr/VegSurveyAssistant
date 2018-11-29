package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemYangfangBinding;
import com.thcreate.vegsurveyassistant.db.entity.BaseYangfang;

import java.util.List;

public class YangfangAdapter<T extends BaseYangfang> extends RecyclerView.Adapter<YangfangAdapter.YangfangViewHolder> {

    List<T> mYangfangList;

    @Nullable
    private final ItemClickCallback<T> mYangfangClickCallback;

    public YangfangAdapter(ItemClickCallback<T> callback) {
        mYangfangClickCallback = callback;
        setHasStableIds(true);
    }

    public void setYangfangList(final List<T> yangfangList) {
        if (mYangfangList == null) {
            mYangfangList = yangfangList;
            notifyItemRangeInserted(0, yangfangList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mYangfangList.size();
                }

                @Override
                public int getNewListSize() {
                    return yangfangList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mYangfangList.get(oldItemPosition).id ==
                            yangfangList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    T newYangfang = yangfangList.get(newItemPosition);
                    T oldYangfang = mYangfangList.get(oldItemPosition);
                    return newYangfang.id == oldYangfang.id
//                            && Objects.equals(newYangdian.getDescription(), oldYangdian.getDescription())
//                            && Objects.equals(newProduct.getName(), oldProduct.getName())
                            && newYangfang.yangfangCode.equals(oldYangfang.yangfangCode);
                }
            });
            mYangfangList = yangfangList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public YangfangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemYangfangBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_yangfang,
                        viewGroup, false);
        binding.setClickCallback(mYangfangClickCallback);
        return new YangfangViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull YangfangViewHolder yangdiViewHolder, int i) {
        yangdiViewHolder.binding.setYangfang(mYangfangList.get(i));
        yangdiViewHolder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return mYangfangList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return mYangfangList == null ? 0 : mYangfangList.size();
    }


    static class YangfangViewHolder extends RecyclerView.ViewHolder {

        final ItemYangfangBinding binding;

        public YangfangViewHolder(ItemYangfangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
