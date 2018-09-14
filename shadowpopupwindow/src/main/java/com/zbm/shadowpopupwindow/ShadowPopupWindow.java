package com.zbm.shadowpopupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;


import java.lang.ref.WeakReference;

import debug.R;


/**
 * Created by Zbm阿铭 on 2017/10/29.
 */

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class ShadowPopupWindow extends PopupWindow {
    private View mDarkView;
    private WindowManager mWindowManager;
    private int mScreenWidth, mScreenHeight;
    private int mRightOf, mLeftOf, mBelow, mAbove;
    private int[] mLocationInWindowPosition = new int[2];
    private WeakReference<View> mRightOfPositionView, mLeftOfPositionView, mBelowPositionView,
            mAbovePositionView, mFillPositionView;
    private boolean mIsDarkInvoked = false;
    private int contentHeight;

    public ShadowPopupWindow(final View contentView, int width, int height) {
        super(contentView, width, height);
        if (contentView != null) {
            contentHeight=getViewMeasuredHeight(contentView);
            Log.d("shadow","内容高度："+contentHeight);
            mWindowManager = (WindowManager) contentView.getContext().getSystemService(Context.WINDOW_SERVICE);
            mDarkView = new View(contentView.getContext());
            mDarkView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            mDarkView.setBackgroundColor(Color.parseColor("#50000000"));

            DisplayMetrics dm = new DisplayMetrics();
            mWindowManager.getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;

        }
    }

    //如果创建PopupWindow的时候没有指定高宽，那么showAsDropDown默认只会向下弹出显示，
    // 这种情况有个最明显的缺点就是：弹窗口可能被屏幕截断，显示不全，
    // 所以需要使用到另外一个方法showAtLocation,这个的坐标是相对于整个屏幕的，所以需要我们自己计算位置
    @Override
    public void showAsDropDown(View anchor, int xOff, int yOff) {
        initShadow(anchor);
        super.showAsDropDown(anchor, xOff, yOff);
        getContentView().startAnimation(AnimationUtils.createInAnimation(getContentView().getContext(),contentHeight));
    }

    //1.设置Gravity.NO_GRAVITY的话，就相对屏幕左上角作为参照(即原点[0,0]是屏幕左上角)
    //
    //2.若设置Gravity.LEFT的话，则原点为 [0,1/2屏幕高],即[x=0,y=1/2屏幕高度];
    //showAtLocation(view,int,x,y)方法的第三个、第四个参数则是PopupWindow的左上角坐标。
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        initShadow(parent);
        super.showAtLocation(parent, gravity, x, y);
        getContentView().startAnimation(AnimationUtils.createInAnimation(getContentView().getContext(),contentHeight));
    }

    private void initShadow(View view) {
        if (mIsDarkInvoked || isShowing() || getContentView() == null) {
            return;
        }
        checkPosition();
        if (mDarkView != null) {

            WindowManager.LayoutParams darkLP = createShadowLayout(view);
            mWindowManager.addView(mDarkView, darkLP);
            mIsDarkInvoked = true;
        }
    }

    private void checkPosition() {
        checkPositionLeft();
        checkPositionRight();
        checkPositionBelow();
        checkPositionAbove();
        checkPositionFill();
    }

    private void checkPositionLeft() {
        if (mLeftOfPositionView != null) {
            View leftOfView = mLeftOfPositionView.get();
            if (leftOfView != null && mLeftOf == 0) {
                shadowLeftOf(leftOfView);
            }
        }
    }

    private void checkPositionRight() {
        if (mRightOfPositionView != null) {
            View rightOfView = mRightOfPositionView.get();
            if (rightOfView != null && mRightOf == 0) {
                shadowRightOf(rightOfView);
            }
        }
    }

    private void checkPositionBelow() {
        if (mBelowPositionView != null) {
            View belowView = mBelowPositionView.get();
            if (belowView != null && mBelow == 0) {
                shadowBelow(belowView);
            }
        }
    }

    private void checkPositionAbove() {
        if (mAbovePositionView != null) {
            View aboveView = mAbovePositionView.get();
            if (aboveView != null && mAbove == 0) {
                shadowAbove(aboveView);
            }
        }
    }

    private void checkPositionFill() {
        if (mFillPositionView != null) {
            View fillView = mFillPositionView.get();
            if (fillView != null && (mLeftOf == 0 || mAbove == 0)) {
                darkFillView(fillView);
            }
        }
    }

    private void darkFillView(View view) {
        mFillPositionView = new WeakReference<>(view);
        view.getLocationInWindow(mLocationInWindowPosition);
        mRightOf = mLocationInWindowPosition[0];
        mLeftOf = mLocationInWindowPosition[0] + view.getWidth();
        mAbove = mLocationInWindowPosition[1] + view.getHeight();
        mBelow = mLocationInWindowPosition[1];
    }

    private WindowManager.LayoutParams createShadowLayout(View view) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = Gravity.START | Gravity.TOP;
        p.x = mRightOf;
        p.y = mBelow;
        p.width = mLeftOf - mRightOf;
        p.height = mAbove - mBelow;
        p.windowAnimations = R.style.DarkAnimation;
        p.format = PixelFormat.TRANSLUCENT;
        p.flags = computeFlags(p.flags);
        p.type = WindowManager.LayoutParams.LAST_SUB_WINDOW;
        p.token = view.getWindowToken();
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
        return p;
    }

    private int computeFlags(int curFlags) {
        curFlags &= ~(
                WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM |
                        WindowManager.LayoutParams.FLAG_SPLIT_TOUCH);
        curFlags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        curFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        return curFlags;
    }


    @Override
    public void dismiss() {
        getContentView().startAnimation(AnimationUtils.createOutAnimation(getContentView().getContext(), contentHeight));
        getContentView().postDelayed(new Runnable() {
            @Override
            public void run() {
                ShadowPopupWindow.super.dismiss();
            }
        },AnimationUtils.ANIMATION_OUT_TIME);
        if (mDarkView != null && mIsDarkInvoked) {
            mWindowManager.removeViewImmediate(mDarkView);
            mIsDarkInvoked = false;
        }
    }

    public void shadowLeftOf(View view) {
        resetShadowPosition();
        mLeftOfPositionView = new WeakReference<>(view);
        view.getLocationInWindow(mLocationInWindowPosition);
        mLeftOf = mLocationInWindowPosition[0];
    }

    public void shadowRightOf(View view) {
        resetShadowPosition();
        mRightOfPositionView = new WeakReference<>(view);
        view.getLocationInWindow(mLocationInWindowPosition);
        mRightOf = mLocationInWindowPosition[0] + view.getWidth();
    }

    public void shadowAbove(View view) {
        resetShadowPosition();
        mAbovePositionView = new WeakReference<>(view);
        view.getLocationInWindow(mLocationInWindowPosition);
        mAbove = mLocationInWindowPosition[1];
    }

    public void shadowBelow(View view) {
        resetShadowPosition();
        mBelowPositionView = new WeakReference<>(view);
        view.getLocationInWindow(mLocationInWindowPosition);
        mBelow = mLocationInWindowPosition[1] + view.getHeight();
    }


    private void resetShadowPosition() {
        mRightOf = 0;
        mLeftOf = mScreenWidth;
        mAbove = mScreenHeight;
        mBelow = 0;

        if (mRightOfPositionView != null) {
            mRightOfPositionView.clear();
        }
        if (mLeftOfPositionView != null) {
            mLeftOfPositionView.clear();
        }
        if (mBelowPositionView != null) {
            mBelowPositionView.clear();
        }
        if (mAbovePositionView != null) {
            mAbovePositionView.clear();
        }
        if (mFillPositionView != null) {
            mFillPositionView.clear();
        }
        mRightOfPositionView = mLeftOfPositionView = mBelowPositionView = mAbovePositionView =
                mFillPositionView = null;
    }

    public static int getViewMeasuredHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }
}
