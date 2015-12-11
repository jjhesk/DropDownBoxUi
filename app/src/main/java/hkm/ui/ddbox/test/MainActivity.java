package hkm.ui.ddbox.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import hkm.ui.ddbox.test.apiHelper.SelectionNewHelper;


public class MainActivity extends AppCompatActivity {
    private float[] por3 = {0.4f, 0.3f, 0.3f};
    private float[] por2 = {0.6f, 0.4f};
    private float sum = 1f;
    private String[] labels = {"cate", "size", "qty"};
    private Button redraw, triggererror, button_addcart;
    private SelectionNewHelper wrapped_object;
    private FrameLayout holder_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redraw = (Button) findViewById(R.id.button_redraw);
        triggererror = (Button) findViewById(R.id.button_error);
        button_addcart = (Button) findViewById(R.id.button_addcart);
        holder_container = (FrameLayout) findViewById(R.id.selection_holder);
        wrapped_object = new SelectionNewHelper(this, holder_container, 49910);
        redraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapped_object.init();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
