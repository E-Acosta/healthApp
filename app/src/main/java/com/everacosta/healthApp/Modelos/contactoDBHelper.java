package com.everacosta.healthApp.Modelos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class contactoDBHelper extends SQLiteOpenHelper {

    public contactoDBHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(contactoContract.CREAR_TABLA_CONTACTO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionAntigua, int versionNueva) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS registros");
    onCreate(sqLiteDatabase);
    }
}
