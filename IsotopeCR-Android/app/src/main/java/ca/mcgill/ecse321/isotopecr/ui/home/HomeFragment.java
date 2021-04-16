package ca.mcgill.ecse321.isotopecr.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.mcgill.ecse321.isotopecr.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Create click listeners for home page navigation buttons
        final Button loginButton = (Button) root.findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_login);
            }
        });
        final Button bookAppointmentButton = (Button) root.findViewById(R.id.bookAppointmentButton);
        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_bookappointment);
            }
        });
        final Button viewAppointmentButton = (Button) root.findViewById(R.id.ViewAppointmentButton);
        viewAppointmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_viewappointment);
            }
        });

        // Create observer for updating home page UI elements
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Button bookAppointmentButton = (Button) root.findViewById(R.id.bookAppointmentButton);
                Button viewAppointmentButton = (Button) root.findViewById(R.id.ViewAppointmentButton);
                TextView signUpText = root.findViewById(R.id.signUp);

                if(s.equals("Isotope Repair Shop")){    // user logged out
                    // Reveal login button and sign up text and hide book appointment and view appointment buttons
                    loginButton.setVisibility(View.VISIBLE);
                    signUpText.setVisibility(View.VISIBLE);
                    bookAppointmentButton.setVisibility(View.INVISIBLE);
                    viewAppointmentButton.setVisibility(View.INVISIBLE);
                } else {    // user logged in
                    // Hide login button and sign up text and reveal book appointment and view appointment buttons
                    loginButton.setVisibility(View.INVISIBLE);
                    signUpText.setVisibility(View.INVISIBLE);
                    bookAppointmentButton.setVisibility(View.VISIBLE);
                    viewAppointmentButton.setVisibility(View.VISIBLE);
                }

                // Update homepage welcome message
                textView.setText(s);
                textView.setGravity(Gravity.CENTER);
            }
        });

        return root;
    }
}