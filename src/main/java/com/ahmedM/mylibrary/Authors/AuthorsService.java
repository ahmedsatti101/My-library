package com.ahmedM.mylibrary.Authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;

    public List<Authors> getAll(int limit, int p) {
        PageRequest pageRequest = PageRequest.of(p, limit);
        Page<Authors> authorsPage = authorsRepository.findAll(pageRequest);
        return authorsPage.getContent();
    }

    public Optional<Authors> getById(int id) {
        return authorsRepository.findById(id);
    }
}
