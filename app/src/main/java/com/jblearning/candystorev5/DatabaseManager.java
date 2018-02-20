package com.jblearning.candystorev5;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "taskDB";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_TASK = "task";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "text";
	private static final String PRIORITY = "priority";
	private static final String DEADLINE = "deadline";

	DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		// build sql create statement
		String sqlCreate = "create table " + TABLE_TASK + "( "
				+ ID + " integer primary key autoincrement, "
				+ NAME + " text, "
				+ DESCRIPTION + " text, "
				+ PRIORITY + " integer,"
				+ DEADLINE + " string )";

		//CREATE TABLE TBLE_TASK(ID integer primary key autoincrement, NAME text, DESCRIPTION text, PRIORITY int );
		db.execSQL(sqlCreate);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop old table if it exists
		db.execSQL("drop table if exists " + TABLE_TASK);
		// Re-create tables
		onCreate(db);
	}

	void insert(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sqlInsert = "insert into " + TABLE_TASK +
				" values( null, '"
				+ task.getName() + "', '"
				+ task.getDescription() + "', '"
				+ task.getPriority() + "', '"
				+ task.getDeadline() + "' )";
		//INSERT INTO TABLE_TASK values(null, 'taskNAME', 'taskDescription', 'taskPriority')
		//COME BACK AND DO DEADLINE
		db.execSQL(sqlInsert);
		db.close();
	}

	void deleteById(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sqlDelete = "delete from " + TABLE_TASK + " where " + ID + " = " + id;
		//DELETE FROM TABLE_TASK WHERE ID = id
		db.execSQL(sqlDelete);
		db.close();
	}

	void updateById(int id, String name, String description, int priority) {
		SQLiteDatabase db = this.getWritableDatabase();

		String sqlUpdate = "update " + TABLE_TASK + " set " +
				NAME + " = '" + name + "', " +
				DESCRIPTION + " = '" + description + "', " +
				PRIORITY + " = '" + priority + "'" +
				" where " + ID + " = " + id;
		//UPDATE TABLE_TASK SET NAME = 'name', DESCRIPTION = 'description', PRIORITY = 'priority' WHERE ID = id
		db.execSQL(sqlUpdate);
		db.close();
	}

	ArrayList<Task> selectAll() {
		String sqlQuery = "select * from " + TABLE_TASK;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sqlQuery, null);

		ArrayList<Task> tasks = new ArrayList<>();
		while (cursor.moveToNext()) {
			Task currentTask = new Task(
					Integer.parseInt(cursor.getString(0)),
					cursor.getString(1),
					cursor.getString(2),
					Integer.parseInt(cursor.getString(3)),
					cursor.getString(4));

			tasks.add(currentTask);
		}
		cursor.close();
		db.close();
		return tasks;
	}

	public Task selectById(int id) {
		String sqlQuery = "select * from " + TABLE_TASK + " where " + ID + " = " + id;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sqlQuery, null);

		Task task = null;
		if (cursor.moveToFirst()) {
			task = new Task(
					Integer.parseInt(cursor.getString(0)),
					cursor.getString(1),
					cursor.getString(2),
					Integer.parseInt(cursor.getString(3)),
					cursor.getString(4));
		}
		cursor.close();
		return task;
	}
}
