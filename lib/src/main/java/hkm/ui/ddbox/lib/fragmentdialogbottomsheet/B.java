package hkm.ui.ddbox.lib.fragmentdialogbottomsheet;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hkm.ui.ddbox.lib.data.ProductGroupContainer;

/**
 * Created by hesk on 4/3/16.
 */
public class B {
    public final static String HM = "source_title";
    public final static String HLink = "source_link";
    public final static String MAX = "number_picker_max";
    public final static String START = "number_picker_start";
    public final static String TOOL_BAR_TITLE = "dialog_title";

    public static Bundle getListChoices(String title, List<ProductGroupContainer> list) {
        Bundle b = new Bundle();
        Iterator<ProductGroupContainer> ir = list.iterator();
        ArrayList<String> l1 = new ArrayList<>();
        ArrayList<String> l2 = new ArrayList<>();
        while (ir.hasNext()) {
            ProductGroupContainer p = ir.next();
            //hm.put(p.getTitle(), p.getHref());
            l1.add(p.getTitle());
            l2.add(p.getHref());
        }
        b.putStringArrayList(HM, l1);
        b.putStringArrayList(HLink, l2);
        b.putString(TOOL_BAR_TITLE, title);
        return b;
    }

    public static Bundle getListStringChoices(String title, ArrayList<String> list) {
        Bundle b = new Bundle();
        b.putStringArrayList(HM, list);
        b.putString(TOOL_BAR_TITLE, title);
        return b;
    }


    public static Bundle numberPickerDataBundle(
            final String title,
            final int max_num,
            final int start_at
    ) {
        Bundle b = new Bundle();
        b.putInt(MAX, max_num);
        b.putInt(START, start_at);
        b.putString(TOOL_BAR_TITLE, title);
        return b;
    }


}
