package hkm.ui.ddbox.lib.data;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.LinkedHashMap;
import java.util.List;

import hkm.ui.ddbox.lib.R;

/**
 * Created by hesk on 31/8/15.
 */
public class LiAdapter extends ArrayAdapter<ProductGroupContainer> {

    private final LayoutInflater inflater;
    private final Resources res;
    private final int itemLayoutRes;


    public LiAdapter(Context context, List<ProductGroupContainer> list) {
        super(context, R.layout.item, R.id.alsx_text, list);
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        this.itemLayoutRes = R.layout.item;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(itemLayoutRes, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.text.setBackgroundColor(res.getColor(Utils.getBackgroundColorRes(position, itemLayoutRes)));
        holder.text.setText(getItem(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        final TextView text;

        ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.text);
        }
    }

}
