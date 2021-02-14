package com.example.bookauthor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"address_id"})})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private int telephone;

    @OneToMany(mappedBy = "author")
    //@JoinColumn(name="book_id", referencedColumnName="id")
    @JsonIgnoreProperties("author")
    List<Book> books;

    @ElementCollection
    private List<String> comments;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="address_id", referencedColumnName = "id")
    @JsonIgnoreProperties("author")
    private Address address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
