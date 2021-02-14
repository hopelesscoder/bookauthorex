package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookWithYear implements Specification<Book> {

    private Integer year;

    public BookWithYear(Integer year) {
        this.year = year;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (year == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.equal(root.get("year"), this.year);
    }
}
