package hkm.ui.ddbox.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import hkm.ui.ddbox.lib.Droptouch;


public class MainActivity extends ActionBarActivity {
    private int[] por = {
            4, 2, 4
    };
    private String[] labels = {"cate", "size", "qty"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout g = (LinearLayout) findViewById(R.id.lvt);
        for (int i = 0; i < 3; i++) {
            final Droptouch d = new Droptouch(this);
            //d.setLabel(labels[i]).setWidthHardCode(100);
            d.initPortionCalculation(this).setPortion(por[i], 10);
            g.addView(d);
        }
        g.requestLayout();
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
