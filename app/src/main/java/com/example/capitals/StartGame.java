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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import me.grantland.widget.AutofitHelper;

public class StartGame extends AppCompatActivity {

	TextView letterTxt;
	Handler handler;
	boolean[] chosenCategories;
	char chosenLetter;
	TextView timeLeft;
	ScrollView gameView;
	TableLayout tabLay;
	static final int TIME_FOR_ANSWER = 3;

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

		if(gameView == null) {
			setContentView(R.layout.category_1);

			LinearLayout categoryTitleLayout = findViewById(R.id.category);

			TextView categoryTitle = new TextView(this);
			categoryTitle.setText("Countries");
			categoryTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
			categoryTitle.setTextSize(30);
			categoryTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
			categoryTitle.setTextColor(Color.BLACK);
			categoryTitleLayout.addView(categoryTitle);

			gameView = findViewById(R.id.gameView);
			tabLay = findViewById(R.id.tabLay);
			timeLeft = findViewById(R.id.timeLeftTxt);
		} else {
			setContentView(gameView);
		}
		timeLeft.setText(String.valueOf(TIME_FOR_ANSWER));

		TableRow tabRow = new TableRow(this);	// 1 Row = 1 round
		ConstraintLayout rowConLay = new ConstraintLayout(this);	// Layout to fill the TableRow
		TextView letter = new TextView(this);	//	random letter
		EditText category = new EditText(this);	//	answer
		LinearLayout answersLay = new LinearLayout(this);	//	Layout containing all the answers
		LinearLayout categoryAndPointsLay = new LinearLayout(this);	//	Layout with a particular answer and points form it
		TextView points = new TextView(this);	//	points scored in a particular round
		TextView categoryPoints = new TextView(this);	// points scored on each answer
		TableRow vertLineRow = new TableRow(this);	// row for the vertical line
		ImageView vertLine = new ImageView(this);	// vertical line between each round

		tabLay.addView(tabRow);
		tabRow.addView(rowConLay);

		letter.setText(String.valueOf(chosenLetter));
		letter.setTextSize(30);
		letter.setTextColor(Color.BLACK);
		letter.setId(View.generateViewId());

		answersLay.setId(View.generateViewId());
		categoryAndPointsLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
		categoryAndPointsLay.setOrientation(LinearLayout.VERTICAL);

		category.setMaxLines(2);
		category.setMaxWidth((int) getResources().getDisplayMetrics().density * 312);
		category.setTextSize(30);
		category.setTextColor(Color.BLACK);
		category.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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

		categoryPoints.setTextColor(Color.BLACK);
		categoryPoints.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		categoryPoints.setTextSize(15);

		points.setTextSize(30);
		points.setTextColor(Color.BLACK);
		points.setId(View.generateViewId());

		rowConLay.addView(letter);
		rowConLay.addView(answersLay);
		rowConLay.addView(points);
		answersLay.addView(categoryAndPointsLay);
		categoryAndPointsLay.addView(category);
		categoryAndPointsLay.addView(categoryPoints);

		ConstraintSet conSet = new ConstraintSet();
		conSet.clone(rowConLay);
		conSet.connect(letter.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32);
		conSet.connect(letter.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 20);
		conSet.connect(answersLay.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 48);
		conSet.connect(answersLay.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,64);
		conSet.connect(answersLay.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
		conSet.connect(points.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
		conSet.connect(points.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 20);
		conSet.applyTo(rowConLay);

		vertLine.setImageResource(R.color.black);
		vertLine.setVisibility(View.VISIBLE);
		vertLine.setId(View.generateViewId());
		int dividerHeight = (int) getResources().getDisplayMetrics().density * 2;
		vertLine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight));

		ConstraintLayout vertLineLayout = new ConstraintLayout(this);
		vertLineLayout.setLayoutParams(new TableRow.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f));
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
						categoryPoints.setText("15");
					} else {
						categoryPoints.setText("0");
					}
					points.setText(categoryPoints.getText());
					Handler endOfRoundHandler = new Handler();
					Runnable endOfRoundRunnable = new Runnable() {
						@Override
						public void run() {
							setContentView(R.layout.activity_start_game);
						}
					};
					endOfRoundHandler.postDelayed(endOfRoundRunnable, 5000);
				}
			}
		};
		timerHandler.postDelayed(timerRunnable, 0);





	}



}