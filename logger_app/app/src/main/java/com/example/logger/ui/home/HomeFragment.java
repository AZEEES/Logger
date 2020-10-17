package com.example.logger.ui.home;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logger.HomeActivity;
import com.example.logger.LoggerApplication;
import com.example.logger.NodeActivity;
import com.example.logger.R;
import com.example.logger.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final ImageView fillDataButtonView = root.findViewById(R.id.home_fetch_btn);
        final ImageView syncDataButtonView = root.findViewById(R.id.home_sync_button);
        final LinearLayout homeProgressView = root.findViewById(R.id.home_progressBarView);
        final TextView homeProgressTextView = root.findViewById(R.id.home_progress_label);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
                fillDataButtonView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final LoggerApplication loggerApp = ((LoggerApplication) getActivity().getApplicationContext());
                        String init_node_id = loggerApp.get_InitNode_Id();
                        int selectedColor = R.color.colorAccent;
                        setRoundedDrawable(fillDataButtonView, getContext().getResources().getColor(selectedColor));
                        Intent nodeIntent = new Intent(getContext(), NodeActivity.class);
                        nodeIntent.putExtra("node_id", init_node_id);
                        startActivity(nodeIntent);
                    }
                });

                syncDataButtonView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        homeProgressView.setVisibility(View.VISIBLE);
                        homeProgressTextView.setText("Getting data from server");
                        fetch_data(homeProgressView, homeProgressTextView);
                    }
                });

            }
        });

        setRoundedDrawable(fillDataButtonView, getResources().getColor(R.color.uptickBackground));
        setRoundedDrawable(syncDataButtonView, getResources().getColor(R.color.downtickBackground));
        return root;
    }

    public void fetch_data(final LinearLayout homeProgressView, final TextView homeProgressTextView) {
        final LoggerApplication loggerApp = ((LoggerApplication) getActivity().getApplicationContext());
        String server_ip = loggerApp.get_Server_IP();
        final String url = "http://" + server_ip + "/api/data/get_latest_data";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        homeTextView.setText(response.toString());
                        try {
                            final Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            JSONArray jsonArray = new JSONArray(response);
                            String resp = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String id = jsonObject.getString("_id");
                                String value = jsonObject.getString("value");
                                resp += id + " : " + value + "\n";
                                Structure str_edit = realm.where(Structure.class).equalTo("_id", id).findFirst();
                                if(str_edit!=null){
                                    str_edit.setValue(value);
                                }
                            }
//                            textView.setText(resp);
                            homeProgressView.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Data received from server",Toast.LENGTH_SHORT).show();
                            realm.commitTransaction();
                            realm.close();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Server issue", Toast.LENGTH_SHORT)
                                .show();
//                        homeTextView.setText("Server issue on " + url + "\n" + error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    //Function to create rounded rectangles
    public static void setRoundedDrawable(View view, int backgroundColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(120f);
        shape.setColor(backgroundColor);
        view.setBackgroundDrawable(shape);
    }
}