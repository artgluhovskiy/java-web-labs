package org.art.web.persistence.model;

import org.art.web.persistence.model.enums.Role;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraph(
        name = User.ENTITY_GRAPH_USER_ITEMS,
        attributeNodes = {
                @NamedAttributeNode("items")
        }
)
@FetchProfiles(
        @FetchProfile(name = User.PROFILE_USER_BIDS,
        fetchOverrides = @FetchProfile.FetchOverride(
                entity = User.class,
                association = "bids",
                mode = FetchMode.JOIN
        ))
)
@Cacheable
@Cache(
        usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
        region = "org.art.web.chat.model.User"
)
@DynamicUpdate
@Table(name = "USERS")
public class User {

    public static final String PROFILE_USER_BIDS = "UserBids";
    public static final String ENTITY_GRAPH_USER_ITEMS = "UserItems";

    public User() {
    }

    public User(String firstName, String lastName, Address homeAddress, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.role = role;
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "ZIP")),
            @AttributeOverride(name = "city", column = @Column(name = "CITY"))
    })
    private Address homeAddress;

    @CreationTimestamp
    @Column(updatable = false, name = "REG_DATE")
    private Date regDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_BILLING_ID")
    private BillingDetails defaultBilling;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<BillingDetails> additionalBillings = new HashSet<>();

    @OneToMany(
            mappedBy = "owner",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<Bid> bids = new HashSet<>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.PERSIST)
    private Set<Item> items = new HashSet<>();

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

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Date getRegDate() {
        return regDate;
    }

    public Role getRole() {
        return role;
    }

    public BillingDetails getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(BillingDetails defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public void setAdditionalBillings(Set<BillingDetails> additionalBillings) {
        this.additionalBillings = additionalBillings;
    }

    public Set<BillingDetails> getAdditionalBillings() {
        return additionalBillings;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", homeAddress=" + homeAddress +
                ", regDate=" + regDate +
                ", role=" + role +
                ", defaultBilling=" + defaultBilling +
                ", additionalBillings=" + additionalBillings +
                ", bids=" + bids +
                '}';
    }
}
