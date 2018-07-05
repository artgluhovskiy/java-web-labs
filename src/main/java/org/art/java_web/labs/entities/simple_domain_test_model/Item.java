package org.art.java_web.labs.entities.simple_domain_test_model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private Set<Bid> bids = new HashSet<>();

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
    }

    public void setBids(Set<Bid> bids) {
        this.bids = new HashSet<>(bids);
    }

    public void addBid(Bid bid) {
        Objects.requireNonNull(bid, "Can't add null Bid!");
        if (bid.getItem() != null) {
            throw new IllegalStateException("Bid is already assigned to an Item!");
        }
        getBids().add(bid);
        bid.setItem(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
