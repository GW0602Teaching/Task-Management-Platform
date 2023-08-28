package io.gwteaching.tmptool.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer taskSequence = 0;

    private  String projectId;

    // OneToOne with Project
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_identifier", nullable = false)
    private Project project;


    // OneToMany Tasks

}
