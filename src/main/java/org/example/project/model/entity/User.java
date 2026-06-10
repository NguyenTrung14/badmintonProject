package org.example.project.model.entity;
import jakarta.persistence.*;
import lombok.*;
import org.example.project.common.enumType.RoleType;
import org.example.project.model.entity.dto.RegisterRequestDto;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private String email;
    private String phoneNumber;
    private Boolean isEnabled;
    public User(RegisterRequestDto registerRequestDto) {
        this.username = registerRequestDto.getUsername();
        this.password = registerRequestDto.getPassword();
        this.fullName = registerRequestDto.getFullName();
        this.email = registerRequestDto.getEmail();
        this.phoneNumber = registerRequestDto.getPhoneNumber();
        this.role= RoleType.ROLE_CUSTOMER.toString();
        this.isEnabled = true;
    }
}
