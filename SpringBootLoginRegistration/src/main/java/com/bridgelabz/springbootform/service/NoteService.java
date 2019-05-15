package com.bridgelabz.springbootform.service;

import java.util.List;

import com.bridgelabz.springbootform.model.Note;

public interface NoteService {
	public Note createNote(Note note, String token);

	public Note findById(int userId);

	public Note updateNote(Note note,String token);

	public String deleteNote(int noteId,String token);

	public Note getNoteInfo(int noteId);

	public List<Note> getAllNotes();

	public List<Note> getNotes(String token);
}
