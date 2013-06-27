package de.fuberlin.whitespace.regelbau.logic.SQLDB;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLDataBase {

	private SQLiteDatabase db;
	private SQLHelper dbHelper;
	private String[] spalten = {"rule"};
	private Context context;

	public SQLDataBase(Context context){
		this.context=context;
		dbHelper = new SQLHelper(context);
	}

	public void open() throws SQLException{
		db = dbHelper.getWritableDatabase();
	}

	public void close(){
		db.close();
	}
	
	public void AddToDB(byte[] s)
	{
		open();
		ContentValues newRow = new ContentValues();
		newRow.put("rule", s);
		
		db.insert("Rules", null, newRow);
		close();
	}

	public void Update(List<byte[]> b)
	{
		reset();
		for (byte[] cs : b) {
			AddToDB(cs);
		}
	}
	
	private byte[] cursorToString(Cursor cursor) {
		
		return cursor.getBlob(0);

	}
	
	public  LinkedList<byte[]> getAllEntries(){
		open();
		LinkedList<byte[]> list = new LinkedList<byte[]>();
		
		Cursor cursor = db.query("Rules", spalten, null, null, null, null, null);
		
		cursor.moveToLast();
		if(cursor.getCount() == 0) return list;


		while(!cursor.isBeforeFirst()){
			byte[] rule = cursorToString(cursor);
			list.add(rule);
			cursor.moveToPrevious();
		}

		cursor.close();
		close();
		return list;
	}
	
	public void reset()
	{
		open();
		dbHelper.onUpgrade(db, 0, 0);
		close();
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