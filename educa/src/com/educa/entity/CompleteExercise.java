
package com.educa.entity;


public class CompleteExercise extends Exercise {
    private String word;
    private String hiddenIndexes;

    public CompleteExercise(String name, String type, String date, String status,
            String correction, String question, String word, String hiddenIdexes) {
        super(null, name, question, type, date, status, correction);
        this.word = word;
        hiddenIndexes = hiddenIdexes;
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
