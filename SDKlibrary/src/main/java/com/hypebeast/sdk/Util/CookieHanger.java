package com.hypebeast.sdk.Util;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.CookieManager;

/**
 * Created by hesk on 29/9/15.
 */
public class CookieHanger {
    protected String _raw_cookie;

    private String domain;
    private CookieManager instance;

    public CookieHanger() {
    }

    public static CookieHanger base(String url) {
        final CookieHanger c = new CookieHanger();
        c.setDomain(url);
        return c;
    }

    private void setDomain(String domain) {
        this.domain = domain;
        instance = CookieManager.getInstance();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void invalidate() {
        instance.flush();
    }

    public String getRaw() {
        if (_raw_cookie == null) {
            _raw_cookie = instance.getCookie(this.domain);
            if (_raw_cookie == null) return "";
            else return _raw_cookie;
        } else return _raw_cookie;
    }

    public String getValue(String key_name) {
        // Cookieの保存が予想されるURLの場合
        // WebViewのCookieを取得
        _raw_cookie = instance.getCookie(this.domain);
        //System.out.println("cookie - " + cookie);
        final String[] _raw_cookie_array = _raw_cookie.split(";");
        for (String keyValue : _raw_cookie_array) {
            keyValue = keyValue.trim();
            String[] cookieSet = keyValue.split("=");
            final String cookie_key = cookieSet[0];
            final String cookie_val = cookieSet[1];
            // Cookieを作成
            //  BasicClientCookie bCookie = new BasicClientCookie(cookie_key, cookie_val);
            ///   bCookie.setDomain("lab.hisasann.com");
            //    bCookie.setPath("/");
            // CookieStoreを取得
            //     CookieStore store = httpClient.getCookieStore();
            // Cookieを追加
            //   store.addCookie(bCookie);
            if (cookie_key.trim().equalsIgnoreCase(key_name)) {
                return cookie_val.trim();
            }
        }
        return "";
    }

}
