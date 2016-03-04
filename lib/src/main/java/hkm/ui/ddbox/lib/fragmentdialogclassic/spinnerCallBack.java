package hkm.ui.ddbox.lib.fragmentdialogclassic;

import hkm.ui.ddbox.lib.SelectionSP;

/**
 * Created by hesk on 25/9/15.
 */
public interface spinnerCallBack {
    void onSubmission(final int group, final int size, final int quantity);

    void onDialogQuantityBoxNotAvailable(final SelectionSP self);

    void onUpdateSize(final int selection);

    void onUpdateRelatedProduct(final int group, final int quantity);
}
