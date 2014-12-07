
package com.educa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseProfessor extends SQLiteOpenHelper {

	private static DataBaseProfessor instance;

    private static final String COLUNA_PROFESSOR_ID = "ID";
    private static final String COLUNA_PROFESSOR_NOME = "Nome";
    private static final String COLUNA_PROFESSOR_TIPO_ATIVIDADE = "Tipo_atividade";
    private static final String COLUNA_PROFESSOR_EXERCICIO = "Atividade";
    private static final String TABLE_ATIVIDADES_PROFESSOR = "AtividadesProfessor";

    private static final String SQL_CREATE_PROFESSOR = "CREATE TABLE "
            + TABLE_ATIVIDADES_PROFESSOR + "("
            + COLUNA_PROFESSOR_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_PROFESSOR_TIPO_ATIVIDADE + " VARCHAR,"
            + COLUNA_PROFESSOR_EXERCICIO + " VARCHAR,"
            + COLUNA_PROFESSOR_NOME + " VARCHAR );";

    private static final String SQL_DELETE_PROFESSOR_TABLE = "DROP TABLE IF EXISTS Professor";

    private DataBaseProfessor(Context context) {
        super(context, "educa.db", null, 1);
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
        
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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

}
