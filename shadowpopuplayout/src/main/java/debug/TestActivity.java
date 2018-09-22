package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //View view=LayoutInflater.from(this).inflate(R.layout.bottom_bar,null);
        //button=view.findViewById(R.id.menu);
        //View v= LayoutInflater.from(this).inflate(R.layout.dark_popup_window,null);
        //final BottomPopupWindowView windowView=findViewById(R.id.window_view);
        //button=windowView.getBarView().findViewById(R.id.menu);
        //windowView.setContentView(v);
        //windowView.setBaseView(view);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new MyFragment()).commit();
    }
}
