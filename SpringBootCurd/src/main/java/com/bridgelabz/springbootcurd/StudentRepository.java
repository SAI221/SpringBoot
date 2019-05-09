package com.bridgelabz.springbootcurd;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface StudentRepository extends CrudRepository<Student, Long>  {

}
