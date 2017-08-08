package com.olek.testify.model;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.olek.testify.Utils.RandomString;
import com.olek.testify.Utils.Wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TAG = SQLiteHelper.class.getSimpleName();

    public static final String CONTACT_INFO = "ContactInfo";

    public static final String T_COURSE = "Course";
    public static final String T_LANGUAGE = "Language";
    public static final String T_SUBJECT = "Subject";
    public static final String T_GROUP = "StudentGroup";
    public static final String T_STUDENT = "Student";
    public static final String T_GROUP_COURSES = "StudentGroupCourses";
    public static final String T_TEST = "Test";
    public static final String T_RESULTS = "Results";
    public static final String T_TASK = "Task";
    public static final String T_ANSWER = "Answer";



    public static final int version = 25 ;

    final String CREATE_TABLE = "CREATE TABLE "+ CONTACT_INFO +"(" +
            "   ID                INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   NAME VARCHAR (36)     NOT NULL," +
            "   SURNAME VARCHAR (36)  NOT NULL," +
            "   EMAIL VARCHAR (46)    NOT NULL" +
            ")";

     final static String DB_NAME = "mySuperDB.db";
    Context mContext;

    private SQLiteHelper(Context context){
        super(context, DB_NAME, null, version);
        mContext = context;

        try {
            SQLAsString = extractSQLFromFile();
            ShittySQLParser.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    static SQLiteHelper instance = null;

    static public SQLiteHelper get(Context context){
        if (instance == null){
            instance = new SQLiteHelper(context);
        }

        return instance;
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        try {
            initDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillDatabase(db);
    }

    private static String SQLAsString = null;


    private void initDatabase(SQLiteDatabase db) throws IOException {


        BufferedReader is = getBufferedReaderSQL();


        StringBuilder sb = new StringBuilder();
        String line = "";

        while((line = is.readLine()) != null){
            sb.append(line);

            if (line.endsWith(";")){
                // make a querry

                String querry = sb.toString();
                Log.d(TAG, querry);
                db.execSQL(querry);

                sb = new StringBuilder();
            }
        }
    }

    private String extractSQLFromFile() throws IOException {

        BufferedReader br = null;

        try {
            br = getBufferedReaderSQL();
        } catch (UnsupportedEncodingException e) { e.printStackTrace(); }

        String line = "";

        StringBuilder sb = new StringBuilder();

        while((line = br.readLine()) != null){
            sb.append(line + "\n");
        }

        return sb.toString();
    }

    @NonNull
    private BufferedReader getBufferedReaderSQL() throws UnsupportedEncodingException {
        mContext.getResources().getIdentifier("sql",
                "raw", mContext.getPackageName());

        InputStream raw = mContext.getResources().openRawResource(
                mContext.getResources().getIdentifier("sql",
                        "raw", mContext.getPackageName()));

        return new BufferedReader(new InputStreamReader(raw, "UTF8"));
    }




    private void fillDatabase(SQLiteDatabase db) {


        Wrapper w = new Wrapper('\"');
        RandomString rsg = new RandomString(10);
        RandomString rsgShort = new RandomString(5);

        for(int i = 0; i < 10; i++){
            db.execSQL("INSERT INTO " + CONTACT_INFO
                    + "(NAME, SURNAME, EMAIL) VALUES ("
                    + w.wrap(rsg.nextString()) + ","
                    + w.wrap(rsg.nextString()) + ","
                    + w.wrap(rsg.nextString() + "@" + rsgShort.nextString() + "." + rsgShort.nextString()) + ");");

        }

        final int languageCount = 5;
        final int subjectCount = 20;
        final int studentCount = 10;
        final int courseCount = 4;

        Random random = new Random();

        for(int i = 0; i < subjectCount; i++){
            new Subject(0, rsgShort.nextString(), rsg.nextString()).insert(db, "ID", true);
        }

        for(int i = 0; i < languageCount; i++){
            new Language(0, rsg.nextString()).insert(db, "ID", true);
        }

        for(int i = 0; i < studentCount; i++){
            new Group(0, rsgShort.nextString(),
                    rsg.nextString(),
                    random.nextInt(languageCount))
                    .insert(db, "ID", true);
        }

        for(int i = 0; i < courseCount; i++){
            new Course(0, random.nextInt(10), random.nextInt(subjectCount))
                    .insert(db, "ID", true);
        }




    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //проверяете какая версия сейчас и делаете апдейт

        db.execSQL("DROP TABLE IF EXISTS ContactInfo");
        onCreate(db);
    }

    public static class ShittySQLParser {

        private static Map<String, Class> sqlTJ
                = new LinkedHashMap<>();

        private static Map<String, Map<String, Class>> tableMapping
                = new LinkedHashMap<>();



        static {
            sqlTJ.put("INT", Integer.class);
            sqlTJ.put("INTEGER", Integer.class);
            sqlTJ.put("VARCHAR", String.class);
            sqlTJ.put("TEXT", String.class);
            sqlTJ.put("BLOB", byte[].class);
            sqlTJ.put("BOOLEAN", Boolean.class);
        }

        public static Map<String, Class> getTableMapping(String tableName)
                throws IllegalArgumentException {

            if (!tableMapping.containsKey(tableName))
                throw new IllegalArgumentException("No such table in parsed sql.");

            return tableMapping.get(tableName);
        }

        static public void init(){
            String[] lines = SQLAsString.split("\n");

            String currentTableName = "";
            boolean newTable = true;

            for(String line : lines){

                line = line.trim().replaceAll(" +", " ");

                String[] parts = line.split(" ");

                if(parts == null || parts.length < 2) continue;

                switch (parts[0]){
                    case "CREATE":
                        newTable = true;
                        currentTableName = parts[2];
                        break;
                    case "PRIMARY":
                    case "FOREIGN":
                    case ");":
                    case "DROP":
                        continue;
                    default:
                        break;
                }

                if (newTable){
                    tableMapping.put(currentTableName, new HashMap<String, Class>());
                    newTable = false;
                } else {
                    tableMapping.get(currentTableName).put(parts[0], sqlTJ.get(parts[1]));
                }


            }

        }

    }
}






















