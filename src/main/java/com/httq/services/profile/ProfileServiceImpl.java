package com.httq.services.profile;

import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    ProfileRepository repository;

    @Override
    public Iterable<UserInfo> findAll() {
        return null;
    }

    @Override
    public void save(UserInfo entity) {

    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

    }
}
