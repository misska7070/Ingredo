package net.MobileApp.Ingredo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyExpandTextAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<ListItem> _listDataHeader2;
    private HashMap<String, List<ListItem>> _listDataChild;
    ExpandableListView expandableList;

    public MyExpandTextAdapter(Context context, ArrayList<ListItem> listDataHeader,
                               HashMap listChildData, ExpandableListView expandableList) {
        this._context = context;
        this._listDataHeader2 = listDataHeader;
        this._listDataChild = listChildData;
        this.expandableList = expandableList;

    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader2.size();

    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;

    }
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader2.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader2.get(groupPosition)).get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.list_text_parent, parent, false);
        }
        ListItem item = (ListItem) getGroup(groupPosition);
        if (item.hasBackground == 1) {
            convertView.setBackgroundColor(Color.parseColor("#FFF59D"));
        }
        else if(item.hasBackground == 2){
            convertView.setBackgroundColor(Color.parseColor("#FF6347"));
        }
        else {
            convertView.setBackgroundColor(Color.parseColor("#ABEBC6"));
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textParent2);
        textView.setText(item.value);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_text_child, null);
        }
        TextView txtListChild = (TextView) convertView.findViewById(R.id.textChild2);

        txtListChild.setText(childText);
        return convertView;

    }

    private int getItemId() {
        int position = 0;
        return position;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
