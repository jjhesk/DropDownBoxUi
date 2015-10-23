package hkm.ui.ddbox.test.apiHelper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.hypebeast.sdk.api.exception.ApiException;
import com.hypebeast.sdk.api.model.hypebeaststore.ResponseSingleProduct;
import com.hypebeast.sdk.api.model.symfony.Product;
import com.hypebeast.sdk.api.model.symfony.Variant;
import com.hypebeast.sdk.api.model.symfony.VariantItem;
import com.hypebeast.sdk.api.resources.hbstore.SingleProduct;
import com.hypebeast.sdk.clients.HBStoreApiClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hkm.ui.ddbox.lib.SelectionSpinner;
import hkm.ui.ddbox.lib.bottomsheet.BottomSheetDialogFragment;
import hkm.ui.ddbox.lib.bottomsheet.NumberPickerDialog;
import hkm.ui.ddbox.lib.bottomsheet.SingleStringBS;
import hkm.ui.ddbox.lib.bottomsheet.onCallBackSimple;
import hkm.ui.ddbox.lib.bottomsheet.spinnerCallBack;
import hkm.ui.ddbox.lib.data.ProductGroupContainer;
import hkm.ui.ddbox.test.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hesk on 24/9/15.
 */
public class SelectionHelper implements Callback<ResponseSingleProduct>, spinnerCallBack {
    private SelectionSpinner mainlogic;

    private SingleStringBS mDialogList;
    private NumberPickerDialog mDialogPicker;
    private Activity activity;
    private SingleProduct client;
    private Product mProductContainer;

    public SelectionHelper(final Activity activity, final @IdRes int container) {
        this.activity = activity;
        mainlogic = new SelectionSpinner(activity, container);
        mainlogic.setSubmissionCallBack(this);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);

        //hardcode
        ArrayList<ProductGroupContainer> p = new ArrayList<>();
        p.add(new ProductGroupContainer("A", "ifosnffse"));
        p.add(new ProductGroupContainer("B tis is i", "ifosnffse"));
        p.add(new ProductGroupContainer("V cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("H cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("HH cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("BS cloan noi niosnn n", "ifosnffse"));

        ArrayList<String> nh = new ArrayList<>();
        nh.add("M+");
        nh.add("GM+");
        nh.add("EM+");
        nh.add("VM+");

        mainlogic.addGroupProducts(p);
        mainlogic.addSizeGroup(nh);
        mainlogic.init();
    }

    public SelectionHelper(final Activity activity, final @IdRes int container, final String starting_url) {
        this.activity = activity;
        mainlogic = new SelectionSpinner(activity, container);
        mainlogic.setSubmissionCallBack(this);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        client = HBStoreApiClient.newInstance().createRequest();
        Uri t = Uri.parse(starting_url);
        try {
            client.FullPathReq(t.getPathSegments().get(0), t.getPathSegments().get(1), t.getPathSegments().get(2), this);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public SelectionHelper(final Activity activity, final @IdRes int container, final long product_id) {
        this.activity = activity;
        mainlogic = new SelectionSpinner(activity, container);
        mainlogic.setSubmissionCallBack(this);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        client = HBStoreApiClient.newInstance().createRequest();
        try {
            client.PIDReq(product_id, this);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    public SelectionHelper(final Activity activity, final @IdRes int container, final Product mProduct) {
        this.activity = activity;
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic = new SelectionSpinner(activity, container);
        mainlogic.setSubmissionCallBack(this);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        payload(mProduct);
    }


    public void addVariance(final Product mProduct, final SelectionSpinner listObject) {
        if (mProduct.hasVariance()) {
            try {
                final ArrayList<Variant> list = mProduct.getMappedVariants();
                final ArrayList<String> sizelist = new ArrayList<>();
                for (Variant t : list) {
                    final String r = t.getMetaValueFromType() + t.stock_logic_configuration();
                    if (t.getTypeConstant() == VariantItem.VType.Size) {
                        sizelist.add(r);
                    }
                }
                listObject.addSizeGroup(sizelist);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void init() {
        mainlogic.init();
    }

    private SingleStringBS prepareSingleStringListBS() {
        final Bundle bloop = new Bundle();
        bloop.putInt(BottomSheetDialogFragment.MEASUREMENT_HEIGHT, (int) activity.getResources().getDimension(R.dimen.ui_bs_dialog_height));
        bloop.putFloat(BottomSheetDialogFragment.MEASUREMENT_HEIGHT, (int) activity.getResources().getDimension(R.dimen.ui_bs_dialog_height));
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


    /**
     * Successful HTTP response.
     *
     * @param responseSingleProduct only in demo
     * @param response              test only in demo
     */
    @Override
    public void success(ResponseSingleProduct responseSingleProduct, Response response) {
        if (onExploreRelatedProduct) {
            onExploreMoreProduct(responseSingleProduct.mproduct);
        } else
            payload(responseSingleProduct.mproduct);
    }

    private void onExploreMoreProduct(Product mproduct) {
        mProductContainer = mproduct;
        addVariance(mproduct, mainlogic);
        mainlogic.setSize("");
    }

    public void payload(Product mProduct) {
        mainlogic.addGroupProducts(getGroup(mProduct));
        mProductContainer = mProduct;
        addVariance(mProduct, mainlogic);
        mainlogic.init();
    }

    /**
     * Unsuccessful HTTP response due to network failure, non-2XX status code, or unexpected
     * exception.
     *
     * @param error only in demo
     */
    @Override
    public void failure(RetrofitError error) {
        final SMessage mss = SMessage.message("Connection error." + error.getMessage());
        mss.show(activity.getFragmentManager(), "con_err");
    }

    public void confirm() {
        mainlogic.triggerAddBag();
    }

    private int holding_quantity = 0;
    private boolean onExploreRelatedProduct = false;

    @Override
    public void onSelectRelatedProduct(int group, int quantity) {
        try {
            final String href = mProductContainer.getProductGroupContainer().get(group).href;
            final List<String> h = Uri.parse(href).getPathSegments();
            holding_quantity = quantity;
            onExploreRelatedProduct = true;
            client.FullPathReq(h.get(0), h.get(1), h.get(2), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSubmission(int group, int size, int quantity) {
        try {
            if (mProductContainer.hasVariance()) {
                if (mProductContainer.getMappedVariants().get(size).stock_available() >= quantity) {

                } else {
                    final SMessage mss = SMessage.message("Stock error. we dont have enough of these");
                    mss.show(activity.getFragmentManager(), "con_err");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("ValidFragment")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SMessage extends DialogFragment {
        public static SMessage message(final String mes) {
            Bundle h = new Bundle();
            h.putString("message", mes);
            SMessage e = new SMessage();
            e.setArguments(h);
            return e;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getArguments().getString("message"))
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }
}
