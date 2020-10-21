package com.example.logger;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;

public class NodeL2_Activity extends AppCompatActivity {
    private String parent_nodeId;
    private String parent_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_l2);

        if(getIntent().hasExtra("node_id")){
            parent_nodeId = getIntent().getExtras().getString("node_id");
        }
        else{
            parent_nodeId = "na";
        }

        if(getIntent().hasExtra("node_name")){
            parent_name = getIntent().getExtras().getString("node_name");
        }
        else {
            parent_name = "";
        }

        TextView nodeL2TextView = findViewById(R.id.nodel2_title);
        nodeL2TextView.setText(parent_name);

        TextView nodeTextView = findViewById(R.id.nodeL2_text);
        nodeTextView.setText("parent_node : " + parent_nodeId);
        get_nodeL1(parent_nodeId);
        get_nodeL0(parent_nodeId);

        final Button nodeL2submit_btnView = findViewById(R.id.nodeL2_submit);
        final ProgressBar nodeL2progressBar = findViewById(R.id.nodeL2_progressBar);
        setRoundedDrawable(nodeL2submit_btnView, getResources().getColor(R.color.buttonColor));
        nodeL2submit_btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nodeL2submit_btnView.setEnabled(false);
                nodeL2progressBar.setVisibility(View.VISIBLE);
                nodeL2submit_btnView.setFocusable(true);
                nodeL2submit_btnView.setFocusableInTouchMode(true);///add this line
                nodeL2submit_btnView.requestFocus();
                Log.v("NODEL2TAG", "Submit " + parent_nodeId);
                final Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                ArrayList<StructureData> structureData = new ArrayList<>();
                final RealmResults<Structure> structures = realm.where(Structure.class).equalTo("parent", parent_nodeId).equalTo("level_leaf","L0").findAll();
                final RealmResults<Structure> structures1 = realm.where(Structure.class).equalTo("parent", parent_nodeId).equalTo("level_leaf","L1").findAll();
                for (int j=0;j<structures1.size();j++){
                    String node_id = structures1.get(j).getId();
                    final RealmResults<Structure> structures2 = realm.where(Structure.class).equalTo("parent", node_id).equalTo("level_leaf","L0").findAll();
                    for(int l=0; l<structures2.size();l++){
                        Structure currentStructure = structures2.get(l);
                        String id = currentStructure.getId();
                        String name = currentStructure.getName();
                        String value = currentStructure.getValue();
                        structureData.add(new StructureData(id, name, value));
                    }
                }
                for(int i=0; i<structures.size();i++){
                    Structure currentStructure = structures.get(i);
                    String id = currentStructure.getId();
                    String name = currentStructure.getName();
                    String value = currentStructure.getValue();
                    structureData.add(new StructureData(id, name, value));
                }
                User user = realm.where(User.class).equalTo("id", 1).findFirst();
                String logger_id = user.getPhone();
                realm.close();
                for(int k=0;k<structureData.size();k++){
                    Log.v("NODEL2TAG", structureData.get(k).getName());
                }

                JSONArray array=new JSONArray();
                for(int k=0;k<structureData.size();k++){
                    JSONObject obj=new JSONObject();
                    try {
                        StructureData structureData1 = structureData.get(k);
                        obj.put("id",structureData1.getId());
                        obj.put("value",structureData1.getValue());
                        obj.put("logger_id", logger_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(obj);
                }

                Log.v("NODEL2TAG",array.toString());
                update_data(array, nodeL2submit_btnView, nodeL2progressBar);

            }
        });

    }

    //Function to create rounded rectangles
    public static void setRoundedDrawable(View view, int backgroundColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(20f);
        shape.setColor(backgroundColor);
        view.setBackgroundDrawable(shape);
    }

    public void get_nodeL0(final String parent_node_id){
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Structure> structures = realm.where(Structure.class).equalTo("parent", parent_node_id).equalTo("level_leaf","L0").findAll();
        if(structures.size()>0){
            ArrayList<NodeL0> nodeList = new ArrayList<NodeL0>();
            for(int i=0; i<structures.size(); i++){
                Structure currentStructure = structures.get(i);
                String id = currentStructure.getId();
                String name = currentStructure.getName();
                String dtype = currentStructure.getDtype();
                String slider_entries = currentStructure.getSliderEntries();
                String lim_low = currentStructure.getLowLim();
                String lim_high = currentStructure.getHighLim();
                String disable_entry = currentStructure.getDisable_entry();
                String hint_text = currentStructure.getHintText();
                String default_value = currentStructure.getDefault_value();
                String value = currentStructure.getValue();
                String unit = currentStructure.getUnit();
                NodeL0 n1 = new NodeL0(id, name, dtype, slider_entries, lim_low, lim_high, disable_entry, hint_text, default_value, value, unit);
                nodeList.add(n1);
//                Toast.makeText(NodeL2_Activity.this, id + "Created", Toast.LENGTH_LONG).show();
                Log.v("NODETAG", id);
            }
            realm.close();
            NodeL0Adapter nodeAdapter = new NodeL0Adapter(NodeL2_Activity.this, nodeList);
            ListView nodelistView = (ListView) findViewById(R.id.nodeL2_L0_list);
            nodelistView.setAdapter(nodeAdapter);
            Utilities.setListViewHeightBasedOnItems(nodelistView);
        }
        realm.close();
    }

    public void get_nodeL1(final String parent_node_id){
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Structure> structures = realm.where(Structure.class).equalTo("parent", parent_node_id).equalTo("level_leaf","L1").findAll();
        if(structures.size()>0){
            ArrayList<NodeL1> nodeList = new ArrayList<NodeL1>();
            for(int i=0; i<structures.size(); i++){
                Structure currentStructure = structures.get(i);
                String id = currentStructure.getId();
                String name = currentStructure.getName();
                String level_leaf = currentStructure.getLevelLeaf();
                String parent_id = currentStructure.getParent();
                NodeL1 n1 = new NodeL1(id, name, level_leaf, parent_id);
                nodeList.add(n1);
            }
            realm.close();
            NodeL1Adapter nodeAdapter = new NodeL1Adapter(NodeL2_Activity.this, nodeList);
            ListView nodelistView = (ListView) findViewById(R.id.nodeL2_L1_list);
            nodelistView.setAdapter(nodeAdapter);
            Utilities.setListViewHeightBasedOnItems(nodelistView);
        }
        realm.close();
    }

    public void update_data(final JSONArray array, final Button nodeL2SubmitButtonView, final ProgressBar nodeL2ProgressBar) {
        final LoggerApplication loggerApp = ((LoggerApplication) getApplicationContext());
        String server_ip = loggerApp.get_Server_IP();
        final String url = "http://" + server_ip + "/api/data/update";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("NODEL2TAG", response);
                        if(response.equals("\"Success\"")) {
                            Toast.makeText(NodeL2_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                            nodeL2SubmitButtonView.setEnabled(true);
                            nodeL2ProgressBar.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(NodeL2_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                            nodeL2SubmitButtonView.setEnabled(true);
                            nodeL2ProgressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NodeL2_Activity.this, "Server issue", Toast.LENGTH_SHORT)
                                .show();
                        nodeL2SubmitButtonView.setEnabled(true);
                        nodeL2ProgressBar.setVisibility(View.GONE);
//                        homeTextView.setText("Server issue on " + url + "\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("array", array.toString());
                return params;
            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
