package com.example.capitals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.AutoText;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import me.grantland.widget.AutofitHelper;

@SuppressLint("ViewConstructor")
public class Category extends androidx.appcompat.widget.AppCompatTextView {
	private int type;

	public Category(@NonNull Context context, int type) {
		super(context);
		this.type = type;

		this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
		this.setTextSize(20);
		if (type == 5) {
			this.setMaxLines(2);
		}else {
			this.setMaxLines(1);
		}
		this.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		this.setTextColor(Color.BLACK);
		AutofitHelper.create(this);
		this.setName();

	}

	private void setName() {
		switch (type) {
			case 0:
				this.setText("COUNTRIES");
				break;
			case 1:
				this.setText("CAPITALS");
				break;
			case 2:
				this.setText("ANIMALS");
				break;
			case 3:
				this.setText("PLANTS");
				break;
			case 4:
				this.setText("MOUNTAINS");
				break;
			case 5:
				this.setText("RIVERS/\nLAKES");
				break;
			default:
				this.setText("ERROR");
				break;

		}
	}
}
