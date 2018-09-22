package debug;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zbm.shadowpopuplayout.ShadowPopupLayout;

public class MyFragment extends Fragment {
    private Button button,mainButton;
    private ShadowPopupLayout windowView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_layout,container,false);
        mainButton=v.findViewById(R.id.main_button);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击了main button",Toast.LENGTH_SHORT).show();
            }
        });
        windowView=v.findViewById(R.id.window_view);
        button=windowView.getBarView().findViewById(R.id.menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowView.showPopupView();
            }
        });
        windowView.setOnDismissListener(new ShadowPopupLayout.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(getActivity(),"dismiss",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
