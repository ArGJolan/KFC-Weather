package ovh.argjolan.kfcweather;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ArGJolan on 28/07/2017.
 */

public class FragmentTemperatureTab extends Fragment {

    TextView messageTextView;
    TextView temperatureTextView;

    SpannableStringBuilder messageString;
    String temperatureString;

    String location;
    String username;
    String temperature;
    String unit;
    String statusMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.activity_fragment_temperature_tab, container, false);

        messageTextView = (TextView) view.findViewById(R.id.salutationsTextView);
        temperatureTextView = (TextView) view.findViewById(R.id.temperatureTextView);
        Bundle arguments = getArguments();
        if (username == null) {
            username = arguments.getString("username");
            location = arguments.getString("location");
            unit = arguments.getString("unit");
        }
        if (temperature == null) {
            temperature = arguments.getString("temperature");
            statusMessage = arguments.getString("statusMessage");
        }

        displayMessage();

        return view;
    }

    /**
     * setter for temperature // also refreshes the message displayed
     *
     * @param temperature : value to be set
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * setter for statusMessage // also refreshes the message displayed
     *
     * @param statusMessage : value de be set
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * displays greetings message and temperature
     */
    public void displayMessage() {
        messageString = new SpannableStringBuilder(String.format("%s %s %s %s. %s",
                getResources().getString(R.string.salutation),
                username,
                getResources().getString(R.string.from),
                location,
                statusMessage));

        int salutationLength = getResources().getString(R.string.salutation).length();
        int fromLength = getResources().getString(R.string.salutation).length();

        // sets bold style to user and location
        messageString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                salutationLength + 1,
                salutationLength + username.length() + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        messageString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                salutationLength + username.length() + fromLength + 3,
                salutationLength + username.length() + fromLength + location.length() + 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // sets white text style to user and location
        messageString.setSpan(new ForegroundColorSpan(Color.WHITE),
                salutationLength + 1,
                salutationLength + username.length() + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        messageString.setSpan(new ForegroundColorSpan(Color.WHITE),
                salutationLength + username.length() + fromLength + 3,
                salutationLength + username.length() + fromLength + location.length() + 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        temperatureString = String.format("%s%s",
                temperature,
                unit);

        messageTextView.setText(messageString);
        temperatureTextView.setText(temperatureString);
    }

    /**
     * same as displayMessage() but checks if the fragment has been created
     */
    public void safeDisplayMessage() {
        if (getView() != null) {
            displayMessage();
        }
    }
}
