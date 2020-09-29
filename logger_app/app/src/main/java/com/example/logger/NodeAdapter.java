package com.example.logger;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
//import android.support.v
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NodeAdapter extends ArrayAdapter<Node> {

    public NodeAdapter(Context context, ArrayList<Node> nodes)
    {
//        super(context, nodes);
        super(context,0, nodes);
    }


//    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.node_list_item,parent,false);
        }
        Node currentNode = getItem(position);

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

        TextView nodeId = listItemView.findViewById(R.id.nodeItem_id);
        nodeId.setText(currentNode.getNodeId());

        TextView nodeName = listItemView.findViewById(R.id.nodeItem_name);
        nodeName.setText(currentNode.getNodeName());

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nodeId = v.findViewById(R.id.nodeItem_id);
                String node_id = nodeId.getText().toString();
                Intent nodeActivityIntent = new Intent(getContext(),NodeActivity.class);
                nodeActivityIntent.putExtra("node_id",node_id);
                getContext().startActivity(nodeActivityIntent);
            }
        });

//        listItemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                TextView roomId = (TextView) v.findViewById(R.id.roomItem_roomid);
//                String room_id = roomId.getText().toString();
//                Intent roomconfigactivityIntent = new Intent(getContext(),RoomConfigActivity.class);
//                roomconfigactivityIntent.putExtra("room_id",room_id);
//                roomconfigactivityIntent.putExtra("profile_id", profile_id);
//                getContext().startActivity(roomconfigactivityIntent);
//                return true;
//            }
//        });

        LinearLayout nodelistParentLayout = listItemView.findViewById(R.id.nodeItem_parentLayout);
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
