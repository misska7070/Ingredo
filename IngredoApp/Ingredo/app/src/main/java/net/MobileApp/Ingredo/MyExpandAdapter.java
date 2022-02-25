package net.MobileApp.Ingredo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyExpandAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;
    ExpandableListView expandableList;


    public MyExpandAdapter(Context context, ArrayList<String> listDataHeader,
                           HashMap listChildData, ExpandableListView expandableList) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.expandableList = expandableList;

    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();

    }

    @Override
    public int getChildrenCount(int groupPosition) {
          //return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);

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

        String listTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.list_parent, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textParent);

        textView.setText(listTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.textChild);

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

