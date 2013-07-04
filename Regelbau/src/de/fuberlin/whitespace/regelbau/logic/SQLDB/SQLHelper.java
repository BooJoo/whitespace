package de.fuberlin.whitespace.regelbau.logic.SQLDB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "RuleDB.db";
	private static final int DATABASE_VERSION = 1;
	

	private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS Rules(id INTEGER PRIMARY KEY, rule BLOB)";

	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS Rules");
		onCreate(db);


	}


}
