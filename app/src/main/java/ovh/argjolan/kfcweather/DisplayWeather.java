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

        /// TODO : Asynchronous loading
        /// TODO : Add customized messages depending on the temperature
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("KFC", response);
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
                        setupViewPager((ViewPager) findViewById(R.id.container));
                        ((TabLayout) findViewById(R.id.temperatureUnitsTabs)).setupWithViewPager((ViewPager) findViewById(R.id.container));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setupViewPager((ViewPager) findViewById(R.id.container));
                        ((TabLayout) findViewById(R.id.temperatureUnitsTabs)).setupWithViewPager((ViewPager) findViewById(R.id.container));
                        Toast.makeText(getApplication(), "An error has occured", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(stringRequest);
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
