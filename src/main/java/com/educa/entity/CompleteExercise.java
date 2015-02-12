
package com.educa.entity;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;


public class CompleteExercise extends Exercise {
    private static final String LOG = "LOGs";
    private String word;
    private String hiddenIndexes;

    public CompleteExercise(String name, String type, String date, String status,
            String correction, String question, String word, String hiddenIndexes) {
        super(null, name, question, type, date, status, correction);
        this.word = word;
        this.hiddenIndexes = hiddenIndexes;
		
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
			activityData.put("word", word);
			activityData.put("hiddenIndexes", hiddenIndexes);
			
		} catch (JSONException e) {
            Log.e(LOG, e.getMessage());
		}
		return activityData.toString();
	}


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHiddenIndexes() {
        return hiddenIndexes;
    }

    public void setHiddenIndexes(String hiddenIndexes) {
        this.hiddenIndexes = hiddenIndexes;
    }

    @Override
    public String toString() {
        return "CompleteExercise, word=" + word
                + ", hiddenIndexes=" + hiddenIndexes + "]";
    }
}
