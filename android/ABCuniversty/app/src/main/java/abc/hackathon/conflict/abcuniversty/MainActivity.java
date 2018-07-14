package abc.hackathon.conflict.abcuniversty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abc.hackathon.conflict.abcuniversty.adapters.PostsAdapter;
import abc.hackathon.conflict.abcuniversty.models.Post;
import abc.hackathon.conflict.abcuniversty.models.student;
import abc.hackathon.conflict.abcuniversty.network.MySingleton;
import abc.hackathon.conflict.abcuniversty.utils.Constants;
import abc.hackathon.conflict.abcuniversty.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PostsAdapter.Listener {

    RecyclerView recyclerView;
    PostsAdapter adapter;
    List<Post> postList;
    View v;
    student student;
    int moredata=1;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.PostRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        postList =new ArrayList<>();

        student=SharedPrefManager.getInstance(this).getStudent();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createpost=new Intent(getBaseContext(),CreatePost.class);
                startActivity(createpost);



                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_feeds) {
            Intent intent=new Intent(this,MainActivity.class);
            this.startActivity(intent);
            this.finish();
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_noti) {

        } else if (id == R.id.nav_logout) {
            Toast.makeText(this,"dsfs",Toast.LENGTH_LONG).show();
            SharedPrefManager.getInstance(getApplicationContext()).clear();
            Intent intent=new Intent(this,LoginActivity.class);
            this.startActivity(intent);
            this.finish();

        } else if (id == R.id.nav_about) {

        }
        else if (id == R.id.nav_services) {
            Intent intent = new Intent(MainActivity.this,ServiceActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getData(){

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, Constants.API+"/posts", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        swipeRefreshLayout.setRefreshing(false);
                        postList.clear();
                        Post post=new Post();
                        // the response is already constructed as a JSONObject!
                        try {
                           if (response.getInt("status")==1){
                               JSONArray jsonArray=response.getJSONArray("data");
                               parseData(jsonArray);


                           }
                        } catch (JSONException e) {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(MainActivity.this,"e1 "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this,"e1 "+error,Toast.LENGTH_LONG).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonRequest);


    }

    public void parseData(JSONArray jsonArray){
        for (int i=0;i<jsonArray.length();i++){
            Post post=new Post();
            try {
                post.setStudentName(""+jsonArray.getJSONObject(i).getInt("id"));
                post.setPostTitle(jsonArray.getJSONObject(i).getString("title"));
                post.setPostContent(jsonArray.getJSONObject(i).getString("body"));
                post.setPostingDate(jsonArray.getJSONObject(i).getString("created_at"));
                postList.add(post);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"e3 "+e,Toast.LENGTH_LONG).show();

            }

        }

        adapter=new PostsAdapter(this, postList);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCustomClick(int pos) {
        Post post = postList.get(pos);

        Intent intent = new Intent(MainActivity.this,PostDetails.class);
        intent.putExtra("id",post.getId());
        startActivity(intent);

    }
}
