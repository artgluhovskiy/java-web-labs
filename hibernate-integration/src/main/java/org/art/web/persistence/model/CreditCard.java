package org.art.web.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "CREDIT_CARD_ID")
public class CreditCard extends BillingDetails {

    public CreditCard() {
        super();
    }

    public CreditCard(User owner, String cardNumber, LocalDate expMonth) {
        super(owner);
        this.cardNumber = cardNumber;
        this.expMonth = expMonth;
    }

    @NotNull
    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @NotNull
    @Column(name = "EXP_MONTH")
    private LocalDate expMonth;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(LocalDate expMonth) {
        this.expMonth = expMonth;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expMonth=" + expMonth +
                '}';
    }
}
