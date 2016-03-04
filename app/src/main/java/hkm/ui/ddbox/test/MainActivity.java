package hkm.ui.ddbox.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.flipboard.bottomsheet.BottomSheetLayout;

import hkm.ui.ddbox.test.apiHelper.SelectionNewHelper;


public class MainActivity extends AppCompatActivity {
    private float[] por3 = {0.4f, 0.3f, 0.3f};
    private float[] por2 = {0.6f, 0.4f};
    private float sum = 1f;
    private String[] labels = {"cate", "size", "qty"};
    private Button redraw, triggererror, button_addcart;
    private SelectionNewHelper wrapped_object;
    private FrameLayout holder_container;
    private BottomSheetLayout bslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redraw = (Button) findViewById(R.id.button_redraw);
        triggererror = (Button) findViewById(R.id.button_error);
        button_addcart = (Button) findViewById(R.id.button_addcart);
        holder_container = (FrameLayout) findViewById(R.id.selection_holder);
        bslayout = (BottomSheetLayout) findViewById(R.id.actionsheet);

        wrapped_object = new SelectionNewHelper(this, holder_container, 49910);
        wrapped_object.addFlipBoardBottomSheet(R.id.actionsheet);
        redraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapped_object.init(getSupportFragmentManager());
            }
        });

        triggererror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  wrapped_object.invalidate();
            }
        });

        button_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapped_object.confirm();
            }
        });


        wrapped_object.init(getSupportFragmentManager());
    }


}