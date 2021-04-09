package com.zoho.superhero;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zoho.superhero.pojo.SuperHeroPojo;
import com.zoho.superhero.util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zoho.superhero.util.ContURL.SUPER_HERO_JSON_ARRAY_1;
import static com.zoho.superhero.util.ContURL.SUPER_HERO_JSON_ARRAY_2;
import static com.zoho.superhero.util.ContURL.URL_SUPERHERO_ID;

/**
 * Created by Preethi on 07/04/2021.
 * Using Recycle view instance Recycle View layout is created
 * Add item Decoration function is called for setting up the edges
 * DefaultItem Animator provides smooth animation
 * In Adapter the extension of Recycle view is given
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ArrayList<SuperHeroPojo> superHeroArrayList = prepareSuperHeroAlbums();
        com.zoho.superhero.AlbumsAdapter adapter = new com.zoho.superhero.AlbumsAdapter(this, superHeroArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void getSuperHeroImg(int superHeroId) {
        String tag_json_obj = "json_obj_req";
        String url = URL_SUPERHERO_ID + superHeroId + "/image";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                response -> {
                    System.out.println("^^^^^^^response = " + response + "************");
                    System.out.println(" ");
                    try {

                        if (!response.getString("response").equalsIgnoreCase("success")) {
                            Toast.makeText(com.zoho.superhero.MainActivity.this, "Request failed...!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(com.zoho.superhero.MainActivity.this, "Server unable to reach..!", Toast.LENGTH_SHORT).show());
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
//Arraylist is created using Pojo by Getter and setter Methods
    private ArrayList<SuperHeroPojo> prepareSuperHeroAlbums() {
        try {

            JSONArray superHeroJsonArray1 = new JSONArray(SUPER_HERO_JSON_ARRAY_1);
            JSONArray superHeroJsonArray2 = new JSONArray(SUPER_HERO_JSON_ARRAY_2);

            ArrayList<SuperHeroPojo> superHeroArrayList = new ArrayList<>();

            superHeroJsonToArrayList(superHeroArrayList, superHeroJsonArray1);
            superHeroJsonToArrayList(superHeroArrayList, superHeroJsonArray2);

            return superHeroArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void superHeroJsonToArrayList(ArrayList<SuperHeroPojo> superHeroArrayList, JSONArray superHeroJsonArray) {
        for (int iterationTemp = 0; iterationTemp < superHeroJsonArray.length() - 1; iterationTemp++) {
            try {
                JSONObject superHeroJson = superHeroJsonArray.getJSONObject(iterationTemp);

                SuperHeroPojo superHeroPojo = new SuperHeroPojo();
                superHeroPojo.setFullName_textView(superHeroJson.getString("name"));
                superHeroPojo.setId(Integer.parseInt(superHeroJson.getString("id")));
                superHeroPojo.setImageUrl(superHeroJson.getString("url"));

                superHeroArrayList.add(superHeroPojo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics()));
    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_action:
                this.startActivity(new Intent(com.zoho.superhero.MainActivity.this, com.zoho.superhero.SearchActivity.class));
                return true;
            case R.id.about_action:
                this.startActivity(new Intent(com.zoho.superhero.MainActivity.this, com.zoho.superhero.AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}