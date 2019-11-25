package com.example.customcalendarlibrary;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class WrappedGridView extends RecyclerView {
    public WrappedGridView(Context context) {
        super(context);
    }

    public WrappedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrappedGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {

            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >>2, MeasureSpec.AT_MOST);
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
