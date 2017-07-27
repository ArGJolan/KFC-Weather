package ovh.argjolan.kfcweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by ArGJolan on 27/07/2017.
 */

public class FragmentKelvinTab extends Fragment {
    @Override
    public View     onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View        kelvinView = inflater.inflate(R.layout.fragment_kelvin_tab, container, false);
        return kelvinView;
    }
}
