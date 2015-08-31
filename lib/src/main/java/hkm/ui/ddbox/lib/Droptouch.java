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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.Objects;

/**
 * Created by hesk on 3/23/15.
 */
public class Droptouch extends LinearLayout {
    private String label = "";
    private int vId;
    private TextView tv;
    private LinearLayout child, main;
    private View mainlayout;
    private RelativeLayout fme;
    private ViewGroup.LayoutParams totalWidth;
    private int pops_presentation;
    private int height;
    private int width;
    private int width_banner_text;
    public static String TAG = "DropTouch";
    private int weight_now;
    private int sum;
    private Point size;

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

    private Button button;
    private float density;

    /**
     * initialization
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {
        vId = getRootView().generateViewId();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainlayout = (View) inflater.inflate(R.layout.ddbox_lib_body, this, true);
        fme = (RelativeLayout) mainlayout.findViewById(R.id.box_touch_area);
        tv = (TextView) mainlayout.findViewById(R.id.box_ui_text_id);
        density = getContext().getResources().getDisplayMetrics().density;
    }

    public void setTag(int n) {
        fme.setTag(n);
    }

    @Override
    public Object getTag() {
        return (int) fme.getTag();
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        fme.setOnClickListener(listener);
    }

    public Droptouch initPortionCalculation(Activity act) {
        Display display = act.getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        return this;
    }

    public Droptouch setPortion(float factor, int sum) {
        float f = (float) factor * (float) sum;
        setWidthPixel((int) (f - 1));
        return this;
    }

    public Droptouch setPortionAuto(float factor) {
        setLayoutParams(ViewUtils.getParamWeight(factor));
        return this;
    }

    public Droptouch setRightMargin(int r) {
        //MarginLayoutParams mp = new MarginLayoutParams(getLay);
        fme.setPadding(0, 0, r, 0);
        fme.requestLayout();

        return this;
    }

    public Droptouch setLeftMargin(int l) {
        //MarginLayoutParams mp = new MarginLayoutParams(getLay);
        fme.setPadding(l, 0, 0, 0);
        fme.requestLayout();
        return this;
    }

    public Droptouch setHorizontalMargin(int w) {
        fme.setPadding(w, 0, w, 0);
        fme.requestLayout();
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


    public TextView getTextView() {
        return tv;
    }

    public Droptouch setLabel(String s) {
        label = s;
        tv.setText(label);
        tv.requestLayout();
        return this;
    }

    public Droptouch setWidthPixel(float w) {
        setLayoutParams(ViewUtils.getParamsL((int) w));
        Log.d(TAG, w + "");
        return this;
    }

    public Droptouch setWidthPixel(int w) {
        setLayoutParams(ViewUtils.getParamsL(w));
        Log.d(TAG, w + "");
        return this;
    }

    public Droptouch setWidthHardCode(float width) {
        setLayoutParams(ViewUtils.getParamsL((int) ViewUtils.dipToPixels(getContext(), width)));
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


}
