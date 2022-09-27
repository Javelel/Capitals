package com.example.capitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class Categories extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		setContentView(R.layout.activity_categories);
	}

	public void startGame(View view) {
		boolean[] chosenCategories = new boolean[6];
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch countries = findViewById(R.id.countriesSwt);
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch capitals = findViewById(R.id.capitalsSwt);
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch animals = findViewById(R.id.animalsSwt);
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch plants = findViewById(R.id.plantsSwt);
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch mountains = findViewById(R.id.mountainsSwt);
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch riversLakes = findViewById(R.id.riversSwt);

		chosenCategories[0] = countries.isChecked();
		chosenCategories[1] = capitals.isChecked();
		chosenCategories[2] = animals.isChecked();
		chosenCategories[3] = plants.isChecked();
		chosenCategories[4] = mountains.isChecked();
		chosenCategories[5] = riversLakes.isChecked();

		Intent intent = new Intent(this, StartGame.class);
		intent.putExtra("categories", chosenCategories);
		startActivity(intent);
		finish();
	}
}