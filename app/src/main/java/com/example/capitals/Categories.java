package com.example.capitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
	}

	public void startGame(View view) {
		boolean[] chosenCategories = new boolean[1];
		Switch countries = findViewById(R.id.countriesSwt);
		chosenCategories[0] = countries.isChecked();

		Intent intent = new Intent(this, StartGame.class);
		intent.putExtra("categories", chosenCategories);
		startActivity(intent);
		finish();
	}
}