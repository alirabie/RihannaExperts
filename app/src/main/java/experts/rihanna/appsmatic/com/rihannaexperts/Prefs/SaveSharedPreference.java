package experts.rihanna.appsmatic.com.rihannaexperts.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Eng Ali on 11/6/2017.
 */
public class SaveSharedPreference {
    static final String LANG_ID="langId";
    static final String CUSTOMER_ID="customerid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }



    public static void setLangId(Context context,String lang){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LANG_ID,lang);
        editor.commit();
    }

    public static String getLangId(Context context){
        return getSharedPreferences(context).getString(LANG_ID, "");
    }






    public static void setCustomerId(Context context,String id){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(CUSTOMER_ID, id);
        editor.commit();
    }

    public static String getCustomerId(Context context){
        return getSharedPreferences(context).getString(CUSTOMER_ID, "");
    }










}
