package com.tracking.activitytracking.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity

public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(orphanRemoval = true,
            mappedBy = "appUser", cascade = CascadeType.ALL)
    @ToString.Exclude
   private List<Task> task = new ArrayList<>();
}
