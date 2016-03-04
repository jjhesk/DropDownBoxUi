package hkm.ui.ddbox.lib.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hesk on 31/8/15.
 */
public class ProductGroupContainer implements Parcelable {
    private String href = "";
    private String title = "";

    public ProductGroupContainer() {
    }

    public ProductGroupContainer(String title, String href) {
        this.title = title;
        this.href = href;
    }

    protected ProductGroupContainer(Parcel in) {
        href = in.readString();
        title = in.readString();
    }

    public static final Creator<ProductGroupContainer> CREATOR = new Creator<ProductGroupContainer>() {
        @Override
        public ProductGroupContainer createFromParcel(Parcel in) {
            return new ProductGroupContainer(in);
        }

        @Override
        public ProductGroupContainer[] newArray(int size) {
            return new ProductGroupContainer[size];
        }
    };

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    public void setHref(String h) {
        href = h;
    }

    public void setTitle(String h) {
        title = h;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
        dest.writeString(title);
    }
}
