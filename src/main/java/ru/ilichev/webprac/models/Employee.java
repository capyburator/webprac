package ru.ilichev.webprac.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee", schema = "webprac", catalog = "webprac")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@ToString
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "full_name", nullable = false)
    @NonNull
    private String fullName;

    @Column(name = "address", nullable = false)
    @NonNull
    private String address;

    @Column(name = "phone_no", nullable = false)
    @NonNull
    private String phoneNo;

    @Column(name = "birth_date")
    @NonNull
    private LocalDate birthDate;

    @Column(name = "education_level", nullable = false)
    @NonNull
    private String educationLevel;

    @Column(name = "alma_mater")
    private String almaMater;

    @Column(name = "email", nullable = false, unique = true)
    @NonNull
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private List<JobHistory> history = new ArrayList<>();
}
