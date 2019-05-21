package com.bridgelabz.fundonoteapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundonoteapp.model.Label;
@Repository
public interface LabelRepository extends JpaRepositoryImplementation<Label, Long> {

	List<Label> findByUserId(int userId);

	List<Label> findByUserIdAndLabelId(int userId, int labelId);

}