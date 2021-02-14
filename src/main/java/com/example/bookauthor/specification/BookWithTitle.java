package com.example.bookauthor.specification;

import com.example.bookauthor.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookWithTitle implements Specification<Book> {

    private String title;

    public BookWithTitle(String title) {
        this.title = title;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (title == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.like(root.get("title"), "%"+this.title+"%");
    }
}
