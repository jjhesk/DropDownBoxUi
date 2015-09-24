package hkm.ui.ddbox.lib.bottomsheet;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hkm.ui.ddbox.lib.R;
import hkm.ui.ddbox.lib.data.SampleAdapter;

/**
 * Created by hesk on 24/9/15.
 */
public class SingleStringBS extends BottomSheetDialogFragment {
    private final List<String> dataList = new ArrayList<>();

    public static SingleStringBS newInstace(Bundle data) {
        final SingleStringBS fragment = new SingleStringBS();
        fragment.setArguments(data);
        fragment.setStyle(BottomSheetDialogFragment.STYLE_NO_TITLE, R.style.SlideUpDialog);
        return fragment;
    }



    public void setListData(List<String> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(new SampleAdapter(dataList));
            pro.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        mCallBack.onSetItem(dataList, position, dataList.get(position));
        dismiss();
    }

    @Override
    protected void onCreatExtraElements() {
        super.onCreatExtraElements();
        if (dataList.size() > 0) {
            mRecyclerView.setAdapter(new SampleAdapter(dataList));
            pro.setVisibility(View.GONE);
        }
    }
}
