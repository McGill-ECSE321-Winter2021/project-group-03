package ca.mcgill.ecse321.isotopecr.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mHomePageText;  // home page welcome message
    private final String IsotopeStr = "Isotope Repair Shop";

    public HomeViewModel() {
        mHomePageText = new MutableLiveData<>();
    }

    public MutableLiveData<String> getText() {
        return mHomePageText;
    }

    /**
     * Set a custom welcome message in home page.
     * The message will be "Welcome, [firstName] !"
     *
     * @param firstName first name of the logged in customer
     * @author Jack Wei
     */
    public void setFirstName(String firstName) {
        mHomePageText.setValue("Welcome, " + firstName + "!");
    }

    /**
     * Reset the custom welcome page to default.
     * The message will be "Isotope Repair Shop".
     *
     * @author Jack Wei
     */
    public void resetText() {
        mHomePageText.setValue(IsotopeStr);
    }
}