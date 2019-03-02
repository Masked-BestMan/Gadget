package debug;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zbm.shadowpopuplayout.BottomDrawer;

public class MyFragment extends Fragment {
    private Button mainButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_layout,container,false);
        final BottomDrawer drawer=new BottomDrawer(getActivity(),R.layout.layout_bottom_popup);
        drawer.setBottomMargin(100);
        mainButton=v.findViewById(R.id.main_button);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.showPopupView();
            }
        });
        return v;
    }
}
