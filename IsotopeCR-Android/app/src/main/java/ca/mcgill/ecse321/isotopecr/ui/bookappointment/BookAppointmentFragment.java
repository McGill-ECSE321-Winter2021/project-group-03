package ca.mcgill.ecse321.isotopecr.ui.bookappointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.isotopecr.R;



public class BookAppointmentFragment extends Fragment {

    private List<String> licensePlates = new ArrayList<>();
    private ArrayAdapter<String> vehicleAdapter;
    private List<String> services = new ArrayList<>();
    private ArrayAdapter<String> serviceAdapter;

    private String selectedVehicle = "";
    private String selectedService = "";

    private Spinner vehicleSpinner;
    private Spinner serviceSpinner;

    private TextView vehicleView;
    private TextView serviceView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        return inflater.inflate(R.layout.fragment_bookappointment, container, false);
    }


}
