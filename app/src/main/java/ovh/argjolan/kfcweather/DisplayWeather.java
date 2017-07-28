package ovh.argjolan.kfcweather;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

/**
 * Created by ArGJolan on 27/07/2017.
 */

public class DisplayWeather extends AppCompatActivity {

    private SectionsPageAdapter adapter;

    private ViewPager weatherMessageViewPager;

    private String username;
    private String location;

    private double kelvinTemperature;
    private double fahrenheitTemperature;
    private double celsiusTemperature = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);

        username = getIntent().getStringExtra("username");
        location = getIntent().getStringExtra("location");

        /// TODO : http request to weather API
        fahrenheitTemperature = celsiusTemperature * 9 / 5 + 32;
        kelvinTemperature = celsiusTemperature + 273.15;

        weatherMessageViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(weatherMessageViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(weatherMessageViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(createTemperatureTabFragment("K", kelvinTemperature));
        adapter.addFragment(createTemperatureTabFragment("°F", fahrenheitTemperature));
        adapter.addFragment(createTemperatureTabFragment("°C", celsiusTemperature));

        viewPager.setAdapter(adapter);
    }

    private Bundle createTemperatureTabFragmentBundle(String temperatureUnit, double temperatureValue) {
        Bundle bundle = new Bundle();

        bundle.putString("username", username);
        bundle.putString("location", location);
        bundle.putString("unit", temperatureUnit);
        bundle.putDouble("temperature", temperatureValue);

        return bundle;
    }

    private  Fragment createTemperatureTabFragment(String temperatureUnit, double temperatureValue) {
        FragmentTemperatureTab nTemperatureTabFragment = new FragmentTemperatureTab();

        nTemperatureTabFragment.setArguments(createTemperatureTabFragmentBundle(temperatureUnit, temperatureValue));

        return nTemperatureTabFragment;
    }

}
