package com.nassreml.crm.customer;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Customer")
@Table(name = "customers")
public class Customer {

    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "customer_sequence"
    )
    private Long id;
    private String firstName;
    private String surname;
    private String photo;
    private String whoCreate;
    private String whoWasTheLastToModify;

    public Customer() {
    }

    public Customer(final String firstName,
                    final String surname,
                    final String photo,
                    final String whoCreate,
                    final String  whoWasTheLastToModify) {
        this.firstName = firstName;
        this.surname = surname;
        this.photo = photo;
        this.whoCreate = whoCreate;
        this.whoWasTheLastToModify = whoWasTheLastToModify;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoto() {
        return photo;
    }

    public String getWhoCreate() {
        return whoCreate;
    }

    public String getWhoWasTheLastToModify() {
        return whoWasTheLastToModify;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setWhoCreate(String whoCreate) {
        this.whoCreate = whoCreate;
    }

    public void setWhoWasTheLastToModify(String whoWasTheLastToModify) {
        this.whoWasTheLastToModify = whoWasTheLastToModify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId()) &&
                Objects.equals(getFirstName(), customer.getFirstName()) &&
                Objects.equals(getSurname(), customer.getSurname()) &&
                Objects.equals(getPhoto(), customer.getPhoto()) &&
                Objects.equals(getWhoCreate(), customer.getWhoCreate()) &&
                Objects.equals(getWhoWasTheLastToModify(), customer.getWhoWasTheLastToModify());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getSurname(), getPhoto(), getWhoCreate(), getWhoWasTheLastToModify());
    }
}
