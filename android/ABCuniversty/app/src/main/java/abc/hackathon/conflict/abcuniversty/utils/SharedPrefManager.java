package abc.hackathon.conflict.abcuniversty.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import abc.hackathon.conflict.abcuniversty.LoginActivity;
import abc.hackathon.conflict.abcuniversty.models.student;

/**
 * Created by mamoun on 14/07/18.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "STUDENT_DATA";
    private static final String STUDENT_ID = "STUDENT_ID";
    private static final String STUDENT_NAME = "STUDEMT_NAME";
    private static final String STUDENT_DEPARTMENT = "STUDENT_DEPARTMENT";
    private static final String STUDENT_LEVEL = "STUDENT_LEVEL";
    private static final String TOKEN = "TOKEN";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the student login
    //this method will store the student data in shared preferences
    public void userLogin(student student) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STUDENT_ID, student.getId());
        editor.putString(STUDENT_NAME, student.getName());
        editor.putString(STUDENT_DEPARTMENT, student.getDepatrment());
        editor.putString(STUDENT_LEVEL, student.getLevel());
        editor.putString(TOKEN, student.getToken());
        editor.apply();
    }


    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(STUDENT_NAME, null) != null;
    }

    //this method will give the logged in user
    public student getStudent() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        student student=new student();

        student.setDepatrment(sharedPreferences.getString(STUDENT_DEPARTMENT, null));
        student.setId(sharedPreferences.getString(STUDENT_ID, null));
        student.setLevel(sharedPreferences.getString(STUDENT_LEVEL, null));
        student.setName(sharedPreferences.getString(STUDENT_NAME, null));
        student.setToken(sharedPreferences.getString(TOKEN, null));

        return student;


    }




    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();


    }

}
