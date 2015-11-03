package com.hypebeast.sdk.clients;

import android.content.Context;
import android.os.Build;

import com.google.gson.GsonBuilder;
import com.hypebeast.sdk.Constants;
import com.hypebeast.sdk.Util.CookieHanger;
import com.hypebeast.sdk.api.gson.GsonFactory;
import com.hypebeast.sdk.api.gson.MissingCharacterConversion;
import com.hypebeast.sdk.api.gson.RealmExclusion;
import com.hypebeast.sdk.api.gson.StringConverter;
import com.hypebeast.sdk.api.model.hypebeaststore.ResponseMobileOverhead;
import com.hypebeast.sdk.api.model.symfony.Product;
import com.hypebeast.sdk.api.realm.QLRealmString;
import com.hypebeast.sdk.api.realm.hbx.rProduct;
import com.hypebeast.sdk.api.resources.hbstore.Authentication;
import com.hypebeast.sdk.api.resources.hbstore.Brand;
import com.hypebeast.sdk.api.resources.hbstore.Overhead;
import com.hypebeast.sdk.api.resources.hbstore.Products;
import com.hypebeast.sdk.api.resources.hbstore.SingleProduct;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by hesk on 30/6/15.
 */
public class HBStoreApiClient extends Client {

    /**
     * Base URL for all Disqus endpoints
     */
    private static final String BASE_URL_STORE = "https://store.hypebeast.com/";
    private static final String AUTHENTICATION = "http://hypebeast.com/";
    private static final String BASE_LOGIN = "https://disqus.com/api";
    /**
     * User agent
     */
    private static final String USER_AGENT = "HypebeastStoreApp/1.0 Android" + Build.VERSION.SDK_INT;

    /**
     * login adapter
     */
    private RestAdapter mLoginAdapter;
    private static HBStoreApiClient static_instance;

    public static HBStoreApiClient newInstance() {
        return new HBStoreApiClient();
    }

    public static HBStoreApiClient getInstance() {
        if (static_instance == null) {
            static_instance = newInstance();
            return static_instance;
        } else {
            return static_instance;
        }
    }

    public HBStoreApiClient() {
        super();
    }


