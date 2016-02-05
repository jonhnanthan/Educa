
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
    private static final String COLUNA_PROFESSOR_OWNER = "Dono_da_atividade";
    private static final String COLUNA_PROFESSOR_NOME = "Nome";
    private static final String COLUNA_PROFESSOR_TIPO_ATIVIDADE = "Tipo_atividade";
    private static final String COLUNA_PROFESSOR_ATIVIDADE_JSON = "Atividade";
    private static final String TABLE_ATIVIDADES_PROFESSOR = "AtividadesProfessor";
    private static final String SQL_CREATE_PROFESSOR = "CREATE TABLE "
    		+ TABLE_ATIVIDADES_PROFESSOR + "("
    		+ COLUNA_PROFESSOR_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
    		+ COLUNA_PROFESSOR_OWNER + " VARCHAR,"
    		+ COLUNA_PROFESSOR_TIPO_ATIVIDADE + " VARCHAR,"
    		+ COLUNA_PROFESSOR_ATIVIDADE_JSON + " VARCHAR,"
    		+ COLUNA_PROFESSOR_NOME + " VARCHAR );";
    private static final String SQL_DELETE_PROFESSOR_TABLE = "DROP TABLE IF EXISTS " + TABLE_ATIVIDADES_PROFESSOR;
    
    private static final String COLUNA_LOGIN_JSON_DATA = "JSON_DATA";
    private static final String TABLE_LOGIN = "Login";
    private static final String SQL_CREATE_LOGIN = "CREATE TABLE " 
    		+ TABLE_LOGIN + " ("
    		+ "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
    		+ COLUNA_LOGIN_JSON_DATA + " VARCHAR );";
    private static final String SQL_DELETE_PROFESSOR_LOGIN = "DROP TABLE IF EXISTS " + TABLE_LOGIN;

    private static final String TABLE_RELATORIO = "Relatorio";
    private static final String COLUNA_RELATORIO_ID = "ID";
    private static final String COLUNA_RELATORIO_NOME_ALUNO = "Nome_aluno";
    private static final String COLUNA_RELATORIO_NOME_ATIVIDADE = "Nome_atividade";
    private static final String COLUNA_RELATORIO_TIPO_ATIVIDADE = "Tipo_atividade";
    private static final String COLUNA_RELATORIO_ATIVIDADE_JSON = "Atividade";
    private static final String SQL_CREATE_RELATORIO = "CREATE TABLE "
    		+ TABLE_RELATORIO + " ("
    		+ COLUNA_RELATORIO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
    		+ COLUNA_RELATORIO_NOME_ALUNO + " VARCHAR,"
    		+ COLUNA_RELATORIO_NOME_ATIVIDADE + " VARCHAR,"
    		+ COLUNA_RELATORIO_TIPO_ATIVIDADE + " VARCHAR,"
    		+ COLUNA_RELATORIO_ATIVIDADE_JSON + " VARCHAR );";
    private static final String SQL_DELETE_RELATORIO = "DROP TABLE IF EXISTS " + TABLE_RELATORIO;

    public final String COLOR_MATCH_EXERCISE_TYPECODE = "COLOR_MATCH_EXERCISE";
    public final String MULTIPLE_CHOICE_EXERCISE_TYPECODE = "MULTIPLE_CHOICE_EXERCISE";
    public final String MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE = "MULTIPLE_CORRECT_CHOICE_EXERCISE";
    public final String COMPLETE_EXERCISE_TYPECODE = "COMPLETE_EXERCISE";
    public final String IMAGE_MATCH_EXERCISE_TYPECODE = "IMAGE_MATCH_EXERCISE";
    public final String NUM_MATCH_EXERCISE_TYPECODE = "NUM_MATCH_EXERCISE";

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
        db.execSQL(SQL_CREATE_RELATORIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PROFESSOR_TABLE);
        db.execSQL(SQL_DELETE_PROFESSOR_LOGIN);
        db.execSQL(SQL_DELETE_RELATORIO);
        onCreate(db);
        
    }

    @Override
    public final void onDowngrade(final SQLiteDatabase dbHelper,
            final int oldVersion, final int newVersion) {
        onUpgrade(dbHelper, oldVersion, newVersion);
        
    }
    
    public final long addActivity(String owner, String name, String activityType, String activity) {

        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(COLUNA_PROFESSOR_NOME, name);
        values.put(COLUNA_PROFESSOR_OWNER, owner);
        values.put(COLUNA_PROFESSOR_TIPO_ATIVIDADE, activityType);
        values.put(COLUNA_PROFESSOR_ATIVIDADE_JSON, activity);

        System.out.println("addActivity: " + name + " " + activityType + " " + activity);
        
        long id = db.insert(TABLE_ATIVIDADES_PROFESSOR, null, values);

        db.close();
        
        return id;
    }
    
    public List<String> getActivities(String owner){
    	List<String> activities = new ArrayList<String>();
    	
    	String sql = "select * from " + TABLE_ATIVIDADES_PROFESSOR + " where " + COLUNA_PROFESSOR_OWNER + " = '" + owner + "'";;
    	
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
    
//    public List<String> getActivities(String owner, String type){
//    	List<String> activities = new ArrayList<String>();
//    	
//    	String sql = "select * from " + TABLE_ATIVIDADES_PROFESSOR + " where " + COLUNA_PROFESSOR_TIPO_ATIVIDADE + " = '" + type + "'";
//    	
//    	final SQLiteDatabase db = getWritableDatabase();
//    	final Cursor c = db.rawQuery(sql, null);
//    	
//    	if (c.getCount() > 0 && c.moveToFirst()){
//    		for (int i = 0; i < c.getCount(); i++) {
//    			activities.add(c.getString(2));
//    			c.moveToNext();
//			}
//    	}
//    	
//    	c.close();
//    	db.close();
//    	
//    	return activities;
//    }

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
    	
    	String sql = "select " + COLUNA_LOGIN_JSON_DATA + " from " + TABLE_LOGIN;
    	
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
    
    public HashMap<String, ArrayList<String>> getReport(String json){
    	HashMap<String, ArrayList<String>> report = new HashMap<String, ArrayList<String>>();
    	
    	ArrayList<String> key = new ArrayList<String>();
    	
    	JSONObject data;
    	String atividadeName;
    	try {
    		data = new JSONObject(json);
    		atividadeName = data.getString("name");
		} catch (Exception e) {
			return null;
		}

    	String sql = "select * from " + TABLE_RELATORIO + " where " + COLUNA_RELATORIO_NOME_ATIVIDADE + " = '" + atividadeName + "'";
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	final Cursor c = db.rawQuery(sql, null);
    	
    	if (c.getCount() > 0 && c.moveToFirst()){
    		for (int i = 0; i < c.getCount(); i++) {
    			String name = c.getString(1);
    			if (!key.contains(name)){
    				key.add(name);
    			}
    			
    			c.moveToNext();
			}
    	}

    	c.close();

    	for (int i = 0; i < key.size(); i++) {
    		
    		String sqlName = "select * from " + TABLE_RELATORIO + " where " + COLUNA_RELATORIO_NOME_ATIVIDADE + " = '" + atividadeName
    				+ "' and " + COLUNA_RELATORIO_NOME_ALUNO + " = '" + key.get(i) + "'";
    		
    		final Cursor c1 = db.rawQuery(sqlName, null);
    		
    		ArrayList<String> values = new ArrayList<String>();
    		
    		if (c1.getCount() > 0 && c1.moveToFirst()){
    			for (int j = 0; j < c1.getCount(); j++) {
    				values.add(c1.getString(4));
    				c1.moveToNext();
    			}
    		}
    		
    		c1.close();
			
    		report.put(key.get(i), values);
		}
    	db.close();
    	
    	return report;
    }
    
    public final long addReport(String nameAluno, String json){
    	 final SQLiteDatabase db = getWritableDatabase();
         final ContentValues values = new ContentValues();
         
         JSONObject data;
         try {
        	 data = new JSONObject(json);
        	 values.put(COLUNA_RELATORIO_NOME_ALUNO, nameAluno);
        	 values.put(COLUNA_RELATORIO_NOME_ATIVIDADE, data.getString("name"));
        	 values.put(COLUNA_RELATORIO_TIPO_ATIVIDADE, data.getString("type"));
        	 values.put(COLUNA_RELATORIO_ATIVIDADE_JSON, json);
 		} catch (JSONException e) {
             String LOG = "DATABASE report";
             Log.e(LOG, e.getMessage());
         }

         
         long id = db.insert(TABLE_RELATORIO, null, values);

         db.close();
         
         return id;
    }
}
