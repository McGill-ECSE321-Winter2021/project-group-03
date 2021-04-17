package ca.mcgill.ecse321.isotopecr;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.isotopecr.ui.home.HomeViewModel;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private final List<String> licensePlates = new ArrayList<>();
    private final List<String> services = new ArrayList<>();
    private final List<String> appointmentTimes = new ArrayList<>();
    private final List<String> appointmentDates = new ArrayList<>();

    private AppBarConfiguration mAppBarConfiguration;
    private String loginEmail = null;           // variable to store the email of the logged in user
    private HomeViewModel viewModel = null;     // viewModel for updating UI elements in home page

    private ArrayAdapter<String> vehicleAdapter;
    private ArrayAdapter<String> serviceAdapter;
    private String selectedVehicle = "";
    private String selectedService = "";
    private Spinner vehicleSpinner;
    private Spinner serviceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create a click listener for floating action button in home page
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact us at 1111111111", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Create a click listener for logout button in navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                logout();
                return true;
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_login, R.id.nav_bookappointment, R.id.nav_viewappointment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Get the view model for home page for subsequent updates to UI
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // ===================================================
    // ============ Http Request Functions ===============
    // ===================================================

    /**
     * Customer login method.
     * This method would update the logged in email and UI elements in navigation bar and home page.
     *
     * @param v view
     * @author Jack Wei
     */
    public void login(final View v) {
        final TextView email = findViewById(R.id.login_email);
        final TextView password = findViewById(R.id.login_password);

        hideVirtualKeyboard(v);

        // Check if input email could be an employee's account (prohibited on android app), if so, shown an error.
        String emailString = email.getText().toString();
        if (emailString.contains("isotopecr.ca")) {
            showMessageWithToast("ERROR: Profile does not exist.");
            return;
        }

        // Construct request parameter
        RequestParams params = new RequestParams();
        params.put("email", emailString);
        params.put("password", password.getText().toString());

        // Send login post request
        HttpUtils.post("/api/profile/login/", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    // Update the logged in email
                    loginEmail = response.getString("email");

                    // Update UI in Home page for custom welcome message
                    String firstName = response.getString("firstName");
                    viewModel.setFirstName(firstName);

                    // Hide login and reveal book appointment and view appointments in navigation
                    NavigationView navigationView = findViewById(R.id.nav_view);
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_login).setVisible(false);
                    nav_Menu.findItem(R.id.nav_bookappointment).setVisible(true);
                    nav_Menu.findItem(R.id.nav_viewappointment).setVisible(true);
                    nav_Menu.findItem(R.id.nav_logout).setVisible(true);

                    // Navigate to home page
                    Navigation.findNavController(v).navigate(R.id.nav_home);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Show error message
                showMessageWithToast(responseString);
            }
        });
    }

    /**
     * Customer logout function.
     *
     * @author Jack Wei
     */
    public void logout() {
        // clear email for currently logged in user
        loginEmail = null;

        // reveal login and hide book appointment and logout in navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_login).setVisible(true);
        nav_Menu.findItem(R.id.nav_bookappointment).setVisible(false);
        nav_Menu.findItem(R.id.nav_viewappointment).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);

        // update home page UI to generic welcome message
        viewModel.resetText();

        // navigate to home page
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_home);
    }


    /**
     * This method get all the vehicles registered by a customer.
     *
     * @param v view
     * @author Zichen
     */
    public void GetVehicle(View v) {
        // set up the Listener for the spinner once clicked
        vehicleSpinner = findViewById(R.id.vehiclespinner);
        vehicleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, licensePlates);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(vehicleAdapter);
        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get selected item and assign to textview
                String licensePlate = vehicleSpinner.getSelectedItem().toString();
                selectedVehicle = licensePlate;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedVehicle = "";
            }
        });

        HttpUtils.get("/api/profile/customer/vehicle/get-all/" + loginEmail, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                licensePlates.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String vehicle = response.getJSONObject(i).getString("licensePlate");
                        licensePlates.add(vehicle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                vehicleAdapter.notifyDataSetChanged();  // update the change in the frontend
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Show error message
                showMessageWithToast(responseString);
            }
        });
    }

    /**
     * This method get all the services provided in the system.
     *
     * @param v view
     * @author Zichen
     */
    public void GetServices(View v) {
        // set up the Listener for the spinner once clicked
        serviceSpinner = findViewById(R.id.servicespinner);
        serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, services);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(serviceAdapter);
        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get selected item and assign to textview
                String serviceName = serviceSpinner.getSelectedItem().toString();
                selectedService = serviceName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedService = "";
            }
        });

        HttpUtils.get("/api/autorepairshop/service/get-all", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                services.clear();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject serviceJSON = null;
                    try {
                        serviceJSON = response.getJSONObject(i);
                        String service = serviceJSON.getString("serviceName");
                        services.add(service);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                serviceAdapter.notifyDataSetChanged();  // update the change in the frontend
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Show error message
                showMessageWithToast(responseString);
            }
        });
    }

    /**
     * This method gets the future appointments for current login user in the system.
     *
     * @param v view
     * @author Jiatong
     */
    public void GetFutureAppointment(View v) {

        HttpUtils.get("/api/appointment/futureappointment/customer/" + loginEmail, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //calling on helper method to generate the appointment table
                getAppointments(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Show error message
                showMessageWithToast(responseString);
            }
        });
    }

    /**
     * This method gets the past appointments for current login user in the system.
     *
     * @param v view
     * @author Jiatong
     */
    public void GetPastAppointment(View v) {

        HttpUtils.get("/api/appointment/pastappointment/customer/" + loginEmail, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //calling on helper method to generate the appointment table
                getAppointments(response);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Show error message
                showMessageWithToast(responseString);
            }
        });
    }

    /**
     * This method will book an appointment for the user.
     *
     * @param v view
     * @autor Zichen
     */
    public void BookAppointment(View v) {
        serviceSpinner = findViewById(R.id.servicespinner);
        vehicleSpinner = findViewById(R.id.vehiclespinner);
        TextView date = findViewById(R.id.newappointment_date);
        TextView time = findViewById(R.id.starttime);

        if (vehicleSpinner.getSelectedItem() != null && serviceSpinner.getSelectedItem() != null) {
            // format the input date & time to backend-accepted format
            Bundle dateBundle = getDateFromLabel(date.getText().toString());
            String formatDate = formatISODate(dateBundle);

            Bundle timeBundle = getTimeFromLabel(time.getText().toString());
            String formatTime = formatISOTime(timeBundle);

            // Construct request parameter
            RequestParams params = new RequestParams();
            params.put("start", formatTime);
            params.put("date", formatDate);

            // Encode the URL
            String url = "/api/appointment/create/" + selectedVehicle + "/" + selectedService;
            String fullUrl = url.replace(" ", "%20");

            HttpUtils.post(fullUrl, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    showMessageWithToast("Book Appointment Successful!");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    showMessageWithToast(responseString);
                }
            });

        } else {
            showMessageWithToast("Please select vehicle and services before your Booking");
        }
    }


    // ===================================================
    // ==================== Helpers ======================
    // ===================================================

    /**
     * Transfer the format of Android-frontend time into ISO format
     *
     * @param timeBundle
     * @return a time string with desired format
     */
    private String formatISOTime(Bundle timeBundle) {
        int hour = timeBundle.getInt("hour");
        int minute = timeBundle.getInt("minute");

        return String.format("%02d:%02d:%02d", hour, minute, 0);
    }

    /**
     * Transfer the format of Android-frontend date into ISO format
     *
     * @param dateBundle
     * @return a date string with desired format
     */
    private String formatISODate(Bundle dateBundle) {
        int year = dateBundle.getInt("year");
        int month = dateBundle.getInt("month");
        int day = dateBundle.getInt("day");
        return String.format("%04d-%02d-%02d", year, month + 1, day);
    }

    /**
     * Get the time bundle from the label
     *
     * @param text
     * @return a bundle of time
     */
    private Bundle getTimeFromLabel(String text) {
        Bundle rtn = new Bundle();
        String[] comps = text.split(":");
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

    /**
     * Get the date bundle from the label
     *
     * @param text
     * @return a bundle of date
     */
    private Bundle getDateFromLabel(String text) {
        Bundle rtn = new Bundle();
        String[] comps = text.split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month - 1);
        rtn.putInt("year", year);

        return rtn;
    }

    /**
     * Show the TimePickerDialog when booking an appointment
     *
     * @param v
     */
    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Show the DatePickerDialog when booking an appointment
     *
     * @param v
     */
    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Set the content of the text view after choosing the time
     *
     * @param id TextView id
     * @param h  hour
     * @param m  minute
     */
    public void setTime(int id, int h, int m) {
        TextView tv = findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    /**
     * Set the content of the text view after choosing the time
     *
     * @param id TextView id
     * @param d  day
     * @param m  month
     * @param y  year
     */
    public void setDate(int id, int d, int m, int y) {
        TextView tv = findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

    /**
     * Helper method for hiding virtual keyboard.
     *
     * @param v view from which virtual keyboard should be hidden
     * @author Jack Wei
     */
    private void hideVirtualKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Helper method for showing a popup message.
     *
     * @param message message shown
     */
    private void showMessageWithToast(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, text, duration).show();
    }

    /**
     * Helper method that deal with the response and put the information that we want into
     * the adapter that is manually created and link the adapter with listview
     *
     * @param response response after calling HttpUtil.get
     * @author Jiatong Niu
     */
    private void getAppointments(JSONArray response) {
        //make sure every time before putting information into the array, the array is empty
        licensePlates.clear();
        services.clear();
        appointmentDates.clear();
        appointmentTimes.clear();

        for (int i = 0; i < response.length(); i++) {
            JSONObject appointmentJSON = null;
            JSONObject vehicleJSON = null;
            JSONObject serviceJSON = null;
            JSONArray timeslotsJSON = null;
            JSONObject firsttimeslotJSON = null;
            try {
                appointmentJSON = response.getJSONObject(i);

                //use a JSONObject to represent vehicle and get licensePlate
                vehicleJSON = appointmentJSON.getJSONObject("vehicle");
                String licensePlate = vehicleJSON.getString("licensePlate");
                licensePlates.add(licensePlate);

                //use a JSONObject to represent service and get serviceName
                serviceJSON = appointmentJSON.getJSONObject("service");
                String serviceName = serviceJSON.getString("serviceName");
                services.add(serviceName);

                //use a JSONArray to represent timeslots, use JSONObject to get the first time slot and get start Time and star Date
                timeslotsJSON = appointmentJSON.getJSONArray("timeslots");
                firsttimeslotJSON = timeslotsJSON.getJSONObject(0);
                String appointmentTime = firsttimeslotJSON.getString("time");
                appointmentTimes.add(appointmentTime);
                String appointmentDate = firsttimeslotJSON.getString("date");
                appointmentDates.add(appointmentDate);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ViewAppointmentAdapter appointmentAdapter = new ViewAppointmentAdapter(getBaseContext(), licensePlates, services, appointmentDates, appointmentTimes);

        //represent the listview in the viewappointment.xml
        ListView aListView = findViewById(R.id.futureappointmentListView);
        aListView.setAdapter(appointmentAdapter);
    }
}
