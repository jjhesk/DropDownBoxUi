package hkm.ui.ddbox.lib.fragmentdialogclassic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.LinearLayout;

import hkm.ui.ddbox.lib.R;


/**
 * Created by hesk on 24/9/15.
 */
public abstract class BottomSheetBase extends DialogFragment {
    public static final String MEASUREMENT_HEIGHT = "HEIGHT";
    public static final String BACKGROUND_COLOR = "COLOR_BG";
    public static final String DIALOG_TITLE = "TITLE";
    private int mHeight;
    protected boolean isInProgress = false;
    protected View root;

    protected onCallBack mCallBack = new onCallBackSimple();

    @LayoutRes
    protected abstract int getHiddenPanelLayout();

    /*

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Activity activity = getActivity();
            final View content = activity.findViewById(android.R.id.content).getRootView();
            try {
                if (content.getWidth() > 0) {
                    Bitmap image = BlurBuilder.blur(content);
                    getDialog().getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
                } else {
                    content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            Bitmap image = BlurBuilder.blur(content);
                            getDialog().getWindow().
                                    setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
                        }
                    });
                }
            } catch (Exception e) {
                Log.d("LOG_IMG", e.getMessage());
            }
        }
    */
    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dL = super.onCreateDialog(savedInstanceState);
        dL.setCancelable(false);
        if (isTablet(getActivity())) {
            int width = getResources().getDimensionPixelOffset(R.dimen.ui_width_dialog_tablet);
            dL.getWindow().setLayout(width, getArguments().getInt(MEASUREMENT_HEIGHT, 800));
        } else {
            dL.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, getArguments().getInt(MEASUREMENT_HEIGHT, 800));
        }
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
        // enableBlurBackGround(getDialog().getWindow(), root.getRootView(), getActivity());
        onCreatExtraElements();
        return root;
    }

    protected void enableBlurBackGround(final Window rootview, final View content, final Activity activity) {
        if (content.getWidth() > 0) {
            Bitmap image = BlurBuilder.blur(content);
            rootview.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
        } else {
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Bitmap image = BlurBuilder.blur(content);
                    rootview.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
                }
            });
        }

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
