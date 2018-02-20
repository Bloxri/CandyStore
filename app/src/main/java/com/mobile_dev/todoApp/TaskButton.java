package com.mobile_dev.todoApp;

import android.content.Context;

public class TaskButton extends android.support.v7.widget.AppCompatButton {
	private Task task;

	public TaskButton(Context context, Task newTask) {
		super(context);
		task = newTask;
	}

	public String getText() {
		return task.getDeadline();
	}
}
