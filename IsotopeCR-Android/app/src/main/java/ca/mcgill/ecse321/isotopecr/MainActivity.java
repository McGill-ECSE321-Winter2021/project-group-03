package ca.mcgill.ecse321.isotopecr;

import android.os.Bundle;
import ca.mcgill.ecse321.isotopecr.HttpUtils;
import cz.msebera.android.httpclient.Header;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String error = null;
    // APPEND NEW CONTENT STARTING FROM HERE
    /*private List<String> personNames = new ArrayList<>();
    private ArrayAdapter<String> personAdapter;             // arrayadapter is a way of viewing array objects for front-end
    private List<String> eventNames = new ArrayList<>();
    private ArrayAdapter<String> eventAdapter;*/

    private List<String> licensePlates = new ArrayList<>();
    private ArrayAdapter<String> vehicleAdapter;
    private List<String> services = new ArrayList<>();
    private ArrayAdapter<String> serviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        refreshErrorMessage();

        // 4.5.3
       /* Spinner personSpinner = (Spinner) findViewById(R.id.personspinner);
        Spinner eventSpinner = (Spinner) findViewById(R.id.eventspinner);

        personAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, personNames);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);

        eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventNames);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdaptereventAdapter);

        // Get initial content for spinners
        refreshLists(this.getCurrentFocus());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // added in 4.3.2
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    // ===================================================
    // ============ Write function handler ===============
    // ===================================================
   /* public void addPerson(View v) {
        error = "";
        final TextView tv = (TextView) findViewById(R.id.newperson_name);
        HttpUtils.post("persons/" + tv.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                tv.setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }*/

    /**
     * This method get all the vehicles registered by a customer.
     * @param v
     */
    public void GetVehicle(View v) {
        error = "";
        final TextView tv = (TextView) findViewById(R.id.customer_email);
        final Spinner s = (Spinner) findViewById(R.id.vehiclespinner);
        vehicleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, licensePlates);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(vehicleAdapter);

        HttpUtils.get("/api/profile/customer/vehicle/get-all/" + tv.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                refreshErrorMessage();
                /*System.out.println("==========================================");
                System.out.println("StatusCode = " + statusCode);
                System.out.println("==========================================");
                System.out.println(response);*/
                for (int i = 0; i < response.length(); i++) {
                    JSONObject vehicleJSON = null;
                    try {
                        vehicleJSON = response.getJSONObject(i);
                        String vehicle = vehicleJSON.getString("licensePlate");
                        licensePlates.add(vehicle);
                        System.out.println(vehicle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tv.setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    /**
     * This method get all the services provided in the system.
     * @param v
     */
    public void GetServices(View v) {
        error = "";

        Spinner s = (Spinner) findViewById(R.id.servicespinner);
        serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, services);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(serviceAdapter);

        HttpUtils.get("/api/autorepairshop/service/get-all" , new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                refreshErrorMessage();
                System.out.println("==========================================");
                System.out.println("StatusCode = " + statusCode);
                System.out.println("==========================================");
                System.out.println(response);
                for (int i = 0; i < response.length(); i++) {
                    JSONObject serviceJSON = null;
                    try {
                        serviceJSON = response.getJSONObject(i);
                        String service = serviceJSON.getString("serviceName");
                        services.add(service);
                        System.out.println(service);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }


    // ===================================================
    // ==================== Helpers ======================
    // ===================================================
    private Bundle getTimeFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;

        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);

        return rtn;
    }

    private Bundle getDateFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }
}