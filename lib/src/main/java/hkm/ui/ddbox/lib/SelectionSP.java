package hkm.ui.ddbox.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hkm.ui.ddbox.lib.bottomsheet.BottomSheetBase;
import hkm.ui.ddbox.lib.bottomsheet.NumberPickerDialog;
import hkm.ui.ddbox.lib.bottomsheet.SingleStringBS;
import hkm.ui.ddbox.lib.bottomsheet.onCallBackSimple;
import hkm.ui.ddbox.lib.bottomsheet.spinnerCallBack;
import hkm.ui.ddbox.lib.data.ProductGroupContainer;
import hkm.ui.ddbox.lib.data.RecyclerItemClickListener;
import hkm.ui.ddbox.lib.data.SampleAdapter;

/**
 * Created by hesk on 3/11/15.
 */
public class SelectionSP extends onCallBackSimple {
    private static final String KEY_TRANSITION_EFFECT = "transition_effect";
    public final static String TAG = "showlist";
    private int choice_of_variance = 0, choice_of_quantity = 0, choice_of_size;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private int mCurrentTransitionEffect = JazzyHelper.FADE;
    private BottomSheetBase numberpicker, listpicker;
    private boolean lock_quantity_dropdown;
    private final int
            layout_options1 = R.layout.options_1,
            layout_options21 = R.layout.options_21,
            layout_options22 = R.layout.options_22,
            layout_options3 = R.layout.options_3;
    private supporttype current_choosing_type = supporttype.NONE;

    private enum supporttype {
        VARIANT, COLOR, SIZE, QTY, NONE
    }

    public static final int SELECT_RETURNABLE = -2439;
    public static final String LIST = "thelistoptions";
    private FrameLayout container1;
    private Activity mcontext;
    private List<ProductGroupContainer> varients = new ArrayList<>();
    private ArrayList<String> sizelist = new ArrayList<>();
    private spinnerCallBack cb = new spinnerCallBack() {
        @Override
        public void onUpdateRelatedProduct(int group, int quantity) {

        }

        @Override
        public void onUpdateSize(int selection) {

        }

        @Override
        public void onDialogQuantityBoxNotAvailable(SelectionSP self) {

        }

        @Override
        public void onSubmission(int group, final int size, int quantity) {

        }
    };


    public SelectionSP(final Activity act, FrameLayout inflated_container) {
        container1 = inflated_container;
        mcontext = act;
        max_pickable_number = 99;
        lock_quantity_dropdown = false;
    }

    public SelectionSP lockQuantityBox(boolean tox) {
        lock_quantity_dropdown = tox;
        return this;
    }

    public SelectionSP setMaxNumToBePicked(final int max) {
        max_pickable_number = max;
        if (numberpicker != null) {
            if (numberpicker instanceof NumberPickerDialog) {
                final NumberPickerDialog numpick = (NumberPickerDialog) numberpicker;
                numpick.setMaxPickerNum(max);
            }
        }
        return this;
    }

    public SelectionSP setCustomNumberPicker(BottomSheetBase picker) {
        numberpicker = picker;
        numberpicker.setRenderCallback(this);
        return this;
    }

    public SelectionSP setCustomListPicker(BottomSheetBase picker) {
        listpicker = picker;
        listpicker.setRenderCallback(this);
        return this;
    }

    public SelectionSP addGroupProducts(List<ProductGroupContainer> list) {
        varients = list;
        return this;
    }

    public SelectionSP addSizeGroup(ArrayList<String> list) {
        sizelist = list;
        return this;
    }

    private Droptouch quantity, size, group;
    private int max_pickable_number;

