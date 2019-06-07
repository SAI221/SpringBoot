package com.bridgelabz.fundonoteapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.model.Label;
import com.bridgelabz.fundonoteapp.service.NoteService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") 
@RequestMapping(value="/note")
public class LabelController {

	@Autowired
	private NoteService noteService;

	@PostMapping(value = "/label")
	public ResponseEntity<Label> createLabel(@RequestBody Label label, HttpServletRequest request) {
		String token = request.getHeader("token");
		if(label.equals(noteService.labelCreate(label, token)))
		return new ResponseEntity<Label>(noteService.labelCreate(label, token),HttpStatus.CREATED);
		else
			return new ResponseEntity<Label>(noteService.labelCreate(label, token),HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/label/{labelId}")
	public ResponseEntity<Label> updateLabel(@RequestBody Label label, HttpServletRequest request,@PathVariable int labelId) {
		String token = request.getHeader("token");
		
		return new ResponseEntity<Label>(noteService.labelUpdate(label, token,labelId),HttpStatus.ACCEPTED);
	}

	@DeleteMapping(value = "/label/{labelId}")
	public ResponseEntity<String> deleteLabel(@PathVariable int labelId, HttpServletRequest request) {
		String token = request.getHeader("token");
		return new ResponseEntity<String>(noteService.labelDelete(token, labelId),HttpStatus.OK);
	}

	@GetMapping(value = "/labels")
	public ResponseEntity<List<Label>> fetchLabels(HttpServletRequest request) {
		String token = request.getHeader("token");
		return new ResponseEntity<List<Label>>(noteService.getLabels(token),HttpStatus.FOUND);
	}
	
	@PostMapping(value="/notelabel/{noteId}")
	public ResponseEntity<Label> createNoteLabel(@PathVariable int noteId,@RequestBody Label label,HttpServletRequest request){
		String token = request.getHeader("token");
		return new ResponseEntity<Label>(noteService.createNoteLabel(token,label,noteId),HttpStatus.OK);
	}
	
	@PutMapping(value="/update/{noteId}")
	public ResponseEntity<Label> updateNoteLabel(@PathVariable int noteId,@RequestBody Label label){
		
		return new ResponseEntity<Label>(noteService.noteLabelUpdate(label, noteId),HttpStatus.ACCEPTED);
	}

}
