package hkm.ui.ddbox.lib;

import java.util.List;

/**
 * Created by hesk on 4/3/16.
 */
public interface icontainer {
    void showDialogPickerNumber();

    void showPickerListStringDialog(String title, List<String> list);

    void showFragmentDialog();
}
