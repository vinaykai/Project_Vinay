package com.vkai.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by HOME on 3/11/2016.
 */
public class FoodSuggestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_suggestion);
    }
        @Override
        public void onBackPressed () {
            super.onBackPressed();
            Intent intent = new Intent(FoodSuggestionActivity.this, navigation.class);
            startActivity(intent);
            finish();
        }

    protected void onStart() {
        super.onStart();
        TextView tv1 = (TextView) findViewById(R.id.Breakfast_title);
        tv1.setText("BREAKFAST\n");
        tv1.setTypeface(tv1.getTypeface(), Typeface.BOLD_ITALIC);
        TextView tv2 = (TextView) findViewById(R.id.Breakfast_content);
        tv2.setText("Whether your first stop is the office or the gym, adding protein to your breakfast is a great way to rev up your metabolism - if you do exercise first thing a protein breakfast helps promote muscle recovery and repair. Eggs are an ideal choice because they provide a good balance of quality protein and fat, other options include lean ham, fish like salmon or haddock, as well as low-fat dairy foods. Protein foods slow stomach emptying, which means you stay fuller for longer so you'll tend to eat fewer calories the rest of the day.\n");
        TextView tv3 = (TextView) findViewById(R.id.Lunch_title);
        tv3.setText("LUNCH\n");
        tv3.setTypeface(tv3.getTypeface(), Typeface.BOLD_ITALIC);

        TextView tv4 = (TextView) findViewById(R.id.Lunch_content);
        tv4.setText("Make lunch a mix of lean protein and starchy carbs. Carb-rich foods supply energy so you'll suffer from mid-afternoon slumps if you cut them out. The key is to choose carbs that produce a steady rise in blood sugar, which means passing on sugary 'white' foods and going for high fibre whole-grains, which help you manage those afternoon munchies. Whole-grains like rye, wholewheat and barley keep you satisfied for longer - in fact studies show rye bread keeps blood sugar stable for up to 10 hours - a sure way to dampen those mid-afternoon energy crashes.\n" +
                "Opt for an open sandwich topped with lean beef or pork, salmon, turkey or chicken with plenty of salad or toast some whole-grain bread and enjoy with baked beans.\n");
        TextView tv5 = (TextView) findViewById(R.id.MidAfternoon_title);
        tv5.setText("MID-AFTERNOON SNACKS\n");
        tv5.setTypeface(tv5.getTypeface(), Typeface.BOLD_ITALIC);
        TextView tv6 = (TextView) findViewById(R.id.MidAfternoon_content);
        tv6.setText("For many it's not sugar so much as salty, savoury foods they crave in the afternoon. If this sounds like you forget the crisps and opt instead for spiced nuts, seeds and savoury popcorn or enjoy low-fat cream cheese on crackers.\n");
        TextView tv7 = (TextView) findViewById(R.id.Dinner_title);
        tv7.setText("DINNER\n");
        tv7.setTypeface(tv7.getTypeface(), Typeface.BOLD_ITALIC);
        TextView tv8 = (TextView) findViewById(R.id.Dinner_content);
        tv8.setText("Don't curfew carbs, they're low in fat, fibre-rich and help you relax in the evening. Combine them with healthy essential fats which your body can use overnight for growth and repair. You can get these healthy fats from oily fish like salmon, trout and mackerel as well as nuts, seeds and their oils.\n" +
                "Fill half your plate with a riot of colour - choosing from a wide variety of vegetables or salad, drizzle with a dressing made from flaxseed or rapeseed oil and add meat, fish or beans with a serving of brown rice, quinoa or wholemeal pasta.**Don't curfew carbs, they're low in fat, fibre-rich and help you relax in the evening. Combine them with healthy essential fats which your body can use overnight for growth and repair. You can get these healthy fats from oily fish like salmon, trout and mackerel as well as nuts, seeds and their oils.\n");
    }


}
