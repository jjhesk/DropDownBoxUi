package hkm.ui.ddbox.lib;

/**
 * Created by hesk on 3/23/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

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
