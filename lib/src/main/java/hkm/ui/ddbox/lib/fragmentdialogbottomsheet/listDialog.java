package hkm.ui.ddbox.lib.fragmentdialogbottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import hkm.ui.ddbox.lib.R;
import hkm.ui.ddbox.lib.data.RecyclerItemClickListener;
import hkm.ui.ddbox.lib.data.SimpleStringAdapter;

/**
 * Created by hesk on 4/3/16.
 */
public class listDialog extends dialog_toolbar_integration {

    private RecyclerView list;

    public static listDialog newInstsance(Bundle b) {
        listDialog ld = new listDialog();
        ld.setArguments(b);
        return ld;
    }

    @Override
    protected int layoutRes() {
        return R.layout.bs_list_v2;
    }

    private RecyclerView.LayoutManager createLayoutManager(int itemLayoutRes, boolean isStaggered) {
        if (itemLayoutRes == R.layout.item) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            if (isStaggered) {
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            } else {
                return new GridLayoutManager(getContext(), 2);
            }
        }
    }

    private ArrayList<String> titles = new ArrayList<String>();
    private ArrayList<String> links = new ArrayList<String>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind_v2_toolbar(view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            titles = bundle.getStringArrayList(B.HM);
            links = bundle.getStringArrayList(B.HLink);
            setTitle(bundle.getString(B.TOOL_BAR_TITLE, "options"));
        } else {
            return;
        }
        list = (RecyclerView) view.findViewById(R.id.hkmspinnersheet_list);
        list.setLayoutManager(createLayoutManager(R.layout.item, false));
        list.setHasFixedSize(true);
        list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (onselect != null) {


                    if (links == null && titles != null) {
                        if (position < titles.size()) {
                            onselect.onItemSelect(position, titles.get(position), null);
                        }
                    }

                    if (links != null && titles == null) {
                        if (position < links.size()) {
                            onselect.onItemSelect(position, links.get(position), null);
                        }
                    }

                    if (links != null && titles != null) {
                        if (links.size() == titles.size()) {
                            onselect.onItemSelect(position, titles.get(position), links.get(position));
                        }
                    }

                    dismiss();
                }
            }
        }

        ));
        list.setAdapter(new SimpleStringAdapter(titles));
    }


}
