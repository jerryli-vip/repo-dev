package com.pactera.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pactera.web.model.Department;

public interface DepartmentDAO extends JpaRepository<Department, Integer> {

}
