package com.olek.testify.model;

/*

	ID_STUDENT INT NOT NULL,
	ID_TEST INT NOT NULL,
	MARK INT,
	PRIMARY KEY (ID_STUDENT, ID_TEST),
FOREIGN KEY (ID_STUDENT) REFERENCES Student(ID),
FOREIGN KEY (ID_TEST) REFERENCES Test(ID)
 */

@MLHTable(tableName = Table.T_RESULTS)
public class Result extends TableAdapter {

    @MLHField(fieldName = "ID_STUDENT")
    private int id_student;
    @MLHField(fieldName = "ID_TEST")
    private int id_test;

    public Result(){

    }

    public Result(int id_student, int id_test) {
        this.id_student = id_student;
        this.id_test = id_test;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }
}
