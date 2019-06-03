package com.bridgelabz.fundonoteapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Label {
	@Id

	private int labelId;
	private String labelName;
	private int userId;

	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
