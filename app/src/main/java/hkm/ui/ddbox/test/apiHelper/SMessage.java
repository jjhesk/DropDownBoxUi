package hkm.ui.ddbox.test.apiHelper;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by hesk on 3/11/15.
 */
public class SMessage extends DialogFragment {
    public static Runnable onclick;

    public static void showAlert(final String message, FragmentManager fm) {
        final SMessage mss = SMessage.message(message);
        mss.show(fm, "con_err");
    }

    public static void showAlert(final String message, FragmentManager fm, Runnable nextAction) {
        final SMessage mss = SMessage.message(message);
        mss.show(fm, "con_err");
        onclick = nextAction;
    }

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
                        if (onclick != null) {
                            onclick.run();
                            onclick = null;
                        }
                    }
                });
        return builder.create();
    }

}
