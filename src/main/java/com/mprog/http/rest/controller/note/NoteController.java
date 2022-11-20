package com.mprog.http.rest.controller.note;

import com.mprog.dto.NoteDto;
import com.mprog.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteDto>> getNotes() {
        return new ResponseEntity<>(noteService.getNotes(), HttpStatus.OK);
    }

    @GetMapping("/save")
    public ResponseEntity<Boolean> saveNote(@RequestBody NoteDto noteDto) {
        return new ResponseEntity<>(noteService.saveNote(noteDto), HttpStatus.OK);
    }
}
