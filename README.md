# todocrud

## FRAMEWORK AND LANGUAGE USED
* JAVA 17
* MAVEN
* SPRINGBOOT 3.1.1
<!-- Headings -->   
## DATA FLOW

<!-- Code Blocks -->

    

 ### CONTROLLER
  ```
package com.geekster.TodoAPP.controller;

import com.geekster.TodoAPP.entity.Todo;
import com.geekster.TodoAPP.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    TodoService todoService;






    @ResponseBody
    @GetMapping("todo/done")
    public Iterable<Todo> getDoneTodos()
    {
        return todoService.getAllDoneTodos();
    }

    @GetMapping("todos/{todoId}")
    public Todo getTodoById(@PathVariable Integer todoId)
    {
        return todoService.getTodoById(todoId);
    }

    @GetMapping("todo/undone")
    public List<Todo> getNotDoneTodos()
    {
        return todoService.getUndoneTodos();
    }

    @PostMapping("addtodo")
    public String addTodo(@RequestBody Todo todo)
    {
        return todoService.addTodo(todo);
    }

    @PutMapping("todo/{todoId}/{status}")
    public String markTodo(@PathVariable Integer todoId,@PathVariable boolean status)
    {
        return todoService.updateTodoStatus(todoId,status);
    }

    @DeleteMapping("todo")
    public String removeTodo(@RequestParam Integer todoId)
    {
        return todoService.removeTodo(todoId);

    }


}

```


 ### MODEL
  ```
package com.geekster.TodoAPP.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Todo {

    @Id
    private Integer todoId;
    private String todoName;
    private boolean isTodoDoneStatus;


}

```

### REPO
  ``` 
package com.geekster.TodoAPP.repository;

import com.geekster.TodoAPP.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TodoRepo extends CrudRepository<Todo,Integer> {


}

```


### SERVICE
  ``` 
package com.geekster.TodoAPP.service;

import com.geekster.TodoAPP.entity.Todo;
import com.geekster.TodoAPP.repository.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {


    @Autowired
    TodoRepo todoRepo;

    public Iterable<Todo> getAllTodos()
    {
        return todoRepo.findAll();
    }

    public List<Todo> getAllDoneTodos()
    {
        List<Todo> doneTodos = new ArrayList<>();
        for(Todo myTodo : todoRepo.findAll())
        {
            if(myTodo.isTodoDoneStatus())
            {
                doneTodos.add(myTodo);
            }
        }

        return doneTodos;
    }

    public List<Todo> getUndoneTodos()
    {
        List<Todo> unDoneTodos = new ArrayList<>();
        for(Todo myTodo : todoRepo.findAll())
        {
            if(!myTodo.isTodoDoneStatus())
            {
                unDoneTodos.add(myTodo);
            }
        }

        return unDoneTodos;
    }

    public String addTodo(Todo myTodo)
    {
        todoRepo.save(myTodo);
        return "Todo added";
    }

    public String updateTodoStatus(Integer todoId,boolean status)
    {
        for(Todo myTodo : todoRepo.findAll())
        {
            if(myTodo.getTodoId().equals(todoId))
            {
                myTodo.setTodoDoneStatus(status);
                return "todo updated for todo ID:" + todoId;
            }
        }

        return "todo not found todo ID: " + todoId;
    }


    public String removeTodo(Integer todoId)
    {
        for(Todo myTodo : todoRepo.findAll())
        {
            if(myTodo.getTodoId().equals(todoId))
            {
                todoRepo.delete(myTodo);
                return "todo removed for todo ID:" + todoId;
            }
        }
        return "todo :" + todoId + " not deleted as it doesn't exist" ;
    }

    public Todo getTodoById(Integer todoId) {
        for(Todo todo : todoRepo.findAll())
        {
            if(todo.getTodoId().equals(todoId))
            {
                return todo;
            }
        }
        throw new IllegalStateException("id not found");
    }
}

```


### MAIN
  ``` 

package com.geekster.TodoAPP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

}


```


 ### POM
  ``` 
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.1.1</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.geekster</groupId>
	<artifactId>Hotel-Management</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Hotel-Management</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>

```
## DATA STRUCTURE USED
* LIST 
* STRING
* LOCAL DATE
* INTEGER
* USER

# PROJECT SUMMARY

## Todo backend Has been created with the following attribute

* todoId
* todoName
* isTodoDoneStatus

### Endpoint to be provided 

* Add todo
* update todo list
* Delete todo 
* getall done
* get all undone









<!-- Headings -->   
# Author
SHUBHAM PATHAK
 <!-- UL -->
* Twitter <!-- Links -->
[@ShubhamPathak]( https://twitter.com/Shubham15022000)
* Github  <!-- Links -->
[@ShubhamPathak]( https://github.com/ShubhamPatha)
<!-- Headings -->   
