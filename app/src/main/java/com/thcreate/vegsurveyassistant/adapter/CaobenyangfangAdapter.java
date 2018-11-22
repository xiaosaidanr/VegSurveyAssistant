package com.thcreate.vegsurveyassistant.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ItemCaobenyangfangBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;

import java.util.List;

public class CaobenyangfangAdapter extends RecyclerView.Adapter<CaobenyangfangAdapter.CaobenyangfangViewHolder> {

    List<CaobenYangfang> mYangfangList;

    @Nullable
    private final ItemClickCallback<CaobenYangfang> mYangfangClickCallback;

    public CaobenyangfangAdapter(ItemClickCallback<CaobenYangfang> callback) {
        mYangfangClickCallback = callback;
        setHasStableIds(true);
    }

    public void setCaobenyangfangList(final List<CaobenYangfang> yangfangList) {
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
                    CaobenYangfang newCaobenYangfang = yangfangList.get(newItemPosition);
                    CaobenYangfang oldCaobenYangfang = mYangfangList.get(oldItemPosition);
                    return newCaobenYangfang.id == oldCaobenYangfang.id
//                            && Objects.equals(newYangdian.getDescription(), oldYangdian.getDescription())
//                            && Objects.equals(newProduct.getName(), oldProduct.getName())
                            && newCaobenYangfang.yangfangCode.equals(oldCaobenYangfang.yangfangCode);
                }
            });
            mYangfangList = yangfangList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public CaobenyangfangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemCaobenyangfangBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_caobenyangfang,
                        viewGroup, false);
        binding.setClickCallback(mYangfangClickCallback);
        return new CaobenyangfangViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CaobenyangfangViewHolder yangdiViewHolder, int i) {
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


    static class CaobenyangfangViewHolder extends RecyclerView.ViewHolder {

        final ItemCaobenyangfangBinding binding;

        public CaobenyangfangViewHolder(ItemCaobenyangfangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
