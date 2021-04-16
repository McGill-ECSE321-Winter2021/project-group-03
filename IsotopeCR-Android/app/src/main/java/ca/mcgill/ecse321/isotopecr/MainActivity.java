package ca.mcgill.ecse321.isotopecr;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.isotopecr.ui.home.HomeViewModel;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // APPEND NEW CONTENT STARTING FROM HERE
    // private final List<String> personNames = new ArrayList<>();
    private final List<String> eventNames = new ArrayList<>();
    private final List<String> licensePlates = new ArrayList<>();
    private final List<String> services = new ArrayList<>();
    private AppBarConfiguration mAppBarConfiguration;
    private String error = null;
    private String loginEmail = null;
    private HomeViewModel viewModel = null;



    // APPEND NEW CONTENT STARTING FROM HERE
    private List<String> personNames = new ArrayList<>();

    private ArrayAdapter<String> personAdapter;             // arrayadapter is a way of viewing array objects for front-end
    private ArrayAdapter<String> eventAdapter;
    private ArrayAdapter<String> vehicleAdapter;
    private ArrayAdapter<String> serviceAdapter;

    private String selectedVehicle = "";
    private String selectedService = "";

    private Spinner vehicleSpinner;
    private Spinner serviceSpinner;

    private TextView vehicleView;
    private TextView serviceView;

    ListView aListView;
    private final List<String> appointmentTimes = new ArrayList<>();
    private final List<String> appointmentDates = new ArrayList<>();

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                R.id.nav_home, R.id.nav_login, R.id.nav_slideshow, R.id.nav_bookappointment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        refreshErrorMessage();
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


    // added in 4.3.2
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error1);
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
//    public void addPerson(View v) {
//        error = "";
//        final TextView tv = (TextView) findViewById(R.id.newperson_name);
//        HttpUtils.post("persons/" + tv.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                refreshErrorMessage();
//                tv.setText("");
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                try {
//                    error += errorResponse.get("message").toString();
//                } catch (JSONException e) {
//                    error += e.getMessage();
//                }
//                refreshErrorMessage();
//            }
//        });
//    }

    /**
     * Customer login function.
     *
     * @param v
     * @author Jack Wei
     */
    public void login(final View v) {
        error = "";

        final TextView email = (TextView) findViewById(R.id.login_email);
        final TextView password = (TextView) findViewById(R.id.login_password);

        // Construct request parameter
        RequestParams params = new RequestParams();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());

        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        System.out.println(params.toString());

        // Send login post request
        HttpUtils.post("/api/profile/login/", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error = "";
                refreshErrorMessage();
                try {
                    // Updates the logged in email
                    loginEmail = response.getString("email");
                    String firstName =  response.getString("firstName");

                    viewModel.setFirstName(firstName);

                    NavigationView navigationView = findViewById(R.id.nav_view);
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_login).setVisible(false);
                    nav_Menu.findItem(R.id.nav_logout).setVisible(true);
                    Navigation.findNavController(v).navigate(R.id.nav_home);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                error += responseString;
                refreshErrorMessage();
            }
        });
    }

    /**
     * Customer logout function.
     *
     * @author Jack Wei
     */
    public void logout() {
        loginEmail = null;

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_login).setVisible(true);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);

        viewModel.resetText();

        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_home);

    }


    /**
     * This method get all the vehicles registered by a customer.
     *
     * @param v
     */
    public void GetVehicle(View v) {
        error = "";
//        final TextView customerEmail = (TextView) findViewById(R.id.customer_email);

        vehicleSpinner = (Spinner) findViewById(R.id.vehiclespinner);

        vehicleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, licensePlates);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(vehicleAdapter);
        vehicleView = (TextView) findViewById(R.id.vehicle_licenseplate);

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get selected item and assign to textview
                String licensePlate = vehicleSpinner.getSelectedItem().toString();

                selectedVehicle = licensePlate;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // handle if you'd like to
                selectedVehicle = "";
            }
        });


        HttpUtils.get("/api/profile/customer/vehicle/get-all/" + loginEmail, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                refreshErrorMessage();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String vehicle = response.getJSONObject(i).getString("licensePlate");
                        licensePlates.add(vehicle);
                        System.out.println(vehicle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                customerEmail.setText("");
                vehicleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
//                refreshErrorMessage();
            }
        });
    }

    /**
     * This method get all the services provided in the system.
     *
     * @param v
     */
    public void GetServices(View v) {
        error = "";

        serviceSpinner = (Spinner) findViewById(R.id.servicespinner);
        serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, services);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(serviceAdapter);
        serviceView = (TextView) findViewById(R.id.service_name);

//        serviceView.setText("Hello world");

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get selected item and assign to textview
                String serviceName = serviceSpinner.getSelectedItem().toString();
//                String serviceName = (String) parent.getItemAtPosition(position);
//                serviceView.setText(serviceName);
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
//                refreshErrorMessage();
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
                serviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
