package com.project.americo.jsonbin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.americo.jsonbin.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

	@Query(value = "SELECT * FROM file AS obj WHERE obj.key = :key AND obj.name = :name", 
			  nativeQuery = true)
	List<File> thisFileExist(@Param("key") Integer id, @Param("name") String itsFrom);
}
