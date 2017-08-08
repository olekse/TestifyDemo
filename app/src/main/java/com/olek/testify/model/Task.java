package com.olek.testify.model;


import java.io.Serializable;

/*
ID INT NOT NULL,
 TEXT TEXT     NOT NULL,

ID_TEST INT NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (ID_TEST) REFERENCES Test(ID)
 */
@MLHTable(tableName = Table.T_TASK)
public class Task extends TableAdapter implements Serializable{

    @MLHField(fieldName = "ID")
    private int id;
    @MLHField(fieldName = "TEXT")
    private String text;
    @MLHField(fieldName = "ID_TEST")
    private int id_test;

    @MLHField(fieldName = "NUMBER")
    private int number;

    @Override
    public String toString() {
        return "Task[" +
                "text='" + text + '\'' +
                ", number=" + number +
                ']';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Task(){

    }

    public Task(int id, String text, int id_test) {
        this.id = id;
        this.text = text;
        this.id_test = id_test;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }
}
