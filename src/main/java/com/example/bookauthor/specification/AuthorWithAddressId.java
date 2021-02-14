package com.example.bookauthor.specification;

import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuthorWithAddressId implements Specification<Book> {

    private Integer addressId;

    public AuthorWithAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (addressId == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.equal(root.get("address").get("id"), this.addressId);
    }
}
