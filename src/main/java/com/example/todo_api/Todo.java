//declares where this file lives
package com.example.todo_api;

//imports the entity annotation from the jakarta persistence library, making the class an entity that can be persisted to the database
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue; // Auto-generates unique IDs for each todo
import jakarta.persistence.Id; 


//makes an entity
@Entity
public class Todo {
	@Id
	@GeneratedValue
	//identifies the fields that are part of the entity
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