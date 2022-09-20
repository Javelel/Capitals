package com.example.capitals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class GameView extends View {

	Context context;
	boolean[] chosenCategories;
	char chosenLetter;
	Rect rect;
	static int dWidth, dHeight;

	public GameView( Context context, boolean[] chosenCategories, char chosenLetter ) {
		super(context);
		this.chosenCategories = chosenCategories;
		this.chosenLetter = chosenLetter;
		int numOfCategories = 0;
		for (boolean cat : chosenCategories) {
			if(cat == true)
				numOfCategories++;
		}


		Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		dWidth = size.x;
		dHeight = size.y;
		rect = new Rect(0, 0, dWidth, dHeight);

		this.context = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);


		/*LinearLayout testLayout = new LinearLayout(context);

		TextView test = new TextView(context);
		test.setVisibility(View.VISIBLE);
		test.setTextSize(25);
		test.setTextColor(Color.RED);
		test.setText(String.valueOf(life));
		test.setPadding(16, canvas.getHeight()/2 + 100, canvas.getWidth() - 16, 0);

		testLayout.addView(lifesTextValue);
		testLayout.measure(canvas.getWidth(), canvas.getHeight());
		testLayout.layout(0, 0, canvas.getWidth(), canvas.getHeight());
		testLayout.draw(canvas);*/



		TextView letter = new TextView(context);
		letter.setText(String.valueOf(chosenLetter));
		letter.layout(0,0,canvas.getWidth(),canvas.getHeight());
		letter.setVisibility(View.VISIBLE);
		letter.draw(canvas);
		System.out.println(chosenLetter);


	}
}
