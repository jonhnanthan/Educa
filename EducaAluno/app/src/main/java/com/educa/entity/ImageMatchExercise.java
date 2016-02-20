package com.educa.entity;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageMatchExercise extends Exercise {
	private static final String LOG = "LOGs";
	private String color;
	private String rightAnswer;

	public ImageMatchExercise(String name, String type, String date,
			String status, String correction, String question,
			String rightAnswer, String colorCode) {
		super(null, name, question, type, date, status, correction);

		this.rightAnswer = rightAnswer;
		this.color = colorCode;

	}

	public String getJsonTextObject() {
		JSONObject activityData = new JSONObject();
		try {
			activityData.put("name", this.getName());
			activityData.put("type", this.getType());
			activityData.put("date", this.getDate());
			activityData.put("status", this.getStatus());
			activityData.put("correction", this.getCorrection());
			activityData.put("question", this.getQuestion());
			activityData.put("answer", rightAnswer);
			activityData.put("color", color);

		} catch (JSONException e) {
			Log.e(LOG, e.getMessage());
		}
		return activityData.toString();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
}
