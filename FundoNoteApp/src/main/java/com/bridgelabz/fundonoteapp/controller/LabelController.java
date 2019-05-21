package com.bridgelabz.fundonoteapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/note")
public class LabelController {

	@Autowired
	private NoteService noteService;

	@PostMapping(value = "/label")
	public Label createLabel(@RequestBody Label label, HttpServletRequest request) {
		String token = request.getHeader("token");
		return noteService.labelCreate(label, token);

	}

	@PutMapping(value = "/updatelabel")
	public Label updateLabel(@RequestBody Label label, HttpServletRequest request,@PathVariable int labelId) {
		String token = request.getHeader("token");
		return noteService.labelUpdate(label, token,labelId);
	}

	@DeleteMapping(value = "/deletelabel/{labelId}")
	public String deleteLabel(@PathVariable int labelId, HttpServletRequest request) {
		String token = request.getHeader("token");
		return noteService.labelDelete(token, labelId);
	}

	@GetMapping(value = "/fetchlabels")
	public List<Label> fetchLabels(HttpServletRequest request) {
		String token = request.getHeader("token");
		return noteService.getLabels(token);
	}

}
