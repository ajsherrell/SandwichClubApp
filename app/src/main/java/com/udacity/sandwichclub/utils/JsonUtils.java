package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // log tag
    private final static String TAG = JsonUtils.class.getSimpleName();

    // declare constant variables
    private final static String SANDWICH_NAME = "name";
    private final static String SANDWICH_MAIN_NAME = "mainName";
    private final static String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String SANDWICH_DESCRIPTION = "description";
    private final static String SANDWICH_IMAGE = "image";
    private final static String SANDWICH_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        // declare local variables for JSON
        JSONObject jsonObject;
        String sandwichMainName;
        String sandwichPlaceOfOrigin;
        String sandwichDescription;
        String sandwichImage;
        //arrayLists here:
        List<String> sandwichAlsoKnownAsArray;
        List<String> sandwichIngredientsArray;

        // parse the JSON with a try/catch block
        try {
            jsonObject = new JSONObject(json);

            // get json optStrings
            JSONObject sandwichName = jsonObject.getJSONObject(SANDWICH_NAME);
            sandwichMainName = sandwichName.optString(SANDWICH_MAIN_NAME);
            sandwichPlaceOfOrigin = jsonObject.optString(SANDWICH_PLACE_OF_ORIGIN);
            sandwichDescription = jsonObject.optString(SANDWICH_DESCRIPTION);
            sandwichImage = jsonObject.optString(SANDWICH_IMAGE);

            // get jsonArrayLists
            sandwichAlsoKnownAsArray = jsonArrayList(sandwichName.getJSONArray(SANDWICH_ALSO_KNOWN_AS));
            sandwichIngredientsArray = jsonArrayList(jsonObject.getJSONArray(SANDWICH_INGREDIENTS));

        } catch (JSONException e) {
            Log.e(TAG, "Not able to parse JSON data", e);
            e.printStackTrace();
            return null;
        }

        return new Sandwich(sandwichMainName, sandwichAlsoKnownAsArray, sandwichPlaceOfOrigin, sandwichDescription, sandwichImage, sandwichIngredientsArray);
    }


    // make method to get array list from json array
    private static List<String> jsonArrayList(JSONArray jsonArray) throws JSONException {
        // declare arrayList from 0th index
        List<String> arrayList = new ArrayList<>(0);

        // loop through list array
        for (int i = 0; i < jsonArray.length(); i++) {
            arrayList.add(jsonArray.getString(i));
        }

        return arrayList;
    }
}
