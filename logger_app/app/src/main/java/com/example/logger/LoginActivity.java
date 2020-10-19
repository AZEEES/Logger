package com.example.logger;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;


public class LoginActivity extends AppCompatActivity {
    private int password_visible = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = findViewById(R.id.login_button);
        final EditText phoneEditText = findViewById(R.id.login_phone);
        final EditText passwordEditText = findViewById(R.id.login_password);
        final ImageView passwordView = findViewById(R.id.login_passwordVisible);
        final ProgressBar loginProgress = findViewById(R.id.login_progressbar);

        passwordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password_visible==0){
                    passwordView.setImageResource(R.drawable.show);
                    password_visible = 1;
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    passwordView.setImageResource(R.drawable.hide);
                    password_visible = 0;
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginButton.setEnabled(false);
                loginProgress.setVisibility(View.VISIBLE);
                login(phone, password, loginButton, loginProgress);
            }
        });
    }

    public void login(final String phone, final String password, final Button loginButton, final ProgressBar progressBar) {
        final LoggerApplication loggerApp = ((LoggerApplication) getApplicationContext());
        String server_ip = loggerApp.get_Server_IP();
        final String url = "http://" + server_ip + "/api/user/check_valid";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LOGINTAG", response);
//                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        if(response.equals("null")){
                            Toast.makeText(LoginActivity.this, "Invalid phone/password combination", Toast.LENGTH_SHORT).show();
                            loginButton.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            loginButton.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            final Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            User user = realm.where(User.class).equalTo("id", 1).findFirst();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                user.setName(jsonObject.getString("name"));
                                user.setPhone(jsonObject.getString("phone"));
                                user.setRootNode(jsonObject.getString("root_node"));
                                user.setRootNodeName(jsonObject.getString("root_node_name"));
                                user.setLoginStatus("Y");
                                realm.copyToRealmOrUpdate(user);
                                realm.commitTransaction();
                            }
                            catch (JSONException e){
                                Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            realm.close();
                            Intent homeActivityIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(homeActivityIntent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Server issue", Toast.LENGTH_SHORT)
                                .show();
                        loginButton.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
//                        homeTextView.setText("Server issue on " + url + "\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
