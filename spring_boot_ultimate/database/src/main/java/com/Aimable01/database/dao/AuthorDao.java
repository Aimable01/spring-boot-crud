package com.Aimable01.database.dao;

import com.Aimable01.database.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(long id);
}