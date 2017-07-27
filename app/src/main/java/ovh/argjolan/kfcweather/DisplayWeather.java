package ovh.argjolan.kfcweather;

import android.support.design.widget.TabItem;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayWeather extends AppCompatActivity {

    Fragment    fragmentKelvinTab = new FragmentKelvinTab();
    Fragment    fragmentFahrenheitTab = new FragmentFahrenheitTab();
    Fragment    fragmentCelsiusTab = new FragmentCelsiusTab();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);

        //((TabItem)findViewById(R.id.kelvinTab)).
    }
}
