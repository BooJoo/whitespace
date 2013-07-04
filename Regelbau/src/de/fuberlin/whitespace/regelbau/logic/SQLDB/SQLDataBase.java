package de.fuberlin.whitespace.regelbau.logic.SQLDB;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import de.fuberlin.whitespace.regelbau.logic.Rule;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLDataBase {

	private SQLiteDatabase db;
	private SQLHelper dbHelper;
	private String[] spalten = {"id", "rule"};

	public SQLDataBase(Context context){
		dbHelper = new SQLHelper(context);
	}

	public void open() throws SQLException{
		db = dbHelper.getWritableDatabase();
	}

	public void close(){
		db.close();
	}
	
	public void AddToDB(Rule newRule)
	{
	    	ContentValues newRow;
	    	long id;
	    	
		newRow = new ContentValues();
		newRow.put("rule", SQLDataBase.serialize(newRule));
		id = db.insert("Rules", null, newRow);
		
		// Id der Rule-Instanz auf die Id der neu erzeugten Tabellenzeile setzen
		newRule.setId(id);
	}
	
	public void UpdateRow(Rule r) {
	    
	    assert(r.getId() != null);
	    
	    ContentValues row = new ContentValues();
	    row.put("rule", SQLDataBase.serialize(r));
	    
	    db.update("Rules", row, "id=" + r.getId(), null);
	}

	public void RemoveFromDB(Rule r) {
	    
	    assert(r.getId() != null);
	    
	    db.delete("Rules", "id=" + r.getId(), null);
	}

	public void Update(List<Rule> rules)
	{
		reset();
		for (Rule r : rules) {
			AddToDB(r);
		}
	}
	
	public  LinkedList<Rule> getAllEntries(){
		
		LinkedList<Rule> list = new LinkedList<Rule>();
		
		Cursor cursor = db.query("Rules", spalten, null, null, null, null, null);
		
		cursor.moveToLast();
		if(cursor.getCount() == 0) return list;


		while(!cursor.isBeforeFirst()){
			Rule rule = (Rule) SQLDataBase.deserialize(cursor.getBlob(1));
			rule.setId(cursor.getLong(0));
			
			list.add(rule);
			cursor.moveToPrevious();
		}

		cursor.close();
		
		return list;
	}
	
	public void reset()
	{
		dbHelper.onUpgrade(db, 0, 0);
	}
	
	public static byte[] serialize(Object obj)  {
	    try{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	    }
	    catch(Exception e)
	    {return null;}
	}

	public static Object deserialize(byte[] bytes)  {
	    try
	    {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	    }
	    catch(Exception e)
	    {
		return null;
	    }
	}

}
