package net.MobileApp.Ingredo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private String URL_PRODUCTS = "http://192.168.2.9/MyApi/TestApi4.php?id=8902080000227";
    private String URL_PRODUCTS2 = "http://192.168.2.9/MyApi/warning_info_barcode.php";

    //a list to store all the products
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    public static ArrayList<String> holder = new ArrayList<String>();
    public static ArrayList<String> holder2 = new ArrayList<String>();
    public static ArrayList<String> holder3;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("SCAN_RESULTS") != null) {
            String v = bundle.getString("SCAN_RESULTS");
            Toast.makeText(getApplicationContext(), "value" + v, Toast.LENGTH_SHORT).show();
            URL_PRODUCTS = "http://192.168.2.9/MyApi/TestApi4.php?id=" + v;

            //TODO here get the string stored in the string variable and do
            // setText() on userName
        }
        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview

        fetchdata();
    }

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

    public void fetchdata() {

        class dbmanager extends AsyncTask<String, Void, String> {

            public void onPostExecute(String data) {
                try {
                    JSONObject jo = null;
                    holder.clear();
                    //ARRAYLIST 2
                    JSONArray ja = new JSONArray(data);

                    for (int i = 0; i < ja.length(); i++) {
                        //getting product object from json array
                        JSONObject product = ja.getJSONObject(i);

                        //adding the product to product list
                        productList.add(new Product(
                                product.getString("note"),
                                product.getString("image")

                        ));
                    }
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        String content = jo.getString("bad_contents");
                        String[] replace = content.split(",");
                        int arraySize = replace.length;

                        Arrays.asList(replace);

                        for (int j = 0; j < arraySize; j++) {
                            holder.add((replace[j]));

                        }
                    }

                    Intent intent = new Intent(MainActivity.this, ExpandView.class);
                    intent.putExtra("FILES_TO_SEND", holder);

                    ProductsAdapter adapter = new ProductsAdapter(MainActivity.this, productList);
                    recyclerView.setAdapter(adapter);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                } catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public String doInBackground(String... strings) {
                //return null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {
                        data.append(line + "\n");
                    }
                    br.close();
                    return data.toString();

                } catch (Exception ex) {

                    return ex.getMessage();

                }
            }
        }

        dbmanager obj = new dbmanager();
        obj.execute(URL_PRODUCTS);

    }
}

