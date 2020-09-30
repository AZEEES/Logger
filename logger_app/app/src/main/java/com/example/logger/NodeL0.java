package com.example.logger;

public class NodeL0 {
    private String mid;
    private String mname;
    private String mdtype;
    private String mslider_entries;
    private String mlow_lim;
    private String mhigh_lim;
    private String mdisable_entry;
    private String mhint_text;
    private String mdefault_value;

    public NodeL0(String id, String name, String dtype, String slider_entries, String low_lim, String high_lim, String disable_entry, String hint_text, String default_value){
        mid = id;
        mname = name;
        mdtype = dtype;
        mslider_entries = slider_entries;
        mlow_lim = low_lim;
        mhigh_lim = high_lim;
        mdisable_entry = disable_entry;
        mhint_text = hint_text;
        mdefault_value = default_value;
    }

    public String get_id(){ return mid; }
    public String get_name() { return mname; }
    public String get_dtype() { return mdtype; }
    public String get_slider_entries() { return mslider_entries; }
    public String get_low_lim() { return mlow_lim; }
    public String get_high_lim(){ return mhigh_lim; }
    public String get_disable_entry() { return mdisable_entry; }
    public String get_hint_text() { return mhint_text; }
    public String get_default_value() { return mdefault_value; }

}
