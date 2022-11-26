package com.mprog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class GeneralDto {

    private Long externalId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime matchDate;
    private String tournamentName;
    private String tournamentStage;
    private String stadiumName;
    private String teamHostName;
    private String teamGuestName;
//    private Integer goalsHost;
//    private Integer goalsGuest;
//    private String protocolLink;
}
