
package com.educa.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.educa.entity.ColorMatchExercise;
import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.entity.MultipleChoiceExercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBaseStorage implements StorageSystem {

    /**
     * Table of Exercise Object
     */
    private static final String EXERCISE_TABLE_NAME = "EXERCISE";
    private static final String EXERCISE_ID = "_id";
    private static final String EXERCISE_NAME = "NAME";
    private static final String EXERCISE_QUESTION = "QUESTION";
    private static final String EXERCISE_TYPE = "TYPE";
    private static final String EXERCISE_DATE = "DATE";
    private static final String EXERCISE_STATUS = "STATUS";
    private static final String EXERCISE_CORRECTION = "CORRECTION";
    private static final String EXERCISE_ALTERNATIVE_1 = "ALTERNATIVE_1";
    private static final String EXERCISE_ALTERNATIVE_2 = "ALTERNATIVE_2";
    private static final String EXERCISE_ALTERNATIVE_3 = "ALTERNATIVE_3";
    private static final String EXERCISE_ALTERNATIVE_4 = "ALTERNATIVE_4";
    private static final String EXERCISE_RIGHT_ANSWER = "RIGHT_ANSWER";
    private static final String EXERCISE_COLOR = "COLOR";
    private static final String EXERCISE_WORD = "WORD";
    private static final String EXERCISE_HIDDEN_INDEXES = "HIDDEN_INDEXES";

    private static final String COLOR_MATCH_EXERCISE_TYPECODE = "COLOR_MATCH_EXERCISE";
    private static final String MULTIPLE_CHOICE_EXERCISE_TYPECODE = "MULTIPLE_CHOICE_EXERCISE";
    private static final String COMPLETE_EXERCISE_TYPECODE = "COMPLETE_EXERCISE";

    private static final String EXERCISE_CREATE_TABLE = "CREATE TABLE "
            + EXERCISE_TABLE_NAME + "("
            + EXERCISE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + EXERCISE_NAME + " VARCHAR,"
            + EXERCISE_QUESTION + " VARCHAR,"
            + EXERCISE_TYPE + " VARCHAR,"
            + EXERCISE_DATE + " VARCHAR,"
            + EXERCISE_STATUS + " VARCHAR,"
            + EXERCISE_CORRECTION + " VARCHAR,"
            + EXERCISE_ALTERNATIVE_1 + " VARCHAR,"
            + EXERCISE_ALTERNATIVE_2 + " VARCHAR,"
            + EXERCISE_ALTERNATIVE_3 + " VARCHAR,"
            + EXERCISE_ALTERNATIVE_4 + " VARCHAR,"
            + EXERCISE_RIGHT_ANSWER + " VARCHAR,"
            + EXERCISE_COLOR + " VARCHAR,"
            + EXERCISE_WORD + " VARCHAR,"
            + EXERCISE_HIDDEN_INDEXES + " VARCHAR );";

    /**
     * Create Database
     */
    @SuppressWarnings("unused")
    private static final String TAG = "DataBaseStorage";
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase dataBase;
    private static final String DATABASE_NAME = "EDUCA_DB";
    private static final int DATABASE_VERSION = 1;

    private final Context mContext;

    public static class DatabaseHelper extends SQLiteOpenHelper {
        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(EXERCISE_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
            onCreate(db);
        }
    }

    public DataBaseStorage(Context context) {
        mContext = context;
        open();
    }

    public void create() {
        dataBase.execSQL(EXERCISE_CREATE_TABLE);
    }

    public void recreate() {
        dataBase.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
    }

    public DataBaseStorage open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(mContext);
        dataBase = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
        dataBase.close();
    }

    public DatabaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public SQLiteDatabase getDataBase() {
        return dataBase;
    }

    @Override
    public void addExercise(Exercise exercise) {
        ContentValues values = new ContentValues();
        values = exerciseContentValues(exercise);
        dataBase.insert(EXERCISE_TABLE_NAME, null, values);
    }

    @Override
    public void deleteExercise(Integer id) {
        dataBase.delete(EXERCISE_TABLE_NAME, EXERCISE_ID + "=" + id, null);
    }
    
    @Override
    public void deleteExercise(Exercise exercise) {
        String whereClause = EXERCISE_NAME + " = '" + exercise.getName()  + "'";
        dataBase.delete(EXERCISE_TABLE_NAME, whereClause, null);
    }

    @Override
    public void editExercise(Exercise exercise) {
        ContentValues values = exerciseContentValues(exercise);
        //dataBase.update(EXERCISE_TABLE_NAME, values, EXERCISE_ID + "=" + exercise.getId(), null);
        dataBase.update(EXERCISE_TABLE_NAME, values, EXERCISE_NAME + " = '" + exercise.getName()  + "'", null);
    }

    @Override
    public Exercise getExercise(int key) {
        Cursor cursor = null;
        cursor = dataBase.query(true, EXERCISE_TABLE_NAME, new String[] {
                EXERCISE_ID, EXERCISE_NAME, EXERCISE_QUESTION, EXERCISE_TYPE,
                EXERCISE_DATE, EXERCISE_STATUS, EXERCISE_CORRECTION
        }, EXERCISE_ID + "=?",
                new String[] {
                    String.valueOf(key)
                }, null, null, null, null);
        ;

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Integer id = cursor.getInt(cursor.getColumnIndex(EXERCISE_ID));
        String name = cursor.getString(cursor.getColumnIndex(EXERCISE_NAME));
        String question = cursor.getString(cursor.getColumnIndex(EXERCISE_QUESTION));
        String type = cursor.getString(cursor.getColumnIndex(EXERCISE_TYPE));
        String date = cursor.getString(cursor.getColumnIndex(EXERCISE_DATE));
        String status = cursor.getString(cursor.getColumnIndex(EXERCISE_STATUS));
        String correction = cursor.getString(cursor.getColumnIndex(EXERCISE_CORRECTION));

        Exercise exercise = new Exercise(id, name, question, type, date, status, correction);
        exercise.setId(id);
        cursor.close();
        return exercise;
    }

    @Override
    public List<Exercise> getExercises() {

        List<Exercise> exercises = new ArrayList<Exercise>();
        Exercise exercise;

        int id;
        String name;
        String question;
        String type;
        String date;
        String status;
        String correction;

        try {
            Cursor mCursor = dataBase.query(true, EXERCISE_TABLE_NAME, new String[] {
                    EXERCISE_ID, EXERCISE_NAME, EXERCISE_QUESTION, EXERCISE_TYPE,
                    EXERCISE_DATE, EXERCISE_STATUS, EXERCISE_CORRECTION, EXERCISE_ALTERNATIVE_1,
                    EXERCISE_ALTERNATIVE_2, EXERCISE_ALTERNATIVE_3, EXERCISE_ALTERNATIVE_4,
                    EXERCISE_RIGHT_ANSWER,
                    EXERCISE_COLOR, EXERCISE_WORD, EXERCISE_HIDDEN_INDEXES
            }, null, null, null, null, null,
                    null);
            ;

            if (mCursor != null) {
                mCursor.moveToFirst();
                while (mCursor.isAfterLast() == false) {
                    id = mCursor.getInt(mCursor.getColumnIndex(EXERCISE_ID));
                    name = mCursor.getString(mCursor.getColumnIndex(EXERCISE_NAME));
                    question = mCursor.getString(mCursor.getColumnIndex(EXERCISE_QUESTION));
                    type = mCursor.getString(mCursor.getColumnIndex(EXERCISE_TYPE));
                    date = mCursor.getString(mCursor.getColumnIndex(EXERCISE_DATE));
                    status = mCursor.getString(mCursor.getColumnIndex(EXERCISE_STATUS));
                    correction = mCursor.getString(mCursor.getColumnIndex(EXERCISE_CORRECTION));

                    if (type.equals(getColorMatchExerciseTypecode())) {
                        String alternative1 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_1));
                        String alternative2 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_2));
                        String alternative3 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_3));
                        String alternative4 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_4));
                        String rightAnswer = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_RIGHT_ANSWER));
                        String color = mCursor.getString(mCursor.getColumnIndex(EXERCISE_COLOR));

                        exercise = new ColorMatchExercise(name, type, date, status, correction,
                                question, alternative1, alternative2, alternative3, alternative4,
                                rightAnswer, color);
                        exercises.add(exercise);
                    }
                    if (type.equals(getMultipleChoiceExerciseTypecode())) {
                        String alternative1 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_1));
                        String alternative2 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_2));
                        String alternative3 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_3));
                        String alternative4 = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_ALTERNATIVE_4));
                        String rightAnswer = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_RIGHT_ANSWER));

                        exercise = new MultipleChoiceExercise(name, type, date, status, correction,
                                question, alternative1, alternative2, alternative3, alternative4,
                                rightAnswer);
                        exercises.add(exercise);
                    }
                    if (type.equals(getCompleteExerciseTypecode())) {
                        String word = mCursor.getString(mCursor.getColumnIndex(EXERCISE_WORD));
                        String hiddenIndexes = mCursor.getString(mCursor
                                .getColumnIndex(EXERCISE_HIDDEN_INDEXES));

                        exercise = new CompleteExercise(name, type, date, status, correction,
                                question, word, hiddenIndexes);
                        exercises.add(exercise);
                    }
                    mCursor.moveToNext();
                }
            }
            if (mCursor != null) {
                mCursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(exercises);
        return exercises;
    }

    private ContentValues exerciseContentValues(Exercise exercise) {
        ContentValues values = new ContentValues();

        values.put(EXERCISE_NAME, exercise.getName());
        values.put(EXERCISE_QUESTION, exercise.getQuestion());
        values.put(EXERCISE_TYPE, exercise.getType());
        values.put(EXERCISE_DATE, exercise.getDate());
        values.put(EXERCISE_STATUS, exercise.getStatus());
        values.put(EXERCISE_CORRECTION, exercise.getCorrection());

        if (exercise instanceof MultipleChoiceExercise) {
            values.put(EXERCISE_ALTERNATIVE_1,
                    ((MultipleChoiceExercise) exercise).getAlternative1());
            values.put(EXERCISE_ALTERNATIVE_2,
                    ((MultipleChoiceExercise) exercise).getAlternative2());
            values.put(EXERCISE_ALTERNATIVE_3,
                    ((MultipleChoiceExercise) exercise).getAlternative3());
            values.put(EXERCISE_ALTERNATIVE_4,
                    ((MultipleChoiceExercise) exercise).getAlternative4());
            values.put(EXERCISE_RIGHT_ANSWER, ((MultipleChoiceExercise) exercise).getRightAnswer());
        }
        if (exercise instanceof ColorMatchExercise) {
            values.put(EXERCISE_ALTERNATIVE_1, ((ColorMatchExercise) exercise).getAlternative1());
            values.put(EXERCISE_ALTERNATIVE_2, ((ColorMatchExercise) exercise).getAlternative2());
            values.put(EXERCISE_ALTERNATIVE_3, ((ColorMatchExercise) exercise).getAlternative3());
            values.put(EXERCISE_ALTERNATIVE_4, ((ColorMatchExercise) exercise).getAlternative4());
            values.put(EXERCISE_RIGHT_ANSWER, ((ColorMatchExercise) exercise).getRightAnswer());
            values.put(EXERCISE_COLOR, ((ColorMatchExercise) exercise).getColor());
        }
        if (exercise instanceof CompleteExercise) {
            values.put(EXERCISE_WORD, ((CompleteExercise) exercise).getWord());
            values.put(EXERCISE_HIDDEN_INDEXES, ((CompleteExercise) exercise).getHiddenIndexes());

        }

        return values;
    }

    public static String getColorMatchExerciseTypecode() {
        return COLOR_MATCH_EXERCISE_TYPECODE;
    }

    public static String getMultipleChoiceExerciseTypecode() {
        return MULTIPLE_CHOICE_EXERCISE_TYPECODE;
    }

    public static String getCompleteExerciseTypecode() {
        return COMPLETE_EXERCISE_TYPECODE;
    }
}
