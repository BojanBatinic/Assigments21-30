package com.example.androiddevelopment.glumcilegende.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.model.NavigationItem;

import java.util.ArrayList;

/**
 * Created by BBLOJB on 22.11.2017..
 */
// Custom adapter extends BaseAdapter and overrides its methods
public class DrawerListAdapter extends BaseAdapter {

    // A reference to the context (i.e. the activity containing the adapter)
    Context context;
    ArrayList<NavigationItem> navigationItems;

    // Constructor should at least have context as a parameter


    public DrawerListAdapter(Context context, ArrayList<NavigationItem> navigationItems) {
        this.context = context;
        this.navigationItems = navigationItems;
    }

    // Returns the item count
    @Override
    public int getCount() {
        return navigationItems.size();
    }

    // Returns an item
    @Override
    public Object getItem(int position) {
        return navigationItems.get(position);
    }

    // Returns an item ID
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_list_item, null);
        }
        else{
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText(navigationItems.get(position).getTitle());
        subtitleView.setText(navigationItems.get(position).getSubtitle());
        iconView.setImageResource(navigationItems.get(position).getIcon());

        return view;
    }
}
