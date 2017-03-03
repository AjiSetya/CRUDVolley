package com.aji.crudvolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aji.crudvolley.app.AppController;
import com.aji.crudvolley.helper.helper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahActivity extends AppCompatActivity {

    private EditText txtmerk, txttipe, txtket;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtmerk = (EditText) findViewById(R.id.txtmerk);
        txttipe = (EditText) findViewById(R.id.txttipe);
        txtket = (EditText) findViewById(R.id.txtket);

        pd = new ProgressDialog(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnsimpan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String merk = txtmerk.getText().toString().trim();
                String tipe = txttipe.getText().toString().trim();
                String keterangan = txtket.getText().toString().trim();
                if (!merk.isEmpty() && !tipe.isEmpty() && !keterangan.isEmpty()) {
                    simpanData(merk, tipe, keterangan);
                } else if (merk.isEmpty()) {
                    txtmerk.setError("merk tidak boleh kosong");
                    txtmerk.requestFocus();
                } else if (tipe.isEmpty()) {
                    txttipe.setError("tipe tidak boleh kosong");
                    txttipe.requestFocus();
                } else if (keterangan.isEmpty()) {
                    txtket.setError("keterangan tidak boleh kosong");
                    txtket.requestFocus();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void simpanData(final String merk, final String tipe, final String keterangan) {
        String url_simpan = helper.main_url + "simpan.php";

        String tag_json = "tag_json";

        pd.setCancelable(false);
        pd.setMessage("Menyimpan...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_simpan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response.toString());
                hideDialog();

                try {
                    JSONObject jObject = new JSONObject(response);
                    String pesan = jObject.getString("pesan");
                    String hasil = jObject.getString("result");
                    if (hasil.equalsIgnoreCase("true")) {
                        Toast.makeText(TambahActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(TambahActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(TambahActivity.this, "Error JSON", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(TambahActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("merk", merk);
                param.put("tipe", tipe);
                param.put("keterangan", keterangan);
                return param;
            }
        };

        AppController.getAppController().addToRequestQueue(stringRequest, tag_json);
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
