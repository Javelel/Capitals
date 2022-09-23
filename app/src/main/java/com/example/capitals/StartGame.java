package com.example.capitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
	ScrollView gameView;
	TableLayout tabLay;
	static final int TIME_FOR_ANSWER = 31;
	static final int MAX_ROUNDS = 8;
	int totalPoints;
	TextView totalPointsTxt;
	Button readyBtn;
	int roundId;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
		Intent intent = getIntent();
		chosenCategories = intent.getBooleanArrayExtra("categories");
		numOfCategories = getNumOfCategories();
		totalPoints = 0;
	}

	public void randomizeLetter(View view) {
		Button randBtn = findViewById(R.id.randomizeBtn);
		randBtn.setClickable(false);
		letterTxt = findViewById(R.id.letterTxt);
		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		int duration = rnd.nextInt(20) + 10;
		final int[] i = {0};
		final int[] timer = {0};
		handler = new Handler();
		handler.post(new Runnable() {

			public void run() {
				if(roundId >= MAX_ROUNDS-1) {
					// End of the game
				}
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

		TableRow tabRow = new TableRow(this);	// 1 Row = 1 round
		ConstraintLayout rowConLay = new ConstraintLayout(this);	// Layout to fill the TableRow
		TextView letter = new TextView(this);	//	random letter
		Answer[] answers = new Answer[numOfCategories];	//	answers
		LinearLayout answersLay = new LinearLayout(this);	//	Layout containing all the answers
		LinearLayout[] answersAndPointsLay = new LinearLayout[numOfCategories];	//	Layouts with a particular answer and points form it
		TextView points = new TextView(this);	//	points scored in a particular round
		TextView[] smallPoints = new TextView[numOfCategories];	// points scored on each answer
		TableRow vertLineRow = new TableRow(this);	// row for the vertical line
		ImageView vertLine = new ImageView(this);	// vertical line between each round

		tabLay.addView(tabRow);
		tabRow.addView(rowConLay);

		letter.setText(String.valueOf(chosenLetter));
		letter.setTextSize(30);
		letter.setTextColor(Color.BLACK);
		letter.setId(View.generateViewId());

		answersLay.setId(View.generateViewId());
		answersLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


		points.setTextSize(30);
		points.setTextColor(Color.BLACK);
		points.setId(View.generateViewId());

		rowConLay.addView(letter);
		rowConLay.addView(answersLay);
		rowConLay.addView(points);

		int count = 0;
		for (int i=0; i<chosenCategories.length; i++) {
			if(chosenCategories[i]) {
				answers[count] = new Answer(this, i);
				smallPoints[count] = new TextView(this);
				answersAndPointsLay[count] = new LinearLayout(this);


				smallPoints[count].setTextSize(15);
				smallPoints[count].setTextColor(Color.BLACK);
				smallPoints[count].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
				answersAndPointsLay[count].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
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
		conSet.connect(answersLay.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 128);
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
					Handler endOfRoundHandler = new Handler();
					Runnable endOfRoundRunnable = () -> setContentView(R.layout.activity_start_game);
					endOfRoundHandler.postDelayed(endOfRoundRunnable, 5000);	// delay at the end of the round
				}
			}
		};


		View.OnClickListener ready = v -> {
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


}