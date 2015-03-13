package com.educa.database;

import java.util.ArrayList;

import com.educa.entity.ColorMatchExercise;
import com.educa.entity.CompleteExercise;
import com.educa.entity.MultipleChoiceExercise;

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
    
	private void populateDataBase(){
		
    	String sql = "select * from " + TABLE_ATIVIDADES_ALUNO;
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	final Cursor c = db.rawQuery(sql, null);
    	
    	if (c.getCount() == 0){
    		ColorMatchExercise cm = new ColorMatchExercise("Exercicio de cores", COLOR_MATCH_EXERCISE_TYPECODE, "25-02-2015", "NEW", "NOT RATED", "Que cor eh essa?", "Preta", "Cinza", "Marrom", "Amarela", "Marrom", "-11199487");
    		addActivity("Exercicio de cores", COLOR_MATCH_EXERCISE_TYPECODE, cm.getJsonTextObject());
    		
    		CompleteExercise ce = new CompleteExercise("Exercicio de completar", COMPLETE_EXERCISE_TYPECODE, "25-02-2015", "NEW", "NOT_RATED", "Lugar onde voce mora", "casa", "2");
    		addActivity("Exercicio de completar", COMPLETE_EXERCISE_TYPECODE, ce.getJsonTextObject());
    		
    		MultipleChoiceExercise me = new MultipleChoiceExercise("Exercicio dos meses", MULTIPLE_CHOICE_EXERCISE_TYPECODE, "25-02-2015", "NEW", "NOT RATED", "Qual o ultimo mes do ano?", "Janeiro", "Novembro", "Dezembro", "Outubro", "Dezembro");
    		addActivity("Exercicio dos meses", MULTIPLE_CHOICE_EXERCISE_TYPECODE, me.getJsonTextObject());
    	}

    	c.close();
    	db.close();
//		addActivity: Exercicio de cores COLOR_MATCH_EXERCISE {"alternative2":"Cinza","alternative1":"Preta","alternative4":"Amarela","alternative3":"Marrom","color":"-11199487","status":"NEW","name":"Exercicio de cores","answer":"Marrom","correction":"NOT_RATED","question":"Que cor eh essa?","date":"25-02-2015","type":"COLOR_MATCH_EXERCISE"}
//		addActivity: Exercicio de completar COMPLETE_EXERCISE {"hiddenIndexes":"2","status":"NEW","name":"Exercicio de completar","correction":"NOT_RATED","word":"casa","question":"Lugar onde voce mora","date":"25-02-2015","type":"COMPLETE_EXERCISE"}
//		addActivity: Exercicio dos meses MULTIPLE_CHOICE_EXERCISE {"alternative2":"Novembro","alternative1":"Janeiro","alternative4":"Outubro","alternative3":"Dezembro","status":"NEW","name":"Exercicio dos meses","answer":"Dezembro","correction":"NOT_RATED","question":"Qual o ultimo mes do ano?","date":"25-02-2015","type":"MULTIPLE_CHOICE_EXERCISE"}
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
