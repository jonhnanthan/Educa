package com.educa.entity;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class MultipleCorrectChoiceExercise extends Exercise {
	private String alternative1;
	private String alternative2;
	private String alternative3;
	private String alternative4;
	private String rightAnswer[];

	public MultipleCorrectChoiceExercise(String name, String type, String date,
			String status, String correction, String question,
			String alternative1, String alternative2, String alternative3,
			String alternative4, String rightAnswer[]) {
		super(null, name, question, type, date, status, correction);
		this.alternative1 = alternative1;
		this.alternative2 = alternative2;
		this.alternative3 = alternative3;
		this.alternative4 = alternative4;
		this.rightAnswer = rightAnswer;

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
			activityData.put("alternative1", alternative1);
			activityData.put("alternative2", alternative2);
			activityData.put("alternative3", alternative3);
			activityData.put("alternative4", alternative4);
			activityData.put("answer", getStringRightAnswer());

		} catch (JSONException e) {
			String LOG = "LOGs";
			Log.e(LOG, e.getMessage());
		}

		return activityData.toString();
	}

	private String getStringRightAnswer() {
		String answer = "";

		for (int i = 0; i < rightAnswer.length; i++) {
			if (i == 0) {
				answer += rightAnswer[i];
			} else {
				answer += ", " + rightAnswer[i];
			}
		}

		return answer;
	}

	public String getAlternative1() {
		return alternative1;
	}

	public void setAlternative1(String alternative1) {
		this.alternative1 = alternative1;
	}

	public String getAlternative2() {
		return alternative2;
	}

	public void setAlternative2(String alternative2) {
		this.alternative2 = alternative2;
	}

	public String getAlternative3() {
		return alternative3;
	}

	public void setAlternative3(String alternative3) {
		this.alternative3 = alternative3;
	}

	public String getAlternative4() {
		return alternative4;
	}

	public void setAlternative4(String alternative4) {
		this.alternative4 = alternative4;
	}

	public String[] getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer[]) {
		this.rightAnswer = rightAnswer;
	}

	@Override
	public String toString() {
		return "Answer1: " + alternative1 + "\n" + "Answer2: " + alternative2
				+ "\n" + "Answer3: " + alternative3 + "\n" + "Answer4: "
				+ alternative4 + "\n" + "RightAnswer: " + rightAnswer;
	}
}
