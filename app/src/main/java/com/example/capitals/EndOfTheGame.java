package com.example.capitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndOfTheGame extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_of_the_game);
		TextView endScore = findViewById(R.id.endScoreTxt);
		Intent intent = getIntent();
		int result = intent.getIntExtra("result", 0);
		endScore.setText(String.valueOf(result));
	}

	public void goBackToMenu(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}