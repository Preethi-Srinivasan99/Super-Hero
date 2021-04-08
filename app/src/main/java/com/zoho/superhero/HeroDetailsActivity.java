package com.zoho.superhero;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.zoho.superhero.util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import static com.zoho.superhero.util.ContURL.URL_SUPERHERO_ID;

/**
 * Created by Preethi on 07/04/2021.
 */

public class HeroDetailsActivity extends AppCompatActivity {
    private TextView superHeroName_textView;
    private TextView gender_textView;
    private TextView eye_color_textView;
    private TextView hair_color_textView;
    private TextView race_textView;
    private TextView height_textView;
    private TextView weight_textView;
    private TextView fullName_textView;
    private TextView aliases_textView;
    private TextView place_of_birth_textView;
    private TextView alignment_textView;
    private TextView occupation_textView;
    private TextView base_textView;
    private TextView groupAffiliation_textView;
    private TextView relatives_textView;
    private ProgressBar intelligence_progressView;
    private ProgressBar strength_progressView;
    private ProgressBar speed_progressView;
    private ProgressBar combat_progressView;
    private ProgressBar power_progressView;
    private ProgressBar durability_progressView;
    private ImageView superHeroImageView;
    private LinearLayout powerLinearLayout;
    private LinearLayout biographyLayout;
    private LinearLayout workLinearLayout;
    private LinearLayout connectionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_details);

        Intent intent = getIntent();
        String superHeroId = intent.getStringExtra("id");
        // System.out.println("id = " + id);

        superHeroImageView = findViewById(R.id.superHeroImageView);

        superHeroName_textView = findViewById(R.id.superHeroName_textView);
        gender_textView = findViewById(R.id.gender_textView);
        eye_color_textView = findViewById(R.id.eye_color_textView);
        hair_color_textView = findViewById(R.id.hair_color_textView);
        race_textView = findViewById(R.id.race_textView);
        height_textView = findViewById(R.id.height_textView);
        weight_textView = findViewById(R.id.weight_textView);

        powerLinearLayout = findViewById(R.id.powerLinearLayout);
        biographyLayout = findViewById(R.id.biographyLayout);
        workLinearLayout = findViewById(R.id.workLinearLayout);
        connectionLayout = findViewById(R.id.connectionLayout);

        //    biography
        fullName_textView = findViewById(R.id.fullName_textView);
        aliases_textView = findViewById(R.id.aliases_textView);
        place_of_birth_textView = findViewById(R.id.place_of_birth_textView);
        alignment_textView = findViewById(R.id.alignment_textView);

        //    work
        occupation_textView = findViewById(R.id.occupation_textView);
        base_textView = findViewById(R.id.base_textView);

        //    Connection
        groupAffiliation_textView = findViewById(R.id.groupAffiliation_textView);
        relatives_textView = findViewById(R.id.relatives_textView);

        // Power Stats
        intelligence_progressView = findViewById(R.id.intelligence_progressView);
        strength_progressView = findViewById(R.id.strength_progressView);
        speed_progressView = findViewById(R.id.speed_progressView);
        combat_progressView = findViewById(R.id.combat_progressView);
        power_progressView = findViewById(R.id.power_progressView);
        durability_progressView = findViewById(R.id.durability_progressView);

        Button filterBtn = findViewById(R.id.filterBtn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog(v);
            }
        });
        getRequest(superHeroId);
    }

    void getRequest(String superHeroId) {
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";
        ProgressDialog pDialog = new ProgressDialog(com.zoho.superhero.HeroDetailsActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url = URL_SUPERHERO_ID + superHeroId;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                response -> {
                    System.out.println("response = " + response);
                    try {
                        if (response.getString("response").equalsIgnoreCase("success")) {
                            String imgURL = response.getJSONObject("image").getString("url");
                            System.out.println("imgURL = " + imgURL);

                            // loading album cover using Glide library
                            Glide.with(getApplicationContext()).load(imgURL).error(R.drawable.ic_superhero).into(superHeroImageView);
                            superHeroName_textView.setText(response.getString("name"));

                            JSONObject appearanceJsonObj = response.getJSONObject("appearance");
                            gender_textView.setText(appearanceJsonObj.getString("gender"));
                            eye_color_textView.setText(appearanceJsonObj.getString("eye-color"));
                            hair_color_textView.setText(appearanceJsonObj.getString("hair-color"));
                            race_textView.setText(appearanceJsonObj.getString("race"));
                            height_textView.setText((CharSequence) appearanceJsonObj.getJSONArray("height").get(0));
                            weight_textView.setText((CharSequence) appearanceJsonObj.getJSONArray("weight").get(0));

                            JSONObject biographyJsonObj = response.getJSONObject("biography");
                            fullName_textView.setText(biographyJsonObj.getString("full-name"));
                            aliases_textView.setText(biographyJsonObj.getJSONArray("aliases").getString(0));
                            place_of_birth_textView.setText(biographyJsonObj.getString("place-of-birth"));
                            alignment_textView.setText(biographyJsonObj.getString("alignment"));

                            JSONObject workJsonObj = response.getJSONObject("work");
                            occupation_textView.setText(workJsonObj.getString("occupation"));
                            base_textView.setText(workJsonObj.getString("base"));

                            JSONObject connectionsJsonObj = response.getJSONObject("connections");
                            groupAffiliation_textView.setText(connectionsJsonObj.getString("group-affiliation"));
                            relatives_textView.setText(connectionsJsonObj.getString("relatives"));

                            JSONObject powerStatsJsonObj = response.getJSONObject("powerstats");
                            intelligence_progressView.setProgress(checkIntValues(powerStatsJsonObj.getString("intelligence")));
                            strength_progressView.setProgress(checkIntValues(powerStatsJsonObj.getString("strength")));
                            speed_progressView.setProgress(checkIntValues(powerStatsJsonObj.getString("speed")));
                            combat_progressView.setProgress(checkIntValues(powerStatsJsonObj.getString("combat")));
                            power_progressView.setProgress(checkIntValues(powerStatsJsonObj.getString("power")));
                            durability_progressView.setProgress(checkIntValues(powerStatsJsonObj.getString("durability")));
                        } else {
                            Toast.makeText(com.zoho.superhero.HeroDetailsActivity.this, "Request failed...!", Toast.LENGTH_SHORT).show();
                        }
                        pDialog.hide();
                    } catch (JSONException e) {
                        pDialog.hide();
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(com.zoho.superhero.HeroDetailsActivity.this, "Server unable to reach..!", Toast.LENGTH_SHORT).show();
            pDialog.hide();
        });
        System.out.println("jsonObjReq = " + jsonObjReq.toString());
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private int checkIntValues(String value) {
        if (!value.equalsIgnoreCase("null")) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    private void filterDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.alert_filter_dialog, viewGroup, false);
        builder.setView(dialogView);
        CheckBox powerStatsCB = dialogView.findViewById(R.id.powerStatsCB);
        CheckBox workCB = dialogView.findViewById(R.id.workCB);
        CheckBox biographyCB = dialogView.findViewById(R.id.biographyCB);
        CheckBox connectionCB = dialogView.findViewById(R.id.connectionCB);
        Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if (powerLinearLayout.getVisibility()== View.VISIBLE) {
            powerStatsCB.setChecked(true);
        } else {
            powerStatsCB.setChecked(false);
        }
        if (workLinearLayout.getVisibility()== View.VISIBLE) {
            workCB.setChecked(true);
        } else {
            workCB.setChecked(false);
        }
        if (biographyLayout.getVisibility()== View.VISIBLE) {
            biographyCB.setChecked(true);
        } else {
            biographyCB.setChecked(false);
        }
        if (connectionLayout.getVisibility()== View.VISIBLE) {
            connectionCB.setChecked(true);
        } else {
            connectionCB.setChecked(false);
        }

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (powerStatsCB.isChecked()) {
                    powerLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    powerLinearLayout.setVisibility(View.GONE);
                }
                if (workCB.isChecked()) {
                    workLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    workLinearLayout.setVisibility(View.GONE);
                }
                if (biographyCB.isChecked()) {
                    biographyLayout.setVisibility(View.VISIBLE);
                } else {
                    biographyLayout.setVisibility(View.GONE);
                }
                if (connectionCB.isChecked()) {
                    connectionLayout.setVisibility(View.VISIBLE);
                } else {
                    connectionLayout.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });

    }
}