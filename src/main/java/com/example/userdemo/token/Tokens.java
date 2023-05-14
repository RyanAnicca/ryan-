package com.example.userdemo.token;


import com.example.userdemo.model.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tokens {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String tokens;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    public Users users;
}
//    CREATE TABLE tokens (
//        id INT PRIMARY KEY IDENTITY(1,1),
//    tokens NVARCHAR(255) UNIQUE,
//    tokenType NVARCHAR(50),
//    revoked BIT,
//    expired BIT,
//    user_id BIGINT,
//    FOREIGN KEY (user_id) REFERENCES users(id)
//        );
