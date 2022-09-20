package com.example.capitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

import me.grantland.widget.AutofitHelper;

public class StartGame extends AppCompatActivity {

	TextView letterTxt;
	Handler handler;
	boolean[] chosenCategories;
	char chosenLetter;
	TextView timeLeft;
	ConstraintLayout conLay;
	TableLayout tabLay;
	static final int TIME_FOR_ANSWER = 15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
		Intent intent = getIntent();
		chosenCategories = intent.getBooleanArrayExtra("categories");
	}

	public void randomizeLetter(View view) {
		Button randBtn = findViewById(R.id.randomizeBtn);
		randBtn.setClickable(false);
		letterTxt = findViewById(R.id.letterTxt);
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		int duration = rnd.nextInt(20) + 10;
		final int[] i = {0};
		final int[] timer = {0};
		handler = new Handler();
		handler.post(new Runnable() {

			public void run() {
				char lastLetter = i[0] ==0 ? chars.charAt(chars.length() - 1) : chars.charAt(i[0] - 1);
				char letter = chars.charAt(i[0]++);
				if(i[0] >= chars.length()) {
					i[0] = 0;
				}
				letterTxt.setText(String.valueOf(letter));
				timer[0]++;
				if(timer[0] <= duration) {
					handler.postDelayed(this, 80);
				} else {
					chosenLetter = lastLetter;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					theGame();
				}
			}
		});
	}

	private void theGame() {

		if(conLay == null) {
			setContentView(R.layout.category_1);
			TextView categoryTitle = findViewById(R.id.categoryPattern);
			categoryTitle.setText("Countries");
			conLay = findViewById(R.id.conLay);
			tabLay = findViewById(R.id.tabLay);
			timeLeft = findViewById(R.id.timeLeftTxt);
		} else {
			setContentView(conLay);
		}
		timeLeft.setText(String.valueOf(TIME_FOR_ANSWER));

		TableRow tabRow = new TableRow(this);
		ConstraintLayout rowConLay = new ConstraintLayout(this);
		TextView letter = new TextView(this);
		EditText category = new EditText(this);
		TextView points = new TextView(this);
		ImageView vertLine = new ImageView(this);

		tabLay.addView(tabRow);
		tabRow.addView(rowConLay);

		letter.setText(String.valueOf(chosenLetter));
		letter.setTextSize(30);
		letter.setTextColor(Color.BLACK);
		letter.setId(View.generateViewId());

		category.setMaxLines(2);
		category.setMaxWidth((int) getResources().getDisplayMetrics().density * 312);
		category.setTextSize(30);
		category.setTextColor(Color.BLACK);
		InputFilter[] filterArray = new InputFilter[3];
		filterArray[0] = new InputFilter.LengthFilter(256);
		filterArray[1] = new InputFilter.AllCaps();
		filterArray[2] = (source, start, end, dest, dstart, dend) -> {
			for (int i = start; i < end; i++) {
				if (!Character.isLetter(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {
					return "";
				}
			}
			return null;
		};
		category.setFilters(filterArray);
		AutofitHelper.create(category);
		category.setId(View.generateViewId());

		points.setTextSize(30);
		points.setTextColor(Color.BLACK);
		points.setId(View.generateViewId());

		rowConLay.addView(letter);
		rowConLay.addView(category);
		rowConLay.addView(points);

		ConstraintSet conSet = new ConstraintSet();
		conSet.clone(rowConLay);
		conSet.connect(letter.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 8);
		conSet.connect(letter.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 20);
		conSet.connect(category.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
		conSet.connect(category.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
		conSet.connect(category.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
		conSet.connect(points.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 64);
		conSet.connect(points.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 20);
		conSet.applyTo(rowConLay);

		vertLine.setImageResource(R.color.black);
		vertLine.setVisibility(View.VISIBLE);
		vertLine.setId(View.generateViewId());
		int dividerHeight = (int) getResources().getDisplayMetrics().density * 2;
		vertLine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight));

		TableRow vertLineRow = new TableRow(this);
		ConstraintLayout vertLineLayout = new ConstraintLayout(this);
		vertLineLayout.addView(vertLine);
		vertLineRow.addView(vertLineLayout);

		Handler timerHandler = new Handler();
		Runnable timerRunnable = new Runnable() {
			@Override
			public void run() {
				if(!timeLeft.getText().equals("0")) {
					timeLeft.setText(String.valueOf(Integer.parseInt((String) timeLeft.getText()) - 1));
					timerHandler.postDelayed(this, 1000);
				} else {
					category.setEnabled(false);
					tabLay.addView(vertLineRow);

					if(category.getText() != null && Objects.requireNonNull(category.getText()).length() >= 1 && Objects.requireNonNull(category.getText()).charAt(0) == chosenLetter) {
						points.setText("15");
					} else {
						points.setText("0");
					}
					setContentView(R.layout.activity_start_game);
				}
			}
		};
		timerHandler.postDelayed(timerRunnable, 0);





	}



}