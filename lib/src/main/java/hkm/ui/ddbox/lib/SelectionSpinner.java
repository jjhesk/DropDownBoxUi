package hkm.ui.ddbox.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hkm.ui.ddbox.lib.bottomsheet.BottomSheetBase;
import hkm.ui.ddbox.lib.bottomsheet.SingleStringBS;
import hkm.ui.ddbox.lib.bottomsheet.onCallBackSimple;
import hkm.ui.ddbox.lib.bottomsheet.spinnerCallBack;
import hkm.ui.ddbox.lib.data.ProductGroupContainer;
import hkm.ui.ddbox.lib.data.RecyclerItemClickListener;
import hkm.ui.ddbox.lib.data.SampleAdapter;
import hkm.ui.ddbox.lib.data.ViewUtils;

/**
 * Created by hesk on 31/8/15.
 */
public class SelectionSpinner extends onCallBackSimple implements View.OnClickListener {

    private boolean instocksize = true, instockcolor = true;
    private int chk_var = 0, chk_size = 0, chk_qty = 0;
    private Button sp_variants, sp_size, sp_qty;
    private ArrayList<Droptouch> listopt = new ArrayList<>();
    private int choice_of_variance = 0, choice_of_quantity = 0;
    private LinearLayout container1;
    private Activity mcontext;
    private static final String KEY_TRANSITION_EFFECT = "transition_effect";
    public final static String TAG = "showlist";
    public final static int T_COLOR = 11, T_SIZE = 12, T_QTY = 13;
    private static float[] setting1 = {1.0f};
    private static float[] setting2 = {0.6f, 0.38f};
    private static float[] setting3 = {0.4f, 0.3f, 0.28f};
    private static int BEGINNING = 0, LAST = -1;
    private List<ProductGroupContainer> varients = new ArrayList<>();
    private ArrayList<String> sizelist = new ArrayList<>();

    private int full_container_width;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private int mCurrentTransitionEffect = JazzyHelper.FADE;
    private BottomSheetBase numberpicker, listpicker;
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
    private supporttype current_choosing_type = supporttype.NONE;

    private enum supporttype {
        VARIANT, COLOR, SIZE, QTY, NONE
    }

    public SelectionSpinner(final Activity act, @IdRes int container_Id) {
        container1 = (LinearLayout) act.findViewById(container_Id);
        mcontext = act;
        container1.removeAllViews();
    }

    public SelectionSpinner setCustomNumberPicker(BottomSheetBase picker) {
        numberpicker = picker;
        numberpicker.setRenderCallback(this);
        return this;
    }

    public SelectionSpinner setCustomListPicker(BottomSheetBase picker) {
        listpicker = picker;
        listpicker.setRenderCallback(this);
        return this;
    }

    public SelectionSpinner addGroupProducts(List<ProductGroupContainer> list) {
        varients = list;
        return this;
    }

    public SelectionSpinner addSizeGroup(ArrayList<String> list) {
        sizelist = list;
        return this;
    }

    public void init() {
        //add_bag.setEnabled(false);
        //  if (container1.getVisibility() != View.GONE || container1.getVisibility() != View.INVISIBLE)
        //     container1.requestLayout();
        container1.requestLayout();
        full_container_width = container1.getWidth();
        refreshSpinner();
        renderSelectionOrder(null);
        setQty("1");
        setQty(1);
    }

    private void addSpace(@DimenRes int resDimension) {
        ImageView imageViewF = (ImageView) ViewUtils.getView(mcontext.getResources().getDimensionPixelSize(resDimension), mcontext);
        container1.addView(imageViewF);
    }

    private void buttonConstruct(int tag_integer, String label, float portion) {
        final Droptouch dt = new Droptouch(mcontext);
        dt.setLabel(label);
        dt.setTag(tag_integer);
        if (portion < 1.0f) {
            //  dt.setPortion(portion, ViewUtils.getWidth(mcontext));
            dt.setPortion(portion, full_container_width);
        }
        dt.setOnClickListener(this);
        listopt.add(dt);
        container1.addView(dt);
    }


