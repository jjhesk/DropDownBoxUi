package hkm.ui.ddbox.lib.data;

/**
 * Created by hesk on 31/8/15.
 */
public class ProductGroupContainer {
    private String href = "";
    private String title = "";

    public ProductGroupContainer() {
    }

    public ProductGroupContainer(String title, String href) {
        this.title = title;
        this.href = href;
    }

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

}
