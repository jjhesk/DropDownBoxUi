package hkm.ui.ddbox.lib.bottomsheet;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import hkm.ui.ddbox.lib.R;


/**
 * Created by hesk on 24/9/15.
 */
public abstract class BottomSheetBase extends DialogFragment {
    public static final String MEASUREMENT_HEIGHT = "HEIGHT";
    public static final String DIALOG_TITLE = "TITLE";
    private int mHeight;
    protected boolean isInProgress = false;
    protected View root;

    protected onCallBack mCallBack = new onCallBackSimple();

    @LayoutRes
    protected abstract int getHiddenPanelLayout();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dL = super.onCreateDialog(savedInstanceState);
        dL.setCancelable(false);
        dL.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, getArguments().getInt(MEASUREMENT_HEIGHT, 800));
        dL.getWindow().setGravity(Gravity.BOTTOM);
        dL.setOnShowListener(new DialogInterface.OnShowListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onShow(DialogInterface dialog) {
                mCallBack.onFragmentRender(dL, true);
                dL.setCancelable(true);
            }
        });
        return dL;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(getHiddenPanelLayout(), container);
        ViewTreeObserver vto = root.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeight = root.getHeight();
                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        onCreatExtraElements();
        return root;
    }

    protected abstract void onCreatExtraElements();

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setRenderCallback(onCallBack mCallBackListen) {
        mCallBack = mCallBackListen;
    }

    protected boolean onPressBack() {
        dismiss();
        return true;
    }
}
