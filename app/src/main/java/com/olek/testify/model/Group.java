package com.olek.testify.model;

/*
ID INT NOT NULL,
	 FIRST_NAME VARCHAR(36) NOT NULL,
	 SURNAME TEXT NOT NULL,
         ID_LANGUAGE INT NOT NULL,

	PRIMARY KEY (ID),
	FOREIGN KEY (ID_LANGUAGE) REFERENCES Language(ID)
 */

@MLHTable(tableName = Table.T_GROUP)
public class Group extends TableAdapter {

    @MLHField(fieldName = "ID")
    private int id;
    @MLHField(fieldName = "CODE_NAME")
    private String code_name;
    @MLHField(fieldName = "DESCRIPTION")
    private String text;
    @MLHField(fieldName = "ID_LANGUAGE")
    private int id_lang;

    public Group() {
    }

    public Group(int id, String code_name, String text, int id_lang) {
        this.id = id;
        this.code_name = code_name;
        this.text = text;
        this.id_lang = id_lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId_lang() {
        return id_lang;
    }

    public void setId_lang(int id_lang) {
        this.id_lang = id_lang;
    }
}
