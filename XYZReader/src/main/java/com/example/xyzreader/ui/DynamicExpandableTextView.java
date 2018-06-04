package com.example.xyzreader.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class DynamicExpandableTextView extends ExpandableTextView {

    public DynamicExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(DynamicExpandableTextView.class.getSimpleName(), "ATTS");
    }

    public DynamicExpandableTextView(Context context) {
        super(context);
        Log.d(DynamicExpandableTextView.class.getSimpleName(), "Context");
    }

    public DynamicExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.d(DynamicExpandableTextView.class.getSimpleName(), "DEF N");
    }
}
