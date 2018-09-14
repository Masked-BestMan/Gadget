package debug;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zbm.shadowpopupwindow.ShadowPopupWindow;

public class TestActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final View view=findViewById(R.id.bottom_bar);
        button=findViewById(R.id.menu);
        View v= LayoutInflater.from(this).inflate(R.layout.dark_popup_window,null);
        final ShadowPopupWindow shadowPopupWindow=new ShadowPopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, 400);
        shadowPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        shadowPopupWindow.setOutsideTouchable(true);
        shadowPopupWindow.setFocusable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shadowPopupWindow.shadowAbove(view);
                shadowPopupWindow.showAsDropDown(view,0,0);
            }
        });
    }
}
