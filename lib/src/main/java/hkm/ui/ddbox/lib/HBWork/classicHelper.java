package hkm.ui.ddbox.lib.HBWork;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.IntDef;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;

import hkm.ui.ddbox.lib.SelectionSP;
import hkm.ui.ddbox.lib.bottomsheet.BottomSheetDialogFragment;
import hkm.ui.ddbox.lib.bottomsheet.NumberPickerDialog;
import hkm.ui.ddbox.lib.bottomsheet.SingleStringBS;
import hkm.ui.ddbox.lib.bottomsheet.spinnerCallBack;

/**
 * Created by hesk on 11/12/15.
 */
public abstract class classicHelper implements spinnerCallBack {

    public static final int STATUS_BUSY = 1, STATUS_IDEAL = 0;

    @IntDef({STATUS_BUSY, STATUS_IDEAL})
    public @interface Status {
    }

    @Status
    public int getStatus() {
        return status_now;
    }

    private int status_now = STATUS_IDEAL;
    protected SelectionSP mainlogic;
    protected SingleStringBS mDialogList;
    protected NumberPickerDialog mDialogPicker;
    protected Activity activity;
    protected int holding_quantity = 0;
    protected boolean onExploreRelatedProduct = false;

    protected void setStatus(final @Status int st) {
        status_now = st;
    }

    @Override
    public void onSubmission(int group, int size, int quantity) {

    }

    @Override
    public void onDialogQuantityBoxNotAvailable(SelectionSP self) {

    }

    @Override
    public void onUpdateSize(int selection) {

    }

    @Override
    public void onUpdateRelatedProduct(int group, int quantity) {

    }


    private SingleStringBS prepareSingleStringListBS() {
        final Bundle bloop = new Bundle();
        bloop.putInt(BottomSheetDialogFragment.MEASUREMENT_HEIGHT, (int) activity.getResources().getDimension(getDialogPickerHeightRes()));
        final SingleStringBS instance = SingleStringBS.newInstace(bloop);
        return instance;
    }

    private NumberPickerDialog prepareNumberPickerBS() {
        final Bundle bloop = new Bundle();
        bloop.putInt(BottomSheetDialogFragment.MEASUREMENT_HEIGHT, (int) activity.getResources().getDimension(getDialogPickerHeightRes()));
        bloop.putString(BottomSheetDialogFragment.DIALOG_TITLE, "Quantity");
        final NumberPickerDialog instance = NumberPickerDialog.newInstace(bloop);
        return instance;
    }

    @DimenRes
    protected abstract int getDialogPickerHeightRes();

    protected abstract void init();

    public classicHelper(final Activity activity, final FrameLayout container) {
        this.activity = activity;
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic = new SelectionSP(activity, container);
        mainlogic.setConnectionModule(this);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        init();
    }
}
