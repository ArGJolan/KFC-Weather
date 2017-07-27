package ovh.argjolan.kfcweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ArGJolan on 27/07/2017.
 */

public class FragmentCelsiusTab extends Fragment {
    @Override
    public View     onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View        celsiusView = inflater.inflate(R.layout.fragment_celsius_tab, container, false);

        return celsiusView;
    }
}
