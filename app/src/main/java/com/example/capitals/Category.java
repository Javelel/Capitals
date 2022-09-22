package com.example.capitals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import me.grantland.widget.AutofitHelper;

@SuppressLint("ViewConstructor")
public class Category extends androidx.appcompat.widget.AppCompatTextView {
	private int type;

	public Category(@NonNull Context context, int type) {
		super(context);
		this.type = type;

		this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
		this.setTextSize(30);
		this.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		this.setTextColor(Color.BLACK);
		this.setName();
		AutofitHelper.create(this);

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
				this.setText("RIVERS/LAKES");
				break;
			default:
				this.setText("ERROR");
				break;

		}
	}
}
