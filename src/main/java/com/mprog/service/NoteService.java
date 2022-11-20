package com.mprog.service;

import com.mprog.context.UserContextHolder;
import com.mprog.database.model.Note;
import com.mprog.database.model.User;
import com.mprog.database.repository.NoteRepository;
import com.mprog.dto.NoteDto;
import com.mprog.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;

    public List<NoteDto> getNotes() {
        User user = UserContextHolder.getUser();
        return noteRepository.findAllByUser(user)
                .stream()
                .map(noteMapper::entityToDTO)
                .collect(Collectors.toList());
    }


    public boolean saveNote(NoteDto noteDto) {
        var note = noteRepository.findById(noteDto.getId())
                .orElse(new Note());
        note.setText(noteDto.getText());
        note.setTittle(noteDto.getTittle());

        if (noteDto.getId() != null) {
            note.setUpdatedAt(LocalDateTime.now());
        } else {
            note.setCreatedAt(LocalDateTime.now());
            note.setUser(UserContextHolder.getUser());
        }
        return true;
    }
}
