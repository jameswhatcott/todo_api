package com.example.todo_api;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
public class Todo {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String description;
	private boolean completed;

	// Getters and setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public boolean isCompleted() { return completed; }
	public void setCompleted(boolean completed) { this.completed = completed; }
}