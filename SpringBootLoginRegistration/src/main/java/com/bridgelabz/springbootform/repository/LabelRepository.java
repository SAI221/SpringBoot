package com.bridgelabz.springbootform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.bridgelabz.springbootform.model.Label;

public interface LabelRepository extends JpaRepositoryImplementation<Label, Long> {

	List<Label> findByUserId(int userId);

	List<Label> findByUserIdAndLabelId(int userId, int labelId);

}
