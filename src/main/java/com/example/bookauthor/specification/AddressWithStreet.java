package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AddressWithStreet implements Specification<Book> {

    private String street;

    public AddressWithStreet(String street) {
        this.street = street;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (street == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.like(root.get("street"), "%"+this.street +"%");
    }
}
