package com.example.springmybatis3jdbc.repository;

import com.example.springmybatis3jdbc.domain.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Integer> {

}
