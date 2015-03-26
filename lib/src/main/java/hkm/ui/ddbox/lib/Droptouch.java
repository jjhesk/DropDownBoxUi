package hkm.ui.ddbox.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.neopixl.pixlui.components.textview.TextView;

/**
 * Created by hesk on 3/23/15.
 */
public class Droptouch extends LinearLayout {


    private String label = "";
    private int vId;
    private TextView tv;
    private LinearLayout child, main;
    private RelativeLayout fme;
    private int pops_presentation;
    private int height;
    private int width;
    private int width_banner_text;

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

    /**
     * initialization
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {
        vId = getRootView().generateViewId();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        child = (LinearLayout) inflater.inflate(R.layout.ddbox_lib_body, this, true);
        //main = (LinearLayout) child.getChildAt(0);
        fme = (RelativeLayout) child.getChildAt(0);
        tv = (TextView) fme.getChildAt(1);
        tv.setId(vId);
        ((Button) fme.getChildAt(0)).setOnClickListener(tp);
    }

    public static String TAG = "DropTouch";
    private int weight_now;
    private int sum;
    private Point size;

    public Droptouch initPortionCalculation(Activity act) {
        Display display = act.getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        return this;
    }

    public Droptouch setPortion(int factor, int sum) {
        if (size != null) {
            float f = (float) factor / (float) sum * (float) size.x;
            setWidthPixel((int) (f - 1));
        } else {
            Log.e(TAG, "size is not yet set");
        }
        return this;
    }

    public Droptouch setPortionAuto(float factor) {
        setLayoutParams(getParamWeight(factor));
        return this;
    }

    public Droptouch setButtonFace(int resId) {
        main.setBackgroundResource(resId);
        return this;
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

    public Droptouch setWidthPixel(int w) {
        setLayoutParams(getParamsL(w));
        Log.d(TAG, w + "");
        return this;
    }

    public Droptouch setWidthHardCode(float width) {
        setLayoutParams(getParamsL((int) ViewUtils.dipToPixels(getContext(), width)));
        Log.d(TAG, width + "");
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
        return new RelativeLayout.LayoutParams(h, -2);
    }

    private LinearLayout.LayoutParams getParamsL(int h) {
        return new LinearLayout.LayoutParams(h, -2);
    }

    private LinearLayout.LayoutParams getParamWeight(float weight) {
        return new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
    }

}
