package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemCaobenwuzhongBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;

import java.util.List;

public class CaobenwuzhongAdapter extends RecyclerView.Adapter<CaobenwuzhongAdapter.CaobenwuzhongViewHolder> {

    List<CaobenWuzhong> mWuzhongList;

    @Nullable
    private final ItemClickCallback<CaobenWuzhong> mWuzhongClickCallback;

    public CaobenwuzhongAdapter(ItemClickCallback<CaobenWuzhong> callback) {
        mWuzhongClickCallback = callback;
        setHasStableIds(true);
    }

    public void setCaobenwuzhongList(final List<CaobenWuzhong> wuzhongList) {
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
                    CaobenWuzhong newCaobenWuzhong = wuzhongList.get(newItemPosition);
                    CaobenWuzhong oldCaobenWuzhong = mWuzhongList.get(oldItemPosition);
                    return newCaobenWuzhong.id == oldCaobenWuzhong.id
//                            && Objects.equals(newYangdian.getDescription(), oldYangdian.getDescription())
//                            && Objects.equals(newProduct.getName(), oldProduct.getName())
                            && newCaobenWuzhong.wuzhongCode.equals(oldCaobenWuzhong.wuzhongCode);
                }
            });
            mWuzhongList = wuzhongList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public CaobenwuzhongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemCaobenwuzhongBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_caobenwuzhong,
                        viewGroup, false);
        binding.setClickCallback(mWuzhongClickCallback);
        return new CaobenwuzhongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CaobenwuzhongViewHolder wuzhongViewHolder, int i) {
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


    static class CaobenwuzhongViewHolder extends RecyclerView.ViewHolder {

        final ItemCaobenwuzhongBinding binding;

        public CaobenwuzhongViewHolder(ItemCaobenwuzhongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
