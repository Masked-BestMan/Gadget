package com.zbm.dragframelayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 只能放置横向滑动的组件
 */
public class DragFrameLayout extends FrameLayout {
    private OnDragFinishedListener listener;
    private ViewDragHelper dragHelper;
    private View content;
    private int contentLeft, contentTop;
    private int offset;
    private boolean canDrag;
    private int mDownY, mDownX;

    public DragFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(android.R.color.black));
        ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                return content == view;
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                if (!canDrag)
                    return 0;    //水平滑动时，需要返回0
                return content.getWidth();
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return content.getHeight();
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                if (!canDrag)
                    return 0;
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xVel, float yVel) {
                super.onViewReleased(releasedChild, xVel, yVel);
                if (releasedChild == content) {
                    if (releasedChild.getTop() - contentTop > offset) {
                        listener.onFinished();
                    } else {
                        if (dragHelper.smoothSlideViewTo(content, contentLeft, contentTop)) {
                            // 注意：参数传递根ViewGroup
                            ViewCompat.postInvalidateOnAnimation(DragFrameLayout.this);
                        }
                    }
                }
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                int width = changedView.getWidth();
                int height = changedView.getHeight();
                int offTop = top - contentTop;
                if (offTop < 0)
                    return;
                content.setScaleX(1 - (float) offTop / width);
                content.setScaleY(1 - (float) offTop / height);
                int color = ((int) ((1 - (float) offTop / height) * 255.0f + 0.5f) << 24);
                setBackgroundColor(color);
            }
        };
        dragHelper = ViewDragHelper.create(this, 1f, mCallBack);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                canDrag = false;
                mDownY = (int) event.getRawY();
                mDownX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getRawY();
                int x = (int) event.getRawX();
                int dDownY = y - mDownY;
                int dDownX = x - mDownX;
                //如果滑动达到一定距离
                if (Math.abs(dDownY) > Math.abs(dDownX)) {
                    canDrag = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                canDrag = false;
                break;
        }
        return dragHelper.shouldInterceptTouchEvent(event) && canDrag;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true))
            ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        content = getChildAt(0);
        contentLeft = content.getLeft();
        contentTop = content.getTop();
        offset = content.getHeight() / 3;
    }

    public void setListener(OnDragFinishedListener listener) {
        this.listener = listener;
    }

    public interface OnDragFinishedListener {
        void onFinished();
    }
}
