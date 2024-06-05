package com.ahmedM.mylibrary.Authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;

    public List<Authors> getAll() {
        return authorsRepository.findAll();
    }

    public Optional<Authors> getById(int id) {
        return authorsRepository.findById(id);
    }
}
