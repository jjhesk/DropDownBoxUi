package hkm.ui.ddbox.test.apiHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;

import com.hypebeast.sdk.api.model.hypebeaststore.ResponseSingleProduct;
import com.hypebeast.sdk.api.model.symfony.Product;
import com.hypebeast.sdk.api.model.symfony.Variant;
import com.hypebeast.sdk.api.model.symfony.VariantItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hkm.ui.ddbox.lib.SelectionSpinner;
import hkm.ui.ddbox.lib.bottomsheet.BottomSheetDialogFragment;
import hkm.ui.ddbox.lib.bottomsheet.NumberPickerDialog;
import hkm.ui.ddbox.lib.bottomsheet.SingleStringBS;
import hkm.ui.ddbox.lib.bottomsheet.onCallBackSimple;
import hkm.ui.ddbox.lib.data.ProductGroupContainer;
import hkm.ui.ddbox.test.R;

/**
 * Created by hesk on 24/9/15.
 */
public class SelectionHelper implements ResponseSingleProduct {
    private SelectionSpinner mainlogic;
    private SingleStringBS mDialogList;
    private NumberPickerDialog mDialogPicker;
    private Activity activity;

    public SelectionHelper(Activity activity, @IdRes int container) {
        this.activity = activity;
        mainlogic = new SelectionSpinner(activity, container);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        ArrayList<ProductGroupContainer> p = new ArrayList<>();
        p.add(new ProductGroupContainer("A", "ifosnffse"));
        p.add(new ProductGroupContainer("B tis is i", "ifosnffse"));
        p.add(new ProductGroupContainer("V cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("H cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("HH cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("BS cloan noi niosnn n", "ifosnffse"));
        mainlogic.addGroupProducts(p);
        ArrayList<String> nh = new ArrayList<>();
        nh.add("M+");
        nh.add("GM+");
        nh.add("EM+");
        nh.add("VM+");
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        mainlogic.addSizeGroup(nh);
        mainlogic.init();
    }

    public SelectionHelper(Activity activity, @IdRes int container, String starting_url) {
        this.activity = activity;
        mainlogic = new SelectionSpinner(activity, container);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        mainlogic.init();
    }

    public void successLoadedData_demo(Product mProduct) {
        mainlogic.addGroupProducts(getGroup(mProduct));
        addVariance(mProduct, mainlogic);
        mainlogic.init();
    }

    public SelectionHelper(Activity activity, @IdRes int container, Product mProduct) {
        this.activity = activity;
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic = new SelectionSpinner(activity, container);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.addGroupProducts(getGroup(mProduct));
        addVariance(mProduct, mainlogic);
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        mainlogic.init();
    }


    public void addVariance(Product mProduct, SelectionSpinner listObject) {
        final ArrayList<Variant> list;
        try {
            list = mProduct.getMappedVariants();
            final ArrayList<String> colorlist = new ArrayList<>();
            final ArrayList<String> sizelist = new ArrayList<>();
            for (Variant t : list) {
                final String r = t.getMetaValueFromType() + t.stock_logic_configuration();
                if (t.getTypeConstant() == VariantItem.VType.Color) {
                    colorlist.add(r);
                } else if (t.getTypeConstant() == VariantItem.VType.Size) {
                    sizelist.add(r);
                }
            }
            listObject.addSizeGroup(sizelist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // variances.clear();
        //  variances.addAll(list);
    }

    private List<ProductGroupContainer> getGroup(Product mproduct) {
        ArrayList<ProductGroupContainer> p = new ArrayList<>();
        ArrayList<com.hypebeast.sdk.api.model.symfony.ProductGroupContainer> group = mproduct.getProductGroupContainer();
        if (group == null) return p;
        Iterator<com.hypebeast.sdk.api.model.symfony.ProductGroupContainer> ig = group.iterator();
        while (ig.hasNext()) {
            com.hypebeast.sdk.api.model.symfony.ProductGroupContainer item = ig.next();
            p.add(new ProductGroupContainer(item.title, item.href));
        }

        return p;
    }

    private ArrayList<String> getSize(Product mproduct) {
        ArrayList<String> p = new ArrayList<>();
        return p;
    }

    public void init() {
        mainlogic.init();
    }

    private SingleStringBS prepareSingleStringListBS() {
        final Bundle bloop = new Bundle();
        bloop.putInt(BottomSheetDialogFragment.MEASUREMENT_HEIGHT, (int) activity.getResources().getDimension(R.dimen.ui_bs_dialog_height));
        final SingleStringBS instance = SingleStringBS.newInstace(bloop);
        return instance;
    }

    private NumberPickerDialog prepareNumberPickerBS() {
        final Bundle bloop = new Bundle();
        bloop.putInt(BottomSheetDialogFragment.MEASUREMENT_HEIGHT, (int) activity.getResources().getDimension(R.dimen.ui_bs_dialog_height));
        bloop.putString(BottomSheetDialogFragment.DIALOG_TITLE, "Quantity");
        final NumberPickerDialog instance = NumberPickerDialog.newInstace(bloop);
        return instance;
    }


}
