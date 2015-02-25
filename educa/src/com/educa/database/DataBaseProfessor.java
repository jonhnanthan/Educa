
package com.educa.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseProfessor extends SQLiteOpenHelper {

	private static DataBaseProfessor instance;

    private static final String COLUNA_PROFESSOR_ID = "ID";
    private static final String COLUNA_PROFESSOR_NOME = "Nome";
    private static final String COLUNA_PROFESSOR_TIPO_ATIVIDADE = "Tipo_atividade";
    private static final String COLUNA_PROFESSOR_ATIVIDADE_JSON = "Atividade";
    private static final String TABLE_ATIVIDADES_PROFESSOR = "AtividadesProfessor";

    private static final String SQL_CREATE_PROFESSOR = "CREATE TABLE "
            + TABLE_ATIVIDADES_PROFESSOR + "("
            + COLUNA_PROFESSOR_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_PROFESSOR_TIPO_ATIVIDADE + " VARCHAR,"
            + COLUNA_PROFESSOR_ATIVIDADE_JSON + " VARCHAR,"
            + COLUNA_PROFESSOR_NOME + " VARCHAR );";

    private static final String SQL_DELETE_PROFESSOR_TABLE = "DROP TABLE IF EXISTS Professor";

    public final String COLOR_MATCH_EXERCISE_TYPECODE = "COLOR_MATCH_EXERCISE";
    public final String MULTIPLE_CHOICE_EXERCISE_TYPECODE = "MULTIPLE_CHOICE_EXERCISE";
    public final String COMPLETE_EXERCISE_TYPECODE = "COMPLETE_EXERCISE";
    public final String IMAGE_MATCH_EXERCISE_TYPECODE = "IMAGE_MATCH_EXERCISE";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PROFESSOR_TABLE);
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
    
    public ArrayList<String> getActivities(){
    	ArrayList<String> activities = new ArrayList<String>();
    	
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
    
    public ArrayList<String> getActivities(String type){
    	ArrayList<String> activities = new ArrayList<String>();
    	
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

	public ArrayList<String> getActivitiesName() {
    	ArrayList<String> activities = new ArrayList<String>();
    	
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
	
}
