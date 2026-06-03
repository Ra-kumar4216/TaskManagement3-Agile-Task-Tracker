package com.TaskManagement3.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import aj.org.objectweb.asm.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label,Long> {
	
	java.util.Optional<com.TaskManagement3.Entity.Label> findByName(String name);
}