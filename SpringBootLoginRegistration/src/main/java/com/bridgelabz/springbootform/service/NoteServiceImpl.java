package com.bridgelabz.springbootform.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.springbootform.model.Label;
import com.bridgelabz.springbootform.model.Note;
import com.bridgelabz.springbootform.repository.LabelRepository;
import com.bridgelabz.springbootform.repository.NoteReposirory;
import com.bridgelabz.springbootform.token.TokenClass;

@Service

public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteReposirory noteRepository;

	@Autowired
	private TokenClass tokenClass;

	@Autowired
	private LabelRepository labelRepository;

	@Override
	public Note createNote(Note note, String token) {
		int userId = tokenClass.parseJWT(token);
		LocalDateTime ts=LocalDateTime.now();
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
		int noteId = note.getNoteId();
		int userId = tokenClass.parseJWT(token);
		List<Note> noteInfo = noteRepository.findByNoteIdAndUserId(noteId, userId);
		LocalDateTime ts=LocalDateTime.now();
		noteInfo.forEach(existingUser -> {
			existingUser
					.setCreatedOn(note.getCreatedOn() != null ? note.getCreatedOn() : noteInfo.get(0).getCreatedOn());
			existingUser.setDescription(
					note.getDescription() != null ? note.getDescription() : noteInfo.get(0).getDescription());
			existingUser.setTitle(note.getTitle() != null ? note.getTitle() : noteInfo.get(0).getTitle());
			existingUser.setArchive(note.isArchive());
			existingUser.setInTrash(note.isInTrash());
			existingUser.setPinned(note.isPinned());
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

	@Override
	public Label labelCreate(Label label, String token) {
		int userId = tokenClass.parseJWT(token);
		label.setUserId(userId);

		return labelRepository.save(label);
	}

	@Override
	public Label labelUpdate(Label label, String token,int labelId) {
		int userId = tokenClass.parseJWT(token);
		List<Label> list = labelRepository.findByUserIdAndLabelId(userId, labelId);
		list.forEach(userLabel -> {
			userLabel.setLabelName(label.getLabelName() != null ? label.getLabelName() : list.get(0).getLabelName());
		});
		label.setLabelId(labelId);
		label.setUserId(userId);
		return labelRepository.save(label);
	}

	@Override
	public String labelDelete(String token, int labelId) {
		int userId = tokenClass.parseJWT(token);
		List<Label> list = labelRepository.findByUserIdAndLabelId(userId, labelId);
		labelRepository.delete(list.get(0));
		return "Deleted";
	}

	@Override
	public List<Label> getLabels(String token) {
		int userId = tokenClass.parseJWT(token);
		List<Label> list = labelRepository.findByUserId(userId);
		return list;
	}

}
