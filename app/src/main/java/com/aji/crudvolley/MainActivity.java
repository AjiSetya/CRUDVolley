package com.aji.crudvolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.aji.crudvolley.adapter.AdapterList;
import com.aji.crudvolley.app.AppController;
import com.aji.crudvolley.helper.helper;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lvhape;

    private ArrayList<HashMap<String, String>> list_data;

    private String tag_json = "tag_json";

    private String url = helper.main_url + "getdata.php";

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btntambah);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TambahActivity.class));
            }
        });

        pd = new ProgressDialog(this);

        lvhape = (RecyclerView)findViewById(R.id.lvhape);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvhape.setLayoutManager(linearLayoutManager);

        list_data = new ArrayList<HashMap<String, String>>();

        showData();
    }

    private void showData() {

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        showDialog();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE ", response.toString());
                hideDialog();
                try {
                    JSONArray jray = response.getJSONArray("handphone");
                    for (int a = 0; a < jray.length(); a++){
                        JSONObject json = jray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", json.getString("idhp"));
                        map.put("merk", json.getString("merk"));
                        map.put("tipe", json.getString("tipe"));
                        map.put("gambar", json.getString("gambar"));
                        map.put("keterangan", json.getString("keterangan"));
                        list_data.add(map);
                        AdapterList adapter = new AdapterList(MainActivity.this, list_data);
                        lvhape.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                hideDialog();
            }
        });

        AppController.getAppController().addToRequestQueue(jsonRequest, tag_json);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void showDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }
}
