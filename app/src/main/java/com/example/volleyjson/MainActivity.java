package com.example.volleyjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btnRequest;
    private RequestQueue queue;
    private StringRequest stringRequest;
    //private String url = "https://run.mocky.io/v3/c47742d9-2df9-4357-9ead-56db7e548124";
    private String url = "https://run.mocky.io/v3/024ea827-9c44-4a8d-a9fc-878ca9c82dfc";

    private EditText usr;
    private EditText pwd;

    private TextView testo;

    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRequest=(Button)findViewById(R.id.request);
        testo=(TextView)findViewById(R.id.textView);

        usr=(EditText)findViewById(R.id.username);
        pwd=(EditText)findViewById(R.id.password);

        btnSearch=(Button)findViewById(R.id.search);
        btnSearch.setVisibility(View.GONE);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        // 1 - Creazione coda di richieste
        queue = Volley.newRequestQueue(this);

        // 2 - Creazione richiesta
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Visualizzare risultato richiesta

                //risposta di prova
                try {
                    JSONObject json=new JSONObject(response);
                    testo.setText("Nome: " + json.getString("name") + "\nPassword: " + json.getString("password"));
                    if(usr.getText().toString().equals(json.getString("name")) && pwd.getText().toString().equals(json.getString("password"))) {
                        Toast.makeText(getApplicationContext(), "Nome e Password Verificati", Toast.LENGTH_LONG).show();
                        btnSearch.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //testo.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //errore di prova
                Log.e("ErroreVolley", error.toString());
            }
        });

        // 3 - Aggiunta richiesta
        queue.add(stringRequest);
    }
}

