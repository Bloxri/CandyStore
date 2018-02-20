package com.jblearning.candystorev5;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
	private DatabaseManager dbManager;
	private String displayedDueDate = "";
	private ScrollView scrollView;
	private int buttonWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		dbManager = new DatabaseManager(this);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		buttonWidth = size.x / 2;
		updateView();
	}

	protected void onResume() {
		super.onResume();
		updateView();
	}

	public void updateView() {
		ArrayList<Task> tasks = dbManager.selectAll();
		if (tasks.size() > 0) {
			// remove subviews inside scrollView if necessary
			scrollView.removeAllViewsInLayout();

			// set up the grid layout
			GridLayout grid = new GridLayout(this);
			grid.setRowCount((tasks.size() + 1) / 2);
			grid.setColumnCount(2);

			// create array of buttons, 2 per row
			TaskButton[] buttons = new TaskButton[tasks.size()];
			ButtonHandler bh = new ButtonHandler();

			// fill the grid
			int i = 0;
			for (Task task : tasks) {
				// create the button
				buttons[i] = new TaskButton(this, task);
				buttons[i].setText(task.getName() + "\n" + task.getDescription());

				try {
					if (DateFormat.getDateInstance().parse(task.getDeadline()).before(Calendar.getInstance().getTime())) {
						buttons[i].setTextColor(Color.RED);
					}
				} catch (ParseException p) {
					Log.d("Exception parsing date!", "updateView: " + p.getMessage());
				}

				// set up event handling
				buttons[i].setOnClickListener(bh);

				// add the button to grid
				grid.addView(buttons[i], buttonWidth, GridLayout.LayoutParams.WRAP_CONTENT);
				i++;
			}
			scrollView.addView(grid);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_add:
				Intent insertIntent = new Intent(this, InsertActivity.class);
				this.startActivity(insertIntent);
				return true;
			case R.id.action_delete:
				Intent deleteIntent = new Intent(this, DeleteActivity.class);
				this.startActivity(deleteIntent);
				return true;
			case R.id.action_update:
				Intent updateIntent = new Intent(this, UpdateActivity.class);
				this.startActivity(updateIntent);
				return true;
			case R.id.action_reset:
				displayedDueDate = "";
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private class ButtonHandler implements View.OnClickListener {
		public void onClick(View v) {
			// retrieve price of the candy and add it to total
			//total += ((TaskButton) v).getDescription();
			displayedDueDate = ((TaskButton) v).getText();
			String burnt = "Due Date: " + displayedDueDate;
			Toast.makeText(MainActivity.this, burnt, Toast.LENGTH_SHORT).show();
		}
	}
}