    private View bindItems(View v) {
        quantity = (Droptouch) v.findViewById(R.id.hkm_sp_lc1);
        if (quantity != null) {
            quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogPickerNumber();
                }
            });
            quantity.setLabel(mcontext.getString(R.string.quantity));
        }
        size = (Droptouch) v.findViewById(R.id.hkm_sp_lc2);
        if (size != null) {
            size.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sizeListDialog(mcontext.getString(R.string.size), sizelist);
                }
            });
            size.setLabel(mcontext.getString(R.string.size));
        }
        group = (Droptouch) v.findViewById(R.id.hkm_sp_lc3);
        if (group != null) {
            group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normalListDialog(mcontext.getString(R.string.group), varients);
                }
            });
            group.setLabel(mcontext.getString(R.string.group));
        }
        return v;
    }

    public void build() {
        container1.removeAllViews();
        if (varients.size() > 0 && sizelist.size() > 0) {
            View view = bindItems(mcontext.getLayoutInflater().inflate(layout_options3, null, false));
            container1.addView(view);
        }
        if (varients.size() > 0 && sizelist.size() == 0) {
            View view = bindItems(mcontext.getLayoutInflater().inflate(layout_options21, null, false));
            container1.addView(view);
        }
        if (varients.size() == 0 && sizelist.size() > 0) {
            View view = bindItems(mcontext.getLayoutInflater().inflate(layout_options22, null, false));
            container1.addView(view);
        }
        if (varients.size() == 0 && sizelist.size() == 0) {
            View view = bindItems(mcontext.getLayoutInflater().inflate(layout_options1, null, false));
            container1.addView(view);
        }
        container1.setVisibility(View.VISIBLE);
    }

    public static List<String> containerToList(List<ProductGroupContainer> container) {
        final List<String> list = new ArrayList<>();
        final Iterator<ProductGroupContainer> i = container.iterator();
        while (i.hasNext()) {
            ProductGroupContainer group = i.next();
            list.add(group.getTitle());
        }
        return list;
    }


    /**
     * shows the number picker dialog at the screen
     */
    private void sizeListDialog(String title, final List<String> list) {

        if (list.size() == 0) return;
        current_choosing_type = supporttype.SIZE;
        if (listpicker == null) {
            defaultsizeListDialog(title, list);
        } else {
            if (listpicker instanceof SingleStringBS) {
                SingleStringBS dialog = (SingleStringBS) listpicker;
                dialog.show(mcontext.getFragmentManager().beginTransaction(), "listpicker");
                dialog.setListData(list);
                dialog.setTitle(title);
            }
        }

    }

    /**
     * show the list choice items on the screen
     */
    private void normalListDialog(String title, List<ProductGroupContainer> list) {
        if (list.size() == 0) return;
        current_choosing_type = supporttype.VARIANT;
        if (listpicker == null) {
            defaultnormalListDialog(title, list);
        } else {
            if (listpicker instanceof SingleStringBS) {
                final SingleStringBS dialog = (SingleStringBS) listpicker;
                dialog.show(mcontext.getFragmentManager().beginTransaction(), "listpicker");
                dialog.setListData(containerToList(list));
                dialog.setTitle(title);
            }
        }
    }

    /**
     * shows the number picker dialog at the screen
     */
    private void showDialogPickerNumber() {
        current_choosing_type = supporttype.QTY;
        if (lock_quantity_dropdown) {
            cb.onDialogQuantityBoxNotAvailable(this);
        } else {
            if (numberpicker == null) {
                defaultnumberpicker();
            } else {
                numberpicker.show(mcontext.getFragmentManager().beginTransaction(), "NumPicker");
            }
        }
    }


    private RecyclerView.LayoutManager createLayoutManager(int itemLayoutRes, boolean isStaggered) {
        if (itemLayoutRes == R.layout.item) {
            return new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
        } else {
            if (isStaggered) {
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            } else {
                return new GridLayoutManager(mcontext, 2);
            }
        }
    }

    private void defaultnumberpicker() {
        //consist of the old style implementation
        final Dialog d = new Dialog(mcontext);
        d.setTitle("Quantity");
        d.setContentView(R.layout.dialog_picker);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        Button b = (Button) d.findViewById(R.id.set_process_button);
        np.setMaxValue(max_pickable_number); // max value 100
        np.setMinValue(1);   // min value 0
        np.setWrapSelectorWheel(false);
            /*np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    Log.d(TAG, oldVal + " : " + newVal);
                }
            });*/
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "display now" + " : " + np.getValue());
                //  tv.setText(String.valueOf(np.getValue())); //set the value to textview
                setQty(np.getValue());
                d.dismiss();
            }
        });
        d.show();
    }

    private void defaultsizeListDialog(String title, final List<String> list) {
        final Dialog d = new Dialog(mcontext);
        d.setTitle(title);
        d.setContentView(R.layout.default_selection_dialog);
        final RecyclerView mlist = (RecyclerView) d.findViewById(R.id.list);
        mlist.setLayoutManager(createLayoutManager(R.layout.item, false));
        mlist.setHasFixedSize(true);
        mlist.setAdapter(new SampleAdapter(list));
        mlist.addOnItemTouchListener(new RecyclerItemClickListener(mcontext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ここで処理
                Log.d(TAG, "display now" + " : " + list.get(position));
                setSize(position);
                d.dismiss();
            }
        }));
        jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        mlist.addOnScrollListener(jazzyScrollListener);
        d.show();
    }

    private void defaultnormalListDialog(String title, List<ProductGroupContainer> flist) {
        final Dialog d = new Dialog(mcontext);
        d.setTitle(title);
        d.setContentView(R.layout.default_selection_dialog);
        final RecyclerView list = (RecyclerView) d.findViewById(R.id.list);
        list.setLayoutManager(createLayoutManager(R.layout.item, false));
        list.setAdapter(new SampleAdapter(flist, false));
        list.setHasFixedSize(true);
        list.addOnItemTouchListener(new RecyclerItemClickListener(mcontext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "display now" + " : " + varients.get(position).getHref());
                setGroup(position);
                d.dismiss();
            }
        }));
        jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        list.addOnScrollListener(jazzyScrollListener);
        d.show();
    }


    public void initInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentTransitionEffect = savedInstanceState.getInt(KEY_TRANSITION_EFFECT, mCurrentTransitionEffect);
            setupJazziness(mCurrentTransitionEffect);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_TRANSITION_EFFECT, mCurrentTransitionEffect);
    }

    private void setupJazziness(int effect) {
        mCurrentTransitionEffect = effect;
        jazzyScrollListener.setTransitionEffect(mCurrentTransitionEffect);
    }


    @Override
    public void onNumberSelected(int quantity) {
        if (current_choosing_type == supporttype.QTY) {
            setQty(quantity);
        }
    }

    @Override
    public void onBack() {
       /* if (current_choosing_type == supporttype.COLOR || current_choosing_type == supporttype.SIZE || current_choosing_type == supporttype.VARIANT) {
            if (listpicker != null) listpicker.dismiss();
        } else {
            if (numberpicker != null) numberpicker.dismiss();
        }*/
    }

    @Override
    public void onSetItem(List<String> dialog, int itemPosition, String data_item) {
        if (current_choosing_type == supporttype.COLOR) {

        } else if (current_choosing_type == supporttype.SIZE) {
            setSize(itemPosition);
        } else if (current_choosing_type == supporttype.VARIANT) {
            setGroup(itemPosition);
            cb.onUpdateRelatedProduct(choice_of_variance, choice_of_size);
        }
    }

    public void triggerAddBag() {
        cb.onSubmission(choice_of_variance, choice_of_size, choice_of_quantity);
    }

    public void setConnectionModule(spinnerCallBack cb) {
        this.cb = cb;
    }

    private SelectionSP setQty(int t) {
        if (quantity != null) {
            quantity.setLabel("Qty: " + t);
            choice_of_quantity = t;
        }
        return this;
    }

    public void setQuantityOption(int t) {
        setQty(t);
    }

    public void setSizeOption(final int selection) {
        setSize(selection);
    }

    private SelectionSP setSize(int t_select_choice) {
        if (size != null && sizelist.size() > t_select_choice) {
            cb.onUpdateSize(t_select_choice);
            String selection = sizelist.get(t_select_choice);
            size.setLabel("Size: " + selection);
            choice_of_size = t_select_choice;
        }
        return this;
    }

    private SelectionSP setGroup(int t) {
        if (group != null && varients.size() > t) {
            String selection = varients.get(t).getTitle();
            group.setLabel(selection);
            choice_of_variance = t;
        }
        return this;
    }

}
