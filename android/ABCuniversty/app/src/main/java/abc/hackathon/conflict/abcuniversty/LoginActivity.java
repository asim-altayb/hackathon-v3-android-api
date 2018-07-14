package abc.hackathon.conflict.abcuniversty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import abc.hackathon.conflict.abcuniversty.network.MySingleton;
import abc.hackathon.conflict.abcuniversty.utils.Constants;
import abc.hackathon.conflict.abcuniversty.utils.SharedPrefManager;
import abc.hackathon.conflict.abcuniversty.models.student;

public class LoginActivity extends AppCompatActivity {

    EditText StudentID,Spassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        StudentID=(EditText) findViewById(R.id.id);
        Spassword=(EditText) findViewById(R.id.pass);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });


    }

    private void userLogin(){
        final String studentid=StudentID.getText().toString();
        final String pass=Spassword.getText().toString();

        if (TextUtils.isEmpty(studentid)) {
            StudentID.setError("Please enter your StudentID");
            StudentID.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Spassword.setError("Please enter your Password");
            Spassword.requestFocus();
            return;
        }

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.API +"/auth/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.getInt("status")==0){
                                Toast.makeText(LoginActivity.this,jsonResponse.getString("error"),Toast.LENGTH_LONG).show();

                            }else {
                                ParseData(new JSONObject(jsonResponse.getString("data")));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getBaseContext(),"e1 "+e,Toast.LENGTH_LONG).show();


                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getBaseContext(),"e2 "+error,Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("studentId", studentid);
                params.put("password", pass);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(this).addToRequestQueue(postRequest);


    }

    public void ParseData(JSONObject jsonObject){
        student student=new student();
        try {
            student.setId(jsonObject.getString("id"));
            student.setToken(jsonObject.getString("token"));
            student.setName(jsonObject.getString("fullName"));
            //student.setName(jsonObject.getString("fullName"));
//            student.setLevel(jsonObject.getString("level"));
//            student.setDepatrment(jsonObject.getString("deparment"));

            SharedPrefManager.getInstance(getApplicationContext()).userLogin(student);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        //starting the profile activity
        startActivity(new Intent(this, MainActivity.class));
        finish();


    }
}
