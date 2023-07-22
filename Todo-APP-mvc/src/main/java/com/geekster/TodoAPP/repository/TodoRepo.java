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
