package com.example.logger.ui.syncdata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.logger.LoggerApplication;
import com.example.logger.R;
import com.example.logger.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class SyncDataFragment extends Fragment {

    private SyncDataViewModel syncDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        syncDataViewModel =
                ViewModelProviders.of(this).get(SyncDataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        final TextView textView = root.findViewById(R.id.text_syncdata);
        syncDataViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                fetch_data(textView);

            }
        });
        return root;
    }

    public void fetch_data(final TextView textView) {
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
                            textView.setText("Sync Completed from server");
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
}