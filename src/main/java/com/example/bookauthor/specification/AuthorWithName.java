package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuthorWithName implements Specification<Book> {

    private String name;

    public AuthorWithName(String name) {
        this.name = name;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (name == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.like(root.get("name"), "%"+this.name +"%");
    }
}
