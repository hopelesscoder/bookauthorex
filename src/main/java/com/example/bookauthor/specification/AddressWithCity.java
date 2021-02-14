package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AddressWithCity implements Specification<Book> {

    private String city;

    public AddressWithCity(String city) {
        this.city = city;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (city == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.like(root.get("city"), "%"+this.city +"%");
    }
}
