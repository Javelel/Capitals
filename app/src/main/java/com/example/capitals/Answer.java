package com.example.capitals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import me.grantland.widget.AutofitHelper;

@SuppressLint("ViewConstructor")
public class Answer extends androidx.appcompat.widget.AppCompatEditText {

	private int type;

	public Answer(@NonNull Context context, int type) {
		super(context);
		this.type = type;

		this.setLines(2);
		this.setMaxLines(2);
		this.setMaxWidth((int) getResources().getDisplayMetrics().density * 312);
		this.setTextSize(20);
		this.setTextColor(Color.BLACK);
		this.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
		InputFilter[] filterArray = new InputFilter[3];
		filterArray[0] = new InputFilter.LengthFilter(32);
		filterArray[1] = new InputFilter.AllCaps();
		filterArray[2] = (source, start, end, dest, dstart, dend) -> {
			for (int i = start; i < end; i++) {
				if (!Character.isLetter(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {
					return "";
				}
			}
			return null;
		};
		this.setFilters(filterArray);
		AutofitHelper.create(this);

	}

	public int getType() {
		return type;
	}
}
