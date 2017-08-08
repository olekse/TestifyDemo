package com.olek.testify.model;

/*
   ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT  ,
   NAME VARCHAR (36) NOT NULL
 */

@MLHTable(tableName = Table.T_LANGUAGE)
public class Language  extends TableAdapter {

    @MLHField(fieldName = "ID")
    private int id;
    @MLHField(fieldName = "NAME")
    private String name;

    public Language() {
    }

    public Language(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
