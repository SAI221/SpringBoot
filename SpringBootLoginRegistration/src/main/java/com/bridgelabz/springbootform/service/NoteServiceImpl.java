package com.bridgelabz.springbootform.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.springbootform.model.Note;
import com.bridgelabz.springbootform.repository.NoteReposirory;
import com.bridgelabz.springbootform.token.TokenClass;

@Service

public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteReposirory noteRepository;

	@Autowired
	private TokenClass tokenClass;

	@Override
	public Note createNote(Note note, String token) {
		int userId = tokenClass.parseJWT(token);
		System.out.println(userId);
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		note.setCreatedOn(ts);
		note.setUserId(userId);
		return noteRepository.save(note);
	}

	@Override
	public Note findById(int userId) {
		List<Note> noteInfo = noteRepository.findByUserId(userId);
		return noteInfo.get(0);
	}

	@Override
	public Note updateNote(Note note, String token) {
		int noteId=note.getNoteId();
		int userId = tokenClass.parseJWT(token);
		List<Note> noteInfo = noteRepository.findByNoteIdAndUserId(noteId, userId);
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		noteInfo.forEach(existingUser -> {
			existingUser
					.setCreatedOn(note.getCreatedOn() != null ? note.getCreatedOn() : noteInfo.get(0).getCreatedOn());
			existingUser.setDescription(
					note.getDescription() != null ? note.getDescription() : noteInfo.get(0).getDescription());
			existingUser.setTitle(note.getTitle() != null ? note.getTitle() : noteInfo.get(0).getTitle());
			/*
			 * existingUser .setUpdatedOn(note.getUpdatedOn() != null ? note.getUpdatedOn()
			 * : noteInfo.get(0).getUpdatedOn());
			 */
		});
		noteInfo.get(0).setUpdatedOn(ts);
		return noteRepository.save(noteInfo.get(0));

	}

	@Override
	public String deleteNote(int noteId, String token) {
		int userId = tokenClass.parseJWT(token);
		List<Note> noteInfo = noteRepository.findByNoteIdAndUserId(noteId, userId);
		noteRepository.delete(noteInfo.get(0));
		return "Deleted";
	}

	@Override
	public Note getNoteInfo(int noteId) {
		List<Note> noteInfo = noteRepository.findByNoteId(noteId);
		return noteInfo.get(0);
	}

	@Override
	public List<Note> getAllNotes() {

		return noteRepository.findAll();
	}

	@Override
	public List<Note> getNotes(String token) {
		int id = tokenClass.parseJWT(token);
		List<Note> list = noteRepository.findByUserId(id);
		return list;
	}

}
