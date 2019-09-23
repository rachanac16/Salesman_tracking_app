package com.eat.erp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class session {
    private SharedPreferences prefs;

    public session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setSessionid(String sessionid) {
        prefs.edit().putString("sessionid", sessionid).commit();
    }

    public String getSessionid() {
        String sessionid = prefs.getString("sessionid","");
        return sessionid;
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getusername() {
        String username = prefs.getString("username","");
        return username;
    }

    public void setlogin_name(String login_name) {
        prefs.edit().putString("login_name", login_name).commit();
    }

    public String getlogin_name() {
        String login_name = prefs.getString("login_name","");
        return login_name;
    }

    public void setrole_id(String role_id) {
        prefs.edit().putString("role_id", role_id).commit();
    }

    public String getrole_id() {
        String role_id = prefs.getString("role_id","");
        return role_id;
    }

    public void setsales_rep_id(String sales_rep_id) {
        prefs.edit().putString("sales_rep_id", sales_rep_id).commit();
    }

    public String getsales_rep_id() {
        String sales_rep_id = prefs.getString("sales_rep_id","");
        return sales_rep_id;
    }

    public void settype(String type) {
        prefs.edit().putString("type", type).commit();
    }

    public String gettype() {
        String type = prefs.getString("type","");
        return type;
    }

    public void setemp_code(String emp_code) {
        prefs.edit().putString("emp_code", emp_code).commit();
    }

    public String getemp_code() {
        String emp_code = prefs.getString("emp_code","");
        return emp_code;
    }

    public void setfirst_name(String first_name) {
        prefs.edit().putString("first_name", first_name).commit();
    }

    public String getfirst_name() {
        String first_name = prefs.getString("first_name","");
        return first_name;
    }

    public void setlast_name(String last_name) {
        prefs.edit().putString("last_name", last_name).commit();
    }

    public String getlast_name() {
        String last_name = prefs.getString("last_name","");
        return last_name;
    }

    public void setcheck_in_time(String check_in_time) {
        prefs.edit().putString("check_in_time", check_in_time).commit();
    }

    public String getcheck_in_time() {
        String check_in_time = prefs.getString("check_in_time","");
        return check_in_time;
    }

    public void setcheck_out_time(String check_out_time) {
        prefs.edit().putString("check_out_time", check_out_time).commit();
    }

    public String getcheck_out_time() {
        String check_out_time = prefs.getString("check_out_time","");
        return check_out_time;
    }

    public void setdistributor_id(String distributor_id) {
        prefs.edit().putString("distributor_id", distributor_id).commit();
    }

    public void setdistributor_name(String distributor_name) {
        prefs.edit().putString("distributor_name", distributor_name).commit();
    }

    public String getdistributor_id() {
        String distributor_id = prefs.getString("distributor_id","");
        return distributor_id;
    }

    public String getdistributor_name() {
        String distributor_name = prefs.getString("distributor_name","");
        return distributor_name;
    }

    public void setbeat_id(String beat_id) {
        prefs.edit().putString("beat_id", beat_id).commit();
    }

    public String getbeat_id() {
        String beat_id = prefs.getString("beat_id","");
        return beat_id;
    }

    public void setbeatname(String beat_name) {
        prefs.edit().putString("beat_name", beat_name).commit();
    }

    public String getbeat_name() {
        String beat_name = prefs.getString("beat_name","");
        return beat_name;
    }

    public void setmon_frag(String mon_frag) {
        prefs.edit().putString("mon_frag", mon_frag).commit();
    }

    public String getmon_frag() {
        String mon_frag = prefs.getString("mon_frag","");
        return mon_frag;
    }

    public void settues_frag(String tues_frag) {
        prefs.edit().putString("tues_frag", tues_frag).commit();
    }

    public String gettues_frag() {
        String tues_frag = prefs.getString("tues_frag","");
        return tues_frag;
    }

    public void setwed_frag(String wed_frag) {
        prefs.edit().putString("wed_frag", wed_frag).commit();
    }

    public String getwed_frag() {
        String wed_frag = prefs.getString("wed_frag","");
        return wed_frag;
    }

    public void setthu_frag(String thu_frag) {
        prefs.edit().putString("thu_frag", thu_frag).commit();
    }

    public String getthu_frag() {
        String thu_frag = prefs.getString("thu_frag","");
        return thu_frag;
    }

    public void setfri_frag(String fri_frag) {
        prefs.edit().putString("fri_frag", fri_frag).commit();
    }

    public String getfri_frag() {
        String fri_frag = prefs.getString("fri_frag","");
        return fri_frag;
    }

    public void setsat_frag(String sat_frag) {
        prefs.edit().putString("sat_frag", sat_frag).commit();
    }

    public String getsat_frag() {
        String sat_frag = prefs.getString("sat_frag","");
        return sat_frag;
    }

    public void setsun_frag(String sun_frag) {
        prefs.edit().putString("sun_frag", sun_frag).commit();
    }

    public String getsun_frag() {
        String sun_frag = prefs.getString("sun_frag","");
        return sun_frag;
    }


}
