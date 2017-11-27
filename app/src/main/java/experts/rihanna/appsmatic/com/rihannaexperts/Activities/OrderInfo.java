package experts.rihanna.appsmatic.com.rihannaexperts.Activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import experts.rihanna.appsmatic.com.rihannaexperts.R;

public class OrderInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_info);
    }
}
