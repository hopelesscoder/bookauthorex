package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AddressWithAuthorId implements Specification<Book> {

    private Integer authorId;

    public AddressWithAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (authorId == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.equal(root.get("author").get("id"), this.authorId);
    }
}
