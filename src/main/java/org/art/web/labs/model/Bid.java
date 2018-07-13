package org.art.web.labs.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "BIDS")
public class Bid {

    public Bid() {}

    public Bid(Item item, String type) {
        this.item = item;
        this.type = type;
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
                    )
            }
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User owner;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", item=" + item +
                '}';
    }
}
