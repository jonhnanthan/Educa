package com.educa.database;

import java.util.ArrayList;

import com.educa.entity.CompleteExercise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAluno extends SQLiteOpenHelper{
	
	private static DataBaseAluno instance;

    private static final String COLUNA_ALUNO_ID = "ID";
    private static final String COLUNA_ALUNO_NOME = "Nome";
    private static final String COLUNA_ALUNO_TIPO_ATIVIDADE = "Tipo_atividade";
    private static final String COLUNA_ALUNO_ATIVIDADE_JSON = "Atividade";
    
    private static final String TABLE_ATIVIDADES_ALUNO = "AtividadesAluno";
    
    private static final String SQL_CREATE_ALUNO = "CREATE TABLE "
            + TABLE_ATIVIDADES_ALUNO + "("
            + COLUNA_ALUNO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_ALUNO_TIPO_ATIVIDADE + " VARCHAR,"
            + COLUNA_ALUNO_ATIVIDADE_JSON + " VARCHAR,"
            + COLUNA_ALUNO_NOME + " VARCHAR );";
    
    private static final String SQL_DELETE_ALUNO_TABLE = "DROP TABLE IF EXISTS Aluno";
    
    public final String COLOR_MATCH_EXERCISE_TYPECODE = "COLOR_MATCH_EXERCISE";
    public final String MULTIPLE_CHOICE_EXERCISE_TYPECODE = "MULTIPLE_CHOICE_EXERCISE";
    public final String COMPLETE_EXERCISE_TYPECODE = "COMPLETE_EXERCISE";
    
    private DataBaseAluno(Context context) {
        super(context, "educaNew.db", null, 1);
    }

    public static DataBaseAluno getInstance(final Context context) {
        if (instance == null) {
            instance = new DataBaseAluno(context);
        }
        return instance;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ALUNO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALUNO_TABLE);
        onCreate(db);
        
    }

    @Override
    public final void onDowngrade(final SQLiteDatabase dbHelper,
            final int oldVersion, final int newVersion) {
        onUpgrade(dbHelper, oldVersion, newVersion);
        
    }
    
    public ArrayList<String> getActivities(){
    	populateDataBase();
    	
    	ArrayList<String> activities = new ArrayList<String>();
    	
    	String sql = "select * from " + TABLE_ATIVIDADES_ALUNO;
    	
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
    	String sql = "select * from " + TABLE_ATIVIDADES_ALUNO + " where " + COLUNA_ALUNO_TIPO_ATIVIDADE + " = '" + type + "'";
    	
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
    
    
	public ArrayList<String> getActivitiesName() {
    	ArrayList<String> activities = new ArrayList<String>();
    	
    	String sql = "select " + COLUNA_ALUNO_NOME + " from " + TABLE_ATIVIDADES_ALUNO;
    	
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

	private void populateDataBase(){
//		{"hiddenIndexes":"1","status":"NEW","name":"jxffk","correction":"NOT_RATED","word":"jfkfkf","question":"kfkfkd","date":"11-02-2015","type":"COMPLETE_EXERCISE"}
		
		CompleteExercise c = new CompleteExercise("Teste", COMPLETE_EXERCISE_TYPECODE, "11-02-2015", "NEW", "NOT_RATED", "Pergunta Teste", "Word", "1");
		CompleteExercise c1 = new CompleteExercise("Teste1", COMPLETE_EXERCISE_TYPECODE, "11-02-2015", "NEW", "NOT_RATED", "Pergunta Teste1", "Worda", "2");
		
		addActivity("Teste", COMPLETE_EXERCISE_TYPECODE, c.getJsonTextObject());
		addActivity("Teste1", COMPLETE_EXERCISE_TYPECODE, c1.getJsonTextObject());
		
	}
	
    private final long addActivity(String name, String activityType, String activity) {

        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(COLUNA_ALUNO_NOME, name);
        values.put(COLUNA_ALUNO_TIPO_ATIVIDADE, activityType);
        values.put(COLUNA_ALUNO_ATIVIDADE_JSON, activity);

        System.out.println("addActivity: " + name + " " + activityType + " " + activity);
        
        long id = db.insert(TABLE_ATIVIDADES_ALUNO, null, values);

        db.close();
        
        return id;
    }


}
