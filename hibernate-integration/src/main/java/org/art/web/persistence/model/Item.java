package org.art.web.persistence.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    @GeneratedValue(generator = "ADVANCED_SEQUENCE")
    @GenericGenerator(
            name = "ADVANCED_SEQUENCE",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(
                            name = "sequence_name",
                            value = "DOMAIN_TEST_SEQUENCE"
                    )
            }
    )
    @Column(name = "ID")
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "IMAGES",
            joinColumns = @JoinColumn(name = "IMAGE_ID")
    )
    @AttributeOverride(
            name = "fileName",
            column = @Column(
                    name = "FILENAME",
                    nullable = false
            )
    )
    @Column(name = "FILENAME")
    private Set<Image> images = new HashSet<>();

    @OneToMany(
            mappedBy = "item",
            cascade = CascadeType.PERSIST
    )
    private Set<Bid> bids = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "ITEM_BUYER",
            joinColumns = @JoinColumn(name = "ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "BUYER_ID", nullable = false)
    )
    private User buyer;

    public Set<Bid> getBids() {
        return bids;
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

    public Set<Image> getImages() {
        return images;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", images=" + images +
                '}';
    }
}
