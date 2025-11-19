package com.chandu.common.events;


public class QuizCreatedEvent {

    private String categoryName;
    private int numOfQuestions;
    private String title;

    public QuizCreatedEvent(String categoryName, int numOfQuestions, String title) {
        this.categoryName = categoryName;
        this.numOfQuestions = numOfQuestions;
        this.title = title;
    }

    public QuizCreatedEvent() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
