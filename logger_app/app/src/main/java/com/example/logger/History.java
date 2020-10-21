package com.example.logger;

public class History {

    private String mid;
    private String mvalue;
    private String mdatetime;
    private String mlogger;

    public History(String id, String value, String datetime, String logger){
        mid = id;
        mvalue = value;
        mdatetime = datetime;
        mlogger = logger;
    }

    public String getId(){ return mid; }
    public String getValue(){ return mvalue; }
    public String getDatetime(){ return mdatetime; }
    public String getLoggerId(){ return mlogger; }

}
