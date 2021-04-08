package com.zoho.superhero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Preethi on 07/04/2021.
 */

public class SearchActivity extends Activity {

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
    private String[] superHeroArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Listview Data
        superHeroArray = this.getApplicationContext().getResources().getStringArray(R.array.super_hero);
        HashMap<String, Integer> superHeroHm = convertArrayListToHashMap(superHeroArray);
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.search_item, R.id.product_name, superHeroArray);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View view, int position, long id) {

                String product = lv.getItemAtPosition(position).toString();
                System.out.println("*******************product = " + product);
                System.out.println("*******************position = " + position);
                System.out.println("*******************position = " + superHeroHm.get(product));
                Intent i = new Intent(getApplicationContext(), HeroDetailsActivity.class);
                i.putExtra("id", superHeroHm.get(product).toString());
                startActivity(i);
                finish();
            }
        });
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                com.zoho.superhero.SearchActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    HashMap<String, Integer> convertArrayListToHashMap(String[] arrayList) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        int superHeroId = 1;
        for (String str : arrayList) {

            hashMap.put(str, superHeroId+1);
            superHeroId++;
        }
        return hashMap;
    }

}