package com.bridgelabz.springbootform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.springbootform.model.Note;

public interface NoteReposirory extends JpaRepository<Note ,Long> {

	public List<Note> findByUserId(int id);

	public List<Note> findByNoteId(int noteId);
	
	public List<Note> findByNoteIdAndUserId(int noteId,int userId);
}
