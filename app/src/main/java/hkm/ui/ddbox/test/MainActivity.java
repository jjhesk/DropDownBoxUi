package hkm.ui.ddbox.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import hkm.ui.ddbox.lib.Droptouch;
import hkm.ui.ddbox.lib.SelectionSpinner;
import hkm.ui.ddbox.lib.ViewUtils;
import hkm.ui.ddbox.lib.data.ProductGroupContainer;


public class MainActivity extends AppCompatActivity {
    private float[] por3 = {
            0.4f, 0.3f, 0.3f
    };
    private float[] por2 = {
            0.6f, 0.4f
    };
    private float sum = 1f;
    private String[] labels = {"cate", "size", "qty"};
    private Button redraw, triggererror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redraw = (Button) findViewById(R.id.button_redraw);
        triggererror = (Button) findViewById(R.id.button_error);


       /* LinearLayout g = (LinearLayout) findViewById(R.id.lvt);
        for (int i = 0; i < 3; i++) {
            final Droptouch d = new Droptouch(this);
            d
                    .setWidthPixel((float) ViewUtils.getWidth(this) / 3f)
                    .setLabel(labels[i])
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "click this now", Toast.LENGTH_SHORT);
                        }
                    });
            g.addView(d);
        }
        g.requestLayout();*/

        final SelectionSpinner n = new SelectionSpinner(this, R.id.lvt);
        ArrayList<ProductGroupContainer> p = new ArrayList<>();
        p.add(new ProductGroupContainer("A", "ifosnffse"));
        p.add(new ProductGroupContainer("B tis is i", "ifosnffse"));
        p.add(new ProductGroupContainer("V cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("H cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("HH cloan noi niosnn n", "ifosnffse"));
        p.add(new ProductGroupContainer("BS cloan noi niosnn n", "ifosnffse"));
        n.addGroupProducts(p);
        ArrayList<String> nh = new ArrayList<>();
        nh.add("M+");
        nh.add("GM+");
        nh.add("EM+");
        nh.add("VM+");
        n.addSizeGroup(nh);
        n.init();


        redraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n.init();
            }
        });


        triggererror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  n.init();
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
