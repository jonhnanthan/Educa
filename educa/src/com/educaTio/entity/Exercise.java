
package com.educaTio.entity;


public class Exercise {
    private Integer id;
    private String name;
    private String question;
    private String type;
    private String date;
    private String status;
    private String correction;

    public Exercise(Integer id, String name, String question, String type, String date, String status, String correction) {
        this.id = id;
        this.name = name;
        this.question = question;
        this.type = type;
        this.date = date;
        this.status = status;
        this.correction = correction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
