package com.example.logger;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class NodeL1_Activity extends AppCompatActivity {
    private String parent_nodeId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_l1);

        if(getIntent().hasExtra("node_id")){
            parent_nodeId = getIntent().getExtras().getString("node_id");
        }
        else{
            parent_nodeId = "na";
        }

        TextView nodeTextView = findViewById(R.id.nodeL1_text);
        nodeTextView.setText("parent_node : " + parent_nodeId);
        get_nodeL0(parent_nodeId);
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
                NodeL0 n1 = new NodeL0(id, name, dtype, slider_entries, lim_low, lim_high, disable_entry, hint_text, default_value);
                nodeList.add(n1);
            }
            NodeL0Adapter nodeAdapter = new NodeL0Adapter(NodeL1_Activity.this, nodeList);
            ListView nodelistView = (ListView) findViewById(R.id.nodeL1_L0_list);
            nodelistView.setAdapter(nodeAdapter);
        }
        realm.close();
    }

}
