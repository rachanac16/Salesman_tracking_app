package com.eat.erp;

public class Beat {
    private String sales_rep;
    private String old_plan;
    private String new_plan;
    private String r_id;

    public Beat(){

    }

    public Beat(String sales_rep, String old_plan, String new_plan, String r_id) {
        this.sales_rep = sales_rep;
        this.old_plan = old_plan;
        this.new_plan = new_plan;
        this.r_id = r_id;
    }

    public String getsales_rep() {
        return sales_rep;
    }

    public void setsales_rep(String sales_rep) {
        this.sales_rep = sales_rep;
    }

    public String getold_plan() {
        return old_plan;
    }

    public void setold_plan(String old_plan) {
        this.old_plan = old_plan;
    }

    public String getnew_plan() {
        return new_plan;
    }

    public void setnew_plan(String new_plan) {
        this.new_plan = new_plan;
    }

    public String getr_id() {
        return r_id;
    }

    public void setr_id(String r_id) {
        this.r_id = r_id;
    }
}
