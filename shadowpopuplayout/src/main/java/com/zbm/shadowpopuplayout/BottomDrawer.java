package com.zbm.shadowpopuplayout;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;



@SuppressWarnings("unused")
public class BottomDrawer extends FrameLayout implements BaseWindow{
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
    private PopupHelper popupHelper;

    public BottomDrawer(Context context, @LayoutRes int layoutRes) {
        super(context);
        setFocusable(true);
        setClickable(true);

        drawerContentView=LayoutInflater.from(context).inflate(layoutRes,this,false);
        View popupWindow=LayoutInflater.from(context).inflate(R.layout.dark_popup_window,this,false);
        FrameLayout container=popupWindow.findViewById(R.id.popup_container);
        container.addView(drawerContentView);
        popupHelper =new PopupHelper.Builder(context)
                .setPopupWindow(popupWindow)
                .setPopupContent(container)
                .setPopupDark(popupWindow.findViewById(R.id.popup_dark))
                .setInAnimation(AnimationUtils.loadAnimation(context,R.anim.bottom_view_in))
                .setOutAnimation(AnimationUtils.loadAnimation(context,R.anim.bottom_view_out))
                .build();

    }

    @Override
    public void show() {
        popupHelper.show();
    }

    @Override
    public void dismiss() {
        popupHelper.dismiss();
    }

    public void setBottomMargin(int margin){
        popupHelper.setMargins(new int[]{0,0,0,margin});
    }

    @Override
    public boolean isShowing() {
        return popupHelper.isShowing();
    }


    public View getDrawerContent() {
        return drawerContentView;
    }

    public OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(final OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        popupHelper.setOnDismissListener(new PopupHelper.OnDismissListener() {
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
