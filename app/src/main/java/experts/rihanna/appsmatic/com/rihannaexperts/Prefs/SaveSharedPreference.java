package experts.rihanna.appsmatic.com.rihannaexperts.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Login.LoginResponse;

/**
 * Created by Eng Ali on 11/6/2017.
 */
public class SaveSharedPreference {
    static final String LANG_ID="langId";
    static final String CUSTOMER_IDD="customerid";
    static final String CUSTOMER_INFO="customerInfo";
    static final String PREF_USER_NAME = "username";
    static final String PREF_USER_PASS = "password";
    static final String EXPERT_ID="expertiid";

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









    public static void setExpertId(Context context,String customerid,String expid){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(CUSTOMER_IDD, customerid);
        editor.putString(EXPERT_ID, expid);
        editor.commit();
    }

    public static String getExpertId(Context context){
        return getSharedPreferences(context).getString(EXPERT_ID, "");
    }


    public static String getCustId(Context context){
        return getSharedPreferences(context).getString(CUSTOMER_IDD, "");
    }







    public static void setCustomerInfo(Context context,LoginResponse regResponse){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(regResponse);
        editor.putString(CUSTOMER_INFO, json);
        editor.commit();
    }


    public static LoginResponse getCustomerInfo(Context context){

        String json= getSharedPreferences(context).getString(CUSTOMER_INFO, "");
        LoginResponse regResponse=new LoginResponse();
        if(!json.isEmpty()) {
            Type type = new TypeToken<LoginResponse>() {}.getType();
            Gson gson = new Gson();
            regResponse= gson.fromJson(json, type);
        }
        return regResponse;
    }


    public static void setUserName(Context ctx, String userName, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_USER_PASS, password);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getUserPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PASS, "");
    }





}
