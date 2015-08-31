package hkm.ui.ddbox.lib;

/**
 * Created by hesk on 3/23/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.DimenRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewUtils {



    /* public static T getItemAt(Map<?, T> frameDescriptors, int x, int y) {
         T returnValue = null;

         for (T item : frameDescriptors.values()) {
             if (item.frame.contains((int) x, (int) y)) {
                 returnValue = item;
             }

         }
         return returnValue;
     }
 */

    private static void addSpace(Context ctx, LinearLayout container, @DimenRes int resDimension) {
        container.addView(ViewUtils.prepareSpace(resDimension, ctx));
    }


    public View getEmptyView(Context ctx, @DimenRes int resId) {
        float dp = ctx.getResources().getDimension(resId);
        return ViewUtils.prepareSpace((int) dp, ctx);
    }

    public static int getWidth(Activity n) {
        DisplayMetrics dm = new DisplayMetrics();
        initialSupport(n, dm);
        return dm.widthPixels;
    }

    private static void initialSupport(Activity activity, DisplayMetrics dm) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public static RelativeLayout.LayoutParams getParamsR(int h) {
        return new RelativeLayout.LayoutParams(h, -2);
    }

    public static LinearLayout.LayoutParams getParamsL(int h) {
        return new LinearLayout.LayoutParams(h, -2);
    }

    public static LinearLayout.LayoutParams getParamWeight(float weight) {
        return new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
    }

    public static View getSpace(Context ctx, int dp, float density) {
        return prepareSpace((int) (dp * density), ctx);
    }

    public static View getView(int width, Context mContext) {
        // Make an ImageView to show a photo
        ImageView i = new ImageView(mContext);
        i.setAdjustViewBounds(true);
        i.setLayoutParams(new AbsListView.LayoutParams(width, AbsListView.LayoutParams.WRAP_CONTENT));
        // Give it a nice background
        //i.setBackgroundResource(R.drawable.picture_frame);
        return i;
    }

    public static View prepareSpace(int dp, Context ctx) {
        ViewGroup.LayoutParams separator = new ViewGroup.LayoutParams(dp, ViewGroup.LayoutParams.MATCH_PARENT);
        //border set
        View borderVertical = new View(ctx);
        borderVertical.setLayoutParams(separator);
        return borderVertical;
    }

    public static Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

}
