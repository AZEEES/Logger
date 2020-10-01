package com.example.logger;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
//import android.support.v
import android.text.InputType;
import android.util.Log;
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

import io.realm.Realm;
import io.realm.RealmResults;

public class NodeL0Adapter extends ArrayAdapter<NodeL0> {

    public NodeL0Adapter(Context context, ArrayList<NodeL0> nodes)
    {
        super(context,0, nodes);
    }

    //    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.node_l0_item, parent,false);
        }
        final NodeL0 currentNode = getItem(position);

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

        TextView nodeId = listItemView.findViewById(R.id.nodeL0_Item_id);
        nodeId.setText(currentNode.get_id());
        final String node_id = currentNode.get_id();

        TextView nodeName = listItemView.findViewById(R.id.nodeL0_Item_name);
        nodeName.setText(currentNode.get_name());

        String dtype = currentNode.get_dtype();
        final EditText nodeEditText = listItemView.findViewById(R.id.nodeL0_Item_editText);
        CheckBox nodeCheckBox = listItemView.findViewById(R.id.nodeL0_Item_checkBox);
        Spinner nodeSpinner = listItemView.findViewById(R.id.nodeL0_Item_spinner);

        if(dtype.equals("number") || dtype.equals("text")){
            nodeEditText.setVisibility(View.VISIBLE);
            if(dtype.equals("number")){
                nodeEditText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
            if(dtype.equals("text")){
                nodeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }
        if(dtype.equals("checkbox")){
            nodeCheckBox.setVisibility(View.VISIBLE);
        }
        if(dtype.equals("dropdown")){
            nodeSpinner.setVisibility(View.VISIBLE);
        }

        nodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    final Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    Structure structure = realm.where(Structure.class).equalTo("id", node_id).findFirst();
                    structure.setValue(nodeEditText.getText().toString());
                    realm.copyToRealmOrUpdate(structure);
                    realm.commitTransaction();
                    realm.close();
                }
            }
        });

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nodeId = v.findViewById(R.id.nodeL0_Item_id);
                String node_id = nodeId.getText().toString();
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

        LinearLayout nodelistParentLayout = listItemView.findViewById(R.id.nodeL0_Item_parentLayout);
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