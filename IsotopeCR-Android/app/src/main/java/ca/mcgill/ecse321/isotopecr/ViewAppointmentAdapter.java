package ca.mcgill.ecse321.isotopecr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;



public class ViewAppointmentAdapter extends BaseAdapter {
    LayoutInflater aInflater;
    ListView aListView;
    List<String> licensePlate;
    List<String> serviceName;
    List<String> startTime;
    List<String> startDate;

    public ViewAppointmentAdapter (Context context, List<String> licensePlate, List<String> serviceName, List<String> startDate, List<String> startTime){
        this.licensePlate = licensePlate;
        this.serviceName = serviceName;
        this.startTime = startTime;
        this.startDate = startDate;
        aInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return licensePlate.size();
    }
    @Override
    public Object getItem (int i){
        return licensePlate.get(i)+"  "+serviceName.get(i)+"  "+startDate.get(i) +"  "+startTime.get(i);
    }
    @Override
    public long getItemId (int i){
        return i ;
    }
    @Override
    public View getView (int i, View convertView, ViewGroup parent){
        View view = aInflater.inflate(R.layout.showfutureappointment,null);
        TextView licensePlateTextView = (TextView) view.findViewById(R.id.licenseplate);
        TextView serviceNameTextView = (TextView) view.findViewById(R.id.serviceName);
        TextView appointmentDateTextView = (TextView) view.findViewById(R.id.appointmentDate);
        TextView appointmentTimeTextView = (TextView) view.findViewById(R.id.appointmentTime);

        String licensePlate_show = licensePlate.get(i);
        String serviceName_show = serviceName.get(i);
        String startTime_show = startTime.get(i);
        String startDate_show = startDate.get(i);

        licensePlateTextView.setText(licensePlate_show);
        serviceNameTextView.setText(serviceName_show);
        appointmentDateTextView.setText(startDate_show);
        appointmentTimeTextView.setText(startTime_show);
        return view;
    }
}
