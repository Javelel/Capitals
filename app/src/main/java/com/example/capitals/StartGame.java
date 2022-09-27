package com.example.capitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	int numOfCategories;
	char chosenLetter;
	String alphabet;
	TextView timeLeft;
	ConstraintLayout gameView;
	TableLayout tabLay;
	static final int TIME_FOR_ANSWER = 3;
	static final int MAX_ROUNDS = 10;
	int totalPoints;
	TextView totalPointsTxt;
	Button readyBtn;
	int roundId;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		setContentView(R.layout.activity_start_game);
		Intent intent = getIntent();
		chosenCategories = intent.getBooleanArrayExtra("categories");
		numOfCategories = getNumOfCategories();
		totalPoints = 0;
		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	}

	public void randomizeLetter(View view) {
		Button randBtn = findViewById(R.id.randomizeBtn);
		randBtn.setClickable(false);
		letterTxt = findViewById(R.id.letterTxt);
		Random rnd = new Random();
		int duration = rnd.nextInt(20) + 10;
		final int[] i = {0};
		final int[] timer = {0};
		handler = new Handler();
		handler.post(new Runnable() {

			public void run() {
				char lastLetter = i[0] == 0 ? alphabet.charAt(alphabet.length() - 1) : alphabet.charAt(i[0] - 1);
				char letter = alphabet.charAt(i[0]);
				if(i[0] >= alphabet.length()-1) {
					i[0] = 0;
				}
				letterTxt.setText(String.valueOf(letter));
				timer[0]++;
				if(timer[0] <= duration) {
					handler.postDelayed(this, 80);
				} else {
					chosenLetter = lastLetter;
					StringBuilder alphTemp = new StringBuilder(alphabet);
					if(i[0] == 0) {
						alphTemp.deleteCharAt(alphTemp.length()-1);
					} else {
						alphTemp.deleteCharAt(i[0] - 1);
					}
					alphabet = alphTemp.toString();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					theGame();
				}
				i[0]++;
			}
		});
	}

	private void theGame() {

		if(gameView == null) {
			setContentView(R.layout.category_1);

			LinearLayout categoryTitleLayout = findViewById(R.id.category);
			ConstraintLayout mainConLay = findViewById(R.id.conLay);
			addHorizontalLines(mainConLay);

			Category[] categories = new Category[numOfCategories];

			int count = 0;
			for (int i=0; i<chosenCategories.length; i++) {
				if(chosenCategories[i]) {
					categories[count] = new Category(this, i);
					categoryTitleLayout.addView(categories[count++]);
				}
			}

			gameView = findViewById(R.id.gameView);
			tabLay = findViewById(R.id.tabLay);
			timeLeft = findViewById(R.id.timeLeftTxt);
			totalPointsTxt = findViewById(R.id.totalPts);
			readyBtn = findViewById(R.id.readyBtn);
			roundId = 0;
		} else {
			setContentView(gameView);
			roundId++;
		}
		timeLeft.setText("");

		TableRow tabRow = new TableRow(this);	// 1 Row = 1 round
		ConstraintLayout rowConLay = new ConstraintLayout(this);	// Layout to fill the TableRow
		TextView letter = new TextView(this);	//	random letter
		Answer[] answers = new Answer[numOfCategories];	//	answers
		LinearLayout answersLay = new LinearLayout(this);	//	Layout containing all the answers
		LinearLayout[] answersAndPointsLay = new LinearLayout[numOfCategories];	//	Layouts with a particular answer and points form it
		TextView points = new TextView(this);	//	points scored in a particular round
		TextView[] smallPoints = new TextView[numOfCategories];	// points scored on each answer
		TableRow vertLineRow = new TableRow(this);	// row for the vertical line
		ImageView horLine = new ImageView(this);	// horizontal line between each round

		tabLay.addView(tabRow);
		tabRow.addView(rowConLay);
		tabRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		letter.setText(String.valueOf(chosenLetter));
		letter.setTextSize(30);
		letter.setTextColor(Color.BLACK);
		letter.setId(View.generateViewId());

		answersLay.setId(View.generateViewId());
		answersLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


		points.setTextSize(30);
		points.setTextColor(Color.BLACK);
		points.setId(View.generateViewId());

		rowConLay.addView(letter);
		rowConLay.addView(answersLay);
		rowConLay.addView(points);
		rowConLay.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

		int count = 0;
		for (int i=0; i<chosenCategories.length; i++) {
			if(chosenCategories[i]) {
				answers[count] = new Answer(this, i);
				smallPoints[count] = new TextView(this);
				answersAndPointsLay[count] = new LinearLayout(this);


				smallPoints[count].setTextSize(15);
				smallPoints[count].setTextColor(Color.BLACK);
				smallPoints[count].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
				answersAndPointsLay[count].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
				answersAndPointsLay[count].setOrientation(LinearLayout.VERTICAL);
				answersLay.addView(answersAndPointsLay[count]);
				answersAndPointsLay[count].addView(answers[count]);
				answersAndPointsLay[count].addView(smallPoints[count++]);
			}
		}


		ConstraintSet conSet = new ConstraintSet();
		conSet.clone(rowConLay);
		conSet.connect(letter.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32);
		conSet.connect(letter.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 20);
		conSet.connect(answersLay.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 128);
		conSet.connect(answersLay.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 176);
		conSet.connect(answersLay.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
		conSet.connect(points.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
		conSet.connect(points.getId(), ConstraintSet.LEFT, answersLay.getId(), ConstraintSet.RIGHT);
		conSet.connect(points.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 20);
		conSet.applyTo(rowConLay);

		horLine.setImageResource(R.color.black);
		horLine.setVisibility(View.VISIBLE);
		horLine.setId(View.generateViewId());
		int dividerHeight = (int) getResources().getDisplayMetrics().density * 2;
		horLine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight));

		ConstraintLayout horLineLayout = new ConstraintLayout(this);
		horLineLayout.setLayoutParams(new TableRow.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f));
		horLineLayout.addView(horLine);
		vertLineRow.addView(horLineLayout);

		Handler timerHandler = new Handler();
		Runnable timerRunnable = new Runnable() {
			@Override
			public void run() {
				if(!timeLeft.getText().equals("0")) {
					timeLeft.setText(String.valueOf(Integer.parseInt((String) timeLeft.getText()) - 1));
					timerHandler.postDelayed(this, 1000);
				} else {
					for (int i=0; i<numOfCategories; i++) {
						answers[i].setEnabled(false);
						if(answers[i].getText() != null && Objects.requireNonNull(answers[i].getText()).length() >= 1 && Objects.requireNonNull(answers[i].getText()).charAt(0) == chosenLetter) {
							smallPoints[i].setText("15");
						} else {
							smallPoints[i].setText("0");
						}
					}

					tabLay.addView(vertLineRow);

					int pointsFromRound = 0;

					for (TextView smallPoint: smallPoints) {
						pointsFromRound += Integer.parseInt((String) smallPoint.getText());
					}

					points.setText(String.valueOf(pointsFromRound));
					totalPoints += pointsFromRound;
					totalPointsTxt.setText(String.valueOf(totalPoints));
					if(roundId >= MAX_ROUNDS-1) {
						// End of the game
						endOfTheGame();
					}
					Handler endOfRoundHandler = new Handler();
					Runnable endOfRoundRunnable = () -> setContentView(R.layout.activity_start_game);
					endOfRoundHandler.postDelayed(endOfRoundRunnable, 2000);	// delay at the end of the round
				}
			}
		};


		View.OnClickListener ready = v -> {
			readyBtn.setClickable(false);
			timeLeft.setText(String.valueOf(TIME_FOR_ANSWER));
			timerHandler.postDelayed(timerRunnable, 0);
		};
		readyBtn.setOnClickListener(ready);

	}

	private int getNumOfCategories() {
		int count = 0;
		for (boolean category : chosenCategories) {
			if(category)
				count++;
		}
		return count;
	}

	private void addHorizontalLines(ConstraintLayout mainConLay) {

		double width;
		if (Resources.getSystem().getDisplayMetrics().heightPixels > Resources.getSystem().getDisplayMetrics().widthPixels) {
			width = Resources.getSystem().getDisplayMetrics().heightPixels - 128 - 176;
		} else {
			width = Resources.getSystem().getDisplayMetrics().widthPixels - 128 - 176;
		}



		for(int i=0; i<numOfCategories-1; i++) {
			ImageView vertLine = new ImageView(this);
			vertLine.setImageResource(R.color.black);
			vertLine.setId(View.generateViewId());
			int dividerWidth = (int) getResources().getDisplayMetrics().density * 2;
			vertLine.setLayoutParams(new ViewGroup.LayoutParams(dividerWidth, 1000));

			double dist = 128 + width * (((double)i+1)/(double)numOfCategories);

			mainConLay.addView(vertLine);
			ConstraintSet vertLineSet = new ConstraintSet();
			vertLineSet.clone(mainConLay);
			vertLineSet.connect(vertLine.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, (int) dist);
			vertLineSet.connect(vertLine.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
			vertLineSet.connect(vertLine.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
			vertLineSet.applyTo(mainConLay);

		}


	}

	private void endOfTheGame() {
		Intent intent = new Intent(this, EndOfTheGame.class);
		intent.putExtra("result", totalPoints);
		startActivity(intent);
		finish();
	}

}