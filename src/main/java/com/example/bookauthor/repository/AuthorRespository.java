package com.example.bookauthor.repository;

import com.example.bookauthor.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRespository extends JpaRepository<Author, Integer> {
}