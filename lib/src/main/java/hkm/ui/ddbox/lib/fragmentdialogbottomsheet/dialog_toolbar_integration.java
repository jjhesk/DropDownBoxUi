package hkm.ui.ddbox.lib.fragmentdialogbottomsheet;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;

import hkm.ui.ddbox.lib.R;

/**
 * Created by hesk on 4/3/16.
 */
public abstract class dialog_toolbar_integration extends BottomSheetFragment implements View.OnClickListener {
    protected selectItem onselect;
    protected TextView mTopLabel;
    protected ImageButton mBack, mConfirm;
    protected ProgressBar mLoadProgress;
    protected EditText mInput;


    protected void bind_v2_toolbar(View view) {
        mTopLabel = (TextView) view.findViewById(R.id.hkmspinnersheet_title);
        mBack = (ImageButton) view.findViewById(R.id.hkmspinnersheet_back);
        mConfirm = (ImageButton) view.findViewById(R.id.hkmspinnersheet_confirm);
        mLoadProgress = (ProgressBar) view.findViewById(R.id.hkmspinnersheet_loader);
        mInput = (EditText) view.findViewById(R.id.hkmspinnersheet_editor);
        if (mBack != null) {
            mBack.setOnClickListener(this);
        }
        if (mConfirm != null) {
            mConfirm.setOnClickListener(this);
        }
    }

    protected void bind_v1_toolbar(View view) {
        mTopLabel = (TextView) view.findViewById(R.id.hkmspinnersheet_title);
        mBack = (ImageButton) view.findViewById(R.id.hkmspinnersheet_back);
        mConfirm = (ImageButton) view.findViewById(R.id.hkmspinnersheet_confirm);
        //  mLoadProgress = (ProgressBar) view.findViewById(R.id.hkmspinnersheet_loader);
        //  mInput = (EditText) view.findViewById(R.id.hkmspinnersheet_editor);
        if (mBack != null) {
            mBack.setOnClickListener(this);
        }
        if (mConfirm != null) {
            mConfirm.setOnClickListener(this);
        }
    }

    protected void setTitle(String mTitle) {
        mTopLabel.setText(mTitle);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            dismiss();
        }
        if (v == mConfirm) {
            onConfirm();
        }
    }

    protected void onConfirm() {
    }

    protected void showConfirmButton() {
        mConfirm.setVisibility(View.VISIBLE);
        if (mLoadProgress == null) return;
        mLoadProgress.setVisibility(View.GONE);
    }

    protected void showLoading() {
        mConfirm.setVisibility(View.GONE);
        if (mLoadProgress == null) return;
        mLoadProgress.setVisibility(View.VISIBLE);
    }

    protected void hideRightSideSQ() {
        mConfirm.setVisibility(View.GONE);
        if (mLoadProgress == null) return;
        mLoadProgress.setVisibility(View.GONE);
    }

    protected void setCustomFont(String mFontName) {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + mFontName);
        mTopLabel.setTypeface(typeface);
    }

    protected void setCustomFontSize(@DimenRes final int sizesp) {
        if (sizesp > 0) {
            mTopLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(sizesp));
        }
    }

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutRes(), container, false);
    }

    public dialog_toolbar_integration setSelectionListener(selectItem item) {
        onselect = item;
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onselect = null;
    }


    /**
     * Style for {@link #setStyle(int, int)}: a basic,
     * normal dialog.
     */
    public static final int STYLE_NORMAL = 0;

    /**
     * Style for {@link #setStyle(int, int)}: don't include
     * a title area.
     */
    public static final int STYLE_NO_TITLE = 1;

    /**
     * Style for {@link #setStyle(int, int)}: don't draw
     * any frame at all; the view hierarchy returned by {@link #onCreateView}
     * is entirely responsible for drawing the dialog.
     */
    public static final int STYLE_NO_FRAME = 2;

    /**
     * Style for {@link #setStyle(int, int)}: like
     * {@link #STYLE_NO_FRAME}, but also disables all input to the dialog.
     * The user can not touch it, and its window will not receive input focus.
     */
    public static final int STYLE_NO_INPUT = 3;

    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";

    int mStyle = STYLE_NORMAL;
    int mTheme = 0;

    public void setStyle(int style, int theme) {
        mStyle = style;
      /*  if (mStyle == STYLE_NO_FRAME || mStyle == STYLE_NO_INPUT) {
            mTheme = com.android.internal.R.style.Theme_DeviceDefault_Dialog_NoFrame;
        }
        if (theme != 0) {
            mTheme = theme;
        }*/
    }

    public int getTheme() {
        return mTheme;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mStyle = savedInstanceState.getInt(SAVED_STYLE, STYLE_NORMAL);
            mTheme = savedInstanceState.getInt(SAVED_THEME, 0);
            //  mCancelable = savedInstanceState.getBoolean(SAVED_CANCELABLE, true);
            //  mShowsDialog = savedInstanceState.getBoolean(SAVED_SHOWS_DIALOG, mShowsDialog);
            //  mBackStackId = savedInstanceState.getInt(SAVED_BACK_STACK_ID, -1);
        }

    }


   /* @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        if (!mShowsDialog) {
            return super.getLayoutInflater(savedInstanceState);
        }

        mDialog = onCreateDialog(savedInstanceState);
        switch (mStyle) {
            case STYLE_NO_INPUT:
                mDialog.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                // fall through...
            case STYLE_NO_FRAME:
            case STYLE_NO_TITLE:
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (mDialog != null) {
            return (LayoutInflater)mDialog.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }
        return (LayoutInflater) mHost.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }*/

}
