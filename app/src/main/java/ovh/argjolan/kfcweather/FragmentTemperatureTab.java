package ovh.argjolan.kfcweather;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ArGJolan on 28/07/2017.
 */

public class FragmentTemperatureTab extends Fragment {

    TextView messageTextView;
    String messageString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View kelvinView = inflater.inflate(R.layout.activity_fragment_temperature_tab, container, false);

        messageTextView = (TextView) kelvinView.findViewById(R.id.textView);
        Bundle arguments = getArguments();
        messageString = "Hi " + arguments.getString("username") + " from " +
                arguments.getString("location") + ". It's currently " +
                arguments.getDouble("temperature") + arguments.getString("unit")  + " here.";

        messageTextView.setText(messageString);


        return kelvinView;
    }
}
