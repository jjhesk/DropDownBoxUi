package hkm.ui.ddbox.lib.bottomsheet;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.internal.widget.TintImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import com.neopixl.pixlui.components.textview.TextView;

import hkm.ui.ddbox.lib.R;

/**
 * Created by hesk on 24/9/15.
 */
public class NumberPickerDialog extends BottomSheetBase {
    private TextView mTextview;
    private ImageButton mback, mapply;
    private android.widget.NumberPicker mPicker;
    private int max_picker_number = 0;

    public static NumberPickerDialog newInstace(Bundle data) {
        final NumberPickerDialog fragment = new NumberPickerDialog();
        fragment.setArguments(data);
        fragment.setStyle(BottomSheetDialogFragment.STYLE_NO_TITLE, R.style.SlidUpNumber);
        return fragment;
    }

    @LayoutRes
    @Override
    protected int getHiddenPanelLayout() {
        return R.layout.bs_picker;
    }

    public void setMaxPickerNum(int max) {
        max_picker_number = max;

    }

    @Override
    protected void onCreatExtraElements() {
        mPicker = (android.widget.NumberPicker) root.findViewById(R.id.hkmspinnersheet_picker);
        mTextview = (TextView) root.findViewById(R.id.hkmspinnersheet_title);
        mTextview.setText(getArguments().getString(DIALOG_TITLE, ""));
        mback = (ImageButton) root.findViewById(R.id.hkmspinnersheet_back);
        mapply = (ImageButton) root.findViewById(R.id.hkmspinnersheet_confirm);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInProgress) {
                    if (onPressBack()) {
                        mCallBack.onBack();
                    }
                }
            }
        });
        mPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        if (max_picker_number == 0) {
            max_picker_number = 99;
        }
        mPicker.setMaxValue(max_picker_number); // max value 100
        mPicker.setMinValue(1);   // min value 0
        mPicker.setWrapSelectorWheel(false);
        mapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onNumberSelected(mPicker.getValue());
                dismiss();
            }
        });
    }
}
