package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuthorWithTelephone implements Specification<Book> {

    private Integer telephone;

    public AuthorWithTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (telephone == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.equal(root.get("telephone"), this.telephone);
    }
}
