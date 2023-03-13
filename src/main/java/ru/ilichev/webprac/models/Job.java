package ru.ilichev.webprac.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job", schema = "webprac", catalog = "webprac")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@ToString
public class Job implements Serializable {
    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, unique = true)
    @NonNull
    private String title;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @Column(name = "is_manager", nullable = false)
    private boolean manager = false;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobHistory> history = new ArrayList<>();
}
