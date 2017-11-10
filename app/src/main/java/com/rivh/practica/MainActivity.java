package com.rivh.practica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mTextViewPostResponse;
    private EditText mEditTextNombre;
    private EditText mEditTextApellido;
    private Button mButtonPost;
    private Button mButtonGet;


    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.main_activity_text);
        mTextViewPostResponse = (TextView) findViewById(R.id.main_activity_text_post_response);
        mEditTextNombre = (EditText) findViewById(R.id.main_activity_txtNombre);
        mEditTextApellido = (EditText) findViewById(R.id.main_activity_txtApellido);
        mButtonPost = (Button) findViewById(R.id.main_activity_button_post);
        mButtonGet = (Button) findViewById(R.id.main_activity_button_get);


        mButtonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGet();
            }
        });

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(true);
        }
    }

    void sendGet() {
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url = "http://172.18.116.71:8082/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);


    }

    void sendPost() {

        String url = "http://172.18.116.71:8082/estudiantes";

        StringRequest StringReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTextViewPostResponse.setText("Response is: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextViewPostResponse.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", mEditTextNombre.getText().toString());
                params.put("apellido", mEditTextApellido.getText().toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        mRequestQueue.add(StringReq);

    }


}
