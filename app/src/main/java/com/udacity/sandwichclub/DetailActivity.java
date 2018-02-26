package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Declare the TextViews and ImageView as global variable, so I can initialize them in onCreate and use them in the populateUI() method
    private ImageView ingredientsIv;
    // TextView variable for the label
    private TextView alsoKnownAsLabelTv;
    // TextView variable for the content of the label
    private TextView alsoKnownAsTv;
    private TextView originLabelTv;
    private TextView originTv;
    private TextView descriptionLabelTv;
    private TextView descriptionTv;
    private TextView ingredientsLabelTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Link the global variable with their corresponding TextView or ImageView
        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        alsoKnownAsLabelTv = findViewById(R.id.also_known_label);
        originTv = findViewById(R.id.origin_tv);
        originLabelTv = findViewById(R.id.also_known_label);
        descriptionTv = findViewById(R.id.description_tv);
        descriptionLabelTv = findViewById(R.id.description_label);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        ingredientsLabelTv = findViewById(R.id.ingredients_label);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        // Move from one sandwich to the other
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Populates the user interface with the JSON Strings, obtained after parsing,
     * in the corresponding TextView or ImageView specified in the activity_detail.xml,
     * using the global variables declared for each view
     * @param  sandwich object
     * @return void
     */
    private void populateUI(Sandwich sandwich) {

        // Check first if the sandwich object exists
        if (sandwich == null) {
            return;
        }

        // If the list of Strings alsoKnownAsArrayList is empty, hide both TextViews (label and content)
        if (sandwich.getAlsoKnownAs() == null) {
            alsoKnownAsTv.setVisibility(View.GONE);
            alsoKnownAsLabelTv.setVisibility(View.GONE);
        } else {
            alsoKnownAsTv.setText(android.text.TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        // If there is no String for placeOfOrigin, hide both TextViews (label and content)
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setVisibility(View.GONE);
            originLabelTv.setVisibility(View.GONE);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        // If there is no String for description, hide both TextViews (label and content)
        if (sandwich.getDescription().isEmpty()) {
            descriptionTv.setVisibility(View.GONE);
            descriptionLabelTv.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }

        // If the list of Strings ingredientsArrayList is empty, hide both TextViews (label and content)
        if (sandwich.getIngredients() == null) {
            ingredientsTv.setVisibility(View.GONE);
            ingredientsLabelTv.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText(android.text.TextUtils.join(", ", sandwich.getIngredients()));
        }
    }
}
