package me.kaelaela.verticalviewpager;

/**
 * Copyright (C) 2015 Kaelaela
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import android.content.Context;
import android.support.v4.view.VerticalParentViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import me.kaelaela.verticalviewpager.transforms.DepthPageTransformer;

public class VerticalViewPager extends VerticalParentViewPager {

    private static String TAG = VerticalViewPager.class.getName();

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new DepthPageTransformer());
    }

    private boolean isEnableScroll = false;

    public void setEnableScroll(boolean enableScroll) {
        isEnableScroll = enableScroll;
    }

    public boolean isEnableScroll() {
        return isEnableScroll;
    }

    private boolean isHeader = true;

    private boolean isBottom = false;

    public void setBottom(boolean bottom) {
        isBottom = bottom;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;
        event.setLocation(swappedX, swappedY);

        return event;
    }

    private float mLastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if(!isEnableScroll)
            return true;

        final int action = event.getAction();
        switch(action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                Log.d(TAG,"TAG-->"+mLastY);
                break;
            case MotionEvent.ACTION_MOVE:

                final float y = event.getY();

                Log.d(TAG,"Y-->"+y+" isHeader->"+isHeader+" isBottom--> "+isBottom);

                if((y - mLastY) < 0){ // 向上
                    if(isHeader) {
                        return false;
                    } else {
                        if(isBottom){
                            boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
                            //If not intercept, touch event should not be swapped.
                            swapTouchEvent(event);
                            return intercept;
                        }else {
                            return false;
                        }
                    }
                }else{
                    if(isHeader) {
                        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
                        //If not intercept, touch event should not be swapped.
                        swapTouchEvent(event);
                        return intercept;
                    } else {
                        return false;
                    }
                }
        }
        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
        //If not intercept, touch event should not be swapped.
        swapTouchEvent(event);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if(!isEnableScroll)
            return true;
        try {
            return super.onTouchEvent(swapTouchEvent(ev));
        } catch (Exception ex) {
            Log.e(TAG,ex.getMessage(),ex);
        }
        return false;
    }

}
