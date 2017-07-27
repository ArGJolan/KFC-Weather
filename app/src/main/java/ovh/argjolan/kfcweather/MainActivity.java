package ovh.argjolan.kfcweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * set as confirm button's onclick
     * will start the display weather activity if both field are set
     * @param v : the confirm button's view (Unused)
     */
    public void onConfirmButtonPressed(View v) {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText    locationEditText = (EditText) findViewById(R.id.locationEditText);
        String      username = usernameEditText.getText().toString().trim();
        String      location = locationEditText.getText().toString().trim();

        if (!username.isEmpty() && !location.isEmpty()) {
            Intent displayWeatherIntent = new Intent(MainActivity.this, DisplayWeather.class);

            displayWeatherIntent.putExtra("username", username);
            displayWeatherIntent.putExtra("location", location);
            MainActivity.this.startActivity(displayWeatherIntent);
        } else {
            Toast.makeText(this, "Both fields are required", Toast.LENGTH_LONG).show();
        }
    }
}
