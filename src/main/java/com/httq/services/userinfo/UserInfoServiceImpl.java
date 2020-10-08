package com.httq.services.userinfo;

import com.httq.model.UserInfo;
import com.httq.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Override
    public Iterable<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public void save(UserInfo entity) {
        userInfoRepository.save(entity);
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userInfoRepository.deleteById(id);
    }
}
