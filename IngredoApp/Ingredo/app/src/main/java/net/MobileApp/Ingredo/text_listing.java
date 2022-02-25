package net.MobileApp.Ingredo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class text_listing extends AppCompatActivity {

    private static final String url ="http://192.168.2.5/MyApi/warning_info.php";
    public static ArrayList<String> holder = new ArrayList<String>() ;
    public static ArrayList<String> holder_danger = new ArrayList<String>() ;

    //WORDLIST IS WHERE YOU GET YOUR HEADERS FROM
    private ArrayList<ListItem> wordList = new ArrayList<>();
    java.util.List<String> nowShowing  = new ArrayList<String>();
    HashMap listDataChild = new HashMap<String, List<ListItem>>();
    ExpandableListView expandableListView ;
    ExpandableListAdapter listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_listing);

        Intent intent = getIntent();
        String passedArg = intent.getStringExtra("Text");
        Toast.makeText(getApplicationContext(),
                "This a toast message 2",
                Toast.LENGTH_LONG);

        fetchdata();

        if (!passedArg.isEmpty()) {
            if (passedArg.length() >= 0) {
                //String[] replace = tv.getText().toString().split(",");
                String[] replace = passedArg.split(",");
                int arraySize = replace.length;
                Arrays.asList(replace);
                //ARRAYlIST 1
                for (int i = 0; i < arraySize; i++) {
                    wordList.add(getDefaultItem(replace[i]));

                }
            }
        }
        updateCommonElements();
        expandableListView=(ExpandableListView) findViewById(R.id.expandlistview2);
        listAdapter= new MyExpandTextAdapter(text_listing.this, wordList, listDataChild,expandableListView);
        expandableListView.setAdapter(listAdapter);

    }
    public void fetchdata()
    {
        class dbmanager extends AsyncTask<String, Void,String>
        {
            public void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    holder.clear();
                    //ARRAYLIST 2
                    for (int i =0; i<ja.length();i++) {
                        jo = ja.getJSONObject(i);
                        String name = jo.getString("Warning");
                        holder.add(name);
                    }

                    for (int i =0; i<ja.length();i++) {
                        jo = ja.getJSONObject(i);
                        String name = jo.getString("Danger");
                        holder_danger.add(name);
                    }

                    JSONArray da = new JSONArray(data);

                    for(int j= 0;j<da.length();j++){
                        JSONObject d = da.getJSONObject(j);
                        String three = d.getString("warning_info");
                        for (int k = 0; k < holder.size() ; k++) {
                            for (int i = 0; i < wordList.size(); i++) {
                                String instrumentList = wordList.get(i).value ;
                                if (holder.get(k).equals(instrumentList)) {
                                    nowShowing.add(three);
                                    listDataChild.put(holder.get(k), nowShowing);

                                }
                            }
                        }
                    }
                } catch(Exception ex){

                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public String doInBackground(String... strings) {
                //return null;
                try {
                    URL url = new URL (strings[0]);
                    HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while( (line = br.readLine())!=null)
                    {
                        data.append(line + "\n");
                    }
                    br.close();
                    return data.toString();

                }catch(Exception ex){

                    return ex.getMessage();

                }
            }
        }

        dbmanager obj = new dbmanager();
        obj.execute(url);

    }

    public ListItem getDefaultItem(String value) {
        return new ListItem(value, 3);

    }

    public void updateCommonElements() {
        for (int j = 0; j < holder.size() ; j++) {
            for (int k = 0; k < holder_danger.size(); k++) {
                for (int i = 0; i < wordList.size(); i++) {
                    String instrumentList = wordList.get(i).value;
                    if (holder.get(j).equals(instrumentList)) {
                        wordList.get(i).hasBackground = 1;
                    }
                    if(holder_danger.get(k).equals(instrumentList)){
                        wordList.get(i).hasBackground = 2;
                    }
                }
            }
        }
    }

}
