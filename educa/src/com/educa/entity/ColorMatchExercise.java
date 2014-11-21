
package com.educa.entity;

public class ColorMatchExercise extends Exercise {

    private String color;
    private String alternative1;
    private String alternative2;
    private String alternative3;
    private String alternative4;
    private String rightAnswer;

    public ColorMatchExercise(String name, String type, String date, String status,
            String correction, String question, String alternative1, String alternative2,
            String alternative3, String alternative4, String rightAnswer, String color) {
        super(null, name, question, type, date, status, correction);
        this.alternative1 = alternative1;
        this.alternative2 = alternative2;
        this.alternative3 = alternative3;
        this.alternative4 = alternative4;
        this.rightAnswer = rightAnswer;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
