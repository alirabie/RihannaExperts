package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.ExperincesFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;

import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class PracticalExFrag extends Fragment {

    private TextView next;
    private BetterSpinner yearsCount;
    private List<String> years;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_experinces, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        next=(TextView)view.findViewById(R.id.next_btn);


        years=new ArrayList<>();
        for(int i=1;i<=10;i++){
            years.add(i+"");
        }
        yearsCount=(BetterSpinner)view.findViewById(R.id.exper_years_count);
        yearsCount.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,years));


        //Save
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });






    }

}
