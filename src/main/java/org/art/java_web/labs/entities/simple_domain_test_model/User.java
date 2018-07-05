package org.art.java_web.labs.entities.simple_domain_test_model;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USERS")
public class User {

    public User() {}

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(generator = "ADVANCED_SEQUENCE")
    @GenericGenerator(
            name = "ADVANCED_SEQUENCE",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(
                            name = "sequence_name",
                            value = "DOMAIN_TEST_SEQUENCE"
                    ),
                    @Parameter(
                            name = "initial_value",
                            value = "1"
                    )
            }
    )
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(
            min = 2,
            max = 255,
            message = "First name is required, maximum 255 characters."
    )
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Size(
            min = 2,
            max = 255,
            message = "Last name is required, maximum 255 characters."
    )
    @Column(name = "LAST_NAME")
    private String lastName;

    @Formula(value = " concat(FIRST_NAME, ' ', LAST_NAME) ")
    private String fullName;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "User {" +
                "id = " + id +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                '}';
    }
}
