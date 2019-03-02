package com.zbm.shadowpopuplayout;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;



@SuppressWarnings("unused")
public class BottomDrawer extends FrameLayout{
    public static final int TOP = 0;
    public static final int BOTTOM = 1;

    private View drawerContentView;   //控制阴影的顶层view
    private View windowView;
    private View barView;
    private LayoutParams contentParams, barParams;
    private int orientation;
    private int barLayout, windowLayout;
    private boolean isShowing;
    private OnDismissListener onDismissListener;
    private PopupAgent popupAgent;

    public BottomDrawer(Context context, @LayoutRes int layoutRes) {
        super(context);
        setFocusable(true);
        setClickable(true);

        drawerContentView=LayoutInflater.from(context).inflate(layoutRes,this,false);
        View popupWindow=LayoutInflater.from(context).inflate(R.layout.dark_popup_window,this,false);
        FrameLayout container=popupWindow.findViewById(R.id.popup_container);
        container.addView(drawerContentView);
        popupAgent=new PopupAgent.Builder(context)
                .setPopupWindow(popupWindow)
                .setPopupContent(container)
                .setPopupDark(popupWindow.findViewById(R.id.popup_dark))
                .setInAnimation(AnimationUtils.loadAnimation(context,R.anim.bottom_view_in))
                .setOutAnimation(AnimationUtils.loadAnimation(context,R.anim.bottom_view_out))
                .build();

    }


    public void showPopupView() {
        popupAgent.show();
    }

    public void disMissPopupView() {
        popupAgent.dismiss();
    }

    public void setBottomMargin(int margin){
        popupAgent.setMargins(new int[]{0,0,0,margin});
    }

    public boolean isShowing() {
        return popupAgent.isShowing();
    }


    public View getDrawerContent() {
        return drawerContentView;
    }

    public OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(final OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        popupAgent.setOnDismissListener(new PopupAgent.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissListener!=null){
                    onDismissListener.onDismiss();
                }
            }
        });
    }

    public interface OnDismissListener{
        void onDismiss();
    }
}
