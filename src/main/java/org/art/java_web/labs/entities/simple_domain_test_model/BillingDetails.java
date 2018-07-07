package org.art.java_web.labs.entities.simple_domain_test_model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetails {

    public BillingDetails() {}

    public BillingDetails(User owner) {
        this.owner = owner;
    }

    @Id
    @GeneratedValue(generator = "ADVANCED_GENERATOR")
    @GenericGenerator(
            name = "ADVANCED_GENERATOR",
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
            })
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "OWNER_ID")
    private User owner;

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "BillingDetails{" +
                "id=" + id +
                ", owner=" + owner +
                '}';
    }
}
