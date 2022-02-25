package net.MobileApp.Ingredo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandView extends AppCompatActivity {

    HashMap listDataChild = new HashMap<String, List<String>>();

    ExpandableListView expandableListView ;
    ExpandableListAdapter listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_view);

        ArrayList<String> holder3 = (ArrayList<String>) getIntent().getSerializableExtra("FILES_TO_SEND");

        expandableListView=(ExpandableListView) findViewById(R.id.expandlistview);

        listAdapter= new MyExpandAdapter(ExpandView.this, holder3, listDataChild,expandableListView);
        //  adapter.areAllItemsEnabled(wordList);
        expandableListView.setAdapter(listAdapter);


    }
}