    private void addQtybutton(float portion) {
        buttonConstruct(T_QTY, "Qty", portion);
    }

    private void refreshSpinner() {
        chk_var = varients.size() > 0 ? 1 : -1;
        chk_size = sizelist.size() > 0 ? 1 : -1;
        listopt.clear();
        container1.removeAllViews();
        container1.setWeightSum(1.0f);
    }

    public void onConfigurationChange(Configuration c) {
        renderSelectionOrder(c);
    }

    private boolean render_portrait() {
        if (varients.size() == 0 && sizelist.size() == 0) {
            addQtybutton(setting1[0]);
            //  container1.setVisibility(View.GONE);
            return true;
        }
        if (varients.size() > 0 && sizelist.size() > 0) {
            // container1.setVisibility(View.VISIBLE);
            buttonConstruct(T_COLOR, "Color", setting3[0]);
            addSpace(R.dimen.ui_box_drop_down_space);
            buttonConstruct(T_SIZE, "Size", setting3[1]);
            addSpace(R.dimen.ui_box_drop_down_space);
            addQtybutton(setting3[2]);
            return true;
        }
        if (varients.size() > 0 && sizelist.size() == 0) {
            // container1.setVisibility(View.GONE);
            buttonConstruct(T_COLOR, "Color", setting2[0]);
            addSpace(R.dimen.ui_box_drop_down_space);
            addQtybutton(setting2[1]);
            return true;
        }

        if (varients.size() == 0 && sizelist.size() > 0) {
            //  container1.setVisibility(View.GONE);
            buttonConstruct(T_SIZE, "Size", setting2[0]);
            addSpace(R.dimen.ui_box_drop_down_space);
            addQtybutton(setting2[1]);
            return true;
        }
        return false;
    }

    private boolean render_landscape() {
        //  container1.setVisibility(View.GONE);
        if (varients.size() == 0 && sizelist.size() == 0) {
            addQtybutton(setting1[0]);
            return true;
        }

        if (varients.size() > 0 && sizelist.size() > 0) {
            buttonConstruct(T_COLOR, "Color", setting3[0]);
            addSpace(R.dimen.ui_box_drop_down_space);
            buttonConstruct(T_SIZE, "Size", setting3[1]);
            addSpace(R.dimen.ui_box_drop_down_space);
            addQtybutton(setting3[2]);
            return true;
        }

        if (varients.size() > 0 && sizelist.size() == 0) {
            buttonConstruct(T_COLOR, "Color", setting2[0]);
            addSpace(R.dimen.ui_box_drop_down_space);
            addQtybutton(setting2[1]);
            return true;
        }

        if (varients.size() == 0 && sizelist.size() > 0) {
            buttonConstruct(T_SIZE, "Size", setting2[0]);
            addSpace(R.dimen.ui_box_drop_down_space);
            addQtybutton(setting2[1]);
            return true;
        }

        return false;
    }

