package hkm.ui.ddbox.lib.bottomsheet;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.neopixl.pixlui.components.textview.TextView;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.SlideInEffect;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import hkm.ui.ddbox.lib.R;
import hkm.ui.ddbox.lib.data.RecyclerItemClickListener;


/**
 * The first time renderer
 * Created by hesk on 18/9/15.
 */
public abstract class BottomSheetDialogFragment extends BottomSheetBase implements RecyclerItemClickListener.OnItemClickListener {
    protected ProgressBar pro;
    protected RecyclerView mRecyclerView;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private TextView mTextview;
    private ImageButton mback;
    private String mTitle;

    private RecyclerView.LayoutManager createLayoutManager(int itemLayoutRes, boolean isStaggered) {
        if (itemLayoutRes == R.layout.item) {
            return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        } else {
            if (isStaggered) {
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            } else {
                return new GridLayoutManager(getActivity(), 2);
            }
        }
    }

    @Override
    protected void onCreatExtraElements() {
        pro = (ProgressBar) root.findViewById(R.id.hkmspinnersheet_loader);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.hkmspinnersheet_list);
        mRecyclerView.setLayoutManager(createLayoutManager(R.layout.item, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        mRecyclerView.addOnScrollListener(jazzyScrollListener);
        //  mRecyclerView.setLayoutTransition(new SlideInEffect());
        mTextview = (TextView) root.findViewById(R.id.hkmspinnersheet_title);
        if (getArguments().getString(DIALOG_TITLE, "").equalsIgnoreCase("") && mTitle != null) {
            mTextview.setText(mTitle);
        } else
            mTextview.setText(getArguments().getString(DIALOG_TITLE, ""));
        mback = (ImageButton) root.findViewById(R.id.hkmspinnersheet_back);
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
    }

    public void setTitle(String title) {
        if (mTextview != null)
            mTextview.setText(title);
        else {
            mTitle = title;
        }
    }

    @LayoutRes
    @Override
    protected int getHiddenPanelLayout() {
        return R.layout.bs_list;
    }
}
