package com.convertigo.barcode;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ConvViewPager extends ViewPager {

    private boolean enabled = true;

    public ConvViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        if ((((ConvScannerActivity) context).barecodeOpts).getOnlyScan().equals(true)
                || (((ConvScannerActivity) context).barecodeOpts).getOnlyKeyboard().equals(true)) {
            this.enabled = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
