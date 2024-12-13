package com.example.merchantapp.Utilites;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.merchantapp.Model.LoginModel;


public class UserSessionManagement {


    private static UserSessionManagement userSessionManagement;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginModel loginModel;


    private static final String TOKEN = "token";
    private static final  String ID = "id";
    private static final String NAME ="name";
    private static  final String PHNO = "phonenumber";
    private  static  final  String LATITUDE  ="latitude";
    private  static  final  String LONGITUDE ="longitude";
    private static final String USERIDS ="userid";

    public static final String CATEGORY_ID ="categoryid";




    public void setUserids(String userids){
        editor.putString(USERIDS, userids);
        editor.apply();
    }
    public String getUserids(){

        return sharedPreferences.getString(USERIDS,null);
    }

    public void setCategoryId(String categoryId){
        editor.putString(CATEGORY_ID, categoryId);
        editor.apply();
    }
    public String getCategoryId(){

        return sharedPreferences.getString(CATEGORY_ID,null);
    }

    public void setPhno(String Phno){
        editor.putString(PHNO,Phno);
        editor.apply();
    }
    public String getPhno(){

        return sharedPreferences.getString(PHNO,null);
    }
    public void setLatitude(String latitude){
        editor.putString(LATITUDE,latitude);
        editor.apply();

    }
    public String getLatitude(){

        return sharedPreferences.getString(LATITUDE,null);
    }
    public void setLongitude(String longitude){
        editor.putString(LONGITUDE,longitude);
        editor.apply();
    }
    public String getLongitude(){

        return sharedPreferences.getString(LONGITUDE,null);
    }

    public void setName(String name){
        editor.putString(NAME,name);
        editor.apply();
    }
    public String getName(){
        return sharedPreferences.getString(NAME,null);
    }

   public  void  setUserid(String userid){
       editor.putString(ID,userid);
       editor.apply();
   }
   public String getUserid(){


       return sharedPreferences.getString(ID,null);
   }
    public void setTokenId(String tokenId) {
        editor.putString(TOKEN, tokenId);
        editor.apply();
    }

    public String getTokenId() {

        return sharedPreferences.getString(TOKEN, null);
    }

    public void deleteToken() {
        editor.remove(TOKEN);
        editor.apply();
    }

    public static void saveeBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor prefEditor = getSharedPreferences(context, 0).edit();
        prefEditor.putBoolean(key, value);
        prefEditor.apply();
    }


    public static boolean gettBoolean(Context context, String key) {
        return getSharedPreferences(context, 0).getBoolean(key, false);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor prefEditor = getSharedPreferences(context, 0).edit();
        prefEditor.putBoolean(key, value);
        prefEditor.apply();
    }

    private static SharedPreferences getSharedPreferences(Context context, int i) {

       return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context, 0).getBoolean(key, false);
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }


    private UserSessionManagement(Context mContext) {
       // sharedPreferences =mContext.getSharedPreferences(Context.USB_SERVICE,Context.MODE_PRIVATE);
        sharedPreferences = mContext.getSharedPreferences(Constants.USER_SESSION_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static synchronized UserSessionManagement getInstance(Context context) {
        if (userSessionManagement == null) {
            userSessionManagement = new UserSessionManagement(context);
        }
        return userSessionManagement;
    }

}
