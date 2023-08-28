package io.gwteaching.tmptool.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    private Project project;


    // OneToMany Tasks
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "backlog")
    private List<Task> taskList = new ArrayList<>();

}
