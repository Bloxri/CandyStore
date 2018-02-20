package com.mobile_dev.todoApp;

public class Task {
	private int id;
	private String name;
	private String description;
	private int priority;
	private String deadline;

	Task(int newId, String newTitle, String newText, int newPriority, String deadline) {
		setId(newId);
		setName(newTitle);
		setText(newText);
		setPriority(newPriority);
		setDeadline(deadline);
	}

	public void setId(int newId) {
		id = newId;
	}

	public void setName(String newTitle) {
		name = newTitle;
	}

	private void setText(String newDescription) {
		if (newDescription != null)
			description = newDescription;
	}

	private void setPriority(int newPriority) {
		priority = newPriority;
	}

	private void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	int getPriority() {
		return priority;
	}

	String getDescription() {
		return description;
	}

	String getDeadline() {
		return deadline;
	}

	public String toString() {
		return id + " - " + name + " - " + description + priority;
	}
}