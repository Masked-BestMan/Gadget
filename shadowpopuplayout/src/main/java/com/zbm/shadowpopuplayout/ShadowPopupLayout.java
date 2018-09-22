package com.zbm.shadowpopuplayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;



@SuppressWarnings("unused")
public class ShadowPopupLayout extends RelativeLayout implements View.OnClickListener{
    public static final int TOP = 0;
    public static final int BOTTOM = 1;

    private View topView;   //控制阴影的顶层view
    private View windowView;
    private View barView;
    private LayoutParams contentParams, barParams;
    private int orientation;
    private int barLayout, windowLayout;
    private boolean isShowing;
    private OnDismissListener onDismissListener;

    public ShadowPopupLayout(Context context) {
        this(context, null);
    }

    public ShadowPopupLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowPopupLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        setOnClickListener(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowPopupLayout);
        orientation = ta.getInt(R.styleable.ShadowPopupLayout_orientation, BOTTOM);
        barLayout = ta.getResourceId(R.styleable.ShadowPopupLayout_navigationBarLayout, -1);
        windowLayout = ta.getResourceId(R.styleable.ShadowPopupLayout_popupWindowLayout, -1);
        ta.recycle();
        barParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initView(context);

        addView(windowView, contentParams);
        addView(barView, barParams);

    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (barLayout != -1) {
            barView = inflater.inflate(barLayout, null);
            barView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            barView.setId(R.id.bottom_view);
        }

        if (windowLayout != -1) {
            windowView = inflater.inflate(windowLayout, null);
            windowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            windowView.setVisibility(GONE);
        }

        if (orientation == TOP) {
            barParams.addRule(ALIGN_PARENT_TOP);
            contentParams.addRule(BELOW, barView.getId());
        } else {
            barParams.addRule(ALIGN_PARENT_BOTTOM);
            contentParams.addRule(ABOVE, barView.getId());
        }
    }

    int size = 0;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //获取DecorView的第一个子View
        topView= ((ViewGroup) this.getRootView()).getChildAt(0);
    }

    public void showPopupView() {
        if (windowView != null) {
            if (isShowing)
                return;
            isShowing = true;
            //把这个区域全部显示出来
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            windowView.setVisibility(View.VISIBLE);

            Animation animation = AnimationUtils.loadAnimation(getContext(), orientation == BOTTOM ? R.anim.bottom_view_in : R.anim.top_view_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    topView.setBackgroundColor(0x707A7A7A);   //背景阴影
                    topView.setOnTouchListener(new OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            disMissPopupView();
                            return true;
                        }
                    });
                }

                @Override
                public void onAnimationEnd(Animation animation) { }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
            windowView.setAnimation(animation);
        }
    }

    public void disMissPopupView() {
        if (!isShowing)
            return;
        isShowing = false;
        windowView.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), orientation == BOTTOM ? R.anim.bottom_view_out : R.anim.top_view_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (onDismissListener!=null)
                    onDismissListener.onDismiss();

                topView.setBackgroundColor(0x00000000);
                topView.setOnTouchListener(null);
                //把整个控件的大小恢复到底部View区域的大小
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, getViewHeight(ShadowPopupLayout.this));
                layoutParams.addRule(orientation == BOTTOM ? RelativeLayout.ALIGN_PARENT_BOTTOM : ALIGN_PARENT_TOP);
                ShadowPopupLayout.this.setLayoutParams(layoutParams);
            }
        });
        windowView.setAnimation(animation);
    }

    public boolean isShowing() {
        return isShowing;
    }

    public View getBarView() {
        return barView;
    }

    public View getWindowView() {
        return windowView;
    }

    //获取View的高度
    private int getViewHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    @Override
    public void onClick(View v) {
        disMissPopupView();
    }

    public OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public interface OnDismissListener{
        void onDismiss();
    }
}
