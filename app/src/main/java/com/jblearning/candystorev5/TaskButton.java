package com.jblearning.candystorev5;

import android.content.Context;
import android.widget.Button;

public class TaskButton extends Button {
  private Task task;

  public TaskButton( Context context, Task newTask ) {
    super( context );
    task = newTask;
  }

  public String getText( ) {
    return task.getText( );
  }
}
