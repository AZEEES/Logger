package com.example.logger;

public class History {

    private String mid;
    private String mvalue;
    private String mdatetime;

    public History(String id, String value, String datetime){
        mid = id;
        mvalue = value;
        mdatetime = datetime;
    }

    public String getId(){ return mid; }
    public String getValue(){ return mvalue; }
    public String getDatetime(){ return mdatetime; }

}
