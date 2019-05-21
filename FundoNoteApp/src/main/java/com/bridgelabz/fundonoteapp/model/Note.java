package com.bridgelabz.fundonoteapp.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Note {
	@Id
	private int noteId;
	private String title;
	private String description;

	private LocalTime createdOn;
	private LocalTime updatedOn;
	private boolean inTrash;
	private boolean isArchive;
	private boolean isPinned;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public boolean isInTrash() {
		return inTrash;
	}

	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

}
