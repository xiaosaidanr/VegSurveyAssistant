package com.thcreate.vegsurveyassistant.customUi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;

public class ExpandableLinearLayoutWithHeader extends LinearLayout {

    private TextView header;

    public ExpandableLinearLayoutWithHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
    }

    public ExpandableLinearLayoutWithHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
        init();
    }

    public ExpandableLinearLayoutWithHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
        init();
    }

    public ExpandableLinearLayoutWithHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
        init();
    }

    private boolean mIsFold = true;

    private void init(){
        header = findViewById(R.id.expandable_linearlayout_header);
        Drawable rightArrow = getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp);
        rightArrow.setBounds(0, 0, rightArrow.getIntrinsicWidth(), rightArrow.getIntrinsicHeight());
        if (mIsFold){
            header.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightArrow, null);
        }
        header.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("testtesttest", "header clicked");
            }
        });
    }

}
