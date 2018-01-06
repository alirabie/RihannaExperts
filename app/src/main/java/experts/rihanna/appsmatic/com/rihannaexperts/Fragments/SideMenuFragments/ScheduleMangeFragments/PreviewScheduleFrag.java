package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.SideMenuFragments.ScheduleMangeFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.EventDecorator;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class PreviewScheduleFrag extends Fragment {

    MaterialCalendarView materialCalendarView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        materialCalendarView=(MaterialCalendarView)view.findViewById(R.id.calendarView);

        //Set today selection
        materialCalendarView.setDateSelected(CalendarDay.today(), true);
        materialCalendarView.setCurrentDate(Calendar.getInstance(Locale.getDefault()));


        //Set Work Day On Calender
        /*
        materialCalendarView.addDecorator(new EventDecorator(Color.RED,20,11,2017));
        materialCalendarView.addDecorator(new EventDecorator(Color.RED,23,11,2017));
        materialCalendarView.addDecorator(new EventDecorator(Color.RED, 25, 10, 2017));
        materialCalendarView.addDecorator(new EventDecorator(Color.RED,8,10,2017));
*/


        //first start send today date
        long date = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        String dateString = format.format(date);
        //Toast.makeText(getContext(),dateString,Toast.LENGTH_SHORT).show();

        ReupdateScudleOrdersFrag reupdateScudleOrdersFrag=new ReupdateScudleOrdersFrag();
        Bundle bundle=new Bundle();
        bundle.putString("date",dateString);
        reupdateScudleOrdersFrag.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_contener, reupdateScudleOrdersFrag);
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.commit();


        //On Day Selected
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDay());
                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy",Locale.ENGLISH);
                String strDate = format.format(calendar.getTime());

                //Toast.makeText(getContext(),strDate,Toast.LENGTH_SHORT).show();
                //send date to updatable fragment
                ReupdateScudleOrdersFrag reupdateScudleOrdersFrag=new ReupdateScudleOrdersFrag();
                Bundle bundle=new Bundle();
                bundle.putString("date",strDate);
                reupdateScudleOrdersFrag.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_contener,reupdateScudleOrdersFrag);
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                fragmentTransaction.commit();


            }
        });

    }
}
