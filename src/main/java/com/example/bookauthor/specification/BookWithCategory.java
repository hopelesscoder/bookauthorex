package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookWithCategory implements Specification<Book> {

    private String category;

    public BookWithCategory(String category) {
        this.category = category;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (category == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.like(root.get("categories"), "%"+this.category +"%");
    }
}
