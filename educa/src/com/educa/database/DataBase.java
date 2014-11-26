
package com.educa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.educa.entity.Exercise;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static DataBase instance;

    private static final String COLUNA_ALUNO_ID = "ID";
    private static final String COLUNA_ALUNO_TURMA = "Turma";
    private static final String COLUNA_ALUNO_NOME = "Nome";
    private static final String TABLE_ALUNO = "Aluno";

    private static final String SQL_CREATE_ALUNO = "CREATE TABLE "
            + TABLE_ALUNO + "("
            + COLUNA_ALUNO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_ALUNO_TURMA + " INTEGER,"
            + COLUNA_ALUNO_NOME + " VARCHAR );";

    private static final String COLUNA_EXERCICIO_ID = "ID";
    private static final String COLUNA_EXERCICIO_NAME = "name";
    private static final String COLUNA_EXERCICIO_QUESTION = "question";
    private static final String COLUNA_EXERCICIO_TYPE = "type", COLUNA_EXERCICIO_DATE = "date";
    private static final String COLUNA_EXERCICIO_STATUS = "status";
    private static final String COLUNA_EXERCICIO_CORRECTION = "correction";
    private static final String TABLE_EXERCICIO = "Exercicio";

    private static final String SQL_CREATE_EXERCICIO = "CREATE TABLE "
            + TABLE_EXERCICIO + "("
            + COLUNA_EXERCICIO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_EXERCICIO_NAME + " VARCHAR,"
            + COLUNA_EXERCICIO_QUESTION + " VARCHAR,"
            + COLUNA_EXERCICIO_TYPE + " VARCHAR,"
            + COLUNA_EXERCICIO_DATE + " VARCHAR,"
            + COLUNA_EXERCICIO_STATUS + " VARCHAR,"
            + COLUNA_EXERCICIO_CORRECTION + " VARCHAR );";

    private static final String COLUNA_PROFESSOR_ID = "ID";
    private static final String COLUNA_PROFESSOR_TURMAS = "Turmas";
    private static final String COLUNA_PROFESSOR_NOME = "Nome";
    private static final String TABLE_PROFESSOR = "Professor";

    private static final String SQL_CREATE_PROFESSOR = "CREATE TABLE "
            + TABLE_PROFESSOR + "("
            + COLUNA_PROFESSOR_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_PROFESSOR_TURMAS + " VARCHAR,"
            + COLUNA_PROFESSOR_NOME + " VARCHAR );";

    private static final String SQL_DELETE_ALUNO_TABLE = "DROP TABLE IF EXISTS Aluno";
    private static final String SQL_DELETE_EXERCICIO_TABLE = "DROP TABLE IF EXISTS Exercicio";
    private static final String SQL_DELETE_PROFESSOR_TABLE = "DROP TABLE IF EXISTS PROFESSOR";

    private DataBase(Context context) {
        super(context, "educa.db", null, 1);
    }

    public static DataBase getInstance(final Context context) {
        if (instance == null) {
            instance = new DataBase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ALUNO);
        db.execSQL(SQL_CREATE_EXERCICIO);
        db.execSQL(SQL_CREATE_PROFESSOR);
        
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALUNO_TABLE);
        db.execSQL(SQL_DELETE_EXERCICIO_TABLE);
        db.execSQL(SQL_DELETE_PROFESSOR_TABLE);
        onCreate(db);
        
        db.close();
        
    }

    @Override
    public final void onDowngrade(final SQLiteDatabase dbHelper,
            final int oldVersion, final int newVersion) {
        onUpgrade(dbHelper, oldVersion, newVersion);
        
        dbHelper.close();
    }

    public long addProfessor(String nome, String turmas) {

        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(COLUNA_PROFESSOR_NOME, nome);
        values.put(COLUNA_PROFESSOR_TURMAS, turmas);

        long id = db.insert(TABLE_PROFESSOR, null, values);

        db.close();

        return id;
    }

    public ArrayList<String> getProfessor(long id) {
        ArrayList<String> array = new ArrayList<String>();

        final SQLiteDatabase db = getWritableDatabase();
        // refazer com rawQuery
        final Cursor cursor = db.query(TABLE_PROFESSOR, null, COLUNA_PROFESSOR_ID + " = '" + id
                + "'", new String[] {}, null, null, null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {

            array.add(String.valueOf(cursor.getInt(0)));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
        }

        cursor.close();
        db.close();

        return array;
    }

    public ArrayList<String> getProfessor(String nome, String turmas) {
        ArrayList<String> array = new ArrayList<String>();

        final SQLiteDatabase db = getWritableDatabase();
        // refazer com rawQuery
        final Cursor cursor = db.query(TABLE_PROFESSOR, null, COLUNA_PROFESSOR_NOME + " = '" + nome
                + "' and " + COLUNA_PROFESSOR_TURMAS + " = '" + turmas + "'", new String[] {},
                null, null, null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {

            array.add(String.valueOf(cursor.getInt(0)));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
        }

        cursor.close();
        db.close();

        return array;
    }

    public final long addExercise(Exercise exercise) {

        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(COLUNA_EXERCICIO_NAME, exercise.getName());
        values.put(COLUNA_EXERCICIO_QUESTION, exercise.getQuestion());
        values.put(COLUNA_EXERCICIO_TYPE, exercise.getType());
        values.put(COLUNA_EXERCICIO_DATE, exercise.getDate());
        values.put(COLUNA_EXERCICIO_STATUS, String.valueOf(exercise.getStatus()));
        values.put(COLUNA_EXERCICIO_CORRECTION, String.valueOf(exercise.getCorrection()));

        long id = db.insert(TABLE_PROFESSOR, null, values);

        db.close();
        
        return id;
    }

    public final long removeExercise(Exercise exercise) {

        final SQLiteDatabase db = getWritableDatabase();

        String whereClause = COLUNA_EXERCICIO_NAME + " = '" + exercise.getName() + "' and "
                + COLUNA_EXERCICIO_QUESTION + " = '" + exercise.getQuestion() + "' and "
                + COLUNA_EXERCICIO_TYPE + " = '" + exercise.getType() + "' and "
                + COLUNA_EXERCICIO_DATE + " = '" + exercise.getDate() + "' and "
                + COLUNA_EXERCICIO_STATUS + " = '" + String.valueOf(exercise.getStatus())
                + "' and "
                + COLUNA_EXERCICIO_CORRECTION + " = '" + String.valueOf(exercise.getCorrection())
                + "'";

        long id = db.delete(TABLE_EXERCICIO, whereClause, null);

        db.close();
                
        return id;
    }

    public final long removeExercise(long id) {
        final SQLiteDatabase db = getWritableDatabase();

        String whereClause = COLUNA_EXERCICIO_ID + " = '" + id + "'";

        long id_ = db.delete(TABLE_EXERCICIO, whereClause, null);

        db.close();

        return id_;
    }

    public ArrayList<Exercise> getListExercise() {
        ArrayList<Exercise> array = new ArrayList<Exercise>();

        final SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.query(TABLE_EXERCICIO, null, null, new String[] {}, null, null,
                null);

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(1), question = cursor.getString(2), type = cursor
                        .getString(3), date = cursor.getString(4);

                /*
                 * Status status = null; for (Status s : Status.values()) { if
                 * (s.getStatus().equalsIgnoreCase(cursor.getString(5))) {
                 * status = s; break; } }
                 */

                /*
                 * Correction correction = null; for (Correction c :
                 * Correction.values()) { if
                 * (c.getCorrection().equalsIgnoreCase(cursor.getString(6))) {
                 * correction = c; break; } } Exercise e = new Exercise(name,
                 * question, type, date, status, correction); array.add(e);
                 */
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return array;
    }

    public long updateExercise(Exercise exercise) {

        final SQLiteDatabase db = getWritableDatabase();

        final ContentValues values = new ContentValues();

        values.put(COLUNA_EXERCICIO_NAME, exercise.getName());
        values.put(COLUNA_EXERCICIO_QUESTION, exercise.getQuestion());
        values.put(COLUNA_EXERCICIO_TYPE, exercise.getType());
        values.put(COLUNA_EXERCICIO_DATE, exercise.getDate());
        values.put(COLUNA_EXERCICIO_STATUS, String.valueOf(exercise.getStatus()));
        values.put(COLUNA_EXERCICIO_CORRECTION, String.valueOf(exercise.getCorrection()));

        String whereClause = COLUNA_EXERCICIO_ID + " = '" + getExerciseId(exercise) + "'";

        long id = db.update(TABLE_EXERCICIO, values, whereClause, null);

        db.close();

        return id;
    }

    public long getExerciseId(Exercise exercise) {

        final SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT " + COLUNA_EXERCICIO_ID + " FROM " + TABLE_EXERCICIO + " WHERE "
                + COLUNA_EXERCICIO_NAME + " = '" + exercise.getName() + "'";

        final Cursor cursor = db.rawQuery(sql, null);

        long id = cursor.getCount() > 0 && cursor.moveToFirst() ? cursor.getInt(0) : 0;

        cursor.close();
        db.close();

        return id;
    }
}
