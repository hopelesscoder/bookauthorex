package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookWithIsbn implements Specification<Book> {

    private Integer isbn;

    public BookWithIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (isbn == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.equal(root.get("isbn"), this.isbn);
    }
}
