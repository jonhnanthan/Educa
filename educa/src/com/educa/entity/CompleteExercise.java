
package com.educa.entity;

import org.json.JSONException;
import org.json.JSONObject;


public class CompleteExercise extends Exercise {
    private String word;
    private String hiddenIndexes;
    private JSONObject activityData = new JSONObject();

    public CompleteExercise(String name, String type, String date, String status,
            String correction, String question, String word, String hiddenIndexes) {
        super(null, name, question, type, date, status, correction);
        this.word = word;
        this.hiddenIndexes = hiddenIndexes;

        try {
			activityData.put("name", name);
			activityData.put("type", type);
			activityData.put("date", date);
			activityData.put("status", status);
			activityData.put("correction", correction);
			activityData.put("question", question);
			activityData.put("word", word);
			activityData.put("hiddenIndexes", hiddenIndexes);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
    }

	public String getJsonTextObject() {
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