    @Override
    protected void registerAdapter() {
        mAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL_STORE)
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setErrorHandler(handlerError)
                .setRequestInterceptor(getIn())
                .setConverter(new GsonConverter(gsonsetup))
                .build();
    }

    @Override
    protected String get_USER_AGENT() {
        return USER_AGENT;
    }

    @Override
    protected void jsonCreate() {
        gsonsetup = new GsonBuilder()
                .setDateFormat(Constants.DATE_FORMAT)
   /*             .registerTypeAdapter(Usage.class, new ApplicationsUsageDeserializer())
                .registerTypeAdapterFactory(new BlacklistsEntryTypeAdapterFactory())
                .registerTypeAdapterFactory(new PostTypeAdapterFactory())
                .registerTypeAdapterFactory(new ThreadTypeAdapterFactory())*/
                .registerTypeAdapterFactory(new GsonFactory.NullStringToEmptyAdapterFactory())
                .registerTypeAdapter(String.class, new MissingCharacterConversion())
                .setExclusionStrategies(new RealmExclusion())
                .create();
    }

    private RestAdapter fullEndpoint(final String endpoint) {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(handlerError)
                .setRequestInterceptor(getIn())
                .setConverter(new GsonConverter(gsonsetup))
                .build();
    }

    public Authentication createAuthentication() {
        RestAdapter mAdapter = new RestAdapter.Builder()
                .setEndpoint(AUTHENTICATION)
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setErrorHandler(handlerError)
                .setRequestInterceptor(getIn())
                .setConverter(new StringConverter())
                .build();

        return mAdapter.create(Authentication.class);
    }

    public Products createProducts() {
        return mAdapter.create(Products.class);
    }

    public Overhead createOverHead() {
        return mAdapter.create(Overhead.class);
    }

    public Brand createBrand() {
        return mAdapter.create(Brand.class);
    }

    public SingleProduct createRequest() {
        return mAdapter.create(SingleProduct.class);
    }

    public String fromJsonToString(ResponseMobileOverhead mFoundation) {
        return "";
    }

    public ResponseMobileOverhead fromsavedConfiguration(String mFoundation_string) {
        return gsonsetup.fromJson(mFoundation_string, ResponseMobileOverhead.class);
    }

    private CookieHanger getCookieClient() {
        return CookieHanger.base(BASE_URL_STORE);
    }

    @Override
    protected RequestInterceptor getIn() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("User-Agent", get_USER_AGENT());
                request.addHeader("Accept", "application/json");
                request.addHeader("X-Api-Version", "2.0");
                request.addHeader("Cookie", getCookieClient().getRaw());
            }
        };
    }

    /**
     * the get the number of current shopping cart item count
     *
     * @return the count in number
     */
    public int retrieve_current_shopping_cart_items() {

        String number = getCookieClient().getValue("_store_item_count");
        return Integer.parseInt(number);
    }

    public void addToWishList(Context basethread, Product copyproduct) {
        Realm realm = Realm.getInstance(basethread);
        if (check_saved_wishlist(realm, copyproduct.product_id)) return;
        realm.beginTransaction();
        rProduct _product = realm.createObject(rProduct.class);
        _product.setLinks(copyproduct._links.self.href);
        _product.setImageHead(copyproduct.images.get(0).data.medium.href);
        _product.setProduct_id(copyproduct.product_id);
        _product.setCreated_at(copyproduct.created_at);
        _product.setDescription(copyproduct.description);
        _product.setName(copyproduct.name);
        _product.setPrice(copyproduct.price);
        _product.setBrandname(copyproduct.get_brand_name());
        realm.commitTransaction();
    }

    public void flushWishList(Context basethread) {
        Realm realm = Realm.getInstance(basethread);
        RealmResults<rProduct> copies = realm.where(rProduct.class).findAll();
        realm.beginTransaction();
        copies.clear();
        realm.commitTransaction();
    }

    public void removeItem(Context basethread, long product_id) {
        Realm realm = Realm.getInstance(basethread);
        RealmResults<rProduct> copies = realm.where(rProduct.class).equalTo("product_id", product_id).findAll();
        realm.beginTransaction();
        copies.clear();
        realm.commitTransaction();
    }

    public void removeItem(Context basethread, rProduct item) {
        Realm realm = Realm.getInstance(basethread);
        RealmResults<rProduct> copies = realm.where(rProduct.class).equalTo("product_id", item.getProduct_id()).findAll();
        realm.beginTransaction();
        copies.clear();
        realm.commitTransaction();
    }

    public List<rProduct> getWishListAll(Context basethread) {
        Realm realm = Realm.getInstance(basethread);
        RealmResults<rProduct> copies = realm.where(rProduct.class).findAll();
        return copies;
    }

    public boolean check_saved_wishlist(Realm realm, long product_id) {
        RealmQuery<rProduct> query = realm.where(rProduct.class);
        query.equalTo("product_id", product_id);
        // Execute the query:
        RealmResults<rProduct> result = query.findAll();
        return result.size() > 0;
    }

    public boolean check_saved_wishlist(Context basethread, long product_id) {
        Realm realm = Realm.getInstance(basethread);
        return check_saved_wishlist(realm, product_id);
    }

    private boolean check_saved_keyword(Context basethread, String keyword) {
        Realm realm = Realm.getInstance(basethread);
        RealmQuery<QLRealmString> query = realm.where(QLRealmString.class);
        query.equalTo("val", keyword);
        // Execute the query:
        RealmResults<QLRealmString> result = query.findAll();
        return result.size() > 0;
    }

    public void addKeyWord(Context basethread, String keyword) {
        Realm realm = Realm.getInstance(basethread);
        if (check_saved_keyword(basethread, keyword)) return;
        realm.beginTransaction();
        QLRealmString _ql = realm.createObject(QLRealmString.class);
        _ql.setVal(keyword);
        realm.commitTransaction();
    }

    public String[] getSavedKeywords(Context base) {
        Realm realm = Realm.getInstance(base);
        RealmQuery<QLRealmString> query = realm.where(QLRealmString.class);
        RealmResults<QLRealmString> result = query.findAll();
        return result.toArray(new String[result.size()]);
    }
}
