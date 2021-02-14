package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookWithPublished implements Specification<Book> {

    private Boolean published;

    public BookWithPublished(Boolean published) {
        this.published = published;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (published == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.equal(root.get("published"), this.published);
    }
}
