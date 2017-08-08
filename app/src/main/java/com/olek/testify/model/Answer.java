package com.olek.testify.model;

/*
ID INT NOT NULL,
 isCorrect BOOLEAN NOT NULL,

ID_TASK INT NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (ID_TASK) REFERENCES Task(ID)
 */

import java.io.Serializable;

@MLHTable(tableName = Table.T_ANSWER)
public class Answer extends TableAdapter implements Serializable{


    @MLHField(fieldName = "ID")
    private int id;
    @MLHField(fieldName = "IS_CORRECT")
    private boolean isCorrect;
    @MLHField(fieldName = "ID_TASK")
    private int id_task;
    @MLHField(fieldName = "KEY_CODE")
    private String keyCode;
    @MLHField(fieldName = "ANSWER_TEXT")
    private String answerText;

    @Override
    public String toString() {
        return "Answer[" +
                "isCorrect=" + isCorrect +
                ", keyCode='" + keyCode + '\'' +
                ", answerText='" + answerText + '\'' +
                ']';
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public Answer(){

    }

    public Answer(int id, boolean isCorrect, int id_task) {
        this.id = id;
        this.isCorrect = isCorrect;
        this.id_task = id_task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }
}
