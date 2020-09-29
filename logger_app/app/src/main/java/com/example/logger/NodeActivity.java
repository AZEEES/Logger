package com.example.logger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private String parent_nodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("node_id")){
            parent_nodeId = getIntent().getExtras().getString("node_id");
        }
        else{
            parent_nodeId = "na";
        }
        String server_ip = "3.134.88.27:3000";

        TextView nodeTextView = findViewById(R.id.node_text);
        nodeTextView.setText("parent_node : " + parent_nodeId);

        fetch_node(server_ip, parent_nodeId);

//        ArrayList<Node> nodeList = new ArrayList<Node>();
//        Node n1 = new Node("M0", "Node 0");
//        nodeList.add(n1);
//
//        NodeAdapter nodeAdapter = new NodeAdapter(NodeActivity.this, nodeList);
//        GridView nodegridView = (GridView) findViewById(R.id.node_grid);
//        nodegridView.setAdapter(nodeAdapter);
//        setGridViewHeightBasedOnChildren(nodegridView, 3);

    }

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if( items > columns ){
            x = items/columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

    public void fetch_node(String server_ip, final String parent_node_id) {
        final String url = "http://" + server_ip + "/api/structure/getchilds?parent=" + parent_node_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        TextView nodeTextView = findViewById(R.id.node_text);
//                        nodeTextView.setText(response);
                        try {
//                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<Node> nodeList = new ArrayList<Node>();
                            for(int i=0; i<jsonArray.length();i++){
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                Node n1 = new Node(jsonObject.getString("id"), jsonObject.getString("name"));
                                nodeList.add(n1);
                            }
                            NodeAdapter nodeAdapter = new NodeAdapter(NodeActivity.this, nodeList);
                            GridView nodegridView = (GridView) findViewById(R.id.node_grid);
                            nodegridView.setAdapter(nodeAdapter);
                            setGridViewHeightBasedOnChildren(nodegridView, 3);

                        } catch (JSONException e) {
                            Toast.makeText(NodeActivity.this, "JSON Error Occured", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NodeActivity.this, "Server issue", Toast.LENGTH_SHORT)
                                .show();
//                        homeTextView.setText("Server issue on " + url + "\n" + error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
