package org.example.project.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @OneToOne
    @JoinColumn(name = "user_Id")
    private User user;
    private Date expiryDate;
    private boolean revoked;
}
