package org.art.web.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User {

    public User(Long id, String fName, String lName, String login, String regDate, Role role) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.login = login;
        this.regDate = regDate;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String fName;

    @Column(name = "LAST_NAME")
    private String lName;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "REG_DATE")
    private String regDate;

    @Column(name = "ROLE")
    private Role role;

    @Transient
    private Set<String> chatRooms = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User: \n")
                .append("id - " + id + ",\n")
                .append("first name - " + fName + ",\n")
                .append("last name - " + lName + ",\n")
                .append("login - " + login + ",\n")
                .append("reg date - " + regDate + ",\n")
                .append("chat rooms - " + chatRooms + ",\n")
                .append("role = " + role + "\n");
        return sb.toString();
    }

    public String getFullName() {
        return fName + ' ' + lName;
    }
}
