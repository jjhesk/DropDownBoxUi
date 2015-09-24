package hkm.ui.ddbox.lib.data;

/**
 * Created by hesk on 31/8/15.
 */


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hkm.ui.ddbox.lib.R;

public class SampleAdapter extends Adapter<SampleAdapter.SampleViewHolder> {

    private List<String> items1 = new ArrayList<>();
    private List<ProductGroupContainer> items2 = new ArrayList<>();

   // private int itemLayoutRes;
    private boolean isStaggered;

    public SampleAdapter(String[] items, boolean isStaggered) {
        this.items1 = Arrays.asList(items);
    //    this.itemLayoutRes = R.layout.item;
        this.isStaggered = isStaggered;
    }

    public SampleAdapter(List<String> items) {
        this.items1 = items;
       // this.itemLayoutRes = R.layout.item;
        this.isStaggered = false;
    }

    public SampleAdapter(List<ProductGroupContainer> items, boolean isStaggered) {
        this.items2 = items;
       // this.itemLayoutRes = R.layout.item;
        this.isStaggered = isStaggered;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (isStaggered && viewType == 0) {
            view = inflater.inflate(R.layout.itemgrid, parent, false);
        } else {
            view = inflater.inflate(R.layout.item, parent, false);
        }
        return new SampleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        //holder.text.setBackgroundColor(backgroundColor(position, holder));
        if (items1.size() > 0) {
            holder.text.setText(items1.get(position));
        } else
            holder.text.setText(items2.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (items2.size() == 0 && items1.size() == 0) return 0;
        if (items2.size() > 0 && items1.size() == 0) return items2.size();
        if (items2.size() == 0 && items1.size() > 0) return items1.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return isStaggered ? position % 2 : 0;
    }

  /*  private int backgroundColor(int position, SampleViewHolder holder) {
        if (isStaggered) {
            int val = (int) (Math.random() * 55 + 200);
            return Color.rgb(val, val, val);
        } else {
            return holder.itemView.getResources().getColor(Utils.getBackgroundColorRes(position, itemLayoutRes));
        }
    }*/

    public static class SampleViewHolder extends RecyclerView.ViewHolder {
        public final TextView text;

        public SampleViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.hkmspinnersheet_list_textview);
        }
    }

   /* public static class SampleViewHolder extends RecyclerView.ViewHolder {
        public final com.neopixl.pixlui.components.textview.TextView text;

        public SampleViewHolder(View view) {
            super(view);
            text = (com.neopixl.pixlui.components.textview.TextView) view.findViewById(R.id.hkmspinnersheet_list_textview);
        }
    }*/
}