//                refreshErrorMessage();
            }
        });
    }

    public void GetFutureAppointment(View v) {
        error = "";

        final TextView email = (TextView) findViewById(R.id.customer_email);
        String inputEmail = email.toString();

        HttpUtils.get("/api/appointment/futureappointment/customer/"+inputEmail, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i=0; i< response.length() ; i++) {
                    JSONObject appointmentJSON = null;
                    JSONObject vehicleJSON = null;
                    JSONObject serviceJSON = null;
                    JSONArray timeslotsJSON = null;
                    JSONObject firsttimeslotJSON = null;
                    try{
                        appointmentJSON = response.getJSONObject(i);
                        vehicleJSON = appointmentJSON.getJSONObject("vehicle");
                        String licensePlate = vehicleJSON.getString("licensePlate");
                        licensePlates.add(licensePlate);
                        serviceJSON = appointmentJSON.getJSONObject("service");
                        String serviceName = serviceJSON.getString("serviceName");
                        services.add(serviceName);
                        timeslotsJSON = appointmentJSON.getJSONArray("timeslot");
                        firsttimeslotJSON = timeslotsJSON.getJSONObject(0);
                        String appointmentTime = firsttimeslotJSON.getString("time");
                        appointmentTimes.add(appointmentTime);
                        String appointmentDate = firsttimeslotJSON.getString("date");
                        appointmentDates.add(appointmentDate);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ViewAppointmentAdapter appointmentAdapter = new ViewAppointmentAdapter(getBaseContext(),licensePlates,services,appointmentDates,appointmentTimes);
                aListView.setAdapter(appointmentAdapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
//                refreshErrorMessage();
            }
        });
    }




    /**
                 * This method book an appointment for the user.
                 *
                 * @param v
                 */
        public void BookAppointment (View v){
            error = "";

            serviceSpinner = (Spinner) findViewById(R.id.servicespinner);
            vehicleSpinner = (Spinner) findViewById(R.id.vehiclespinner);
            TextView date = (TextView) findViewById(R.id.newappointment_date);
            TextView time = (TextView) findViewById(R.id.starttime);

            // get PathVariables
            String vehicleplate = vehicleSpinner.getSelectedItem().toString();
            String servicename = serviceSpinner.getSelectedItem().toString();

            System.out.println("======================================");
            System.out.println(date.getText().toString());
            System.out.println("======================================");

            Bundle dateBundle = getDateFromLabel(date.getText().toString());
            String formatDate = formatISODate(dateBundle);
            System.out.println(formatDate);

            Bundle timeBundle = getTimeFromLabel(time.getText().toString());
            String formatTime = formatISOTime(timeBundle);
            System.out.println(formatTime);

            System.out.println("======================================");

            // Construct request parameter
            RequestParams params = new RequestParams();
            params.put("start", formatTime);
            params.put("date", formatDate);


            String uri = "/api/appointment/create/" + vehicleplate + "/" + servicename;
            String fullUri = uri.replace(" ", "%20");

            System.out.println(fullUri);

            HttpUtils.post(fullUri, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                refreshErrorMessage();
                    System.out.println("==========================================");
                    System.out.println("StatusCode = " + statusCode);
                    System.out.println("==========================================");
                    System.out.println(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
//                refreshErrorMessage();
                }
            });


        }

        private String formatISOTime (Bundle timeBundle){
            int hour = timeBundle.getInt("hour");
            int minute = timeBundle.getInt("minute");

            return String.format("%02d:%02d:%02d", hour, minute, 0);
        }

        private String formatISODate (Bundle dateBundle){
            int year = dateBundle.getInt("year");
            int month = dateBundle.getInt("month");
            int day = dateBundle.getInt("day");

            return String.format("%04d-%02d-%02d", year, month + 1, day);

        }

        // ===================================================
        // ==================== Helpers ======================
        // ===================================================
        private Bundle getTimeFromLabel (String text){
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

        private Bundle getDateFromLabel (String text){
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

        public void showTimePickerDialog (View v){
            TextView tf = (TextView) v;
            Bundle args = getTimeFromLabel(tf.getText().toString());
            args.putInt("id", v.getId());

            TimePickerFragment newFragment = new TimePickerFragment();
            newFragment.setArguments(args);
            newFragment.show(getSupportFragmentManager(), "timePicker");
        }

        public void showDatePickerDialog (View v){
            TextView tf = (TextView) v;
            Bundle args = getDateFromLabel(tf.getText().toString());
            args.putInt("id", v.getId());

            DatePickerFragment newFragment = new DatePickerFragment();
            newFragment.setArguments(args);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }

        public void setTime ( int id, int h, int m){
            TextView tv = (TextView) findViewById(id);
            tv.setText(String.format("%02d:%02d", h, m));
        }

        public void setDate ( int id, int d, int m, int y){
            TextView tv = (TextView) findViewById(id);
            tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
        }
    }
