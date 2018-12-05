package com.thcreate.vegsurveyassistant.customUi;

import android.content.Context;
import android.content.res.TypedArray;
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

    public ExpandableLinearLayoutWithHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
    }

    public ExpandableLinearLayoutWithHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
        initParam(context, attrs);
        initState();
    }

    public ExpandableLinearLayoutWithHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
        initParam(context, attrs);
        initState();
    }

    public ExpandableLinearLayoutWithHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.expandable_linearlayout_with_hearder, this);
        initParam(context, attrs);
        initState();
    }

    private String headerTitle;

    private TextView header;

    private boolean mIsFold;

    private Drawable rightArrow;

    private Drawable downArrow;

    private void initParam(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLinearLayoutWithHeader);
        if (typedArray != null){
            headerTitle = typedArray.getString(R.styleable.ExpandableLinearLayoutWithHeader_header_title);
        }

    }

    private void initState(){
        header = findViewById(R.id.expandable_linearlayout_header);
        header.setText(headerTitle);
        rightArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp);
        rightArrow.setBounds(0, 0, rightArrow.getIntrinsicWidth(), rightArrow.getIntrinsicHeight());
        downArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp);
        downArrow.setBounds(0, 0, downArrow.getIntrinsicWidth(), downArrow.getIntrinsicHeight());

        //默认是折叠的
        mIsFold = true;
        header.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightArrow, null);
//        //默认是打开的
//        mIsFold = false;
//        header.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, downArrow, null);

        header.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mIsFold){
                    header.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, downArrow, null);
                    setChildrenVisibility(VISIBLE);
                }
                else {
                    header.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightArrow, null);
                    setChildrenVisibility(GONE);
                }
                mIsFold = !mIsFold;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mIsFold){
            setChildrenVisibility(GONE);
        }
        else {
            setChildrenVisibility(VISIBLE);
        }
    }

    private void setChildrenVisibility(int visibility){
        int childrenCount = getChildCount();
        for (int i=1; i<childrenCount; i++){
            View view = getChildAt(i);
            view.setVisibility(visibility);
        }
    }

}
