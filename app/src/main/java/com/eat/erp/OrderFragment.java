package com.eat.erp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.

 */
public class OrderFragment extends Fragment implements
        OrderAdapter.customButtonListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    View_load_dialog View_load_dialog;
    public static Activity fa;
    session session;
    ArrayList<Order> order;
    private static OrderAdapter adapter;
    ProgressDialog spinnerDialog;
    ListView lv;
    LinearLayout order_list;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        session = new session(getActivity());
         lv = view.findViewById(R.id.order_list1);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) view);
        /*FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) this.getView());*/
        View_load_dialog=new View_load_dialog(getActivity());

        order_list = view.findViewById(R.id.order_list);
        try{
            new MyAsyncTask().execute();
            //Log.e("order_fragment", "in create");
        }
        catch (Exception ex) {
            Log.e("order_fragment", ex.toString());
            //Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onButtonClickListner(final View convertView, int position, final String value) {
        View v = convertView;
        TextView txtid = (TextView) v.findViewById(R.id.txtid);
        showPopUp(txtid.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            View_load_dialog.hideDialog();
            getActivity().finish();
        }
    }

    public String  getorders()  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_order_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        return "";

    }

    public String  getorderdata(String order_id)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String data = URLEncoder.encode("order_id", "UTF-8")
                + "=" + URLEncoder.encode(order_id, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_order_data_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        return "";

    }

    private void showPopUp(String order_id) {
        try {
            View order_details;

            LinearLayout llorange_bar, llbutterscotch_bar, llchocopeanut_bar, llbambaiyachaat_bar,
                    llmangoginger_bar, llberry_blast_bar, llchyawanprash_bar, llorange_box, llbutterscotch_box,
                    llchocopeanut_box, llbambaiyachaat_box, llmangoginger_box, llberry_blast_box, llchyawanprash_box,
                    llvariety_box, llchocolate_cookies, lldark_chocolate_cookies, llcranberry_cookies,
                    llcranberry_orange_zest, llfig_raisins, llpapaya_pineapple, llBox, llBar, llCookies, llTrailmix;
            TextView orange_bar_qty, butterscotch_bar_qty, chocopeanut_bar_qty,
                    bambaiyachaat_bar_qty, mangoginger_bar_qty, berry_blast_bar_qty,
                    chyawanprash_bar_qty, orange_box_qty, orange_box_diff, butterscotch_box_qty,
                    chocopeanut_box_qty, bambaiyachaat_box_qty, mangoginger_box_qty,
                    berry_blast_box_qty, chyawanprash_box_qty, variety_box_qty,
                    chocolate_cookies_qty, dark_chocolate_cookies_qty,
                    cranberry_cookies_qty, cranberry_orange_zest_qty,
                    fig_raisins_qty, papaya_pineapple_qty, txtBar, txtBox, txtCookies, txtTrailmix;
            Integer intorange_bar_qty=0, intbutterscotch_bar_qty=0,
                    intchocopeanut_bar_qty=0, intbambaiyachaat_bar_qty=0,
                    intmangoginger_bar_qty=0, intberry_blast_bar_qty=0,
                    intchyawanprash_bar_qty=0, intorange_box_qty=0,
                    intbutterscotch_box_qty=0, intchocopeanut_box_qty=0,
                    intbambaiyachaat_box_qty=0, intmangoginger_box_qty=0,
                    intberry_blast_box_qty=0, intchyawanprash_box_qty=0,
                    intvariety_box_qty=0, intchocolate_cookies_qty=0,
                    intdark_chocolate_cookies_qty=0,
                    intcranberry_cookies_qty=0,
                    intcranberry_orange_zest_qty=0, intfig_raisins_qty=0,
                    intpapaya_pineapple_qty=0;


            String text;

            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getContext());

            LayoutInflater inflater = getLayoutInflater();
            order_details = inflater.inflate(R.layout.activity_order_details_pop_up, null);
            helpBuilder.setView(order_details);
            helpBuilder.setTitle("Order Details");

            FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "AvenirNextLTPro-Regular.otf");
            fontChanger.replaceFonts((ViewGroup) order_details);

            View vBar=order_details.findViewById(R.id.vBar);
            View vBox=order_details.findViewById(R.id.vBox);
            View vCookies=order_details.findViewById(R.id.vCookies);
            View vTrailmix=order_details.findViewById(R.id.vTrailmix);


            llorange_bar = (LinearLayout) order_details.findViewById(R.id.llorange_bar);
            orange_bar_qty = (TextView) order_details.findViewById(R.id.orange_bar_qty);
            llbutterscotch_bar = (LinearLayout) order_details.findViewById(R.id.llbutterscotch_bar);
            butterscotch_bar_qty = (TextView) order_details.findViewById(R.id.butterscotch_bar_qty);
            llchocopeanut_bar = (LinearLayout) order_details.findViewById(R.id.llchocopeanut_bar);
            chocopeanut_bar_qty = (TextView) order_details.findViewById(R.id.chocopeanut_bar_qty);
            llbambaiyachaat_bar = (LinearLayout) order_details.findViewById(R.id.llbambaiyachaat_bar);
            bambaiyachaat_bar_qty = (TextView) order_details.findViewById(R.id.bambaiyachaat_bar_qty);
            llmangoginger_bar = (LinearLayout) order_details.findViewById(R.id.llmangoginger_bar);
            mangoginger_bar_qty = (TextView) order_details.findViewById(R.id.mangoginger_bar_qty);
            llberry_blast_bar = (LinearLayout) order_details.findViewById(R.id.llberry_blast_bar);
            berry_blast_bar_qty = (TextView) order_details.findViewById(R.id.berry_blast_bar_qty);
            llchyawanprash_bar = (LinearLayout) order_details.findViewById(R.id.llchyawanprash_bar);
            chyawanprash_bar_qty = (TextView) order_details.findViewById(R.id.chyawanprash_bar_qty);
            llorange_box = (LinearLayout) order_details.findViewById(R.id.llorange_box);
            orange_box_qty = (TextView) order_details.findViewById(R.id.orange_box_qty);
            llbutterscotch_box = (LinearLayout) order_details.findViewById(R.id.llbutterscotch_box);
            butterscotch_box_qty = (TextView) order_details.findViewById(R.id.butterscotch_box_qty);
            llchocopeanut_box = (LinearLayout) order_details.findViewById(R.id.llchocopeanut_box);
            chocopeanut_box_qty = (TextView) order_details.findViewById(R.id.chocopeanut_box_qty);
            llbambaiyachaat_box = (LinearLayout) order_details.findViewById(R.id.llbambaiyachaat_box);
            bambaiyachaat_box_qty = (TextView) order_details.findViewById(R.id.bambaiyachaat_box_qty);
            llmangoginger_box = (LinearLayout) order_details.findViewById(R.id.llmangoginger_box);
            mangoginger_box_qty = (TextView) order_details.findViewById(R.id.mangoginger_box_qty);
            llberry_blast_box = (LinearLayout) order_details.findViewById(R.id.llberry_blast_box);
            berry_blast_box_qty = (TextView) order_details.findViewById(R.id.berry_blast_box_qty);
            llchyawanprash_box = (LinearLayout) order_details.findViewById(R.id.llchyawanprash_box);
            chyawanprash_box_qty = (TextView) order_details.findViewById(R.id.chyawanprash_box_qty);
            llvariety_box = (LinearLayout) order_details.findViewById(R.id.llvariety_box);
            variety_box_qty = (TextView) order_details.findViewById(R.id.variety_box_qty);
            llchocolate_cookies = (LinearLayout) order_details.findViewById(R.id.llchocolate_cookies);
            chocolate_cookies_qty = (TextView) order_details.findViewById(R.id.chocolate_cookies_qty);
            lldark_chocolate_cookies = (LinearLayout) order_details.findViewById(R.id.lldark_chocolate_cookies);
            dark_chocolate_cookies_qty = (TextView) order_details.findViewById(R.id.dark_chocolate_cookies_qty);
            llcranberry_cookies = (LinearLayout) order_details.findViewById(R.id.llcranberry_cookies);
            cranberry_cookies_qty = (TextView) order_details.findViewById(R.id.cranberry_cookies_qty);
            llcranberry_orange_zest = (LinearLayout) order_details.findViewById(R.id.llcranberry_orange_zest);
            cranberry_orange_zest_qty = (TextView) order_details.findViewById(R.id.cranberry_orange_zest_qty);
            llfig_raisins = (LinearLayout) order_details.findViewById(R.id.llfig_raisins);
            fig_raisins_qty = (TextView) order_details.findViewById(R.id.fig_raisins_qty);
            llpapaya_pineapple = (LinearLayout) order_details.findViewById(R.id.llpapaya_pineapple);
            papaya_pineapple_qty = (TextView) order_details.findViewById(R.id.papaya_pineapple_qty);
            llBox=(LinearLayout)order_details.findViewById(R.id.llBox);
            llBar=(LinearLayout)order_details.findViewById(R.id.llBars);
            llCookies=(LinearLayout)order_details.findViewById(R.id.llCookies);
            llTrailmix=(LinearLayout)order_details.findViewById(R.id.llTrailmix);


             /*helpBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });*/

            String response = getorderdata(order_id);
            JSONArray data = new JSONArray(response);
            boolean box=false, bar=false, cookie=false, trailmix=false;
            for (int i = 0; i < data.length(); i++) {

                JSONObject data1 = data.getJSONObject(i);

                text = data1.getString("orange_bar_qty");
                if(text.matches("\\d+")) {
                    intorange_bar_qty = Integer.parseInt(text);
                }
                if(intorange_bar_qty>0){
                    orange_bar_qty.setText(data1.getString("orange_bar_qty"));
                    llorange_bar.setVisibility(View.VISIBLE);
                    bar=true;
                } else {
                    llorange_bar.setVisibility(View.GONE);
                }
                text = data1.getString("butterscotch_bar_qty");
                if(text.matches("\\d+")) {
                    intbutterscotch_bar_qty = Integer.parseInt(text);
                }
                if(intbutterscotch_bar_qty>0){
                    butterscotch_bar_qty.setText(data1.getString("butterscotch_bar_qty"));
                    llbutterscotch_bar.setVisibility(View.VISIBLE);
                    bar=true;
                } else {
                    llbutterscotch_bar.setVisibility(View.GONE);
                }
                text = data1.getString("chocopeanut_bar_qty");
                if(text.matches("\\d+")) {
                    intchocopeanut_bar_qty = Integer.parseInt(text);
                }
                if(intchocopeanut_bar_qty>0){
                    chocopeanut_bar_qty.setText(data1.getString("chocopeanut_bar_qty"));
                    llchocopeanut_bar.setVisibility(View.VISIBLE);
                    bar=true;
                } else {
                    llchocopeanut_bar.setVisibility(View.GONE);
                }
                text = data1.getString("bambaiyachaat_bar_qty");
                if(text.matches("\\d+")) {
                    intbambaiyachaat_bar_qty = Integer.parseInt(text);
                }
                if(intbambaiyachaat_bar_qty>0){
                    bambaiyachaat_bar_qty.setText(data1.getString("bambaiyachaat_bar_qty"));
                    llbambaiyachaat_bar.setVisibility(View.VISIBLE);
                    bar=true;
                } else {
                    llbambaiyachaat_bar.setVisibility(View.GONE);
                }
                text = data1.getString("mangoginger_bar_qty");
                if(text.matches("\\d+")) {
                    intmangoginger_bar_qty = Integer.parseInt(text);
                }
                if(intmangoginger_bar_qty>0){
                    mangoginger_bar_qty.setText(data1.getString("mangoginger_bar_qty"));
                    llmangoginger_bar.setVisibility(View.VISIBLE);
                    bar=true;
                } else {
                    llmangoginger_bar.setVisibility(View.GONE);
                }
                text = data1.getString("berry_blast_bar_qty");
                if(text.matches("\\d+")) {
                    intberry_blast_bar_qty = Integer.parseInt(text);
                }
                if(intberry_blast_bar_qty>0){
                    berry_blast_bar_qty.setText(data1.getString("berry_blast_bar_qty"));
                    llberry_blast_bar.setVisibility(View.VISIBLE);
                    bar=true;
                } else {
                    llberry_blast_bar.setVisibility(View.GONE);
                }
                text = data1.getString("chyawanprash_bar_qty");
                if(text.matches("\\d+")) {
                    intchyawanprash_bar_qty = Integer.parseInt(text);
                }
                if(intchyawanprash_bar_qty>0){
                    chyawanprash_bar_qty.setText(data1.getString("chyawanprash_bar_qty"));
                    llchyawanprash_bar.setVisibility(View.VISIBLE);
                    bar=true;
                } else {
                    llchyawanprash_bar.setVisibility(View.GONE);
                }
                text = data1.getString("orange_box_qty");
                if(text.matches("\\d+")) {
                    intorange_box_qty = Integer.parseInt(text);
                }
                if(intorange_box_qty>0){
                    orange_box_qty.setText(data1.getString("orange_box_qty"));
                    llorange_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llorange_box.setVisibility(View.GONE);
                }
                text = data1.getString("butterscotch_box_qty");
                if(text.matches("\\d+")) {
                    intbutterscotch_box_qty = Integer.parseInt(text);
                }
                if(intbutterscotch_box_qty>0){
                    butterscotch_box_qty.setText(data1.getString("butterscotch_box_qty"));
                    llbutterscotch_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llbutterscotch_box.setVisibility(View.GONE);
                }
                text = data1.getString("chocopeanut_box_qty");
                if(text.matches("\\d+")) {
                    intchocopeanut_box_qty = Integer.parseInt(text);
                }
                if(intchocopeanut_box_qty>0){
                    chocopeanut_box_qty.setText(data1.getString("chocopeanut_box_qty"));
                    llchocopeanut_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llchocopeanut_box.setVisibility(View.GONE);
                }
                text = data1.getString("bambaiyachaat_box_qty");
                if(text.matches("\\d+")) {
                    intbambaiyachaat_box_qty = Integer.parseInt(text);
                }
                if(intbambaiyachaat_box_qty>0){
                    bambaiyachaat_box_qty.setText(data1.getString("bambaiyachaat_box_qty"));
                    llbambaiyachaat_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llbambaiyachaat_box.setVisibility(View.GONE);
                }
                text = data1.getString("mangoginger_box_qty");
                if(text.matches("\\d+")) {
                    intmangoginger_box_qty = Integer.parseInt(text);
                }
                if(intmangoginger_box_qty>0){
                    mangoginger_box_qty.setText(data1.getString("mangoginger_box_qty"));
                    llmangoginger_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llmangoginger_box.setVisibility(View.GONE);
                }
                text = data1.getString("berry_blast_box_qty");
                if(text.matches("\\d+")) {
                    intberry_blast_box_qty = Integer.parseInt(text);
                }
                if(intberry_blast_box_qty>0){
                    berry_blast_box_qty.setText(data1.getString("berry_blast_box_qty"));
                    llberry_blast_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llberry_blast_box.setVisibility(View.GONE);
                }
                text = data1.getString("chyawanprash_box_qty");
                if(text.matches("\\d+")) {
                    intchyawanprash_box_qty = Integer.parseInt(text);
                }
                if(intchyawanprash_box_qty>0){
                    chyawanprash_box_qty.setText(data1.getString("chyawanprash_box_qty"));
                    llchyawanprash_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llchyawanprash_box.setVisibility(View.GONE);

                }
                text = data1.getString("variety_box_qty");
                if(text.matches("\\d+")) {
                    intvariety_box_qty = Integer.parseInt(text);
                }
                if(intvariety_box_qty>0){
                    variety_box_qty.setText(data1.getString("variety_box_qty"));
                    llvariety_box.setVisibility(View.VISIBLE);
                    box=true;
                } else {
                    llvariety_box.setVisibility(View.GONE);
                }
                text = data1.getString("chocolate_cookies_qty");
                if(text.matches("\\d+")) {
                    intchocolate_cookies_qty = Integer.parseInt(text);
                }
                if(intchocolate_cookies_qty>0){
                    chocolate_cookies_qty.setText(data1.getString("chocolate_cookies_qty"));
                    llchocolate_cookies.setVisibility(View.VISIBLE);
                    cookie=true;
                } else {
                    llchocolate_cookies.setVisibility(View.GONE);
                }
                text = data1.getString("dark_chocolate_cookies_qty");
                if(text.matches("\\d+")) {
                    intdark_chocolate_cookies_qty = Integer.parseInt(text);
                }
                if(intdark_chocolate_cookies_qty>0){
                    dark_chocolate_cookies_qty.setText(data1.getString("dark_chocolate_cookies_qty"));
                    lldark_chocolate_cookies.setVisibility(View.VISIBLE);
                    cookie=true;

                } else {
                    lldark_chocolate_cookies.setVisibility(View.GONE);
                }
                text = data1.getString("cranberry_cookies_qty");
                if(text.matches("\\d+")) {
                    intcranberry_cookies_qty = Integer.parseInt(text);
                }
                if(intcranberry_cookies_qty>0){
                    cranberry_cookies_qty.setText(data1.getString("cranberry_cookies_qty"));
                    llcranberry_cookies.setVisibility(View.VISIBLE);
                    cookie=true;
                } else {
                    llcranberry_cookies.setVisibility(View.GONE);
                }
                text = data1.getString("cranberry_orange_zest_qty");
                if(text.matches("\\d+")) {
                    intcranberry_orange_zest_qty = Integer.parseInt(text);
                }
                if(intcranberry_orange_zest_qty>0){
                    cranberry_orange_zest_qty.setText(data1.getString("cranberry_orange_zest_qty"));
                    llcranberry_orange_zest.setVisibility(View.VISIBLE);
                    trailmix=true;
                } else {
                    llcranberry_orange_zest.setVisibility(View.GONE);
                }
                text = data1.getString("fig_raisins_qty");
                if(text.matches("\\d+")) {
                    intfig_raisins_qty = Integer.parseInt(text);
                }
                if(intfig_raisins_qty>0){
                    fig_raisins_qty.setText(data1.getString("fig_raisins_qty"));
                    llfig_raisins.setVisibility(View.VISIBLE);
                    trailmix=true;
                } else {
                    llfig_raisins.setVisibility(View.GONE);
                }
                text = data1.getString("papaya_pineapple_qty");
                if(text.matches("\\d+")) {
                    intpapaya_pineapple_qty = Integer.parseInt(text);
                }
                if(intpapaya_pineapple_qty>0){
                    papaya_pineapple_qty.setText(data1.getString("papaya_pineapple_qty"));
                    llpapaya_pineapple.setVisibility(View.VISIBLE);
                    trailmix=true;
                } else {
                    llpapaya_pineapple.setVisibility(View.GONE);
                }
            }


            if(!bar){
                llBar.setVisibility(View.GONE);
                vBar.setVisibility(View.GONE);
            }
            if(!box){
                llBox.setVisibility(View.GONE);
            vBox.setVisibility(View.GONE);}
            if(!cookie){
                llCookies.setVisibility(View.GONE);
            vCookies.setVisibility(View.GONE);}
            if(!trailmix){
                llTrailmix.setVisibility(View.GONE);
            vTrailmix.setVisibility(View.GONE);}

            helpBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            final AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();
        } catch (Exception ex) {
            Toast.makeText(getContext(),"exception "+ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, String>{
        String response="";
        @Override
        protected void onPreExecute() {
            View_load_dialog.showDialog();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                response = getorders();
                //Log.e("order_fragment", "response: "+response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject obj = new JSONObject(response);
                JSONArray data = obj.getJSONArray("data");
                order= new ArrayList<>();

                if(data.length()>0) {
                    order_list.setVisibility(View.VISIBLE);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject data1 = data.getJSONObject(i);
                        String retailer = data1.getString("distributor_name");
                        String distributor = data1.getString("distributor");
                        String location = data1.getString("location");
                        String id = data1.getString("order_id");

                        // Toast.makeText(getContext(),"exception "+distributor,Toast.LENGTH_LONG).show();

                        order.add(new Order("" + (i + 1), id, retailer, distributor, location, "View"));
                    }
                    adapter= new OrderAdapter(order,getContext());
                    adapter.setCustomButtonListner(OrderFragment.this);
                    lv.setAdapter(adapter);
                    lv.setScrollContainer(false);
                }
                else {
                    order_list.setVisibility(View.GONE);
                }
            View_load_dialog.hideDialog();
            super.onPostExecute(s);

            }catch (Exception e){
                Log.e("order_fragment", e.toString());
            }
        }
    }

}
