package com.jblearning.candystorev5;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
	DatabaseManager dbManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbManager = new DatabaseManager(this);
		updateView();
	}

	// Build a View dynamically with all the tasks
	public void updateView() {
		ArrayList<Task> tasks = dbManager.selectAll();
		if (tasks.size() > 0) {
			// create ScrollView and GridLayout
			ScrollView scrollView = new ScrollView(this);
			GridLayout grid = new GridLayout(this);
			grid.setRowCount(tasks.size());
			grid.setColumnCount(4);

			// create arrays of components
			TextView[] ids = new TextView[tasks.size()];
			EditText[][] namesAndPrices = new EditText[tasks.size()][2];
			Button[] buttons = new Button[tasks.size()];
			ButtonHandler bh = new ButtonHandler();

			// retrieve width of screen
			Point size = new Point();
			getWindowManager().getDefaultDisplay().getSize(size);
			int width = size.x;

			int i = 0;

			for (Task task : tasks) {
				Log.d("Task ID:", "updateView: " + task.getId());
				ids[i] = new TextView(this);
				ids[i].setGravity(Gravity.CENTER);
				ids[i].setText(Integer.toString(task.getId()));

				namesAndPrices[i][0] = new EditText(this);
				namesAndPrices[i][1] = new EditText(this);
				namesAndPrices[i][0].setText(task.getName());
				namesAndPrices[i][1].setText(task.getDescription());
				namesAndPrices[i][1].setInputType(InputType.TYPE_CLASS_TEXT);
				namesAndPrices[i][0].setId(10 * task.getId());
				namesAndPrices[i][1].setId(10 * task.getId() + 1);

				// create the button
				buttons[i] = new Button(this);
				buttons[i].setText("Update");
				buttons[i].setId(task.getId());

				// set up event handling
				buttons[i].setOnClickListener(bh);

				// add the elements to grid
				grid.addView(ids[i], width / 10, ViewGroup.LayoutParams.WRAP_CONTENT);
				grid.addView(namesAndPrices[i][0], (int) (width * .4), ViewGroup.LayoutParams.WRAP_CONTENT);
				grid.addView(namesAndPrices[i][1], (int) (width * .15), ViewGroup.LayoutParams.WRAP_CONTENT);
				grid.addView(buttons[i], (int) (width * .35), ViewGroup.LayoutParams.WRAP_CONTENT);

				i++;
			}
			scrollView.addView(grid);
			setContentView(scrollView);
		}
	}

	//TODO: This is the final piece to complete. It's freaking out when using taskId below.
	private class ButtonHandler implements View.OnClickListener {
		public void onClick(View v) {
			// retrieve name and price of the candy
			int taskId = v.getId();
			EditText nameET = (EditText) findViewById(taskId);
			EditText priceET = (EditText) findViewById(taskId);
			String name = nameET.getText().toString();
			String priceString = priceET.getText().toString();

			// update task in database
			try {
				dbManager.updateById(taskId, name, "", 0);//TODO: this needs help..
				Toast.makeText(UpdateActivity.this, "Task updated", Toast.LENGTH_SHORT).show();

				// update screen
				updateView();
			} catch (Exception nfe) {
				Toast.makeText(UpdateActivity.this, "Price error", Toast.LENGTH_LONG).show();
			}
		}
	}
}
