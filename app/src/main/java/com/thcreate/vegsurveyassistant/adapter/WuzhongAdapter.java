package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemWuzhongBinding;
import com.thcreate.vegsurveyassistant.db.entity.BaseWuzhong;

import java.util.List;

public class WuzhongAdapter<T extends BaseWuzhong> extends RecyclerView.Adapter<WuzhongAdapter.WuzhongViewHolder> {

    List<T> mWuzhongList;

    @Nullable
    private final ItemClickCallback<T> mWuzhongClickCallback;

    public WuzhongAdapter(ItemClickCallback<T> callback) {
        mWuzhongClickCallback = callback;
        setHasStableIds(true);
    }

    public void setWuzhongList(final List<T> wuzhongList) {
        if (mWuzhongList == null) {
            mWuzhongList = wuzhongList;
            notifyItemRangeInserted(0, wuzhongList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mWuzhongList.size();
                }

                @Override
                public int getNewListSize() {
                    return wuzhongList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mWuzhongList.get(oldItemPosition).id ==
                            wuzhongList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    T newWuzhong = wuzhongList.get(newItemPosition);
                    T oldWuzhong = mWuzhongList.get(oldItemPosition);
                    return newWuzhong.id == oldWuzhong.id
//                            && Objects.equals(newYangdian.getDescription(), oldYangdian.getDescription())
//                            && Objects.equals(newProduct.getName(), oldProduct.getName())
                            && newWuzhong.wuzhongCode.equals(oldWuzhong.wuzhongCode);
                }
            });
            mWuzhongList = wuzhongList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public WuzhongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemWuzhongBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_wuzhong,
                        viewGroup, false);
        binding.setClickCallback(mWuzhongClickCallback);
        return new WuzhongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WuzhongViewHolder wuzhongViewHolder, int i) {
        wuzhongViewHolder.binding.setWuzhong(mWuzhongList.get(i));
        wuzhongViewHolder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return mWuzhongList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return mWuzhongList == null ? 0 : mWuzhongList.size();
    }


    static class WuzhongViewHolder extends RecyclerView.ViewHolder {

        final ItemWuzhongBinding binding;

        public WuzhongViewHolder(ItemWuzhongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
