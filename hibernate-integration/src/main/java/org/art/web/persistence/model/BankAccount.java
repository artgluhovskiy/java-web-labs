package org.art.web.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "BANK_ACCOUNT_ID")
public class BankAccount extends BillingDetails {

    public BankAccount() {
        super();
    }

    public BankAccount(User owner, String account, String bankName) {
        super(owner);
        this.account = account;
        this.bankName = bankName;
    }

    @NotNull
    @Column(name = "ACCOUNT")
    private String account;

    @NotNull
    @Column(name = "BANK_NAME")
    private String bankName;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "account='" + account + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
