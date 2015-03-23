package hkm.ui.ddbox.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.neopixl.pixlui.components.relativelayout.RelativeLayout;
import com.neopixl.pixlui.components.textview.TextView;

/**
 * Created by hesk on 3/23/15.
 */
public class Droptouch extends LinearLayout {
    public Droptouch(Context context) {
        super(context);
        init();
    }

    public Droptouch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Droptouch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private String label = "";
    private int vId;
    private TextView tv;
    private LinearLayout child, main;
    private RelativeLayout fme;
    private int pops_presentation;
    private int height;
    private int width;
    private int width_banner_text;

    /**
     * initialization
     */
    private void init() {
        vId = getRootView().generateViewId();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        child = (LinearLayout) inflater.inflate(R.layout.ddbox_lib_body, this, true);
        main = (LinearLayout) child.getChildAt(0);
        fme = (RelativeLayout) main.getChildAt(0);
        tv = (TextView) fme.getChildAt(0);
        tv.setId(vId);
        main.setOnClickListener(tp);
    }


    private OnClickListener tp = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void setClickListener(final OnClickListener ls) {
        tp = ls;
        main.setOnClickListener(ls);
    }

    public TextView getTextView() {
        return tv;
    }

    public Droptouch setLabel(String s) {
        label = s;
        tv.setText(label);
        return this;
    }


    public Droptouch setFont(String loc) {
        tv.setCustomFont(getContext(), loc);
        return this;
    }

    private int gettvid() {
        return vId;
    }

    private RelativeLayout.LayoutParams getParamsR(int h) {
        return new RelativeLayout.LayoutParams(-1, h);
    }

    private LinearLayout.LayoutParams getParamsL(int h) {
        return new LinearLayout.LayoutParams(-1, h);
    }


    public void requestLayout() {
        super.requestLayout();
        if (tv != null)
            tv.setText(label);
    }

}
