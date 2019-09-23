package com.eat.erp;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    int minteger=0, minteger1 = 0, minteger2 = 0, minteger3 = 0, minteger4 = 0, minteger5 = 0, minteger6 = 0, minteger7 = 0, minteger8 = 0, minteger9 = 0, minteger10 = 0, minteger11 = 0, minteger12 = 0, minteger13 = 0, minteger14 = 0, minteger15 = 0, minteger16 = 0, minteger17 = 0, minteger18 = 0, minteger19 = 0, minteger20 = 0;
    EditText txtListChild1;

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        txtListChild1 = (EditText) convertView.findViewById(R.id.lblListItem2);
        Button inc_btn = (Button) convertView.findViewById(R.id.inc_btn);
        Button dec_btn = (Button) convertView.findViewById(R.id.dec_btn);


        Typeface face= Typeface.createFromAsset(convertView.getContext().getAssets(), "AvenirNextLTPro-Regular.otf");
        Typeface face1= Typeface.createFromAsset(convertView.getContext().getAssets(), "AvenirNextLTPro-Demi.otf");
        txtListChild1.setTypeface(face);
        txtListChild.setTypeface(face1);
        txtListChild1.setTextSize(12);
        txtListChild.setTextSize(12);


        txtListChild.setText(childText);

        inc_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(groupPosition==0) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger = 1;
                        } else {
                            minteger = Integer.parseInt(et.getText().toString());
                            minteger = minteger + 1;
                        }
                        et.setText("" + minteger);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger1 = 1;
                        } else {
                            minteger1 = Integer.parseInt(et.getText().toString());
                            minteger1 = minteger1 + 1;
                        }
                        et.setText("" + minteger1);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger2 = 1;
                        } else {
                            minteger2 = Integer.parseInt(et.getText().toString());
                            minteger2 = minteger2 + 1;
                        }
                        et.setText("" + minteger2);
                    }
                    if (childPosition == 3) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger3 = 1;
                        } else {
                            minteger3 = Integer.parseInt(et.getText().toString());
                            minteger3 = minteger3 + 1;
                        }
                        et.setText("" + minteger3);
                    }
                    if (childPosition == 4) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger4 = 1;
                        } else {
                            minteger4 = Integer.parseInt(et.getText().toString());
                            minteger4 = minteger4 + 1;
                        }
                        et.setText("" + minteger4);
                    }
                    if (childPosition == 5) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger5 = 1;
                        } else {
                            minteger5 = Integer.parseInt(et.getText().toString());
                            minteger5 = minteger5 + 1;
                        }
                        et.setText("" + minteger5);
                    }
                    if (childPosition == 6) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger6 = 1;
                        } else {
                            minteger6 = Integer.parseInt(et.getText().toString());
                            minteger6 = minteger6 + 1;
                        }
                        et.setText("" + minteger6);
                    }
                }

                if(groupPosition==1) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger7 = 1;
                        }
                        else {
                            minteger7 = Integer.parseInt(et.getText().toString());
                            minteger7 = minteger7 + 1;
                        }
                        et.setText("" + minteger7);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger8 = 1;
                        }
                        else {
                            minteger8 = Integer.parseInt(et.getText().toString());
                            minteger8 = minteger8 + 1;
                        }
                        et.setText("" + minteger8);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger9 = 1;
                        }
                        else {
                            minteger9 = Integer.parseInt(et.getText().toString());
                            minteger9 = minteger9 + 1;
                        }
                        et.setText("" + minteger9);
                    }
                    if (childPosition == 3) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger10 = 1;
                        }
                        else {
                            minteger10 = Integer.parseInt(et.getText().toString());
                            minteger10 = minteger10 + 1;
                        }
                        et.setText("" + minteger10);
                    }
                    if (childPosition == 4) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger11 = 1;
                        }
                        else {
                            minteger11 = Integer.parseInt(et.getText().toString());
                            minteger11 = minteger11 + 1;
                        }
                        et.setText("" + minteger11);
                    }
                    if (childPosition == 5) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger12 = 1;
                        }
                        else {
                            minteger12 = Integer.parseInt(et.getText().toString());
                            minteger12 = minteger12 + 1;
                        }
                        et.setText("" + minteger12);
                    }
                    if (childPosition == 6) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger13 = 1;
                        }
                        else {
                            minteger13 = Integer.parseInt(et.getText().toString());
                            minteger13 = minteger13 + 1;
                        }
                        et.setText("" + minteger13);
                    }
                    if (childPosition == 7) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger14 = 1;
                        }
                        else {
                            minteger14 = Integer.parseInt(et.getText().toString());
                            minteger14 = minteger14 + 1;
                        }
                        et.setText("" + minteger14);
                    }
                }

                if(groupPosition==2) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger15 = 1;
                        } else {
                            minteger15 = Integer.parseInt(et.getText().toString());
                            minteger15 = minteger15 + 1;
                        }
                        et.setText("" + minteger15);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger16 = 1;
                        } else {
                            minteger16 = Integer.parseInt(et.getText().toString());
                            minteger16 = minteger16 + 1;
                        }
                        et.setText("" + minteger16);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger17 = 1;
                        } else {
                            minteger17 = Integer.parseInt(et.getText().toString());
                            minteger17 = minteger17 + 1;
                        }
                        et.setText("" + minteger17);
                    }
                }

                if(groupPosition==3) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger18 = 1;
                        } else {
                            minteger18 = Integer.parseInt(et.getText().toString());
                            minteger18 = minteger18 + 1;
                        }
                        et.setText("" + minteger18);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger19 = 1;
                        } else {
                            minteger19 = Integer.parseInt(et.getText().toString());
                            minteger19 = minteger19 + 1;
                        }
                        et.setText("" + minteger19);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger20 = 1;
                        } else {
                            minteger20 = Integer.parseInt(et.getText().toString());
                            minteger20 = minteger20 + 1;
                        }
                        et.setText("" + minteger20);
                    }
                }
            }
        });

        dec_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(groupPosition==0) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger = 0;
                        } else {
                            if(minteger > 0) {
                                minteger = Integer.parseInt(et.getText().toString());
                                minteger = minteger - 1;
                            }
                            else {
                                minteger = 0;
                            }
                        }
                        et.setText("" + minteger);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger1 = 1;
                        } else {
                            if(minteger1 > 0) {
                                minteger1 = Integer.parseInt(et.getText().toString());
                                minteger1 = minteger1 - 1;
                            }
                            else {
                                minteger1 = 0;
                            }
                        }
                        et.setText("" + minteger1);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger2 = 1;
                        } else {
                            if(minteger2 > 0) {
                                minteger2 = Integer.parseInt(et.getText().toString());
                                minteger2 = minteger2 - 1;
                            }
                            else {
                                minteger2 = 0;
                            }
                        }
                        et.setText("" + minteger2);
                    }
                    if (childPosition == 3) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger3 = 1;
                        } else {
                            if(minteger3 > 0) {
                                minteger3 = Integer.parseInt(et.getText().toString());
                                minteger3 = minteger3 - 1;
                            }
                            else {
                                minteger3 = 0;
                            }
                        }
                        et.setText("" + minteger3);
                    }
                    if (childPosition == 4) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger4 = 1;
                        } else {
                            if(minteger4 > 0) {
                                minteger4 = Integer.parseInt(et.getText().toString());
                                minteger4 = minteger4 - 1;
                            }
                            else {
                                minteger4 = 0;
                            }
                        }
                        et.setText("" + minteger4);
                    }
                    if (childPosition == 5) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger5 = 1;
                        } else {
                            if(minteger5 > 0) {
                                minteger5 = Integer.parseInt(et.getText().toString());
                                minteger5 = minteger5 - 1;
                            }
                            else {
                                minteger5 = 0;
                            }
                        }
                        et.setText("" + minteger5);
                    }
                    if (childPosition == 6) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger6 = 1;
                        } else {
                            if(minteger6 > 0) {
                                minteger6 = Integer.parseInt(et.getText().toString());
                                minteger6 = minteger6 - 1;
                            }
                            else {
                                minteger6 = 0;
                            }
                        }
                        et.setText("" + minteger6);
                    }
                }

                if(groupPosition==1) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger7 = 1;
                        }
                        else {
                            if(minteger7 > 0) {
                                minteger7 = Integer.parseInt(et.getText().toString());
                                minteger7 = minteger7 - 1;
                            }
                            else {
                                minteger7 = 0;
                            }
                        }
                        et.setText("" + minteger7);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger8 = 1;
                        }
                        else {
                            if(minteger8 > 0) {
                                minteger8 = Integer.parseInt(et.getText().toString());
                                minteger8 = minteger8 - 1;
                            }
                            else {
                                minteger8 = 0;
                            }
                        }
                        et.setText("" + minteger8);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger9 = 1;
                        }
                        else {
                            if(minteger9 > 0) {
                                minteger9 = Integer.parseInt(et.getText().toString());
                                minteger9 = minteger9 - 1;
                            }
                            else {
                                minteger9 = 0;
                            }
                        }
                        et.setText("" + minteger9);
                    }
                    if (childPosition == 3) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger10 = 1;
                        }
                        else {
                            if(minteger10 > 0) {
                                minteger10 = Integer.parseInt(et.getText().toString());
                                minteger10 = minteger10 - 1;
                            }
                            else {
                                minteger10 = 0;
                            }
                        }
                        et.setText("" + minteger10);
                    }
                    if (childPosition == 4) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger11 = 1;
                        }
                        else {
                            if(minteger11 > 0) {
                                minteger11 = Integer.parseInt(et.getText().toString());
                                minteger11 = minteger11 - 1;
                            }
                            else {
                                minteger11 = 0;
                            }
                        }
                        et.setText("" + minteger11);
                    }
                    if (childPosition == 5) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger12 = 1;
                        }
                        else {
                            if(minteger12 > 0) {
                                minteger12 = Integer.parseInt(et.getText().toString());
                                minteger12 = minteger12 - 1;
                            }
                            else {
                                minteger12 = 0;
                            }
                        }
                        et.setText("" + minteger12);
                    }
                    if (childPosition == 6) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger13 = 1;
                        }
                        else {
                            if(minteger13 > 0) {
                                minteger13 = Integer.parseInt(et.getText().toString());
                                minteger13 = minteger13 - 1;
                            }
                            else {
                                minteger13 = 0;
                            }
                        }
                        et.setText("" + minteger13);
                    }
                    if (childPosition == 7) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if(et.getText().toString().equals("")) {
                            minteger14 = 1;
                        }
                        else {
                            if(minteger14 > 0) {
                                minteger14 = Integer.parseInt(et.getText().toString());
                                minteger14 = minteger14 - 1;
                            }
                            else {
                                minteger14 = 0;
                            }
                        }
                        et.setText("" + minteger14);
                    }
                }

                if(groupPosition==2) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger15 = 1;
                        } else {
                            if(minteger15 > 0) {
                                minteger15 = Integer.parseInt(et.getText().toString());
                                minteger15 = minteger15 - 1;
                            }
                            else {
                                minteger15 = 0;
                            }
                        }
                        et.setText("" + minteger15);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger16 = 1;
                        } else {
                            if(minteger16 > 0) {
                                minteger16 = Integer.parseInt(et.getText().toString());
                                minteger16 = minteger16 - 1;
                            }
                            else {
                                minteger16 = 0;
                            }
                        }
                        et.setText("" + minteger16);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger17 = 1;
                        } else {
                            if(minteger17 > 0) {
                                minteger17 = Integer.parseInt(et.getText().toString());
                                minteger17 = minteger17 - 1;
                            }
                            else {
                                minteger17 = 0;
                            }
                        }
                        et.setText("" + minteger17);
                    }
                }

                if(groupPosition==3) {
                    if (childPosition == 0) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger18 = 1;
                        } else {
                            if(minteger18 > 0) {
                                minteger18 = Integer.parseInt(et.getText().toString());
                                minteger18 = minteger18 - 1;
                            }
                            else {
                                minteger18 = 0;
                            }
                        }
                        et.setText("" + minteger18);
                    }
                    if (childPosition == 1) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger19 = 1;
                        } else {
                            if(minteger19 > 0) {
                                minteger19 = Integer.parseInt(et.getText().toString());
                                minteger19 = minteger19 - 1;
                            }
                            else {
                                minteger19 = 0;
                            }
                        }
                        et.setText("" + minteger19);
                    }
                    if (childPosition == 2) {
                        final Button chkBox = (Button) v;
                        final LinearLayout tr = (LinearLayout) chkBox.getParent();
                        final EditText et = (EditText) tr.getChildAt(1);
                        if (et.getText().toString().equals("")) {
                            minteger20 = 1;
                        } else {
                            if(minteger20 > 0) {
                                minteger20 = Integer.parseInt(et.getText().toString());
                                minteger20 = minteger20 - 1;
                            }
                            else {
                                minteger20 = 0;
                            }
                        }
                        et.setText("" + minteger20);
                    }
                }
            }
        });


        txtListChild1.setHint("0");

        if(groupPosition==0) {
            if (childPosition == 0) {
                if (OrdersData.order_orange_bar != null) {
                    txtListChild1.setText(OrdersData.order_orange_bar);
                    //Toast.makeText(_context,.getText().toString(),Toast.LENGTH_LONG).show();
                }
            }
            if(childPosition==1) {
                if(OrdersData.order_butterscotch_bar!=null) {
                    txtListChild1.setText(OrdersData.order_butterscotch_bar);
                }
            }
            if(childPosition==2) {
                if(OrdersData.order_chocopeanut_bar!=null) {
                    txtListChild1.setText(OrdersData.order_chocopeanut_bar);
                }
            }
            if(childPosition==3) {
                if(OrdersData.order_bambaiyachaat_bar!=null) {
                    txtListChild1.setText(OrdersData.order_bambaiyachaat_bar);
                }
            }
            if(childPosition==4) {
                if(OrdersData.order_mangoginger_bar!=null) {
                    txtListChild1.setText(OrdersData.order_mangoginger_bar);
                }
            }
            if(childPosition==5) {
                if(OrdersData.order_berry_blast_bar!=null) {
                    txtListChild1.setText(OrdersData.order_berry_blast_bar);
                }
            }
            if(childPosition==6) {
                if(OrdersData.order_chyawanprash_bar!=null) {
                    txtListChild1.setText(OrdersData.order_chyawanprash_bar);
                }
            }
        }
        if(groupPosition==1) {
            if(childPosition==0) {
                if(OrdersData.order_orange_box!=null) {
                    txtListChild1.setText(OrdersData.order_orange_box);
                }
            }
            if(childPosition==1) {
                if(OrdersData.order_butterscotch_box!=null) {
                    txtListChild1.setText(OrdersData.order_butterscotch_box);
                }
            }
            if(childPosition==2) {
                if(OrdersData.order_chocopeanut_box!=null) {
                    txtListChild1.setText(OrdersData.order_chocopeanut_box);
                }
            }
            if(childPosition==3) {
                if(OrdersData.order_bambaiyachaat_box!=null) {
                    txtListChild1.setText(OrdersData.order_bambaiyachaat_box);
                }
            }
            if(childPosition==4) {
                if(OrdersData.order_mangoginger_box!=null) {
                    txtListChild1.setText(OrdersData.order_mangoginger_box);
                }
            }
            if(childPosition==5) {
                if(OrdersData.order_berry_blast_box!=null) {
                    txtListChild1.setText(OrdersData.order_berry_blast_box);
                }
            }
            if(childPosition==6) {
                if(OrdersData.order_chyawanprash_box!=null) {
                    txtListChild1.setText(OrdersData.order_chyawanprash_box);
                }
            }
            if(childPosition==7) {
                if(OrdersData.order_variety_box!=null) {
                    txtListChild1.setText(OrdersData.order_variety_box);
                }
            }
        }
        if(groupPosition==2) {
            if(childPosition==0) {
                if(OrdersData.order_chocolate_cookies_box!=null) {
                    txtListChild1.setText(OrdersData.order_chocolate_cookies_box);
                }
            }
            if(childPosition==1) {
                if(OrdersData.order_dark_chocolate_cookies_box!=null) {
                    txtListChild1.setText(OrdersData.order_dark_chocolate_cookies_box);
                }
            }
            if(childPosition==2) {
                if(OrdersData.order_cranberry_cookies_box!=null) {
                    txtListChild1.setText(OrdersData.order_cranberry_cookies_box);
                }
            }
        }
        if(groupPosition==3) {
            if(childPosition==0) {
                if(OrdersData.order_cranberry_orange_box!=null) {
                    txtListChild1.setText(OrdersData.order_cranberry_orange_box);
                }
            }
            if(childPosition==1) {
                if(OrdersData.order_fig_raisins_box!=null) {
                    txtListChild1.setText(OrdersData.order_fig_raisins_box);
                }
            }
            if(childPosition==2) {
                if(OrdersData.order_papaya_pineapple_box!=null) {
                    txtListChild1.setText(OrdersData.order_papaya_pineapple_box);
                }
            }
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        Typeface face= Typeface.createFromAsset(convertView.getContext().getAssets(), "AvenirNextLTPro-Regular.otf");
        Typeface face1= Typeface.createFromAsset(convertView.getContext().getAssets(), "AvenirNextLTPro-Demi.otf");
        lblListHeader.setTypeface(face1);
        lblListHeader.setTextSize(12);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
