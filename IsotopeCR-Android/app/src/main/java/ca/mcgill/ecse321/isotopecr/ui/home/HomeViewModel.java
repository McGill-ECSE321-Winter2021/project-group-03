package ca.mcgill.ecse321.isotopecr.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mHomePageText;
    private final String IsotopeStr = "Isotope Repair Shop";

    public HomeViewModel() {
        mHomePageText = new MutableLiveData<>();
    }

    public MutableLiveData<String> getText() {
        return mHomePageText;
    }

    public void setFirstName(String firstName) {
        mHomePageText.setValue("Welcome, " + firstName + "!");
    }

    public void resetText() {
        mHomePageText.setValue(IsotopeStr);
    }
}