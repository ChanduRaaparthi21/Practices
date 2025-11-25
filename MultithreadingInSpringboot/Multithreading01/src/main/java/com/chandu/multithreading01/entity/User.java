package com.chandu.multithreading01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TBL")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String gender;


}
