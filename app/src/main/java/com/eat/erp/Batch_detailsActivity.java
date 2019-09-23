package com.eat.erp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Batch_detailsActivity extends AppCompatActivity {


    String msg="Total qty does not match individual qty";
    Context context;
    LinearLayout llhome, llrouteplan, llorder, llaccount;
    ProgressDialog spinnerDialog;
    session session;
    TextView txtname, txtinitial, txttitle, txtcheckout;
    Button addbatch1, addbatch2,addbatch3,addbatch4,addbatch5,addbatch6,addbatch7,addbatch8,addbatch9,addbatch10,addbatch11,addbatch12,addbatch13,addbatch14,addbatch15,addbatch16,addbatch17,addbatch18,addbatch19,addbatch20,addbatch21;
    Button save_btn, cancel_btn;
    LinearLayout llcheckin, llcheckout ;
    LinearLayout llorange_bar, llbutterscotch_bar, llchocopeanut_bar, llbambaiyachaat_bar,
            llmangoginger_bar, llberry_blast_bar, llchyawanprash_bar, llorange_box, llbutterscotch_box,
            llchocopeanut_box, llbambaiyachaat_box, llmangoginger_box, llberry_blast_box, llchyawanprash_box,
            llvariety_box, llchocolate_cookies, lldark_chocolate_cookies, llcranberry_cookies,
            llcranberry_orange_zest, llfig_raisins, llpapaya_pineapple,allBars, allBox, allCookies, allTrailmix, llBars, llBox, llCookies, llTrailmix, nullview;
    TextView orange_bar_qty, butterscotch_bar_qty, chocopeanut_bar_qty,
            bambaiyachaat_bar_qty, mangoginger_bar_qty, berry_blast_bar_qty,
            chyawanprash_bar_qty, orange_box_qty, orange_box_diff, butterscotch_box_qty,
            chocopeanut_box_qty, bambaiyachaat_box_qty, mangoginger_box_qty,
            berry_blast_box_qty, chyawanprash_box_qty, variety_box_qty,
            chocolate_cookies_qty, dark_chocolate_cookies_qty,
            cranberry_cookies_qty, cranberry_orange_zest_qty,
            fig_raisins_qty, papaya_pineapple_qty, txtbars, txtbox, txtcookies, txttrailmix;

    TextView error1, error2, error3, error4,error5, error6, error7,error8,error9, error10, error11, error12, error13, error14, error15, error16, error17, error18, error19, error20, error21;

    EditText total1,total2,total3,total4,total5,total6,total7,total8,total9,total10,total11,total12,total13,total14,total15,total16,total17,total18,total19,total20,total21, ind1,ind2,ind3,ind4,ind5,ind6,ind7,ind8,ind9,ind10,ind11,ind12,ind13,ind14,ind15,ind16,ind17,ind18,ind19,ind20,ind21;
    Typeface face, face1;
    Button inc1, inc2, inc3, inc4, inc5, inc6, inc7, inc8, inc9, inc10, inc11, inc12, inc13, inc14, inc15, inc16, inc17, inc18, inc19, inc20, inc21, dec1, dec2, dec3, dec4, dec5, dec6, dec7, dec8, dec9, dec10, dec11, dec12, dec13, dec14, dec15, dec16, dec17, dec18, dec19, dec20, dec21, inc_ind1, inc_ind2, inc_ind3, inc_ind4, inc_ind5, inc_ind6, inc_ind7, inc_ind8, inc_ind9, inc_ind10, inc_ind11, inc_ind12, inc_ind13, inc_ind14, inc_ind15, inc_ind16, inc_ind17, inc_ind18, inc_ind19, inc_ind20, inc_ind21, dec_ind1, dec_ind2, dec_ind3, dec_ind4, dec_ind5, dec_ind6, dec_ind7, dec_ind8, dec_ind9, dec_ind10, dec_ind11, dec_ind12, dec_ind13, dec_ind14, dec_ind15, dec_ind16, dec_ind17, dec_ind18, dec_ind19, dec_ind20, dec_ind21;

    View_load_dialog View_load_dialog;
    Spinner orange_batch, butterscotch_batch, choco_batch, chaat_batch, mango_batch, berry_batch, chyawanprash_batch, orange_box_batch, butterscotch_box_batch, choco_box_batch, chaat_box_batch, mango_box_batch, berry_box_batch, chyawanparash_box_batch, variety_box_batch, choco_cookies_batch, dark_choco_batch, cranberry_batch, pap_pine_batch, fig_raisin_batch, cran_orang_batch;
    View batchView;
    EditText indv_qty;
    int backflag=0;
    String globalResponse="";
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details_pop_up);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        context=Batch_detailsActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new session(context);

        face= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        face1= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Batch Details");

        orange_bar_qty=findViewById(R.id.orange_bar_lbl);
        butterscotch_bar_qty=findViewById(R.id.butterscotch_bar_lbl);
        chocopeanut_bar_qty=findViewById(R.id.chocopeanut_bar_lbl);
        bambaiyachaat_bar_qty=findViewById(R.id.bambaiyachaat_bar_lbl);
        mangoginger_bar_qty=findViewById(R.id.mangoginger_bar_lbl);
        berry_blast_bar_qty=findViewById(R.id.berry_blast_bar_lbl);
        chyawanprash_bar_qty=findViewById(R.id.chyawanprash_bar_lbl);
        orange_box_qty=findViewById(R.id.orange_box_lbl);
        butterscotch_box_qty=findViewById(R.id.butterscotch_box_lbl);
        chocopeanut_box_qty=findViewById(R.id.chocopeanut_box_lbl);
        bambaiyachaat_box_qty=findViewById(R.id.bambaiyachaat_box_lbl);
        mangoginger_box_qty=findViewById(R.id.mangoginger_box_lbl);
        berry_blast_box_qty=findViewById(R.id.berry_blast_box_lbl);
        chyawanprash_box_qty=findViewById(R.id.chyawanprash_box_lbl);
        variety_box_qty=findViewById(R.id.variety_box_lbl);
        chocolate_cookies_qty=findViewById(R.id.chocolate_cookies_lbl);
        dark_chocolate_cookies_qty=findViewById(R.id.dark_chocolate_cookies_lbl);
        cranberry_cookies_qty=findViewById(R.id.cranberry_cookies_lbl);
        cranberry_orange_zest_qty=findViewById(R.id.cranberry_orange_zest_lbl);
        fig_raisins_qty=findViewById(R.id.fig_raisins_lbl);
        papaya_pineapple_qty=findViewById(R.id.papaya_pineapple_lbl);
        txtbars=findViewById(R.id.txtBars);
        txtcookies=findViewById(R.id.txtCookies);
        txtbox=findViewById(R.id.txtBox);
        txttrailmix=findViewById(R.id.txtTrailmix);

        mScrollView=findViewById(R.id.batchScroll);
        save_btn=findViewById(R.id.save_btn);
        cancel_btn=findViewById(R.id.cancel_btn);

        orange_bar_qty.setTypeface(face);
        butterscotch_bar_qty.setTypeface(face);
        chocopeanut_bar_qty.setTypeface(face);
        bambaiyachaat_bar_qty.setTypeface(face); mangoginger_bar_qty.setTypeface(face); berry_blast_bar_qty.setTypeface(face);
        chyawanprash_bar_qty.setTypeface(face); orange_box_qty.setTypeface(face); butterscotch_box_qty.setTypeface(face);
        chocopeanut_box_qty.setTypeface(face); bambaiyachaat_box_qty.setTypeface(face); mangoginger_box_qty.setTypeface(face);
        berry_blast_box_qty.setTypeface(face); chyawanprash_box_qty.setTypeface(face); variety_box_qty.setTypeface(face);
        chocolate_cookies_qty.setTypeface(face); dark_chocolate_cookies_qty.setTypeface(face);
        cranberry_cookies_qty.setTypeface(face); cranberry_orange_zest_qty.setTypeface(face);
        fig_raisins_qty.setTypeface(face); papaya_pineapple_qty.setTypeface(face); txtbars.setTypeface(face); txtbox.setTypeface(face); txtcookies.setTypeface(face); txttrailmix.setTypeface(face);

        orange_bar_qty.setTextSize(12); butterscotch_bar_qty.setTextSize(12); chocopeanut_bar_qty.setTextSize(12);
        bambaiyachaat_bar_qty.setTextSize(12); mangoginger_bar_qty.setTextSize(12); berry_blast_bar_qty.setTextSize(12);
        chyawanprash_bar_qty.setTextSize(12); orange_box_qty.setTextSize(12);butterscotch_box_qty.setTextSize(12);
        chocopeanut_box_qty.setTextSize(12); bambaiyachaat_box_qty.setTextSize(12); mangoginger_box_qty.setTextSize(12);
        berry_blast_box_qty.setTextSize(12); chyawanprash_box_qty.setTextSize(12); variety_box_qty.setTextSize(12);
        chocolate_cookies_qty.setTextSize(12); dark_chocolate_cookies_qty.setTextSize(12);
        cranberry_cookies_qty.setTextSize(12); cranberry_orange_zest_qty.setTextSize(12);
        fig_raisins_qty.setTextSize(12); papaya_pineapple_qty.setTextSize(12); txtbars.setTextSize(14); txtbox.setTextSize(14); txtcookies.setTextSize(14); txttrailmix.setTextSize(14);


        orange_batch=findViewById(R.id.orange_batch);
        butterscotch_batch=findViewById(R.id.butterscotch_batch);
        choco_batch=findViewById(R.id.choco_batch);
        chaat_batch=findViewById(R.id.chaat_batch);
        mango_batch=findViewById(R.id.mango_batch);
        berry_batch=findViewById(R.id.berry_batch);
        chyawanprash_batch=findViewById(R.id.chyawanprash_batch);
        orange_box_batch=findViewById(R.id.orange_box_batch);
        butterscotch_box_batch=findViewById(R.id.butterscotch_box_batch);
        chaat_box_batch=findViewById(R.id.chaat_box_batch);
        choco_box_batch=findViewById(R.id.choco_box_batch);
        mango_box_batch=findViewById(R.id.mango_box_batch);
        berry_box_batch=findViewById(R.id.berry_box_batch);
        chyawanparash_box_batch=findViewById(R.id.chyawanprash_box_batch);
        variety_box_batch=findViewById(R.id.variety_box_batch);
        choco_cookies_batch=findViewById(R.id.chocolate_cookies_batch);
        dark_choco_batch=findViewById(R.id.dark_chocolate_cookies_batch);
        cranberry_batch=findViewById(R.id.cranberry_cookies_batch);
        cran_orang_batch=findViewById(R.id.cranberry_orange_zest_batch);
        fig_raisin_batch=findViewById(R.id.fig_raisins_batch);
        pap_pine_batch=findViewById(R.id.papaya_pineapple_batch);


        llorange_bar=findViewById(R.id.orange_container);
        llbutterscotch_bar=findViewById(R.id.butterscotch_bar_container);
        llchocopeanut_bar=findViewById(R.id.chocopeanut_bar_container);
        llbambaiyachaat_bar=findViewById(R.id.chaat_container);
        llmangoginger_bar=findViewById(R.id.mangoginger_bar_container);
        llberry_blast_bar=findViewById(R.id.berry_blast_bar_container);
        llchyawanprash_bar=findViewById(R.id.chyawanprash_bar_container);
        llorange_box=findViewById(R.id.orange_box_container);
        llbutterscotch_box=findViewById(R.id.butterscotch_box_container);
        llchocopeanut_box=findViewById(R.id.choco_box_container);
        llbambaiyachaat_box=findViewById(R.id.chaat_box_container);
        llmangoginger_box=findViewById(R.id.mango_box_container);
        llberry_blast_box=findViewById(R.id.berry_blast_box_container);
        llchyawanprash_box=findViewById(R.id.chyawanprash_box_container);
        llvariety_box=findViewById(R.id.variety_box_container);
        llchocolate_cookies=findViewById(R.id.chocolate_cookies_container);
        lldark_chocolate_cookies=findViewById(R.id.dark_chocolate_cookies_container);
        llcranberry_cookies=findViewById(R.id.cranberry_cookies_container);
        llcranberry_orange_zest=findViewById(R.id.cranberry_orange_zest_container);
        llfig_raisins=findViewById(R.id.fig_raisins_container);
        llpapaya_pineapple=findViewById(R.id.papaya_pineapple_container);
        allBars=findViewById(R.id.allBars);
        allBox=findViewById(R.id.allBox);
        allCookies=findViewById(R.id.allCookies);
        allTrailmix=findViewById(R.id.allTrailmix);
        llBars=findViewById(R.id.llBars);
        llBox=findViewById(R.id.llBox);
        llCookies=findViewById(R.id.llCookies);
        llTrailmix=findViewById(R.id.llTrailmix);
        nullview=findViewById(R.id.nullview);
        nullview.setVisibility(View.INVISIBLE);



        addbatch1=findViewById(R.id.add_batch_btn1);
        addbatch2=findViewById(R.id.add_batch_btn2);
        addbatch3=findViewById(R.id.add_batch_btn3);
        addbatch4=findViewById(R.id.add_batch_btn4);
        addbatch5=findViewById(R.id.add_batch_btn5);
        addbatch6=findViewById(R.id.add_batch_btn6);
        addbatch7=findViewById(R.id.add_batch_btn7);
        addbatch8=findViewById(R.id.add_batch_btn8);
        addbatch9=findViewById(R.id.add_batch_btn9);
        addbatch10=findViewById(R.id.add_batch_btn10);
        addbatch11=findViewById(R.id.add_batch_btn11);
        addbatch12=findViewById(R.id.add_batch_btn12);
        addbatch13=findViewById(R.id.add_batch_btn13);
        addbatch14=findViewById(R.id.add_batch_btn14);
        addbatch15=findViewById(R.id.add_batch_btn15);
        addbatch16=findViewById(R.id.add_batch_btn16);
        addbatch17=findViewById(R.id.add_batch_btn17);
        addbatch18=findViewById(R.id.add_batch_btn18);
        addbatch19=findViewById(R.id.add_batch_btn19);
        addbatch20=findViewById(R.id.add_batch_btn20);
        addbatch21=findViewById(R.id.add_batch_btn21);

        inc1 =findViewById(R.id.inc_btn1);
        inc2=findViewById(R.id.inc_btn2);
        inc3=findViewById(R.id.inc_btn3);
        inc4=findViewById(R.id.inc_btn4);
        inc5=findViewById(R.id.inc_btn5);
        inc6=findViewById(R.id.inc_btn6);
        inc7=findViewById(R.id.inc_btn7);
        inc8=findViewById(R.id.inc_btn8);
        inc9=findViewById(R.id.inc_btn9);
        inc10=findViewById(R.id.inc_btn10);
        inc11=findViewById(R.id.inc_btn11);
        inc12=findViewById(R.id.inc_btn12);
        inc13=findViewById(R.id.inc_btn13);
        inc14=findViewById(R.id.inc_btn14);
        inc15=findViewById(R.id.inc_btn15);
        inc16=findViewById(R.id.inc_btn16);
        inc17=findViewById(R.id.inc_btn17);
        inc18=findViewById(R.id.inc_btn18);
        inc19=findViewById(R.id.inc_btn19);
        inc20=findViewById(R.id.inc_btn20);
        inc21=findViewById(R.id.inc_btn21);

        dec1=findViewById(R.id.dec_btn1);
        dec2=findViewById(R.id.dec_btn2);
        dec3=findViewById(R.id.dec_btn3);
        dec4=findViewById(R.id.dec_btn4);
        dec5=findViewById(R.id.dec_btn5);
        dec6=findViewById(R.id.dec_btn6);
        dec7=findViewById(R.id.dec_btn7);
        dec8=findViewById(R.id.dec_btn8);
        dec9=findViewById(R.id.dec_btn9);
        dec10=findViewById(R.id.dec_btn10);
        dec11=findViewById(R.id.dec_btn11);
        dec12=findViewById(R.id.dec_btn12);
        dec13=findViewById(R.id.dec_btn13);
        dec14=findViewById(R.id.dec_btn14);
        dec15=findViewById(R.id.dec_btn15);
        dec16=findViewById(R.id.dec_btn16);
        dec17=findViewById(R.id.dec_btn17);
        dec18=findViewById(R.id.dec_btn18);
        dec19=findViewById(R.id.dec_btn19);
        dec20=findViewById(R.id.dec_btn20);
        dec21=findViewById(R.id.dec_btn21);

         error1=findViewById(R.id.error1);
         error2=findViewById(R.id.error2);
         error3=findViewById(R.id.error3);
         error4=findViewById(R.id.error4);
         error5=findViewById(R.id.error5);
         error6=findViewById(R.id.error6);
         error7=findViewById(R.id.error7);
         error8=findViewById(R.id.error8);
         error9=findViewById(R.id.error9);
         error10=findViewById(R.id.error10);
         error11=findViewById(R.id.error11);
         error12=findViewById(R.id.error12);
         error13=findViewById(R.id.error13);
         error14=findViewById(R.id.error14);
         error15=findViewById(R.id.error15);
         error16=findViewById(R.id.error16);
         error17=findViewById(R.id.error17);
         error18=findViewById(R.id.error18);
         error19=findViewById(R.id.error19);
         error20=findViewById(R.id.error20);
         error21=findViewById(R.id.error21);


        error1.setVisibility(View.GONE);
        error2.setVisibility(View.GONE);
        error3.setVisibility(View.GONE);
        error4.setVisibility(View.GONE);
        error5.setVisibility(View.GONE);
        error6.setVisibility(View.GONE);
        error7.setVisibility(View.GONE);
        error8.setVisibility(View.GONE);
        error9.setVisibility(View.GONE);
        error10.setVisibility(View.GONE);
        error11.setVisibility(View.GONE);
        error12.setVisibility(View.GONE);
        error13.setVisibility(View.GONE);
        error14.setVisibility(View.GONE);
        error15.setVisibility(View.GONE);
        error16.setVisibility(View.GONE);
        error17.setVisibility(View.GONE);
        error18.setVisibility(View.GONE);
        error19.setVisibility(View.GONE);
        error20.setVisibility(View.GONE);
        error21.setVisibility(View.GONE);


        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backflag=1;
                onBackPressed();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backflag=2;
                onBackPressed();
            }
        });

        total1=findViewById(R.id.total1);total2=findViewById(R.id.total2);total3=findViewById(R.id.total3);total4=findViewById(R.id.total4);total5=findViewById(R.id.total5);total6=findViewById(R.id.total6);total7=findViewById(R.id.total7);total8=findViewById(R.id.total8);total9=findViewById(R.id.total9);total10=findViewById(R.id.total10);total11=findViewById(R.id.total11);total12=findViewById(R.id.total12);total13=findViewById(R.id.total13);total14=findViewById(R.id.total14);total15=findViewById(R.id.total15);total16=findViewById(R.id.total16);total17=findViewById(R.id.total17);total18=findViewById(R.id.total18);total19=findViewById(R.id.total19);total20=findViewById(R.id.total20);total21=findViewById(R.id.total21); ind1=findViewById(R.id.ind1);ind2=findViewById(R.id.ind2);ind3=findViewById(R.id.ind3);ind4=findViewById(R.id.ind4);ind5=findViewById(R.id.ind5);ind6=findViewById(R.id.ind6);ind7=findViewById(R.id.ind7);ind8=findViewById(R.id.ind8);ind9=findViewById(R.id.ind9);ind10=findViewById(R.id.ind10);ind11=findViewById(R.id.ind11);ind12=findViewById(R.id.ind12);ind13=findViewById(R.id.ind13);ind14=findViewById(R.id.ind14);ind15=findViewById(R.id.ind15);ind16=findViewById(R.id.ind16);ind17=findViewById(R.id.ind17);ind18=findViewById(R.id.ind18);ind19=findViewById(R.id.ind19);ind20=findViewById(R.id.ind20);ind21=findViewById(R.id.ind21);
        try {
        addbatch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(1, 0, "");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(2, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(3, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(4, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(5, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(6, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(7, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(8, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(9, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(10, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(11, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(12, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(13, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(14, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(15, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(16, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(17, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(18, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(19, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(20, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addbatch21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_batch(21, 0,"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




            try {
                String response="";
                if(OrdersData.batch_no_json!=null) {
                    response=OrdersData.batch_no_json;
                }else{
                    response = getbatchnos();
                }
                OrdersData.batch_no_json=response;
                globalResponse=response;

                final ArrayList<StringWithTag4> mylist_batchno = new ArrayList<StringWithTag4>();
                HashMap<Integer, String> list_batchno = new HashMap<Integer, String>();

                JSONArray data = new JSONArray(response);

                for (int k = 0; k < data.length(); k++) {
                    JSONObject data1 = data.getJSONObject(k);
                    String batch_no = data1.getString("batch_no");
                    //mylist.add(distributor_name);
                    list_batchno.put(k, batch_no);
                }

                mylist_batchno.add(new StringWithTag4("Select", 0));

                for (Map.Entry<Integer, String> entry : list_batchno.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();

                    /* Build the StringWithTag List using these keys and values. */
                    mylist_batchno.add(new StringWithTag4(value, key));
                }
                // Do nothing but close the dialog


                ArrayAdapter<StringWithTag4> adapter_batchno = new ArrayAdapter<StringWithTag4>(context, android.R.layout.simple_spinner_item, mylist_batchno){
                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                        View v = (TextView) super.getView(position, convertView, parent);
                        Typeface tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                        ((TextView)v).setTypeface(tfavv);
                        v.setPadding(5,10,5,10);
                        //v.setTextColor(Color.RED);
                        //v.setTextSize(35);
                        return v;
                    }


                    public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                        View v = (TextView) super.getView(position, convertView, parent);
                        Typeface tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                        ((TextView)v).setTypeface(tfavv);
                        v.setPadding(5,10,5,10);
                        //v.setTextColor(Color.RED);
                        //v.setTextSize(35);
                        return v;
                    }
                };
                adapter_batchno.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                orange_batch.setAdapter(adapter_batchno);
                butterscotch_batch.setAdapter(adapter_batchno);
                choco_batch.setAdapter(adapter_batchno);
                chaat_batch.setAdapter(adapter_batchno);
                mango_batch.setAdapter(adapter_batchno);
                berry_batch.setAdapter(adapter_batchno);
                chyawanprash_batch.setAdapter(adapter_batchno);
                orange_box_batch.setAdapter(adapter_batchno);
                butterscotch_box_batch.setAdapter(adapter_batchno);
                chaat_box_batch.setAdapter(adapter_batchno);
                choco_box_batch.setAdapter(adapter_batchno);
                mango_box_batch.setAdapter(adapter_batchno);
                berry_box_batch.setAdapter(adapter_batchno);
                chyawanparash_box_batch.setAdapter(adapter_batchno);
                variety_box_batch.setAdapter(adapter_batchno);
                choco_cookies_batch.setAdapter(adapter_batchno);
                dark_choco_batch.setAdapter(adapter_batchno);
                cranberry_batch.setAdapter(adapter_batchno);
                cran_orang_batch.setAdapter(adapter_batchno);
                fig_raisin_batch.setAdapter(adapter_batchno);
                pap_pine_batch.setAdapter(adapter_batchno);


            } catch (Exception ex) {
                Log.e("batch details", "7 "+ex.toString());
                //Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
            }


            if(OrdersData.orange_bar_json!=null){
            String orange_bar_json=OrdersData.orange_bar_json;
            JSONObject obj = new JSONObject(orange_bar_json);

            Iterator<String> keys = obj.keys();
            int count=0;
            while (keys.hasNext()) {
                String key = keys.next();
                    if (count == 0) {
                        orange_batch.setSelection(Integer.parseInt(key));
                        ind1.setText(obj.getString(key));
                        count++;
                    } else {
                        int keyval = Integer.parseInt(key);
                        add_batch(1, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.butterscotch_bar_json!=null){
                String orange_bar_json=OrdersData.butterscotch_bar_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        butterscotch_batch.setSelection(Integer.parseInt(key));
                        ind2.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(2, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.chocopeanut_bar_json!=null){
                String orange_bar_json=OrdersData.chocopeanut_bar_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        choco_batch.setSelection(Integer.parseInt(key));
                        ind3.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(3, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.bambaiyachaat_bar_json!=null){
                String orange_bar_json=OrdersData.bambaiyachaat_bar_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        chaat_batch.setSelection(Integer.parseInt(key));
                        ind4.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(4, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.mangoginger_bar_json!=null){
                String orange_bar_json=OrdersData.mangoginger_bar_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        mango_batch.setSelection(Integer.parseInt(key));
                        ind5.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(5, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.berry_blast_bar_json!=null){
                String orange_bar_json=OrdersData.berry_blast_bar_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        berry_batch.setSelection(Integer.parseInt(key));
                        ind6.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(6, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.chyawanprash_bar_json!=null){
                String orange_bar_json=OrdersData.chyawanprash_bar_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        chyawanprash_batch.setSelection(Integer.parseInt(key));
                        ind7.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(7, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.orange_box_json!=null){
                String orange_bar_json=OrdersData.orange_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        orange_box_batch.setSelection(Integer.parseInt(key));
                        ind8.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(8, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.butterscotch_box_json!=null){
                String orange_bar_json=OrdersData.butterscotch_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        butterscotch_box_batch.setSelection(Integer.parseInt(key));
                        ind9.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(9, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.chocopeanut_box_json!=null){
                String orange_bar_json=OrdersData.chocopeanut_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        choco_box_batch.setSelection(Integer.parseInt(key));
                        ind10.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(10, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.bambaiyachaat_box_json!=null){
                String orange_bar_json=OrdersData.bambaiyachaat_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        chaat_box_batch.setSelection(Integer.parseInt(key));
                        ind11.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(11, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.mangoginger_box_json!=null){
                String orange_bar_json=OrdersData.mangoginger_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        mango_box_batch.setSelection(Integer.parseInt(key));
                        ind12.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(12, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.berry_blast_box_json!=null){
                String orange_bar_json=OrdersData.berry_blast_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        berry_box_batch.setSelection(Integer.parseInt(key));
                        ind13.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(13, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.chyawanprash_box_json!=null){
                String orange_bar_json=OrdersData.chyawanprash_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        chyawanparash_box_batch.setSelection(Integer.parseInt(key));
                        ind14.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(14, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.variety_box_json!=null){
                String orange_bar_json=OrdersData.variety_box_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        variety_box_batch.setSelection(Integer.parseInt(key));
                        ind15.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(15, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.chocolate_cookies_json!=null){
                String orange_bar_json=OrdersData.chocolate_cookies_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        choco_cookies_batch.setSelection(Integer.parseInt(key));
                        ind16.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(16, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.dark_chocolate_cookies_json!=null){
                String orange_bar_json=OrdersData.dark_chocolate_cookies_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        dark_choco_batch.setSelection(Integer.parseInt(key));
                        ind17.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(17, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.cranberry_cookies_json!=null){
                String orange_bar_json=OrdersData.cranberry_cookies_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        cranberry_batch.setSelection(Integer.parseInt(key));
                        ind18.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(18, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.cranberry_orange_json!=null){
                String orange_bar_json=OrdersData.cranberry_orange_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        cran_orang_batch.setSelection(Integer.parseInt(key));
                        ind19.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(19, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.fig_raisins_json!=null){
                String orange_bar_json=OrdersData.fig_raisins_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        fig_raisin_batch.setSelection(Integer.parseInt(key));
                        ind20.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(20, keyval, obj.getString(key));
                    }
                }
            }
            if(OrdersData.papaya_pineapple_json!=null){
                String orange_bar_json=OrdersData.papaya_pineapple_json;
                JSONObject obj = new JSONObject(orange_bar_json);
                Iterator<String> keys = obj.keys();
                int count=0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if(count==0){
                        pap_pine_batch.setSelection(Integer.parseInt(key));
                        ind21.setText(obj.getString(key));
                        count++;
                    }else{
                        int keyval=Integer.parseInt(key);
                        add_batch(21, keyval, obj.getString(key));
                    }
                }
            }

            } catch (Exception e) {
                Log.e("batch details","6 "+e.toString());
            }




        txtname = (TextView) findViewById(R.id.textView2);
        txtinitial = (TextView) findViewById(R.id.txtinitial);
        TextView txtcheckin = (TextView) findViewById(R.id.txtcheckin);
        llcheckin = (LinearLayout) findViewById(R.id.llcheckin);
        llcheckout = (LinearLayout) findViewById(R.id.llcheckout);

        txtcheckout = (TextView) findViewById(R.id.txtcheckout);

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c=Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3+ ", Today");

        View_load_dialog = new View_load_dialog(this);




        try{
            Intent i=getIntent();
            String orange_bar=i.getStringExtra("orange_bar");
            String orange_box=i.getStringExtra("orange_box");
            String butterscotch_bar=i.getStringExtra("butterscotch_bar");
            String butterscotch_box=i.getStringExtra("butterscotch_box");
            String chocopeanut_bar=i.getStringExtra("chocopeanut_bar");
            String chocopeanut_box=i.getStringExtra("chocopeanut_box");
            String bambaiyachaat_bar=i.getStringExtra("bambaiyachaat_bar");
            String bambaiyachaat_box=i.getStringExtra("bambaiyachaat_box");
            String mangoginger_bar=i.getStringExtra("mangoginger_bar");
            String mangoginger_box=i.getStringExtra("mangoginger_box");
            String berry_blast_bar=i.getStringExtra("berry_blast_bar");
            String berry_blast_box=i.getStringExtra("berry_blast_box");
            String chyawanprash_bar=i.getStringExtra("chyawanprash_bar");
            String chyawanprash_box=i.getStringExtra("chyawanprash_box");
            String variety_box=i.getStringExtra("variety_box");
            String chocolate_cookies=i.getStringExtra("chocolate_cookies");
            String dark_chocolate_cookies=i.getStringExtra("dark_chocolate_cookies");
            String cranberry_cookies=i.getStringExtra("cranberry_cookies");
            String fig_raisins=i.getStringExtra("fig_raisins");
            String papaya_pineapple=i.getStringExtra("papaya_pineapple");
            String cranberry_orange_zest=i.getStringExtra("cranberry_orange_zest");

            View v2=findViewById(R.id.v2);
            View v3=findViewById(R.id.v3);
            View v4=findViewById(R.id.v4);
            View v5=findViewById(R.id.v5);
            View v6=findViewById(R.id.v6);
            View v7=findViewById(R.id.v7);
            View v9=findViewById(R.id.v9);
            View v10=findViewById(R.id.v10);
            View v11=findViewById(R.id.v11);
            View v12=findViewById(R.id.v12);
            View v13=findViewById(R.id.v13);
            View v14=findViewById(R.id.v14);
            View v15=findViewById(R.id.v15);
            View v17=findViewById(R.id.v17);
            View v18=findViewById(R.id.v18);
            View v20=findViewById(R.id.v20);
            View v21=findViewById(R.id.v21);

            int countbar=0, countbox=0, countcookies=0, counttrailmix=0;


            boolean barflag=false, boxflag=false, cookiesflag=false, trailmixflag=false;

            if(!orange_bar.equals("")&&Integer.parseInt(orange_bar)!=0){
                llorange_bar.setVisibility(View.VISIBLE);
                total1.setText(orange_bar);
                barflag=true;
                countbar+=1;

            }else{
                llorange_bar.setVisibility(View.GONE);
            }
            if(!orange_box.equals("")&&!orange_box.equals("0")){
                llorange_box.setVisibility(View.VISIBLE);
                total8.setText(orange_box);
                boxflag=true;
                countbox+=1;
            }else{
                llorange_box.setVisibility(View.GONE);
            }
            if(!butterscotch_bar.equals("")&&!butterscotch_bar.equals("0")){
                llbutterscotch_bar.setVisibility(View.VISIBLE);
                barflag=true;
                total2.setText(butterscotch_bar);
                if(countbar>0){v2.setVisibility(View.VISIBLE);}else v2.setVisibility(View.GONE);
                countbar+=1;
            }else{
                llbutterscotch_bar.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
            }
            if(!butterscotch_box.equals("")&&!butterscotch_box.equals("0")){
                llbutterscotch_box.setVisibility(View.VISIBLE);
                boxflag=true;
                total9.setText(butterscotch_box);
                if(countbox>0){v9.setVisibility(View.VISIBLE);}else v9.setVisibility(View.GONE);
                countbox+=1;

            }else{
                llbutterscotch_box.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
            }
            if(!chocopeanut_bar.equals("")&&!chocopeanut_bar.equals("0")){
                llchocopeanut_bar.setVisibility(View.VISIBLE);
                barflag=true;
                total3.setText(chocopeanut_bar);
                if(countbar>0){v3.setVisibility(View.VISIBLE);}else v3.setVisibility(View.GONE);
                countbar+=1;

            }else{
                llchocopeanut_bar.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
            }
            if(!chocopeanut_box.equals("")&&!chocopeanut_box.equals("0")){
                llchocopeanut_box.setVisibility(View.VISIBLE);
                boxflag=true;
                total10.setText(chocopeanut_box);
                if(countbox>0){v10.setVisibility(View.VISIBLE);}else v10.setVisibility(View.GONE);
                countbox+=1;

            }else{
                llchocopeanut_box.setVisibility(View.GONE);
                v10.setVisibility(View.GONE);
            }
            if(!bambaiyachaat_bar.equals("")&&!bambaiyachaat_bar.equals("0")){
                llbambaiyachaat_bar.setVisibility(View.VISIBLE);
                barflag=true;
                total4.setText(bambaiyachaat_bar);
                if(countbar>0){v4.setVisibility(View.VISIBLE);}else v4.setVisibility(View.GONE);
                countbar+=1;

            }else{
                llbambaiyachaat_bar.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
            }
            if(!bambaiyachaat_box.equals("")&&!bambaiyachaat_box.equals("0")){
                llbambaiyachaat_box.setVisibility(View.VISIBLE);
                boxflag=true;
                total11.setText(bambaiyachaat_box);
                if(countbox>0){v11.setVisibility(View.VISIBLE);}else v11.setVisibility(View.GONE);
                countbox+=1;
            }else{
                llbambaiyachaat_box.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if(!berry_blast_bar.equals("")&&!berry_blast_bar.equals("0")){
                llberry_blast_bar.setVisibility(View.VISIBLE);
                barflag=true;
                total6.setText(berry_blast_bar);
                if(countbar>0){v6.setVisibility(View.VISIBLE);}else v6.setVisibility(View.GONE);
                countbar+=1;
            }else{
                llberry_blast_bar.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
            }
            if(!berry_blast_box.equals("")&&!berry_blast_box.equals("0")){
                llberry_blast_box.setVisibility(View.VISIBLE);
                boxflag=true;
                total13.setText(berry_blast_box);
                if(countbox>0){v13.setVisibility(View.VISIBLE);}else v13.setVisibility(View.GONE);
                countbox+=1;
            }else{
                llberry_blast_box.setVisibility(View.GONE);
                v13.setVisibility(View.GONE);
            }
            if(!mangoginger_bar.equals("")&&!mangoginger_bar.equals("0")){
                llmangoginger_bar.setVisibility(View.VISIBLE);
                barflag=true;
                total5.setText(mangoginger_bar);
                if(countbar>0){v5.setVisibility(View.VISIBLE);}else v5.setVisibility(View.GONE);
                countbar+=1;
            }else{
                llmangoginger_bar.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
            }
            if(!mangoginger_box.equals("")&&!mangoginger_box.equals("0")){
                llmangoginger_box.setVisibility(View.VISIBLE);
                boxflag=true;
                total12.setText(mangoginger_box);
                if(countbox>0){v12.setVisibility(View.VISIBLE);}else v12.setVisibility(View.GONE);
                countbox+=1;
            }else{
                llmangoginger_box.setVisibility(View.GONE);
                v12.setVisibility(View.GONE);
            }
            if(!chyawanprash_bar.equals("")&&!chyawanprash_bar.equals("0")){
                llchyawanprash_bar.setVisibility(View.VISIBLE);
                barflag=true;
                total7.setText(chyawanprash_bar);
                if(countbar>0){v7.setVisibility(View.VISIBLE);}else v7.setVisibility(View.GONE);
                countbar+=1;
            }else{
                llchyawanprash_bar.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
            }
            if(!chyawanprash_box.equals("")&&!chyawanprash_box.equals("0")){
                llchyawanprash_box.setVisibility(View.VISIBLE);
                boxflag=true;
                total14.setText(chyawanprash_box);
                if(countbox>0){v14.setVisibility(View.VISIBLE);}else v14.setVisibility(View.GONE);
                countbox+=1;
            }else{
                llchyawanprash_box.setVisibility(View.GONE);
                v14.setVisibility(View.GONE);

            }
            if(!variety_box.equals("")&&!variety_box.equals("0")){
                llvariety_box.setVisibility(View.VISIBLE);
                boxflag=true;
                total15.setText(variety_box);
                if(countbox>0){v15.setVisibility(View.VISIBLE);}else v15.setVisibility(View.GONE);
                countbox+=1;
            }else{
                llvariety_box.setVisibility(View.GONE);
                v15.setVisibility(View.GONE);
            }
            if(!chocolate_cookies.equals("")&&!chocolate_cookies.equals("0")){
                llchocolate_cookies.setVisibility(View.VISIBLE);
                cookiesflag=true;
                total16.setText(chocolate_cookies);
                countcookies+=1;
            }else{
                llchocolate_cookies.setVisibility(View.GONE);
            }
            if(!dark_chocolate_cookies.equals("")&&!dark_chocolate_cookies.equals("0")){
                lldark_chocolate_cookies.setVisibility(View.VISIBLE);
                cookiesflag=true;
                total17.setText(dark_chocolate_cookies);
                if(countcookies>0){v17.setVisibility(View.VISIBLE);}else v17.setVisibility(View.GONE);
                countcookies+=1;
            }else{
                lldark_chocolate_cookies.setVisibility(View.GONE);
                v17.setVisibility(View.GONE);
            }
            if(!cranberry_cookies.equals("")&&!cranberry_cookies.equals("0")){
                llcranberry_cookies.setVisibility(View.VISIBLE);
                cookiesflag=true;
                total18.setText(cranberry_cookies);
                if(countcookies>0){v18.setVisibility(View.VISIBLE);}else v18.setVisibility(View.GONE);
                countcookies+=1;
            }else{
                llcranberry_cookies.setVisibility(View.GONE);
                v18.setVisibility(View.GONE);
            }
            if(!cranberry_orange_zest.equals("")&&!cranberry_orange_zest.equals("0")){
                llcranberry_orange_zest.setVisibility(View.VISIBLE);
                trailmixflag=true;
                total19.setText(cranberry_orange_zest);
                counttrailmix+=1;
            }else{
                llcranberry_orange_zest.setVisibility(View.GONE);
            }
            if(!fig_raisins.equals("")&&!fig_raisins.equals("0")){
                llfig_raisins.setVisibility(View.VISIBLE);
                trailmixflag=true;
                total20.setText(fig_raisins);
                if(counttrailmix>0){v20.setVisibility(View.VISIBLE);}else v20.setVisibility(View.GONE);
                counttrailmix+=1;
            }else{
                llfig_raisins.setVisibility(View.GONE);
                v20.setVisibility(View.GONE);
            }
            if(!papaya_pineapple.equals("")&&!papaya_pineapple.equals("0")){
                llpapaya_pineapple.setVisibility(View.VISIBLE);
                trailmixflag=true;
                total21.setText(papaya_pineapple);
                if(counttrailmix>0){v21.setVisibility(View.VISIBLE);}else v21.setVisibility(View.GONE);
                counttrailmix+=1;
            }else{
                llpapaya_pineapple.setVisibility(View.GONE);
                v21.setVisibility(View.GONE);
            }

            if(barflag){
                allBars.setVisibility(View.VISIBLE);
                llBars.setVisibility(View.VISIBLE);
            }else{
                allBars.setVisibility(View.GONE);
                llBars.setVisibility(View.GONE);
            }
            if(boxflag){
                allBox.setVisibility(View.VISIBLE);
                llBox.setVisibility(View.VISIBLE);
            }else{
                allBox.setVisibility(View.GONE);
                llBox.setVisibility(View.GONE);
            }
            if(cookiesflag){
                allCookies.setVisibility(View.VISIBLE);
                llCookies.setVisibility(View.VISIBLE);
            }else{
                allCookies.setVisibility(View.GONE);
                llCookies.setVisibility(View.GONE);
            }
            if(trailmixflag){
                allTrailmix.setVisibility(View.VISIBLE);
                llTrailmix.setVisibility(View.VISIBLE);
            }else{
                allTrailmix.setVisibility(View.GONE);
                llTrailmix.setVisibility(View.GONE);
            }




            //Log.e("batch details", countbar+" "+countbox+" "+countcookies+" "+counttrailmix);


        }catch(Exception e){
            Log.e("batch details", "1 "+e.toString());
        }


    }

    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.inc_btn1:
                if(!total1.getText().toString().equals("")){
                    String value= total1.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total1.setText(newVal);
                }else{
                    total1.setText("1");
                }
                break;
            case R.id.inc_btn2:
                if(!total2.getText().toString().equals("")){
                    String value= total2.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total2.setText(newVal);
                }else{
                    total2.setText("1");
                }
                break;
            case R.id.inc_btn3:
                if(!total3.getText().toString().equals("")){
                    String value= total3.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total3.setText(newVal);
                }else{
                    total3.setText("1");
                }
                break;
            case R.id.inc_btn4:
                if(!total4.getText().toString().equals("")){
                    String value= total4.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total4.setText(newVal);
                }else{
                    total4.setText("1");
                }
                break;
            case R.id.inc_btn5:
                if(!total5.getText().toString().equals("")){
                    String value= total5.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total5.setText(newVal);
                }else{
                    total5.setText("1");
                }
                break;
            case R.id.inc_btn6:
                if(!total6.getText().toString().equals("")){
                    String value= total6.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total6.setText(newVal);
                }else{
                    total6.setText("1");
                }
                break;
            case R.id.inc_btn7:
                if(!total7.getText().toString().equals("")){
                    String value= total7.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total7.setText(newVal);
                }else{
                    total7.setText("1");
                }
                break;
            case R.id.inc_btn8:
                if(!total8.getText().toString().equals("")){
                    String value= total8.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total8.setText(newVal);
                }else{
                    total8.setText("1");
                }
                break;
            case R.id.inc_btn9:
                if(!total9.getText().toString().equals("")){
                    String value= total9.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total9.setText(newVal);
                }else{
                    total9.setText("1");
                }
                break;
            case R.id.inc_btn10:
                if(!total10.getText().toString().equals("")){
                    String value= total10.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total10.setText(newVal);
                }else{
                    total10.setText("1");
                }
                break;
            case R.id.inc_btn11:
                if(!total11.getText().toString().equals("")){
                    String value= total11.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total11.setText(newVal);
                }else{
                    total11.setText("1");
                }
                break;
            case R.id.inc_btn12:
                if(!total12.getText().toString().equals("")){
                    String value= total12.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total12.setText(newVal);
                }else{
                    total12.setText("1");
                }
                break;
            case R.id.inc_btn13:
                if(!total13.getText().toString().equals("")){
                    String value= total13.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total13.setText(newVal);
                }else{
                    total13.setText("1");
                }
                break;
            case R.id.inc_btn14:
                if(!total14.getText().toString().equals("")){
                    String value= total14.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total14.setText(newVal);
                }else{
                    total14.setText("1");
                }
                break;
            case R.id.inc_btn15:
                if(!total15.getText().toString().equals("")){
                    String value= total15.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total15.setText(newVal);
                }else{
                    total15.setText("1");
                }
                break;
            case R.id.inc_btn16:
                if(!total16.getText().toString().equals("")){
                    String value= total16.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total16.setText(newVal);
                }else{
                    total16.setText("1");
                }
                break;
            case R.id.inc_btn17:
                if(!total17.getText().toString().equals("")){
                    String value= total17.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total17.setText(newVal);
                }else{
                    total17.setText("1");
                }
                break;
            case R.id.inc_btn18:
                if(!total18.getText().toString().equals("")){
                    String value= total18.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total18.setText(newVal);
                }else{
                    total18.setText("1");
                }
                break;
            case R.id.inc_btn19:
                if(!total19.getText().toString().equals("")){
                    String value= total19.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total19.setText(newVal);
                }else{
                    total19.setText("1");
                }
                break;
            case R.id.inc_btn20:
                if(!total20.getText().toString().equals("")){
                    String value= total20.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total20.setText(newVal);
                }else{
                    total20.setText("1");
                }
                break;
            case R.id.inc_btn21:
                if(!total21.getText().toString().equals("")){
                    String value= total21.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    finalValue+=1;
                    String newVal=finalValue+"";
                    total21.setText(newVal);
                }else{
                    total21.setText("1");
                }
                break;
        }}

    public void buttonClick1(View view) {
        String val="";
        int total=0;
        switch (view.getId()) {
            case R.id.inc_ind1:
                val=total1.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind1.getText().toString().equals("")){
                    String value= ind1.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                    finalValue+=1;
                    String newVal=finalValue+"";
                    ind1.setText(newVal);}
                    else if(total==0)
                    {ind1.setText("0");}
                    else{}
                }else{
                    ind1.setText("1");
                }
                break;
            case R.id.inc_ind2:
                val=total2.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind2.getText().toString().equals("")){
                    String value= ind2.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind2.setText(newVal);}else if(total==0){ind2.setText("0");}else{}
                }else{
                    ind2.setText("1");
                }
                break;
            case R.id.inc_ind3:
                val=total3.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind3.getText().toString().equals("")){
                    String value= ind3.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind3.setText(newVal);}else if(total==0){ind3.setText("0");}else{}
                }else{
                    ind3.setText("1");
                }
                break;
            case R.id.inc_ind4:
                val=total4.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind4.getText().toString().equals("")){
                    String value= ind4.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind4.setText(newVal);}else if(total==0){ind4.setText("0");}else{}
                }else{
                    ind4.setText("1");
                }
                break;
            case R.id.inc_ind5:
                val=total5.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind5.getText().toString().equals("")){
                    String value= ind5.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind5.setText(newVal);}else if(total==0){ind5.setText("0");}else{}
                }else{
                    ind5.setText("1");
                }
                break;
            case R.id.inc_ind6:
                val=total6.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind6.getText().toString().equals("")){
                    String value= ind6.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind6.setText(newVal);}else if(total==0){ind6.setText("0");}else{}
                }else{
                    ind6.setText("1");
                }
                break;
            case R.id.inc_ind7:
                val=total7.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind7.getText().toString().equals("")){
                    String value= ind7.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind7.setText(newVal);}else if(total==0){ind7.setText("0");}else{}
                }else{
                    ind7.setText("1");
                }
                break;
            case R.id.inc_ind8:
                val=total8.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind8.getText().toString().equals("")){
                    String value= ind8.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind8.setText(newVal);}else if(total==0){ind8.setText("0");}else{}
                }else{
                    ind8.setText("1");
                }
                break;
            case R.id.inc_ind9:
                val=total9.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind9.getText().toString().equals("")){
                    String value= ind9.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind9.setText(newVal);}else if(total==0){ind9.setText("0");}else{}
                }else{
                    ind9.setText("1");
                }
                break;
            case R.id.inc_ind10:
                val=total10.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind10.getText().toString().equals("")){
                    String value= ind10.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind10.setText(newVal);}else if(total==0){ind10.setText("0");}else{}
                }else{
                    ind10.setText("1");
                }
                break;
            case R.id.inc_ind11:
                val=total11.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind11.getText().toString().equals("")){
                    String value= ind11.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind11.setText(newVal);}else if(total==0){ind11.setText("0");}else{}
                }else{
                    ind11.setText("1");
                }
                break;
            case R.id.inc_ind12:
                val=total12.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind12.getText().toString().equals("")){
                    String value= ind12.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind12.setText(newVal);}else if(total==0){ind12.setText("0");}else{}
                }else{
                    ind12.setText("1");
                }
                break;
            case R.id.inc_ind13:
                val=total13.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind13.getText().toString().equals("")){
                    String value= ind13.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind13.setText(newVal);}else if(total==0){ind13.setText("0");}else{}
                }else{
                    ind13.setText("1");
                }
                break;
            case R.id.inc_ind14:
                val=total14.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind14.getText().toString().equals("")){
                    String value= ind14.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind14.setText(newVal);}else if(total==0){ind14.setText("0");}else{}
                }else{
                    ind14.setText("1");
                }
                break;
            case R.id.inc_ind15:
                val=total15.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind15.getText().toString().equals("")){
                    String value= ind15.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind15.setText(newVal);}else if(total==0){ind15.setText("0");}else{}
                }else{
                    ind15.setText("1");
                }
                break;
            case R.id.inc_ind16:
                val=total16.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind16.getText().toString().equals("")){
                    String value= ind16.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind16.setText(newVal);}else if(total==0){ind16.setText("0");}else{}
                }else{
                    ind16.setText("1");
                }
                break;
            case R.id.inc_ind17:
                val=total17.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind17.getText().toString().equals("")){
                    String value= ind17.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind17.setText(newVal);}else if(total==0){ind17.setText("0");}else{}
                }else{
                    ind17.setText("1");
                }
                break;
            case R.id.inc_ind18:
                val=total18.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind18.getText().toString().equals("")){
                    String value= ind18.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind18.setText(newVal);}else if(total==0){ind18.setText("0");}else{}
                }else{
                    ind18.setText("1");
                }
                break;
            case R.id.inc_ind19:
                val=total19.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind19.getText().toString().equals("")){
                    String value= ind19.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind19.setText(newVal);}else if(total==0){ind19.setText("0");}else{}
                }else{
                    ind19.setText("1");
                }
                break;
            case R.id.inc_ind20:
                val=total20.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind20.getText().toString().equals("")){
                    String value= ind20.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind20.setText(newVal);}else if(total==0){ind20.setText("0");}else{}
                }else{
                    ind20.setText("1");
                }
                break;
            case R.id.inc_ind21:
                val=total21.getText().toString();
                if(!val.equals("")){
                    total=Integer.parseInt(val);
                }else{total=0;}
                if(!ind21.getText().toString().equals("")){
                    String value= ind21.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    if(finalValue<total){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind21.setText(newVal);}else if(total==0){ind21.setText("0");}else{}
                }else{
                    ind21.setText("1");
                }
                break;
        }}

    public void buttonClick2(View view) {
        String value="";
        switch (view.getId()) {
            case R.id.dec_btn1:
                 value= total1.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total1.setText(newVal);
                    }
                    else {total1.setText("0");}
                }else {
                    total1.setError("Required");
                    focusOnViewEdit(total1);
                }
                break;
            case R.id.dec_btn2:
                 value= total2.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total2.setText(newVal);
                    }
                    else {total2.setText("0");}
                }else {total2.setError("Required");focusOnViewEdit(total2);}
                break;
            case R.id.dec_btn3:
                 value= total3.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total3.setText(newVal);
                    }
                    else {total3.setText("0");}
                }else {total3.setError("Required");focusOnViewEdit(total3);}
                break;
            case R.id.dec_btn4:
                 value= total4.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total4.setText(newVal);
                    }
                    else {total4.setText("0");}
                }else {total4.setError("Required");focusOnViewEdit(total4);}
                break;
            case R.id.dec_btn5:
                 value= total5.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total5.setText(newVal);
                    }
                    else {total5.setText("0");}
                }else {total5.setError("Required");focusOnViewEdit(total5);}
                break;
            case R.id.dec_btn6:
                 value= total6.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total6.setText(newVal);
                    }
                    else {total6.setText("0");}
                }else {total6.setError("Required");focusOnViewEdit(total6);}
                break;
            case R.id.dec_btn7:
                 value= total7.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total7.setText(newVal);
                    }
                    else {total7.setText("0");}
                }else {total7.setError("Required");focusOnViewEdit(total7);}
                break;
            case R.id.dec_btn8:
                 value= total8.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total8.setText(newVal);
                    }
                    else {total8.setText("0");}
                }else {total8.setError("Required");focusOnViewEdit(total8);}
                break;
            case R.id.dec_btn9:
                 value= total9.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total9.setText(newVal);
                    }
                    else {total9.setText("0");}
                }else {total9.setError("Required");focusOnViewEdit(total9);}
                break;
            case R.id.dec_btn10:
                value= total10.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total10.setText(newVal);
                    }
                    else {total10.setText("0");}
                }else {total10.setError("Required");focusOnViewEdit(total10);}
                break;
            case R.id.dec_btn11:
                value= total11.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total11.setText(newVal);
                    }
                    else {total11.setText("0");}
                }else {total11.setError("Required");focusOnViewEdit(total11);}
                break;
            case R.id.dec_btn12:
                value= total12.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total12.setText(newVal);
                    }
                    else {total12.setText("0");}
                }else total12.setError("Required");
                break;
            case R.id.dec_btn13:
                value= total13.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total13.setText(newVal);
                    }
                    else {total13.setText("0");}
                }else {total13.setError("Required");focusOnViewEdit(total13);}
                break;
            case R.id.dec_btn14:
                value= total14.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total14.setText(newVal);
                    }
                    else {total14.setText("0");}
                }else {total14.setError("Required");focusOnViewEdit(total14);}
                break;
            case R.id.dec_btn15:
                value= total15.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total15.setText(newVal);
                    }
                    else {total15.setText("0");}
                }else {total15.setError("Required");focusOnViewEdit(total15);}
                break;
            case R.id.dec_btn16:
                value= total16.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total16.setText(newVal);
                    }
                    else {total16.setText("0");}
                }else {total16.setError("Required");focusOnViewEdit(total16);}
                break;
            case R.id.dec_btn17:
                value= total17.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total17.setText(newVal);
                    }
                    else {total17.setText("0");}
                }else {total17.setError("Required");focusOnViewEdit(total17);}
                break;
            case R.id.dec_btn18:
                value= total18.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total18.setText(newVal);
                    }
                    else {total18.setText("0");}
                }else {total18.setError("Required");focusOnViewEdit(total18);}
                break;
            case R.id.dec_btn19:
                value= total19.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total19.setText(newVal);
                    }
                    else {total19.setText("0");}
                }else {total19.setError("Required");focusOnViewEdit(total19);}
                break;
            case R.id.dec_btn20:
                value= total20.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total20.setText(newVal);
                    }
                    else {total20.setText("0");}
                }else {total20.setError("Required");focusOnViewEdit(total20);}
                break;
            case R.id.dec_btn21:
                value= total21.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        total21.setText(newVal);
                    }
                    else {total21.setText("0");}
                }else {total21.setError("Required");focusOnViewEdit(total21);}
                break;
        }}

    public void buttonClick3(View view) {
        String value="";
        switch (view.getId()) {
            case R.id.dec_ind1:
                value= ind1.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind1.setText(newVal);
                    }
                    else {ind1.setText("0");}
                }else {ind1.setError("Required");focusOnViewEdit(ind1);}
                break;
            case R.id.dec_ind2:
                value= ind2.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind2.setText(newVal);
                    }
                    else {ind2.setText("0");}
                }else {ind2.setError("Required");focusOnViewEdit(ind2);}
                break;
            case R.id.dec_ind3:
                value= ind3.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind3.setText(newVal);
                    }
                    else {ind3.setText("0");}
                }else {ind3.setError("Required");focusOnViewEdit(ind3);}
                break;
            case R.id.dec_ind4:
                value= ind4.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind4.setText(newVal);
                    }
                    else {ind4.setText("0");}
                }else {ind4.setError("Required");focusOnViewEdit(ind4);}
                break;
            case R.id.dec_ind5:
                value= ind5.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind5.setText(newVal);
                    }
                    else {ind5.setText("0");}
                }else {ind5.setError("Required");focusOnViewEdit(ind5);}
                break;
            case R.id.dec_ind6:
                value= ind6.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind6.setText(newVal);
                    }
                    else {ind6.setText("0");}
                }else {ind6.setError("Required");focusOnViewEdit(ind6);}
                break;
            case R.id.dec_ind7:
                value= ind7.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind7.setText(newVal);
                    }
                    else {ind7.setText("0");}
                }else {ind7.setError("Required");focusOnViewEdit(ind7);}
                break;
            case R.id.dec_ind8:
                value= ind8.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind8.setText(newVal);
                    }
                    else {ind8.setText("0");}
                }else {ind8.setError("Required");focusOnViewEdit(ind8);}
                break;
            case R.id.dec_ind9:
                value= ind9.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind9.setText(newVal);
                    }
                    else {ind9.setText("0");}
                }else {ind9.setError("Required");focusOnViewEdit(ind9);}
                break;
            case R.id.dec_ind10:
                value= ind10.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind10.setText(newVal);
                    }
                    else {ind10.setText("0");}
                }else {ind10.setError("Required");focusOnViewEdit(ind10);}
                break;
            case R.id.dec_ind11:
                value= ind11.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind11.setText(newVal);
                    }
                    else {ind11.setText("0");}
                }else {ind11.setError("Required");focusOnViewEdit(ind11);}
                break;
            case R.id.dec_ind12:
                value= ind12.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind12.setText(newVal);
                    }
                    else {ind12.setText("0");}
                }else {ind12.setError("Required");focusOnViewEdit(ind12);}
                break;
            case R.id.dec_ind13:
                value= ind13.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind13.setText(newVal);
                    }
                    else {ind13.setText("0");}
                }else {total13.setError("Required");focusOnViewEdit(ind13);}
                break;
            case R.id.dec_ind14:
                value= ind14.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind14.setText(newVal);
                    }
                    else {ind14.setText("0");}
                }else {ind14.setError("Required");focusOnViewEdit(ind14);}
                break;
            case R.id.dec_ind15:
                value= ind15.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind15.setText(newVal);
                    }
                    else {ind15.setText("0");}
                }else {ind15.setError("Required");focusOnViewEdit(ind15);}
                break;
            case R.id.dec_ind16:
                value= ind16.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind16.setText(newVal);
                    }
                    else {ind16.setText("0");}
                }else {ind16.setError("Required");focusOnViewEdit(ind16);}
                break;
            case R.id.dec_ind17:
                value= ind17.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind17.setText(newVal);
                    }
                    else {ind17.setText("0");}
                }else {ind17.setError("Required");focusOnViewEdit(ind17);}
                break;
            case R.id.dec_ind18:
                value= ind18.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind18.setText(newVal);
                    }
                    else {ind18.setText("0");}
                }else {ind18.setError("Required");focusOnViewEdit(ind18);}
                break;
            case R.id.dec_ind19:
                value= ind19.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind19.setText(newVal);
                    }
                    else {ind19.setText("0");}
                }else {ind19.setError("Required");focusOnViewEdit(ind19);}
                break;
            case R.id.dec_ind20:
                value= ind20.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind20.setText(newVal);
                    }
                    else {ind20.setText("0");}
                }else {ind20.setError("Required");focusOnViewEdit(ind20);}
                break;
            case R.id.dec_ind21:
                value= ind21.getText().toString();
                if(!value.equals("")) {
                    int finalValue = Integer.parseInt(value);
                    if(finalValue>0) {finalValue-=1;
                        String newVal=finalValue+"";
                        ind21.setText(newVal);
                    }
                    else {ind21.setText("0");}
                }else {ind21.setError("Required");focusOnViewEdit(ind21);}
                break;
        }}

    public void add_batch(int number, int spinnerVal, String editVal) throws UnsupportedEncodingException, JSONException {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.view_button_add, null);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) rowView);
        // Add the new row before the add field button.
        Button inc, dec;
        final EditText ind_qty;
        Spinner batch_no;
        inc=rowView.findViewById(R.id.inc_btn_batch);
        dec=rowView.findViewById(R.id.dec_btn_batch);
        ind_qty=rowView.findViewById(R.id.ind_qty);
        batch_no=rowView.findViewById(R.id.spinner_batch_no);
        ind_qty.setText(editVal);

        String response=globalResponse;

        final ArrayList<StringWithTag4> mylist_batchno = new ArrayList<StringWithTag4>();
        //JSONObject obj = new JSONObject(responsetext);
        HashMap<Integer, String> list_batchno = new HashMap<Integer, String>();

        JSONArray data = new JSONArray(response);
        //list_relation.put(0, "Select");
        //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
        for (int k = 0; k < data.length(); k++) {
            JSONObject data1 = data.getJSONObject(k);
            String batch_no1 = data1.getString("batch_no");
            //mylist.add(distributor_name);
            list_batchno.put(k, batch_no1);
        }

        mylist_batchno.add(new StringWithTag4("Select", 0));

        for (Map.Entry<Integer, String> entry : list_batchno.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();

            /* Build the StringWithTag List using these keys and values. */
            mylist_batchno.add(new StringWithTag4(value, key));
        }
        // Do nothing but close the dialog


        ArrayAdapter<StringWithTag4> adapter_batchno = new ArrayAdapter<StringWithTag4>(context, android.R.layout.simple_spinner_item, mylist_batchno){
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View v = (TextView) super.getView(position, convertView, parent);
                Typeface tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                ((TextView)v).setTypeface(tfavv);
                v.setPadding(5,10,5,10);
                //v.setTextColor(Color.RED);
                //v.setTextSize(35);
                return v;
            }


            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View v = (TextView) super.getView(position, convertView, parent);
                Typeface tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                ((TextView)v).setTypeface(tfavv);
                v.setPadding(5,10,5,10);
                //v.setTextColor(Color.RED);
                //v.setTextSize(35);
                return v;
            }
        };
        adapter_batchno.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        batch_no.setAdapter(adapter_batchno);
        batch_no.setSelection(spinnerVal);

        String total="";

        final int[] val = {0};

        Button buttonRemove;
        buttonRemove = (Button)rowView.findViewById(R.id.delete_btn_batch);
        final View.OnClickListener thisListener1 = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((LinearLayout)rowView.getParent()).removeView(rowView);
            }
        };
        buttonRemove.setOnClickListener(thisListener1);


        switch(number){
            case 1:
                llorange_bar.addView(rowView, llorange_bar.getChildCount()-1);
                break;
            case 2:
                llbutterscotch_bar.addView(rowView, llbutterscotch_bar.getChildCount()-1);
                break;
            case 3:
                llchocopeanut_bar.addView(rowView, llchocopeanut_bar.getChildCount()-1);
                break;
            case 4:
                llbambaiyachaat_bar.addView(rowView, llbambaiyachaat_bar.getChildCount()-1);
                break;
            case 5:
                llmangoginger_bar.addView(rowView, llmangoginger_bar.getChildCount()-1);
                break;
            case 6:
                llberry_blast_bar.addView(rowView, llberry_blast_bar.getChildCount()-1);
                break;
            case 7:
                llchyawanprash_bar.addView(rowView, llchyawanprash_bar.getChildCount()-1);
                break;
            case 8:
                llorange_box.addView(rowView, llorange_box.getChildCount()-1);
                break;
            case 9:
                llbutterscotch_box.addView(rowView, llbutterscotch_box.getChildCount()-1);
                break;
            case 10:
                llchocopeanut_box.addView(rowView, llchocopeanut_box.getChildCount()-1);
                break;
            case 11:
                llbambaiyachaat_box.addView(rowView, llbambaiyachaat_box.getChildCount()-1);
                break;
            case 12:
                llmangoginger_box.addView(rowView, llmangoginger_box.getChildCount()-1);
                break;
            case 13:
                llberry_blast_box.addView(rowView, llberry_blast_box.getChildCount()-1);
                break;
            case 14:
                llchyawanprash_box.addView(rowView, llchyawanprash_box.getChildCount()-1);
                break;
            case 15:
                llvariety_box.addView(rowView, llvariety_box.getChildCount()-1);
                break;
            case 16:
                llchocolate_cookies.addView(rowView, llchocolate_cookies.getChildCount()-1);
                break;
            case 17:
                lldark_chocolate_cookies.addView(rowView, lldark_chocolate_cookies.getChildCount()-1);
                break;
            case 18:
                llcranberry_cookies.addView(rowView, llcranberry_cookies.getChildCount()-1);
                break;
            case 19:
                llcranberry_orange_zest.addView(rowView, llcranberry_orange_zest.getChildCount()-1);
                break;
            case 20:
                llfig_raisins.addView(rowView, llfig_raisins.getChildCount()-1);
                break;
            case 21:
                llpapaya_pineapple.addView(rowView, llpapaya_pineapple.getChildCount()-1);
                break;
        }


        final View.OnClickListener inclistener= new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    String finalTotal="";
                    if(rowView.getParent().toString().contains("orange_container")){
                        finalTotal=total1.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("butterscotch_bar")){
                        finalTotal=total2.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("chocopeanut_bar_container")){
                        finalTotal=total3.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("chaat_container")){
                        finalTotal=total4.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("mangoginger_bar_container")){
                        finalTotal=total5.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("berry_blast_bar_container")){
                        finalTotal=total6.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("chyawanprash_bar_container")){
                        finalTotal=total7.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("orange_box_container")){
                        finalTotal=total8.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("butterscotch_box_container")){
                        finalTotal=total9.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("choco_box_container")){
                        finalTotal=total10.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("chaat_box_container")){
                        finalTotal=total11.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("mango_box_container")){
                        finalTotal=total12.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("berry_blast_box_container")){
                        finalTotal=total13.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("chyawanprash_box_container")){
                        finalTotal=total14.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("variety_box_container")){
                        finalTotal=total15.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("chocolate_cookies_container")){
                        finalTotal=total16.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("dark_chocolate_cookies_container")){
                        finalTotal=total17.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("cranberry_cookies_container")){
                        finalTotal=total18.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("cranberry_orange_zest_container")){
                        finalTotal=total19.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("fig_raisins_container")){
                        finalTotal=total20.getText().toString();
                    }
                    if(rowView.getParent().toString().contains("papaya_pineapple_container")){
                        finalTotal=total21.getText().toString();
                    }
                    if(!finalTotal.equals("")){
                        val[0] =Integer.parseInt(finalTotal);}
                    if(!ind_qty.getText().toString().equals("")){
                        String value= ind_qty.getText().toString();
                        int finalValue=Integer.parseInt(value);
                        if(finalValue<val[0]){
                        finalValue+=1;
                        String newVal=finalValue+"";
                        ind_qty.setText(newVal);}else if(val[0]==0){ind_qty.setText("0");}else{}
                    }else{
                        ind_qty.setText("1");
                    }
                }catch (Exception e){
                    Log.e("batch details", "2 "+e.toString());
                }
            }
        };
        final View.OnClickListener declistener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    String value= ind_qty.getText().toString();
                    if(!value.equals("")) {
                        int finalValue = Integer.parseInt(value);
                        if(finalValue>0) {finalValue-=1;
                            String newVal=finalValue+"";
                            ind_qty.setText(newVal);
                        }
                        else {ind_qty.setText("0");}
                    }else {ind_qty.setError("Required");focusOnViewEdit(ind_qty);}

                }catch (Exception e){
                    Log.e("batch details", "3 "+e.toString());
                }
            }
        };


        inc.setOnClickListener(inclistener);
        dec.setOnClickListener(declistener);


    }

    @Override
    public void onBackPressed(){
        try{

            if(backflag==0){super.onBackPressed();}
            else if(backflag==1){
                OrdersData od=new OrdersData();
                od.clear();
                super.onBackPressed();
            }else {
                int flag=0;
                String total="0";
                if(llorange_bar.getVisibility()==View.VISIBLE){
                    int num=0;
                    error1.setVisibility(View.GONE);
                    total=total1.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llorange_bar.getChildCount();
                    View firstView=llorange_bar.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.orange_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error1.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required" ,error1);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind1);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llorange_bar.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error1.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required", error1);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                        ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error1.setVisibility(View.VISIBLE);
                        error1.setText(msg);
                        flag=1;}
                    String orange_bar_json=ob1.toString();
                    OrdersData.orange_bar_json=orange_bar_json;
                }

                if(llbutterscotch_bar.getVisibility()==View.VISIBLE){
                    int num=0;
                    error2.setVisibility(View.GONE);
                    total=total2.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llbutterscotch_bar.getChildCount();
                    View firstView=llbutterscotch_bar.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.butterscotch_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error2.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error2);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind2);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llbutterscotch_bar.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error2.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error2);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error2.setVisibility(View.VISIBLE);
                        error2.setText(msg);
                        flag=1;}
                    String butterscotch_bar_json=ob1.toString();
                    OrdersData.butterscotch_bar_json=butterscotch_bar_json;
                }

                if(llchocopeanut_bar.getVisibility()==View.VISIBLE){
                    int num=0;
                    total=total3.getText().toString();
                    error3.setVisibility(View.GONE);
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llchocopeanut_bar.getChildCount();
                    View firstView=llchocopeanut_bar.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.choco_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error3.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error3);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind3);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llchocopeanut_bar.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error3.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error3);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error3.setVisibility(View.VISIBLE);
                        error3.setText(msg);
                        flag=1;}
                    String choco_bar_json=ob1.toString();
                    OrdersData.chocopeanut_bar_json=choco_bar_json;
                }

                if(llbambaiyachaat_bar.getVisibility()==View.VISIBLE){
                    int num=0;
                    error4.setVisibility(View.GONE);
                    total=total4.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llbambaiyachaat_bar.getChildCount();
                    View firstView=llbambaiyachaat_bar.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.chaat_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error4.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error4);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind4);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llbambaiyachaat_bar.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error4.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error4);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error4.setVisibility(View.VISIBLE);
                        error4.setText(msg);
                        flag=1;}
                    String chaat_bar_json=ob1.toString();
                    OrdersData.bambaiyachaat_bar_json=chaat_bar_json;
                }

                if(llmangoginger_bar.getVisibility()==View.VISIBLE){
                    int num=0;
                    error5.setVisibility(View.GONE);
                    total=total5.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llmangoginger_bar.getChildCount();
                    View firstView=llmangoginger_bar.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.mango_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error5.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error5);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind5);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llmangoginger_bar.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error5.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error5);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error5.setVisibility(View.VISIBLE);
                        error5.setText(msg);
                        flag=1;}
                    String mango_bar_json=ob1.toString();
                    OrdersData.mangoginger_bar_json=mango_bar_json;
                }

                if(llberry_blast_bar.getVisibility()==View.VISIBLE){
                    int num=0;
                    error6.setVisibility(View.GONE);
                    total=total6.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llberry_blast_bar.getChildCount();
                    View firstView=llberry_blast_bar.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.berry_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error6.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error6);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind6);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llberry_blast_bar.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error6.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error6);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error6.setVisibility(View.VISIBLE);
                        error6.setText(msg);
                        flag=1;}
                    String berry_jason=ob1.toString();
                    OrdersData.berry_blast_bar_json=berry_jason;
                }

                if(llchyawanprash_bar.getVisibility()==View.VISIBLE){
                    int num=0;
                    error7.setVisibility(View.GONE);
                    total=total7.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llchyawanprash_bar.getChildCount();
                    View firstView=llchyawanprash_bar.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.chyawanprash_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error7.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error7);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind7);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llchyawanprash_bar.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error7.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error7);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error7.setVisibility(View.VISIBLE);
                        error7.setText(msg);
                        flag=1;}
                    String chyawanprash_bar_json=ob1.toString();
                    OrdersData.chyawanprash_bar_json=chyawanprash_bar_json;
                }

                if(llorange_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error8.setVisibility(View.GONE);
                    total=total8.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llorange_box.getChildCount();
                    View firstView=llorange_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.orange_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error8.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error8);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind8);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llorange_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error8.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error8);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error8.setVisibility(View.VISIBLE);
                        error8.setText(msg);
                        flag=1;}
                    String orange_box_json=ob1.toString();
                    OrdersData.orange_box_json=orange_box_json;
                }

                if(llbutterscotch_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error9.setVisibility(View.GONE);
                    total=total9.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llbutterscotch_box.getChildCount();
                    View firstView=llbutterscotch_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.butterscotch_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error9.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error9);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind9);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llbutterscotch_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error9.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error9);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error9.setVisibility(View.VISIBLE);
                        error9.setText(msg);
                        flag=1;}
                    String butterscotch_box_json=ob1.toString();
                    OrdersData.butterscotch_box_json=butterscotch_box_json;
                }

                if(llchocopeanut_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error10.setVisibility(View.GONE);
                    total=total10.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llchocopeanut_box.getChildCount();
                    View firstView=llchocopeanut_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.choco_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error10.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error10);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind10);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llchocopeanut_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error10.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error10);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error10.setVisibility(View.VISIBLE);
                        error10.setText(msg);
                        flag=1;}
                    String choco_box_json=ob1.toString();
                    OrdersData.chocopeanut_box_json=choco_box_json;
                }

                if(llbambaiyachaat_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error11.setVisibility(View.GONE);
                    total=total11.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llbambaiyachaat_box.getChildCount();
                    View firstView=llbambaiyachaat_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.chaat_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error11.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error11);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind11);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llbambaiyachaat_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error11.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error11);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error11.setVisibility(View.VISIBLE);
                        error11.setText(msg);
                        flag=1;}
                    String chaat_box_json=ob1.toString();
                    OrdersData.bambaiyachaat_box_json=chaat_box_json;
                }

                if(llmangoginger_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error12.setVisibility(View.GONE);
                    total=total12.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llmangoginger_box.getChildCount();
                    View firstView=llmangoginger_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.mango_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error12.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error12);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind12);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llmangoginger_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error12.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error12);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error12.setVisibility(View.VISIBLE);
                        error12.setText(msg);
                        flag=1;}
                    String mango_box_json=ob1.toString();
                    OrdersData.mangoginger_box_json=mango_box_json;
                }

                if(llberry_blast_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error13.setVisibility(View.GONE);
                    total=total13.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llberry_blast_box.getChildCount();
                    View firstView=llberry_blast_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.berry_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error13.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error13);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind13);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llberry_blast_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error13.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error13);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error13.setVisibility(View.VISIBLE);
                        error13.setText(msg);
                        flag=1;}
                    String berry_box_json=ob1.toString();
                    OrdersData.berry_blast_box_json=berry_box_json;
                }

                if(llchyawanprash_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error14.setVisibility(View.GONE);
                    total=total14.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llchyawanprash_box.getChildCount();
                    View firstView=llchyawanprash_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.chyawanprash_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error14.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error14);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind14);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llchyawanprash_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error14.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error14);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error14.setVisibility(View.VISIBLE);
                        error14.setText(msg);
                        flag=1;}
                    String chyawanprash_box_json=ob1.toString();
                    OrdersData.chyawanprash_box_json=chyawanprash_box_json;
                }

                if(llvariety_box.getVisibility()==View.VISIBLE){
                    int num=0;
                    error15.setVisibility(View.GONE);
                    total=total15.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llvariety_box.getChildCount();
                    View firstView=llvariety_box.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.variety_box_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error15.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error15);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind15);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llvariety_box.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error15.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error21);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error15.setVisibility(View.VISIBLE);
                        error15.setText(msg);
                        flag=1;}
                    String variety_json=ob1.toString();
                    OrdersData.variety_box_json=variety_json;
                }

                if(llchocolate_cookies.getVisibility()==View.VISIBLE){
                    int num=0;
                    error16.setVisibility(View.GONE);
                    total=total16.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llchocolate_cookies.getChildCount();
                    View firstView=llchocolate_cookies.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.chocolate_cookies_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error16.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error16);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind16);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=llchocolate_cookies.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error16.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error16);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error16.setVisibility(View.VISIBLE);
                        error16.setText(msg);
                        flag=1;}
                    String chocolate_json=ob1.toString();
                    OrdersData.chocolate_cookies_json=chocolate_json;
                }

                if(lldark_chocolate_cookies.getVisibility()==View.VISIBLE){
                    int num=0;
                    error17.setVisibility(View.GONE);
                    total=total17.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=lldark_chocolate_cookies.getChildCount();
                    View firstView=lldark_chocolate_cookies.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.dark_chocolate_cookies_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error17.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error17);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind17);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}
                    for(int i=3;i<count-1;i++){
                        View childView=lldark_chocolate_cookies.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error17.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error17);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error17.setVisibility(View.VISIBLE);
                        error17.setText(msg);
                        flag=1;}
                    String dark_choco_json=ob1.toString();
                    OrdersData.dark_chocolate_cookies_json=dark_choco_json;
                }

                if(llcranberry_cookies.getVisibility()==View.VISIBLE){
                    int num=0;
                    error18.setVisibility(View.GONE);
                    total=total18.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llcranberry_cookies.getChildCount();
                    View firstView=llcranberry_cookies.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.cranberry_cookies_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error18.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error18);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind18);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}                    for(int i=3;i<count-1;i++){
                        View childView=llcranberry_cookies.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error18.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error18);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error18.setVisibility(View.VISIBLE);
                        error18.setText(msg);
                        flag=1;}
                    String cranberry_json=ob1.toString();
                    OrdersData.cranberry_cookies_json=cranberry_json;
                }

                if(llcranberry_orange_zest.getVisibility()==View.VISIBLE){
                    int num=0;
                    error19.setVisibility(View.GONE);
                    total=total19.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llcranberry_orange_zest.getChildCount();
                    View firstView=llcranberry_orange_zest.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.cranberry_orange_zest_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error19.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error19);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind19);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}                    for(int i=3;i<count-1;i++){
                        View childView=llcranberry_orange_zest.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error19.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error19);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error19.setVisibility(View.VISIBLE);
                        error19.setText(msg);
                        flag=1;}
                    String cranberry_irange_json=ob1.toString();
                    OrdersData.cranberry_orange_json=cranberry_irange_json;
                }

                if(llfig_raisins.getVisibility()==View.VISIBLE){
                    int num=0;
                    error20.setVisibility(View.GONE);
                    total=total20.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llfig_raisins.getChildCount();
                    View firstView=llfig_raisins.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.fig_raisins_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error20.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error20);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind20);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}                    for(int i=3;i<count-1;i++){
                        View childView=llfig_raisins.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error20.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error20);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error20.setVisibility(View.VISIBLE);
                        error20.setText(msg);
                        flag=1;}
                    String figs_raisins_json=ob1.toString();
                    OrdersData.fig_raisins_json=figs_raisins_json;
                }

                if(llpapaya_pineapple.getVisibility()==View.VISIBLE){
                    int num=0;
                    error21.setVisibility(View.GONE);
                    total=total21.getText().toString();
                    int totalint=Integer.parseInt(total);
                    JSONObject ob1=new JSONObject();
                    int count=llpapaya_pineapple.getChildCount();
                    View firstView=llpapaya_pineapple.getChildAt(2);
                    Spinner firstSpinner=firstView.findViewById(R.id.papaya_pineapple_batch);
                    if (firstSpinner.getSelectedItemPosition()==0&&totalint>0){
                        error21.setVisibility(View.VISIBLE);
                        setSpinnerError(firstSpinner, "Required",error21);
                        flag=1;
                    }
                    EditText firstEdittext=firstView.findViewById(R.id.ind21);
                    if(count==4){
                        firstEdittext.setText(totalint+"");
                    }
                    num=addStringNo(num+"", firstEdittext.getText().toString());
                    if(ob1.has(firstSpinner.getSelectedItemPosition()+"")){
                        int dup=addStringNo(ob1.getString(firstSpinner.getSelectedItemPosition()+""),firstEdittext.getText().toString());
                        ob1.put(firstSpinner.getSelectedItemPosition()+"", dup+"");
                    }else{ob1.put(firstSpinner.getSelectedItemPosition()+"", firstEdittext.getText().toString());}                    for(int i=3;i<count-1;i++){
                        View childView=llpapaya_pineapple.getChildAt(i);
                        Spinner childspinner=childView.findViewById(R.id.spinner_batch_no);
                        if (childspinner.getSelectedItemPosition()==0&&totalint>0){
                            error21.setVisibility(View.VISIBLE);
                            setSpinnerError(childspinner, "Required",error21);
                            flag=1;
                        }
                        EditText childEditText=childView.findViewById(R.id.ind_qty);
                        num=addStringNo(num+"", childEditText.getText().toString());
                        if(ob1.has(childspinner.getSelectedItemPosition()+"")){
                            int dup=addStringNo(ob1.getString(childspinner.getSelectedItemPosition()+""),childEditText.getText().toString());
                            ob1.put(childspinner.getSelectedItemPosition()+"", dup+"");
                        }else{
                            ob1.put(childspinner.getSelectedItemPosition()+"", childEditText.getText().toString());}
                    }
                    if(num>totalint||num<totalint){
                        error21.setVisibility(View.VISIBLE);
                        error21.setText(msg);
                        flag=1;}
                    String figs_raisins_json=ob1.toString();
                    OrdersData.papaya_pineapple_json=figs_raisins_json;
                }

                if(flag==0){
                    OrdersData.orange_bar=total1.getText().toString();
                    OrdersData.butterscotch_bar=total2.getText().toString();
                    OrdersData.chocopeanut_bar=total3.getText().toString();
                    OrdersData.bambaiyachaat_bar=total4.getText().toString();
                    OrdersData.mangoginger_bar=total5.getText().toString();
                    OrdersData.berry_blast_bar=total6.getText().toString();
                    OrdersData.chyawanprash_bar=total7.getText().toString();
                    OrdersData.orange_box=total8.getText().toString();
                    OrdersData.butterscotch_box=total9.getText().toString();
                    OrdersData.chocopeanut_box=total10.getText().toString();
                    OrdersData.bambaiyachaat_box=total11.getText().toString();
                    OrdersData.mangoginger_box=total12.getText().toString();
                    OrdersData.berry_blast_box=total13.getText().toString();
                    OrdersData.chyawanprash_box=total14.getText().toString();
                    OrdersData.variety_box=total15.getText().toString();
                    OrdersData.chocolate_cookies_box=total16.getText().toString();
                    OrdersData.dark_chocolate_cookies_box=total17.getText().toString();
                    OrdersData.cranberry_cookies_box=total18.getText().toString();
                    OrdersData.cranberry_orange_box=total19.getText().toString();
                    OrdersData.fig_raisins_box=total20.getText().toString();
                    OrdersData.papaya_pineapple_box=total21.getText().toString();

                    super.onBackPressed();
                }
            }

        }catch (Exception e){Log.e("batch details","4 "+ e.toString());}
    }

    public int addStringNo(String val1, String val2){
        try{
            int v1=0;
            int v2=0;
            if(val1.equals("")||val2.equals("")){
            if(val1.equals("")){v1=0;}
            if(val2.equals("")){v2=0;}
            }
            else{
             v1=Integer.parseInt(val1);
             v2=Integer.parseInt(val2);
            }
            int v3=v1+v2;
            return v3;

        }catch(Exception e){
            e.printStackTrace();
            Log.e("batch details","5 " + e.toString());
        }
        return 0;
    }

    private void setSpinnerError(Spinner spinner, String error, TextView textView){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error);
            textView.setText("Enter Batch No.");
            textView.setVisibility(View.VISIBLE);
            focusOnViewSpinner(spinner);
        // actual error message
            // to open the spinner list if error is found.
        }
    }


    public String getbatchnos()  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String data1="";
        String data = URLEncoder.encode("data", "UTF-8")
                + "=" + URLEncoder.encode(data1, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_batch_details_api");

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
            Log.e("batch details", "8 "+ex.toString());
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                Log.e("batch details", "9 "+ex.toString());
            }
        }

        // Show response on activity
        return "";

    }

    private static class StringWithTag4 {
        public String string;
        public Object tag;

        public StringWithTag4(String string, Integer tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private final void focusOnViewEdit(final EditText editText){
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, editText.getBottom());
            }
        });
    }

    private final void focusOnViewSpinner(final Spinner spinner){
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, spinner.getBottom());
            }
        });
    }
}
