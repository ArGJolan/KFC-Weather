package ovh.argjolan.kfcweather;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ArGJolan on 27/07/2017.
 */

public class DisplayWeather extends AppCompatActivity {

    private SectionsPageAdapter adapter;


    private String username;
    private String location;

    private String kelvinTemperature = "-";
    private String fahrenheitTemperature = "-";
    private String celsiusTemperature = "-";

    /**
     * sets up the http request, when it's done sets up the tabs and fragments used to switch
     * the unit of the displayed temperature
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);

        username = getIntent().getStringExtra("username");
        location = getIntent().getStringExtra("location");

        RequestQueue queue = Volley.newRequestQueue(this);
        String requestUrl = String.format(getResources().getString(R.string.api_endpoint), location);

        setupViewPager((ViewPager) findViewById(R.id.container));
        ((TabLayout) findViewById(R.id.temperatureUnitsTabs)).setupWithViewPager((ViewPager) findViewById(R.id.container));

        /// TODO : Change temperature color depending on the temperature
        /// TODO : API exceptions
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject conditionContent = new JSONObject(response).getJSONObject("query")
                                    .getJSONObject("results").getJSONObject("channel")
                                    .getJSONObject("item").getJSONObject("condition");

                            Double numericalFahrenheitTemperature = Double.parseDouble(conditionContent.getString("temp"));
                            fahrenheitTemperature = Double.toString(Math.floor(numericalFahrenheitTemperature * 10) / 10);

                            Double numericalCelsiusTemperature = (numericalFahrenheitTemperature - 32) * 5 / 9;
                            celsiusTemperature = Double.toString(Math.floor(numericalCelsiusTemperature * 10) / 10);

                            Double numericalKelvinTemperature = numericalCelsiusTemperature + 273.15;
                            kelvinTemperature = Double.toString(Math.floor(numericalKelvinTemperature * 10) / 10);
                        } catch (JSONException e) {
                            Toast.makeText(getApplication(), "An error has occured", Toast.LENGTH_SHORT).show();
                        }
                        refreshFragmentsDisplay();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "An error has occured", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(stringRequest);
    }

    /**
     * Sets th temperature and status message according to the result of the request
     * displays the welcoming message in each fragment
     */
    void refreshFragmentsDisplay() {
        String statusMessage = getStatusMessage();

        ((FragmentTemperatureTab) adapter.getItem(0)).setTemperature(kelvinTemperature);
        ((FragmentTemperatureTab) adapter.getItem(1)).setTemperature(fahrenheitTemperature);
        ((FragmentTemperatureTab) adapter.getItem(2)).setTemperature(celsiusTemperature);

        ((FragmentTemperatureTab) adapter.getItem(0)).setStatusMessage(statusMessage);
        ((FragmentTemperatureTab) adapter.getItem(1)).setStatusMessage(statusMessage);
        ((FragmentTemperatureTab) adapter.getItem(2)).setStatusMessage(statusMessage);

        ((FragmentTemperatureTab) adapter.getItem(0)).safeDisplayMessage();
        ((FragmentTemperatureTab) adapter.getItem(1)).safeDisplayMessage();
        ((FragmentTemperatureTab) adapter.getItem(2)).safeDisplayMessage();
    }

    /**
     * @return a string depending on the temperature
     */
    String getStatusMessage() {
        if (!celsiusTemperature.equals("-")) {
            Double numericalDegreesTemperature = Double.parseDouble(celsiusTemperature);
            if (numericalDegreesTemperature <= 0){
                return getResources().getString(R.string.temp_is_freezing);
            }
            if (numericalDegreesTemperature <= 15) {
                return getResources().getString(R.string.temp_is_cold);
            }
            if (numericalDegreesTemperature <= 27) {
                return getResources().getString(R.string.temp_is_normal);
            }
            return getResources().getString(R.string.temp_is_warm);
        }
        return getResources().getString(R.string.no_info);
    }

    /**
     * adds fragment to the page adapter and apply it to the viewpager
     * @param viewPager : the viewPager we need to setup
     */
    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(createTemperatureTabFragment("K", kelvinTemperature));
        adapter.addFragment(createTemperatureTabFragment("°F", fahrenheitTemperature));
        adapter.addFragment(createTemperatureTabFragment("°C", celsiusTemperature));

        viewPager.setAdapter(adapter);
    }

    /**
     * creates a bundle that will be passed to a new temperatureTabFragment as argument
     * it contains a username, a location, a unit, and a temperature value
     * @param temperatureUnit : the unit that will be used in the corresponding tab
     * @param temperatureValue : the value that will be displayed in the corresponding tab
     * @return the newly created bundle
     */
    private Bundle createTemperatureTabFragmentBundle(String temperatureUnit, String temperatureValue) {
        Bundle bundle = new Bundle();

        bundle.putString("username", username);
        bundle.putString("location", location);
        bundle.putString("unit", temperatureUnit);
        bundle.putString("temperature", temperatureValue);
        bundle.putString("statusMessage", getStatusMessage());

        return bundle;
    }

    /**
     * creates a new fragment
     * @param temperatureUnit : the unit that will be used in the corresponding tab
     * @param temperatureValue : the value that will be displayed in the corresponding tab
     * @return the newly created fragment
     */
    private Fragment createTemperatureTabFragment(String temperatureUnit, String temperatureValue) {
        FragmentTemperatureTab nTemperatureTabFragment = new FragmentTemperatureTab();

        nTemperatureTabFragment.setArguments(createTemperatureTabFragmentBundle(temperatureUnit, temperatureValue));

        return nTemperatureTabFragment;
    }

}
