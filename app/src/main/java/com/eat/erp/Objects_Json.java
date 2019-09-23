package com.eat.erp;

import org.json.JSONException;
import org.json.JSONObject;

public class Objects_Json {

    public void getOrderData(String sales_rep_id, String channel_type, String distributor_type, String distributor_id, String store_id, String zone_id, String area_id, String location_id, String distributor_name, String remarks, String reation_id, String follow_type, String followup_date, String srld, String id, String bit_plan_id, String sequence, String mid, String sales_rep_loc_id, String merchandiser_stock_id, String ispermenant) throws JSONException {
        JSONObject obj=new JSONObject();
        obj.put("sales_rep_id",sales_rep_id);
        obj.put("channel_type", channel_type);
        obj.put("distributor_type",distributor_type);
        obj.put("distributor_id",distributor_id);
        obj.put("store_id",store_id);
        obj.put("zone_id", zone_id);
        obj.put("area_id",area_id);
        obj.put("location_id",location_id);
        obj.put("distributor_name",distributor_name);
        obj.put("remarks",remarks);
        obj.put("reation_id",reation_id);
        obj.put("follow_type",follow_type);
        obj.put("followup_date",followup_date);
        obj.put("srld",srld);
        obj.put("id",id);
        obj.put("bit_plan_id",bit_plan_id);
        obj.put("sequence",sequence);
        obj.put("mid",mid);
        obj.put("sales_rep_loc_id",sales_rep_loc_id);
        obj.put("merchandiser_stock_id",merchandiser_stock_id);
        obj.put("ispermenant",ispermenant);

    }

    public void getOrder(String orange_bar, String orange_box, String butterscotch_bar, String butterscotch_box, String chocopeanut_bar, String chocopeanut_box, String bambaiyachaat_bar, String bambaiyachaat_box, String mangoginger_bar, String mangoginger_box, String berry_blast_bar, String berry_blast_box, String chyawanprash_bar, String chyawanprash_box, String variety_box, String chocolate_cookies, String dark_chocolate_cookies, String cranberry_cookies, String fig_raisins, String papaya_pineapple, String cranberry_orange_zest) throws JSONException {
        JSONObject obj =new JSONObject();
        obj.put("orange_bar",orange_bar);
        obj.put("orange_box",orange_box);
        obj.put("butterscotch_bar",butterscotch_bar);
        obj.put("butterscotch_box",butterscotch_box);
        obj.put("chocopeanut_bar",chocopeanut_bar);
        obj.put("chocopeanut_box",chocopeanut_box);
        obj.put("bambaiyachaat_bar",bambaiyachaat_bar);
        obj.put("bambaiyachaat_box",bambaiyachaat_box);
        obj.put("mangoginger_bar",mangoginger_bar);
        obj.put("mangoginger_box",mangoginger_box);
        obj.put("berry_blast_bar",berry_blast_bar);
        obj.put("berry_blast_box",berry_blast_box);
        obj.put("chyawanprash_bar",chyawanprash_bar);
        obj.put("chyawanprash_box",chyawanprash_box);
        obj.put("variety_box",variety_box);
        obj.put("chocolate_cookies",chocolate_cookies);
        obj.put("dark_chocolate_cookies",dark_chocolate_cookies);
        obj.put("cranberry_cookies",cranberry_cookies);
        obj.put("fig_raisins",fig_raisins);
        obj.put("papaya_pineapple",papaya_pineapple);
        obj.put("cranberry_orange_zest",cranberry_orange_zest);


    }
}
