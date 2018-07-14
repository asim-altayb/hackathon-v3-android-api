package abc.hackathon.conflict.abcuniversty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import abc.hackathon.conflict.abcuniversty.models.student;
import abc.hackathon.conflict.abcuniversty.network.MySingleton;
import abc.hackathon.conflict.abcuniversty.utils.Constants;
import abc.hackathon.conflict.abcuniversty.utils.SharedPrefManager;

/**
 * Created by mamoun on 13/07/18.
 */

public class CreatePost extends AppCompatActivity{

    TextView StudentName;
    EditText PostTitle,PostContent;
    student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);

        PostTitle=(EditText) findViewById(R.id.ptitle);
        PostContent=(EditText) findViewById(R.id.pbody);

        student= SharedPrefManager.getInstance(this).getStudent();


        findViewById(R.id.fabb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();

            }
        });

    }

    public void postData(){

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.API+"/posts",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(CreatePost.this,""+error,Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("student_id",student.getId() );
                params.put("channel_id", "1");
                params.put("title", PostTitle.getText().toString());
                params.put("body", PostContent.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(postRequest);


    }
}
