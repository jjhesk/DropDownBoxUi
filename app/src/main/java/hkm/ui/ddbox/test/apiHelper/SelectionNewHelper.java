package hkm.ui.ddbox.test.apiHelper;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;

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

import hkm.ui.ddbox.lib.SelectionSP;
import hkm.ui.ddbox.lib.SelectionSpinner;
import hkm.ui.ddbox.lib.bottomsheet.BottomSheetDialogFragment;
import hkm.ui.ddbox.lib.bottomsheet.NumberPickerDialog;
import hkm.ui.ddbox.lib.bottomsheet.SingleStringBS;
import hkm.ui.ddbox.lib.bottomsheet.spinnerCallBack;
import hkm.ui.ddbox.lib.data.ProductGroupContainer;
import hkm.ui.ddbox.test.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hesk on 3/11/15.
 */
public class SelectionNewHelper implements Callback<ResponseSingleProduct>, spinnerCallBack {
    private SelectionSP mainlogic;

    private SingleStringBS mDialogList;
    private NumberPickerDialog mDialogPicker;
    private Activity activity;
    private SingleProduct client;
    private Product mProductContainer;

    public SelectionNewHelper(final Activity activity, final @IdRes int container) {
        this.activity = activity;
        mainlogic = new SelectionSP(activity, container);
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
        mainlogic.build();
    }

    public SelectionNewHelper(final Activity activity, final @IdRes int container, final String starting_url) {
        this.activity = activity;
        mainlogic = new SelectionSP(activity, container);
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

    public SelectionNewHelper(final Activity activity, final @IdRes int container, final long product_id) {
        this.activity = activity;
        mainlogic = new SelectionSP(activity, container);
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


    public SelectionNewHelper(final Activity activity, final @IdRes int container, final Product mProduct) {
        this.activity = activity;
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic = new SelectionSP(activity, container);
        mainlogic.setSubmissionCallBack(this);
        mDialogList = prepareSingleStringListBS();
        mDialogPicker = prepareNumberPickerBS();
        mainlogic.setCustomListPicker(mDialogList);
        mainlogic.setCustomNumberPicker(mDialogPicker);
        payload(mProduct);
    }


    public void addVariance(final Product mProduct, final SelectionSP listObject) {
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
        mainlogic.build();
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
        mainlogic.setSizeOption(0);
    }

    public void payload(Product mProduct) {
        mainlogic.addGroupProducts(getGroup(mProduct));
        mProductContainer = mProduct;
        addVariance(mProduct, mainlogic);
        mainlogic.build();
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
    public void onSubmission(int group, final int size, int quantity) {
        try {
            final int q = quantity > 1 ? quantity : 1;

            if (mProductContainer.hasVariance()) {
                if (size < 0) {
                    SMessage.showAlert("Stock error. You have not selected the variance above.", activity.getFragmentManager());
                    return;
                }

                if (mProductContainer.getMappedVariants().get(size).instock()) {
                    if (mProductContainer.getMappedVariants().get(size).isLastOne() && q > 1) {
                        SMessage.showAlert("There is only one item available at this moment. The quantity will be adjusted to one instead of '" + q + "'", activity.getFragmentManager(), new Runnable() {

                            /**
                             * Starts executing the active part of the class' code. This method is
                             * called when a thread is started that has been created with a class which
                             * implements {@code Runnable}.
                             */
                            @Override
                            public void run() {
                                mainlogic.setQuantityOption(1);
                                //     successfulselecteditem(mProductContainer.product_id, mProductContainer.getVariantID(size), 1);
                            }
                        });
                    } else {
                        //   successfulselecteditem(mProductContainer.product_id, mProductContainer.getVariantID(size), q);
                    }
                } else {
                    SMessage.showAlert("Stock error. we don't have enough of these", activity.getFragmentManager());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
