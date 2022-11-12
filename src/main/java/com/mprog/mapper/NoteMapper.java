package com.mprog.mapper;

import com.mprog.database.model.Note;
import com.mprog.dto.NoteDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteDto entityToDTO(Note entity);
}
