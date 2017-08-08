package com.olek.testify.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.olek.testify.Utils.Wrapper;

import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TableAdapter {

    public boolean insert(Context context, String id_field_name, boolean autoincrement){
        SQLiteDatabase db = getWriteDb(context);
        return insert(db,id_field_name,autoincrement);
    }

    public boolean insert(SQLiteDatabase db, String id_field_name, boolean autoincrement){
        MLHTable a = this.getClass().getAnnotation(MLHTable.class);


        Table tableName = a.tableName();

        //INSERT INTO table_name
        //VALUES (value1, value2, value3, ...);


        List<String> tableInsertFields = new ArrayList<>();


        Wrapper wrap = new Wrapper('\"');

        List<Object> insertableObjects = new ArrayList<>();
        Map<Object, Class> insertableObjectTypes = new HashMap<>();

        for(Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(MLHField.class)) {
                continue;
            }

            MLHField fieldAnnotation = field.getAnnotation(MLHField.class);


            if (fieldAnnotation.fieldName() == id_field_name) {
                if (autoincrement == true) continue;
            }

            tableInsertFields.add(fieldAnnotation.fieldName());

            try {
                Object value = field.get(this);
                insertableObjectTypes.put(value, field.getType());
                insertableObjects.add(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }

        try{

            ContentValues cv = new ContentValues();

            for(int i = 0; i < insertableObjects.size(); i++){

                String currentInsertName = tableInsertFields.get(i);
                Object currentValue = insertableObjects.get(i);
                Class itemClass = insertableObjectTypes.get(currentValue);

                if (itemClass == Integer.class || itemClass == int.class) {
                    cv.put(currentInsertName, (Integer)currentValue);
                } else if (itemClass == String.class) {
                    cv.put(currentInsertName, (String)currentValue);
                } else if (itemClass == byte[].class) {
                    cv.put(currentInsertName, (byte[])currentValue);
                } else if (itemClass == Boolean.class) {
                    cv.put(currentInsertName, (Boolean)currentValue);
                } else {
                    throw new AnnotationFormatError("Unsuported field was annotated with @MLHField");
                }

            }

            try {
                db.insertOrThrow(tableName.toString(), null, cv);
            } catch (SQLException sqlEx){
                sqlEx.printStackTrace();
                return false;
            }




            //String sql  ="INSERT INTO " + tableName.toString() + " (" + fieldBuilder.toString() + ") VALUES (" + valueBuilder.toString() + ")";
            //SQLiteStatement insertStmt      =   db.compileStatement(sql);

            //for(int i = 0; i < blobMap.keySet().size(); i++){
            //    insertStmt.bindBlob(    i, blobMap.get(i));
            //}

            //insertStmt.execute();

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return true;
    }

    public static List<TableAdapter> getListBy(String tableField, String stringValue, Context context, Class<? extends TableAdapter> cl){

        List<TableAdapter> resultList = new ArrayList<>();

        MLHTable a = cl.getAnnotation(MLHTable.class);
        Table tableType = a.tableName();

        Constructor<?> constructor = null;

        try {
            constructor = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = getReadDb(context);

        Map<String, Class> map = getFieldNameTypeMapping(tableType);

        Cursor result = null;


        result = db.rawQuery("SELECT * FROM " + tableType.toString() + " WHERE " + tableField + " = " + stringValue, null);

        TableAdapter currentInstance = null;

        try {
            while (result.moveToNext()){
                currentInstance = (TableAdapter)constructor.newInstance();
                fillInstance(cl, map, result, currentInstance);
                resultList.add(currentInstance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return resultList;

    }

    public static TableAdapter getBy(String tableField, String stringValue,  Context context, Class<? extends TableAdapter> cl) {
        MLHTable a = cl.getAnnotation(MLHTable.class);
        Table tableType = a.tableName();

        Constructor<?> constructor = null;

        try {
            constructor = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = getReadDb(context);

        Map<String, Class> map = getFieldNameTypeMapping(tableType);

        Cursor result = null;


        result = db.rawQuery("SELECT * FROM " + tableType.toString() + " WHERE " + tableField + " = " + stringValue, null);

        TableAdapter currentInstance = null;

        try {
            if (result.moveToNext()){
                currentInstance = (TableAdapter)constructor.newInstance();
                fillInstance(cl, map, result, currentInstance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return currentInstance;

    }

    private static SQLiteDatabase getReadDb(Context context){
        SQLiteHelper helper = SQLiteHelper.get(context);
        return helper.getReadableDatabase();
    }

    private static SQLiteDatabase getWriteDb(Context context){
        SQLiteHelper helper = SQLiteHelper.get(context);
        return helper.getWritableDatabase();
    }

    public static <T extends TableAdapter> Map<T, Integer > getAllFromTableAsMap(Context context, Class<? extends TableAdapter> cl){

        List<TableAdapter> querriedList = getAllFromTable(context, cl);

        List<T> listConversion = (List<T>) (List<?>) querriedList;


        Field targetField = null;

        for(Field f: cl.getDeclaredFields()){
            if(f.isAnnotationPresent(MLHField.class) && f.getAnnotation(MLHField.class).fieldName().equals("ID")){
                targetField = f;
                break;
            }
        }

        if (targetField == null){
            throw new IllegalStateException("No ID in the table!");
        }

        Map<T, Integer> map = new HashMap<>();

        for(T element : listConversion){
            targetField.setAccessible(true);

            try {
                Integer id = (Integer) targetField.get(element);
                map.put(element, id);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }





        return map;
    }

    public static List<TableAdapter> getAllFromTable(Context context, Class<? extends TableAdapter> cl)
           {

        MLHTable a = cl.getAnnotation(MLHTable.class);
        Table tableName = a.tableName();

        Constructor<?> constructor = null;

        try {
            constructor = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = getReadDb(context);

        Map<String, Class> map = getFieldNameTypeMapping(tableName);

        Cursor result = db.query(false,
                tableName.toString(),
                map.keySet().toArray(new String[map.size()]),
                null, null, null, null, null, null);

        List<ContactInfo> contactInfoList = new ArrayList<>();

        TableAdapter currentInstance = null;

        List<TableAdapter> resultingList = new ArrayList<>();


        try {

            while (result.moveToNext()) {


                    currentInstance = (TableAdapter)constructor.newInstance();


                fillInstance(cl, map, result, currentInstance);

                resultingList.add(currentInstance);
            }

        } catch (InstantiationException e) {
               e.printStackTrace();
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           } catch (InvocationTargetException e) {
               e.printStackTrace();
           }

        return resultingList;

    }

    private static void fillInstance(Class<? extends TableAdapter> cl, Map<String, Class> map, Cursor result, TableAdapter currentInstance) throws IllegalAccessException {
        for (Field field : cl.getDeclaredFields()) {
            if (field.isAnnotationPresent(MLHField.class)) {

                String fieldAnnotationName = field.getAnnotation(MLHField.class).fieldName();
                field.setAccessible(true);

                int index = result.getColumnIndex(fieldAnnotationName);

                Class fieldClass = map.get(fieldAnnotationName);


                if (fieldClass == Integer.class) {
                    field.set(currentInstance, result.getInt(index));
                } else if (fieldClass == String.class) {
                    field.set(currentInstance, result.getString(index));
                } else if (fieldClass == byte[].class) {
                    field.set(currentInstance, result.getBlob(index));
                } else if (fieldClass == Boolean.class) {
                    field.set(currentInstance, result.getInt(index) > 0);
                } else {
                    throw new AnnotationFormatError("Unsuported field was annotated with @MLHField");
                }
            }
        }
    }

    private static Map<String, Class> getFieldNameTypeMapping(Table tableName) {
        return SQLiteHelper.ShittySQLParser.getTableMapping(tableName.toString());
    }

}
