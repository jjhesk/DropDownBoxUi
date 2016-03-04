package hkm.ui.ddbox.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import hkm.ui.ddbox.lib.data.ViewUtils;

/**
 * Created by hesk on 3/23/15.
 */
public class Droptouch extends LinearLayout {

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
    private Button button;
    private float density;

    public Droptouch(Context context) {
        super(context);
        init();
    }

    public Droptouch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public Droptouch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    private String mCurrentLabel = "";
    private String mFontName, mLabel;
    private boolean mSingleLine;
    private int mFontSize;
    private int mFontColor;

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.hbdropdownui);
        try {
          /*  mPadding = (int) typedArray.getDimension(R.styleable.hbdropdownui_recyclerviewPadding, -1.1f);
            mPaddingTop = (int) typedArray.getDimension(R.styleable.hbdropdownui_recyclerviewPaddingTop, 0.0f);
            mPaddingBottom = (int) typedArray.getDimension(R.styleable.hbdropdownui_recyclerviewPaddingBottom, 0.0f);
            mPaddingLeft = (int) typedArray.getDimension(R.styleable.hbdropdownui_recyclerviewPaddingLeft, 0.0f);
            mPaddingRight = (int) typedArray.getDimension(R.styleable.hbdropdownui_recyclerviewPaddingRight, 0.0f);*/
            mSingleLine = typedArray.getBoolean(R.styleable.hbdropdownui_singleline, false);
            mFontName = typedArray.getString(R.styleable.hbdropdownui_fontname);
            mLabel = typedArray.getString(R.styleable.hbdropdownui_label);
            mFontSize = (int) typedArray.getDimension(R.styleable.hbdropdownui_fontsize, 0);
            mFontColor = (int) typedArray.getColor(R.styleable.hbdropdownui_fontcolor, 0);
          /*  mEmptyId = typedArray.getResourceId(R.styleable.hbdropdownui_recyclerviewEmptyView, 0);
            mFloatingButtonId = typedArray.getResourceId(R.styleable.hbdropdownui_recyclerviewFloatingActionView, 0);
            mScrollbarsStyle = typedArray.getInt(R.styleable.hbdropdownui_recyclerviewScrollbars, SCROLLBARS_NONE);
            int colorList = typedArray.getResourceId(R.styleable.hbdropdownui_recyclerviewDefaultSwipeColor, 0);
            if (colorList != 0) {
                defaultSwipeToDismissColors = getResources().getIntArray(colorList);
            }*/
            //mEmptyViewPolicy = EMPTY_VIEW_POLICY_EMPTY_SHOW;
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * initialization
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {
        // setId(generateViewId());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainlayout = inflater.inflate(R.layout.framework_body, this, true);
        fme = (RelativeLayout) mainlayout.findViewById(R.id.ddui_touch_area);
        tv = (TextView) mainlayout.findViewById(R.id.ddui_text_id);
        density = getContext().getResources().getDisplayMetrics().density;
        if (mFontName != null) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + mFontName);
            tv.setTypeface(typeface);
        }
        if (mFontSize > 0) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mFontSize);
        }
        if (mFontColor > 0) {
            tv.setTextColor(mFontColor);
        }
        if (mLabel != null) {
            tv.setText(mLabel);
        } else {
            mLabel = tv.getText().toString();
        }
        if (mSingleLine) {
            tv.setSingleLine(true);
        } else {
            tv.setSingleLine(false);
        }

        //tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.txt_size));
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

    public void labelReset() {
        mCurrentLabel = mLabel;
        tv.setText(mLabel);
        tv.requestLayout();
    }

    public Droptouch setLabel(String s) {
        mCurrentLabel = s;
        tv.setText(mCurrentLabel);
        tv.requestLayout();
        return this;
    }

    public Droptouch setLabel(SpannableString s) {
        mCurrentLabel = s.toString();
        tv.setText(mCurrentLabel);
        tv.requestLayout();
        return this;
    }

    public Droptouch setLabel(Spannable s) {
        mCurrentLabel = s.toString();
        tv.setText(mCurrentLabel);
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


    private int gettvid() {
        return vId;
    }

    public void build() {
        vId = getRootView().generateViewId();
        init();
    }
}
