package de.fuberlin.whitespace.regelbau.logic.SQLDB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper klasse zum erzeugen der DB
 */
public class SQLHelper extends SQLiteOpenHelper{


	private static final String DATABASE_NAME = "RuleDB.db";
	private static final int DATABASE_VERSION = 1;
	

	private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS Rules(id INTEGER PRIMARY KEY, rule BLOB)";


	/**
	 * erzuegt eine DB mit den Namen der unter DATABASE_NAME angegeben ist
	 */
	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	/**
	 * erzeigt eine Tabelle in deer DB mittels TABLE_CREATE
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);

	}

	/**
	 * löscht die Tabelle Rules
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS Rules");
		onCreate(db);


	}


}
