package com.example.capitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		setContentView(R.layout.activity_main);
	}

	public void startCategories(View view) {
		Intent intent = new Intent(this, Categories.class);
		startActivity(intent);
		finish();
	}
}