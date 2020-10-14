package com.example.logger.ui.gallery;

import android.content.Intent;
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
import com.example.logger.NodeActivity;
import com.example.logger.R;
import com.example.logger.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    final String server_ip = "3.134.88.27:3000";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                fetch_data(server_ip, textView);
            }
        });
        return root;
    }

    public void fetch_data(String server_ip, final TextView textView) {
        final String url = "http://" + server_ip + "/api/structure";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        homeTextView.setText(response.toString());
                        try {
                            final Realm realm = Realm.getDefaultInstance();
//                            realm.close();
//                            Realm.deleteRealm(realm.getConfiguration());

                            realm.beginTransaction();
//                            realm.deleteAll();
                            final RealmResults<Structure> structures = realm.where(Structure.class).findAll();
                            structures.deleteAllFromRealm();
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                realm.createObjectFromJson(Structure.class, jsonObject.toString());
                                textView.setText("Sync completed from server");
//                                Intent nodeIntent = new Intent(getContext(), NodeActivity.class);
//                                nodeIntent.putExtra("node_id", "null");
//                                startActivity(nodeIntent);
                            }
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