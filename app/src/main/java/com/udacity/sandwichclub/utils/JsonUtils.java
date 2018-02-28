package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    // Declaring the JSON keys constants
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        // Checking if the json is empty
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Sandwich sandwich = null;

        // JSON parsing - extracting the strings from the JSON objects
        try {
            JSONObject root = new JSONObject(json);
            JSONObject name = root.optJSONObject(NAME);
            String mainName = name.optString(MAIN_NAME);
            JSONArray alsoKnownAsArray = name.optJSONArray(ALSO_KNOWN_AS);
            String placeOfOrigin = root.optString(PLACE_OF_ORIGIN);
            String description = root.optString(DESCRIPTION);
            String image = root.optString(IMAGE);
            JSONArray ingredientsArray = root.optJSONArray(INGREDIENTS);

            // Looping in the alsoKnownArray and and creating the list of Strings alsoKnownArrayList
            List<String> alsoKnownAsArrayList = null;
            // Checking if the alsoKnownArray is not empty
            if (alsoKnownAsArray.length() > 0) {
                alsoKnownAsArrayList = new ArrayList<>();
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAsArrayList.add(alsoKnownAsArray.optString(i));
                }
            }

            // Looping in the ingredientsArray and and creating the list of Strings ingredientsArrayList
            List<String> ingredientsArrayList = null;
            // Checking if the ingredientsArray is not empty
            if (ingredientsArray.length() > 0) {
                ingredientsArrayList = new ArrayList<>();
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredientsArrayList.add(ingredientsArray.optString(i));
                }
            }

            // Creating the sandwich object which has parameters that we have just parsed from JSON
            sandwich = new Sandwich(mainName, alsoKnownAsArrayList, placeOfOrigin, description, image, ingredientsArrayList);

        // Catch exception in case the JSON parsing is unsuccessful and avoid app crash
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

}

