package com.ahmedM.mylibrary.Authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;

    public List<Authors> getAll() {
        return authorsRepository.findAll();
    }
}
