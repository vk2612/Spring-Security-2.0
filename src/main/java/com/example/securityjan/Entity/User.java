package com.example.securityjan.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Long phone;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    private String password;
}
