package com.example.logger;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
//import android.support.v
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class NodeL0DispAdapter extends ArrayAdapter<NodeL0> {

    public NodeL0DispAdapter(Context context, ArrayList<NodeL0> nodes)
    {
        super(context,0, nodes);
    }

    //    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.node_l0_disp_item, parent,false);
        }
        NodeL0 currentNode = getItem(position);

        int selectedColor = 0;
        if (position % 7 == 0) {
            selectedColor = R.color.colorListItem1;
        }
        if (position % 7 == 1) {
            selectedColor = R.color.colorListItem2;
        }
        if (position % 7 == 2) {
            selectedColor = R.color.colorListItem3;
        }
        if (position % 7 == 3) {
            selectedColor = R.color.colorListItem4;
        }
        if (position % 7 == 4) {
            selectedColor = R.color.colorListItem5;
        }
        if (position % 7 == 5) {
            selectedColor = R.color.colorListItem6;
        }
        if (position % 7 == 6) {
            selectedColor = R.color.colorListItem7;
        }

        TextView nodeId = listItemView.findViewById(R.id.nodeL0disp_Item_id);
        nodeId.setText(currentNode.get_id());

        TextView nodeName = listItemView.findViewById(R.id.nodeL0disp_Item_name);
        nodeName.setText(currentNode.get_name());

        TextView nodeValue = listItemView.findViewById(R.id.nodeL0disp_Item_value);
        String node_value = currentNode.get_value();
        if(!(node_value.equals("na"))) {
            nodeValue.setText(node_value);
        }

        TextView nodeUnitText = listItemView.findViewById(R.id.nodeL0disp_Item_unitText);
        nodeUnitText.setText(currentNode.get_unit());

        String node_name = nodeName.getText().toString();
        if(node_name.equals("Defects")){
            LinearLayout nodeL0UnitView = listItemView.findViewById(R.id.nodeL0disp_Item_unitView);
            LinearLayout nodeL0EditView = listItemView.findViewById(R.id.nodeL0disp_Item_valueView);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) nodeL0EditView.getLayoutParams();
            params.weight = 2.0f;
            nodeL0EditView.setLayoutParams(params);
            nodeL0UnitView.setVisibility(View.GONE);
            nodeValue.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nodeId = v.findViewById(R.id.nodeL0disp_Item_id);
                String node_id = nodeId.getText().toString();
            }
        });

        LinearLayout nodelistParentLayout = listItemView.findViewById(R.id.nodeL0disp_Item_parentLayout);
        setRoundedDrawable(nodelistParentLayout,getContext().getResources().getColor(selectedColor));


        return listItemView;
    }

    //Function to create rounded rectangles
    public static void setRoundedDrawable(View view, int backgroundColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(20f);
        shape.setColor(backgroundColor);
        view.setBackgroundDrawable(shape);
    }
}
