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

//    @Enumerated(EnumType.STRING)
    public String tokenType;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Users users;

    public Users getUser() {
        return users;
    }

    public void setUser(Users user) {
        this.users = users;
    }
}
//    CREATE TABLE tokens (
//        id SERIAL PRIMARY KEY,
//        tokens VARCHAR(255) UNIQUE,
//    tokenType VARCHAR(255),
//    revoked BOOLEAN,
//    expired BOOLEAN,
//    user_id INTEGER REFERENCES users(id)
//);

