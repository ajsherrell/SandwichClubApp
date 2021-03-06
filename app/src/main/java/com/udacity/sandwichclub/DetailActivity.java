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

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private Sandwich sandwich;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // declare member variables\
    private TextView originTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private TextView alsoKnowAsTextView;

    // labels
    private TextView originLabel;
    private TextView akaLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //find image view
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // find text views
        originTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        alsoKnowAsTextView = findViewById(R.id.also_known_tv);

        // find labels
        originLabel = findViewById(R.id.origin_label);
        akaLabel = findViewById(R.id.also_known_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // set textview fields, if field is empty, hide the field
        ingredientsTextView.setText(listArray(sandwich.getIngredients()));
        descriptionTextView.setText(sandwich.getDescription());

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnowAsTextView.setVisibility(View.INVISIBLE);
        } else {
            alsoKnowAsTextView.setText(listArray(sandwich.getAlsoKnownAs()));
            akaLabel.setVisibility(View.VISIBLE);
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTextView.setVisibility(View.INVISIBLE);
        } else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
            originLabel.setVisibility(View.VISIBLE);
        }
    }

    // make a string builder method
    public StringBuilder listArray(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        // iterate through array
        for (int i = 0; i < list.size(); i++) {
            // use only one newline or the spacing becomes too much
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }
}
