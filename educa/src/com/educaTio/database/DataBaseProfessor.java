
package com.educaTio.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseProfessor extends SQLiteOpenHelper {

	private static DataBaseProfessor instance;

    private static final String COLUNA_PROFESSOR_ID = "ID";
    private static final String COLUNA_PROFESSOR_NOME = "Nome";
    private static final String COLUNA_PROFESSOR_TIPO_ATIVIDADE = "Tipo_atividade";
    private static final String COLUNA_PROFESSOR_ATIVIDADE_JSON = "Atividade";
    private static final String TABLE_ATIVIDADES_PROFESSOR = "AtividadesProfessor";
    
    private static final String COLUNA_LOGIN_JSON_DATA = "JSON_DATA";
    private static final String TABLE_LOGIN = "Login";
    private static final String SQL_CREATE_LOGIN = "CREATE TABLE " +
    		TABLE_LOGIN + " (" +
    		"ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
    		COLUNA_LOGIN_JSON_DATA + " VARCHAR );";
    
    private static final String SQL_DELETE_PROFESSOR_LOGIN = "DROP TABLE IF EXISTS Login";

    private static final String SQL_CREATE_PROFESSOR = "CREATE TABLE "
            + TABLE_ATIVIDADES_PROFESSOR + "("
            + COLUNA_PROFESSOR_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_PROFESSOR_TIPO_ATIVIDADE + " VARCHAR,"
            + COLUNA_PROFESSOR_ATIVIDADE_JSON + " VARCHAR,"
            + COLUNA_PROFESSOR_NOME + " VARCHAR );";

    private static final String SQL_DELETE_PROFESSOR_TABLE = "DROP TABLE IF EXISTS Professor";


    public static final String COLOR_MATCH_EXERCISE_TYPECODE = "COLOR_MATCH_EXERCISE";
    public static final String MULTIPLE_CHOICE_EXERCISE_TYPECODE = "MULTIPLE_CHOICE_EXERCISE";
    public static final String COMPLETE_EXERCISE_TYPECODE = "COMPLETE_EXERCISE";
    public static final String IMAGE_MATCH_EXERCISE_TYPECODE = "IMAGE_MATCH_EXERCISE";
    public static final String NUM_MATCH_EXERCISE_TYPECODE = "NUM_MATCH_EXERCISE";

    private DataBaseProfessor(Context context) {
        super(context, "educaNew.db", null, 1);
    }

    public static DataBaseProfessor getInstance(final Context context) {
        if (instance == null) {
            instance = new DataBaseProfessor(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_PROFESSOR);
        db.execSQL(SQL_CREATE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PROFESSOR_TABLE);
        db.execSQL(SQL_DELETE_PROFESSOR_LOGIN);
        onCreate(db);
        
    }

    @Override
    public final void onDowngrade(final SQLiteDatabase dbHelper,
            final int oldVersion, final int newVersion) {
        onUpgrade(dbHelper, oldVersion, newVersion);
        
    }
    
    public final long addActivity(String name, String activityType, String activity) {

        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(COLUNA_PROFESSOR_NOME, name);
        values.put(COLUNA_PROFESSOR_TIPO_ATIVIDADE, activityType);
        values.put(COLUNA_PROFESSOR_ATIVIDADE_JSON, activity);

        System.out.println("addActivity: " + name + " " + activityType + " " + activity);
        
        long id = db.insert(TABLE_ATIVIDADES_PROFESSOR, null, values);

        db.close();
        
        return id;
    }
    
    public List<String> getActivities(){
    	List<String> activities = new ArrayList<String>();
    	
    	String sql = "select * from " + TABLE_ATIVIDADES_PROFESSOR;
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	final Cursor c = db.rawQuery(sql, null);
    	
    	if (c.getCount() > 0 && c.moveToFirst()){
    		for (int i = 0; i < c.getCount(); i++) {
    			activities.add(c.getString(2));
    			c.moveToNext();
			}
    	}
    	
    	c.close();
    	db.close();
    	
    	return activities;
    }
    
    public List<String> getActivities(String type){
    	List<String> activities = new ArrayList<String>();
    	
    	String sql = "select * from " + TABLE_ATIVIDADES_PROFESSOR + " where " + COLUNA_PROFESSOR_TIPO_ATIVIDADE + " = '" + type + "'";
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	final Cursor c = db.rawQuery(sql, null);
    	
    	if (c.getCount() > 0 && c.moveToFirst()){
    		for (int i = 0; i < c.getCount(); i++) {
    			activities.add(c.getString(2));
    			c.moveToNext();
			}
    	}
    	
    	c.close();
    	db.close();
    	
    	return activities;
    }

    public void removeActivity(String name){
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	
    	String whereClause = COLUNA_PROFESSOR_NOME + " = '" + name + "'";

    	db.delete(TABLE_ATIVIDADES_PROFESSOR, whereClause, null);
    	
    	db.close();
    }

	public List<String> getActivitiesName() {
    	List<String> activities = new ArrayList<String>();
    	
    	String sql = "select " + COLUNA_PROFESSOR_NOME + " from " + TABLE_ATIVIDADES_PROFESSOR;
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	final Cursor c = db.rawQuery(sql, null);
    	
    	if (c.getCount() > 0 && c.moveToFirst()){
    		for (int i = 0; i < c.getCount(); i++) {
    			activities.add(c.getString(0));
    			c.moveToNext();
			}
    	}
    	
    	c.close();
    	db.close();
		return activities;
	}
	
    public final long addUser(String name, String login, String password) {

        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();
        
        JSONObject data = new JSONObject();
        try {
        	data.put("name", name);
			data.put("login", login);
			data.put("password", password);
		} catch (JSONException e) {
            String LOG = "JSON DATABASE PROFESSOR";
            Log.e(LOG, e.getMessage());
        }

        values.put(COLUNA_LOGIN_JSON_DATA, data.toString());
        
        long id = db.insert(TABLE_LOGIN, null, values);

        db.close();
        
        return id;
    }

    public HashMap<String, String> getUsers(){
    	HashMap<String, String> users = new HashMap<String, String>();
    	
    	String sql = "select " + COLUNA_LOGIN_JSON_DATA + " from Login";
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	final Cursor c = db.rawQuery(sql, null);
    	
    	if (c.getCount() > 0 && c.moveToFirst()){
    		for (int i = 0; i < c.getCount(); i++) {
    			try {
    				JSONObject data = new JSONObject(c.getString(0));
    				users.put(data.getString("login"), data.getString("password"));
				} catch (Exception e) {
		            String LOG = "JSON DATABASE PROFESSOR";
		            Log.e(LOG, e.getMessage());
				}
    			c.moveToNext();
			}
    	}
    	
    	c.close();
    	db.close();
    	
    	return users;
    }
}
