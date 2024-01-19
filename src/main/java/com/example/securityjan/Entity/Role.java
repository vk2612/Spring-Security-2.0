package com.example.securityjan.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Id
    private String id;

    private String name;
}
