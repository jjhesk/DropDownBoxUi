package hkm.ui.ddbox.lib.fragmentdialogbottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import hkm.ui.ddbox.lib.R;

/**
 * Created by hesk on 4/3/16.
 */
public class listNumberPicker extends dialog_toolbar_integration {
    private NumberPicker pickernumber;
    private int max, start;

    public static listNumberPicker newInstsance(Bundle b) {
        listNumberPicker ld = new listNumberPicker();
        ld.setArguments(b);
        return ld;
    }

    @Override
    protected int layoutRes() {
        return R.layout.bs_picker_v1;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind_v1_toolbar(view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            max = bundle.getInt(B.MAX);
            start = bundle.getInt(B.START);
            setTitle(bundle.getString(B.TOOL_BAR_TITLE, "number"));
        } else {
            return;
        }
        pickernumber = (NumberPicker) view.findViewById(R.id.hkmspinnersheet_picker);
        pickernumber.setMaxValue(max); // max value 100
        pickernumber.setMinValue(1);   // min value 0
        pickernumber.setWrapSelectorWheel(false);
    }

    @Override
    protected void onConfirm() {
        if (onselect != null) {
            onselect.onItemSelect(pickernumber.getValue(), pickernumber.getValue() + "", pickernumber.getValue() + "");
            dismiss();
        }
    }
}
