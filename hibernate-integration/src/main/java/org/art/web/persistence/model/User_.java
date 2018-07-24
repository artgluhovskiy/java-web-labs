package org.art.web.persistence.model;

import org.art.web.persistence.model.*;
import org.art.web.persistence.model.enums.Role;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * User entity type static meta model.
 */
@StaticMetamodel(User.class)
public abstract class User_ {

    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, Address> homeAddress;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, BillingDetails> defaultBilling;
    public static volatile SetAttribute<User, Bid> bids;
    public static volatile SetAttribute<User, Item> items;
    public static volatile SetAttribute<User, BillingDetails> additionalBillings;
}
