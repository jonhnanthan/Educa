package com.educa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.educa.entity.ColorMatchExercise;
import com.educa.entity.CompleteExercise;
import com.educa.entity.ImageMatchExercise;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.entity.NumMatchExercise;

import java.util.ArrayList;

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
    public final String MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE = "MULTIPLE_CORRECT_CHOICE_EXERCISE";
    public final String COMPLETE_EXERCISE_TYPECODE = "COMPLETE_EXERCISE";
    public final String NUM_MATCH_EXERCISE_TYPECODE = "NUM_MATCH_EXERCISE";
    public final String IMAGE_MATCH_EXERCISE_TYPECODE = "IMAGE_MATCH_EXERCISE";
    
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

    		NumMatchExercise num = new NumMatchExercise("num", NUM_MATCH_EXERCISE_TYPECODE, "25-02-2015", "NEW", "NOT RATED", "num9", "1", "2", "3", "4", "3", "2130837549");
    		addActivity("num", NUM_MATCH_EXERCISE_TYPECODE, num.getJsonTextObject());

    		ImageMatchExercise image = new ImageMatchExercise("imagens", IMAGE_MATCH_EXERCISE_TYPECODE, "25-02-2015", "NEW", "NOT RATED", "quem", "elefante", "2130837535");
    		addActivity("imagens", IMAGE_MATCH_EXERCISE_TYPECODE, image.getJsonTextObject());
    	}

    	c.close();
    	db.close();
//    	num NUM_MATCH_EXERCISE {"alternative2":"2","alternative1":"1","alternative4":"4","alternative3":"3","color":"2130837549","status":"NEW","name":"num","answer":"3","correction":"NOT_RATED","question":"quantos","date":"17-03-2015","type":"NUM_MATCH_EXERCISE"}
//    	addActivity: imagens IMAGE_MATCH_EXERCISE {"color":"2130837535","status":"NEW","name":"imagens","answer":"elefante","correction":"NOT_RATED","question":"quem","date":"17-03-2015","type":"IMAGE_MATCH_EXERCISE"}
//		addActivity: Exercicio de cores COLOR_MATCH_EXERCISE {"alternative2":"Cinza","alternative1":"Preta","alternative4":"Amarela","alternative3":"Marrom","color":"-11199487","status":"NEW","name":"Exercicio de cores","answer":"Marrom","correction":"NOT_RATED","question":"Que cor eh essa?","date":"25-02-2015","type":"COLOR_MATCH_EXERCISE"}
//		addActivity: Exercicio de completar COMPLETE_EXERCISE {"hiddenIndexes":"2","status":"NEW","name":"Exercicio de completar","correction":"NOT_RATED","word":"casa","question":"Lugar onde voce mora","date":"25-02-2015","type":"COMPLETE_EXERCISE"}
//		addActivity: Exercicio dos meses MULTIPLE_CHOICE_EXERCISE {"alternative2":"Novembro","alternative1":"Janeiro","alternative4":"Outubro","alternative3":"Dezembro","status":"NEW","name":"Exercicio dos meses","answer":"Dezembro","correction":"NOT_RATED","question":"Qual o ultimo mes do ano?","date":"25-02-2015","type":"MULTIPLE_CHOICE_EXERCISE"}
	}
	
    public final void addActivity(String name, String activityType, String activity) {
    	if (verifyData(name)){
            final SQLiteDatabase db = getWritableDatabase();
            final ContentValues values = new ContentValues();

            values.put(COLUNA_ALUNO_NOME, name);
            values.put(COLUNA_ALUNO_TIPO_ATIVIDADE, activityType);
            values.put(COLUNA_ALUNO_ATIVIDADE_JSON, activity);

            System.out.println("addActivity: " + name + " " + activityType + " " + activity);
            
            long id = db.insert(TABLE_ATIVIDADES_ALUNO, null, values);
            System.out.println(id);

            db.close();
    	}
    }

    private final boolean verifyData(String name){
    	String sql = "select " + COLUNA_ALUNO_NOME + " from " + TABLE_ATIVIDADES_ALUNO;
    	
    	final SQLiteDatabase db = getWritableDatabase();
    	final Cursor c = db.rawQuery(sql, null);
    	
    	if (c.getCount() > 0 && c.moveToFirst()){
    		for (int i = 0; i < c.getCount(); i++) {
    			if (c.getString(0).equalsIgnoreCase(name)) return false;
    			c.moveToNext();
			}
    	}
    	
    	c.close();
    	db.close();
    	
    	return true;
    }
    
    public final void removeActivity(String name){
		String sqlDelete = "delete from " + TABLE_ATIVIDADES_ALUNO + " where "
				+ COLUNA_ALUNO_NOME + " = \'" + name + "\';";

		final SQLiteDatabase db = getWritableDatabase();

		db.execSQL(sqlDelete);

		db.close();
    }

}
