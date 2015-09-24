package hkm.ui.ddbox.lib.bottomsheet;

import android.app.Dialog;

import java.util.List;

/**
 * Created by hesk on 24/9/15.
 */
public interface onCallBack {
    void onFragmentRender(Dialog dialog, boolean isFirstTimeRender);

    void onSetItem(List<String> dialog, int itemPosition, String data_item);

    void onNumberSelected(int quantity);

    void onBack();
}
