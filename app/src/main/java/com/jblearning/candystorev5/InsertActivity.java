package com.jblearning.candystorev5;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class InsertActivity extends AppCompatActivity {
	private DatabaseManager dbManager;
	EditText DeadlineDate;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbManager = new DatabaseManager(this);
		setContentView(R.layout.activity_insert);
	}

	public void insert(View v) {
		// Retrieve name and price
		EditText NameText = (EditText) findViewById(R.id.input_name);
		EditText DescriptionText = (EditText) findViewById(R.id.input_description);
		DeadlineDate = (EditText) findViewById(R.id.input_deadline);
		EditText Priority = (EditText) findViewById(R.id.input_priority);
		//TODO: Check for null values...
		String name = NameText.getText().toString();
		String description = DescriptionText.getText().toString();
		int priority = Integer.parseInt(Priority.getText().toString());
		String deadLine = DeadlineDate.getText().toString();
		// insert new task in database
		try {
			Task task = new Task(0, name, description, priority, deadLine);
			dbManager.insert(task);
			Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
		} catch (NumberFormatException nfe) {
			Toast.makeText(this, "Price error", Toast.LENGTH_LONG).show();
		}

		// clear data
		NameText.setText("");
		DescriptionText.setText("");
		DeadlineDate.setText("");
		Priority.setText("");
	}

	public void goBack(View v) {
		this.finish();
	}

	public void chooseDate(View view) {
		Log.d("Testing", "chooseDate: testing!");
		DeadlineDate = (EditText) findViewById(R.id.input_deadline);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		DatePickerDialog dialog = new DatePickerDialog(this, dateChosen, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		dialog.show();

		Log.d("Date Object:", "chooseDate:" + date.toString());
		Log.d("Date Object:", "chooseDate:" + cal.getTime());
	}

	private DatePickerDialog.OnDateSetListener dateChosen = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			Log.d("Date Chosen!", "onDateSet: Just hit the date chosen listener!!");
			Log.d("Year", "onDateSet: " + year);
			Log.d("Month", "onDateSet: " + (monthOfYear + 1));
			Log.d("Day", "onDateSet: " + dayOfMonth);
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, monthOfYear);
			c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			String dateChosen = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
			DeadlineDate.setText(dateChosen);

		}
	};
}

