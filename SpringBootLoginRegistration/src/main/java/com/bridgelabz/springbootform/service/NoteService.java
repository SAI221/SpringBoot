package com.bridgelabz.springbootform.service;

import java.util.List;

import com.bridgelabz.springbootform.model.Note;

public interface NoteService {
public Note createNote(Note note,String token);

public Note findById(int userId);

public Note updateNote(Note note,int noteId);

public String deleteNote(int noteId);

public Note getNoteInfo(int noteId);

public List<Note> getAllNotes();
}
