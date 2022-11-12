package com.mprog.service;

import com.mprog.config.jwt.JwtUtils;
import com.mprog.database.model.User;
import com.mprog.database.repository.NoteRepository;
import com.mprog.database.repository.UserRepository;
import com.mprog.dto.NoteDto;
import com.mprog.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public List<NoteDto> getNotes() {
        var user = userRepository.findByEmail(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        ).orElse(null);
        return noteRepository.findAllByUser(user)
                .stream()
                .map(noteMapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
