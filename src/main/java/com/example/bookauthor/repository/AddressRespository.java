package com.example.bookauthor.repository;

import com.example.bookauthor.model.Address;
import com.example.bookauthor.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRespository extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {
}