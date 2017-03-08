package me.kaelaela.verticalviewpager.transforms;

import android.support.v4.view.VerticalParentViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class DepthPageTransformer implements VerticalParentViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    public void transformPage(View view, float position) {
        Log.d(DepthPageTransformer.class.getName(),"position--> "+position);
        int pageWidth = view.getWidth();
        if(((ViewGroup)view).getChildAt(1) != null && ((ViewGroup)view).getChildAt(1).getVisibility() == View.GONE)
            ((ViewGroup)view).getChildAt(1).setVisibility(View.VISIBLE);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
//            view.setAlpha(0);
            ((ViewGroup)view).getChildAt(1).setAlpha(0);
        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            ((ViewGroup)view).getChildAt(1).setAlpha(0);
            view.setTranslationX(pageWidth * -position);
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            ((ViewGroup)view).getChildAt(1).setAlpha(position);
            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);
            view.setTranslationY(0);
            // Scale the page down (between MIN_SCALE and 1)
//            float scaleFactor = MIN_SCALE
//                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
//            view.setScaleX(scaleFactor);
//            view.setScaleY(scaleFactor);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
//            view.setAlpha(0);
            ((ViewGroup)view).getChildAt(1).setAlpha(0);
        }
    }
}