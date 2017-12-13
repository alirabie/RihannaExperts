package experts.rihanna.appsmatic.com.rihannaexperts.Fragments.UpdateExpertsFragments.AddressFraments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import experts.rihanna.appsmatic.com.rihannaexperts.R;


public class OutdoorAdressesFrag extends Fragment {

    private RecyclerView adressList;
    private TextView next,emptyFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outdoor_adresses, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyFlag=(TextView)view.findViewById(R.id.empty_address_flag_frag);
        adressList = (RecyclerView) view.findViewById(R.id.address_list);
    }
}
