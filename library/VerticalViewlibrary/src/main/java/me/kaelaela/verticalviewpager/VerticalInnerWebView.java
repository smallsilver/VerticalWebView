/*
 *
 *  *
 *  *  *   Created by dongen_wang on 12/21/16 4:16 PM
 *  *  *   Email dongen_wang@163.com
 *  *  *   Copyright 2016 www.mahua.com
 *  *  *
 *  *  *   Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  *  *   use this file except in compliance with the License.  You may obtain a copy
 *  *  *   of the License at
 *  *  *
 *  *  *   http:www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  *   Unless required by applicable law or agreed to in writing, software
 *  *  *   distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  *  *   WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  *  *   License for the specific language governing permissions and limitations under
 *  *  *   the License.
 *  *
 *
 *
 */

package me.kaelaela.verticalviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * @PACKAGE me.kaelaela.sample
 * @DESCRIPTION: TODO
 * @AUTHOR dongen_wang
 * @DATE 12/15/16 18:04
 * @VERSION V1.0
 */
public class VerticalInnerWebView extends WebView {

    private VerticalViewPager pager;

    public VerticalViewPager getPager() {
        return pager;
    }

    public void setPager(VerticalViewPager pager) {
        this.pager = pager;
    }

    public VerticalInnerWebView(Context context) {
        this(context,null);
    }

    public VerticalInnerWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerticalInnerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(pager == null)
            return true;
        Log.i(VerticalInnerWebView.class.getName(),"getScrollY()-->"+getScrollY()+"  computeVerticalScrollRange()- getHeight() "+computeVerticalScrollRange()+"  "+getHeight()+"  "+(computeVerticalScrollRange()- getHeight()));
        if (getScrollY() < 0) {
//                pager.requestDisallowInterceptTouchEvent(false);
//			touchLayout.getParent().requestDisallowInterceptTouchEvent(false);
            pager.setHeader(false);
            pager.setBottom(false);
            return false;

        } else if ((getScrollY() != 0) && (getScrollY() >= (computeVerticalScrollRange()- getHeight())) ) {  //- getWidth()
            pager.setHeader(false);
            pager.setBottom(true);
            return false;

        } else if (getScrollY() == 0) {  //- getWidth()
            pager.setHeader(true);
            pager.setBottom(false);
            return false;

        } else {
            pager.setHeader(false);
            pager.setBottom(false);
            return true;

        }

//		return super.onInterceptTouchEvent(ev);

    }

    public void myDestroy() {
        clearCache(true);
        clearFormData();
        clearMatches();
        clearSslPreferences();
        clearDisappearingChildren();
        clearHistory();
        clearAnimation();
        loadUrl("about:blank");
        removeAllViews();
        freeMemory();
    }
}
