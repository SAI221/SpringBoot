package com.bridgelabz.fundonoteapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundonoteapp.model.Note;

@Repository
public interface NoteReposirory extends JpaRepository<Note ,Long> {

	public List<Note> findByUserId(int id);

	public List<Note> findByNoteId(int noteId);
	
	public List<Note> findByNoteIdAndUserId(int noteId,int userId);
	
	List<Note> findByUserIdAndInTrash(int userId, String string);

	List<Note> findByUserIdAndIsArchive(int userId, String string);
}