    private boolean renderSelectionOrder(@Nullable Configuration conf) {
        if (conf == null) {
            return render_portrait();
        }
        if (conf.orientation == Configuration.ORIENTATION_PORTRAIT) {
            return render_portrait();
        } else if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return render_landscape();
        } else {
            return false;
        }
    }


    public boolean foundSize() {
        return chk_size != -1;
    }


    private void set_value_option() {
        //add_bag.setEnabled(instocksize && instockcolor);
    }


    public int theVarianceChoice() {
        return choice_of_variance;
    }

    /**
     * to get the current number of item that selected.
     *
     * @return the number
     */
    public int theQtyChoice() {
        return choice_of_quantity;
    }

    public SelectionSpinner setSize(String t) {
        Droptouch f = findByTag(T_SIZE);
        f.setLabel("Size: " + t);
        return this;
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
     * @param t the selection in color
     * @return the return in this
     */
    public SelectionSpinner setColor(String t) {
        Droptouch f = findByTag(T_COLOR);
        f.setLabel("C: " + t);
        return this;
    }

    public SelectionSpinner setQty(String t) {
        Droptouch c = findByTag(T_QTY);
        c.setLabel("Qty: " + t);
        choice_of_quantity = Integer.parseInt(t);
        if (sizelist.size() > 0 && !instocksize) {
            //add_bag.setEnabled(false);
        } else if (varients.size() > 0 && !instockcolor) {
            //add_bag.setEnabled(false);
        }
        return this;
    }

    private SelectionSpinner setQty(int t) {
        Droptouch c = findByTag(T_QTY);
        c.setLabel("Qty: " + t);
        choice_of_quantity = t;
        return this;
    }

    private Droptouch findByTag(int OJECT_ID) {
        for (Droptouch t : listopt) {
            if ((int) t.getTag() == OJECT_ID) {
                return t;
            }
        }
        return null;
    }

    public void setEnabled(boolean b) {
        for (Droptouch t : listopt) {
            t.setEnabled(b);
        }
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case T_COLOR:
                Log.d("showlistoptions", "Color here to start");
                normalListDialog("Color", varients);
                break;

            case T_SIZE:
                Log.d("showlistoptions", "Size here to start");
                sizeListDialog("Size dialog", sizelist);
                break;

            case T_QTY:
                showDialogPickerNumber();
                break;
        }
    }

    public static final int SELECT_RETURNABLE = -2439;
    public static final String LIST = "thelistoptions";

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
     * shows the number picker dialog at the screen
     */
    private void normalListDialog(String title, List<ProductGroupContainer> list) {
        if (list.size() == 0) return;
        current_choosing_type = supporttype.VARIANT;
        if (listpicker == null) {
            defaultnormalListDialog(title, list);
        } else {
            if (listpicker instanceof SingleStringBS) {
                SingleStringBS dialog = (SingleStringBS) listpicker;
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
        if (numberpicker == null) {
            defaultnumberpicker();
        } else {
            numberpicker.show(mcontext.getFragmentManager().beginTransaction(), "NumPicker");
        }
    }

    /**
     * show the list choices now
     *
     * @param title string in title
     * @param items list in array string
     */
    private void showListChoices(final String title, final String[] items, final int symbo) {
        final AlertDialog.Builder listchoices = new AlertDialog.Builder(mcontext);
        listchoices.setTitle(title).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(mcontext, "U clicked " + items[which], Toast.LENGTH_LONG).show();
                switch (symbo) {
                    case T_COLOR:
                        //setOption(items[which], which, T_COLOR);
                        break;
                    case T_SIZE:
                        //setOption(items[which], which, T_SIZE);
                        break;
                }
            }
        });
        listchoices.show();
    }

    /**
     * start with the new intent with the special list items
     *
     * @param action_type start new intent for the options
     * @param stringlist  start the new intent for the options
     */
    private <T> void display_options(final int action_type, String[] stringlist, Class<T> clazz) {
        final Intent it = new Intent(mcontext, clazz);
        final Bundle f = new Bundle();
        f.putInt("selection_view_type", action_type);
        it.putExtras(f);
        if (stringlist != null) {
            if (stringlist.length > 0) {
                f.putStringArray(LIST, stringlist);
            }
        }
        mcontext.startActivityForResult(it, SELECT_RETURNABLE);
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
        np.setMaxValue(99); // max value 100
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
                setSize(list.get(position));
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
                setColor(varients.get(position).getTitle());
                d.dismiss();
            }
        }));
        jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        list.addOnScrollListener(jazzyScrollListener);
        d.show();
    }


    public void initInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentTransitionEffect = savedInstanceState.getInt(KEY_TRANSITION_EFFECT, JazzyHelper.HELIX);
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
            chk_qty = quantity;
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
            chk_size = itemPosition;
            setSize(data_item);
        } else if (current_choosing_type == supporttype.VARIANT) {
            chk_var = itemPosition;
            setColor(data_item);
            cb.onUpdateRelatedProduct(chk_var, chk_qty);
        }
    }

    public void triggerAddBag() {
        cb.onSubmission(chk_var, chk_size, chk_qty);
    }

    public void setSubmissionCallBack(spinnerCallBack cb) {
        this.cb = cb;
    }

    public void invalidate() {
        container1.requestLayout();
    }
}
