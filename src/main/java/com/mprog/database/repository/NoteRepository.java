package com.mprog.database.repository;

import com.mprog.database.model.Note;
import com.mprog.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUser(User user);
}
