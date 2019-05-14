package com.bridgelabz.springbootform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.springbootform.model.Note;
import com.bridgelabz.springbootform.repository.NoteReposirory;
import com.bridgelabz.springbootform.token.TokenClass;
@Service
public class NoteServiceImpl implements NoteService{
	
	@Autowired 
	NoteReposirory noteRepository;
	
	@Autowired 
	private TokenClass tokenClass;

	@Override
	public Note createNote(Note note,String token) {
		int userId=tokenClass.parseJWT(token);
		System.out.println(userId);
		note.setUserId(userId);
		return noteRepository.save(note);
	}

	@Override
	public Note findById(int userId) {
		Note noteInfo=noteRepository.findByUserId(userId);
		return noteInfo;
	}

	@Override
	public Note updateNote(Note note, int noteId) {
		List<Note> noteInfo=noteRepository.findByNoteId(noteId);
	 noteInfo.forEach(existingUser -> {
			existingUser.setCreatedOn(note.getCreatedOn() !=null ? note.getCreatedOn() : noteInfo.get(0).getCreatedOn());
			existingUser.setDescription(note.getDescription()!=null ? note.getDescription() : noteInfo.get(0).getDescription());
			existingUser.setTitle(note.getTitle() !=null ? note.getTitle() : noteInfo.get(0).getTitle());
			existingUser.setUpdatedOn(note.getUpdatedOn() !=null ? note.getUpdatedOn() : noteInfo.get(0).getUpdatedOn());});
	return noteRepository.save(noteInfo.get(0));
		
		
		
	}

	@Override
	public String deleteNote(int noteId) {
		List<Note> noteInfo=noteRepository.findByNoteId( noteId);
		noteRepository.delete(noteInfo.get(0));
		return "Deleted";
	}

	@Override
	public Note getNoteInfo(int noteId) {
		List<Note> noteInfo=noteRepository.findByNoteId(noteId);
		return noteInfo.get(0);
	}

	@Override
	public List<Note> getAllNotes() {
		
		return noteRepository.findAll();
	}

}
