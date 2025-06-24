package com.example.todo_api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // Root endpoint to handle base URL requests
    @GetMapping("/")
    public Map<String, Object> getApiInfo() {
        Map<String, Object> apiInfo = new HashMap<>();
        apiInfo.put("message", "Todo API is running!");
        apiInfo.put("version", "1.0.0");
        apiInfo.put("endpoints", Map.of(
            "GET /api/todos", "Get all todos",
            "GET /api/todos/{id}", "Get todo by ID",
            "POST /api/todos", "Create new todo",
            "PUT /api/todos/{id}", "Update todo",
            "DELETE /api/todos/{id}", "Delete todo",
            "GET /actuator/health", "Health check"
        ));
        return apiInfo;
    }

    // Get all todos
    @GetMapping("/api/todos")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // Get todo by ID
    @GetMapping("/api/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return todo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create todo
    @PostMapping("/api/todos")
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    // Update todo
    @PutMapping("/api/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            Todo existingTodo = todo.get();
            existingTodo.setTitle(updatedTodo.getTitle());
            existingTodo.setDescription(updatedTodo.getDescription());
            existingTodo.setCompleted(updatedTodo.isCompleted());
            return ResponseEntity.ok(todoRepository.save(existingTodo));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete todo
    @DeleteMapping("/api/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}