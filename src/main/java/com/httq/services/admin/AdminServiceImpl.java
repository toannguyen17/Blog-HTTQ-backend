package com.httq.services.admin;

import com.httq.dto.user.UserDetailDTO;
import com.httq.model.Role;
import com.httq.model.Tag;
import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.repository.TagRepository;
import com.httq.repository.UserInfoRepository;
import com.httq.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UsersRepository    usersRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private TagRepository      tagRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String DEFAULT_PASSWORD = "123456";

    @Override
    public List<UserDetailDTO> findAllUser() {
        Iterable<User>      users = usersRepository.findAll();
        List<UserDetailDTO> list  = new ArrayList<>();
        for (User u : users) {
            Optional<UserInfo> ui = userInfoRepository.findByUser(u);
            UserDetailDTO      ud = new UserDetailDTO();
            ud.setId(u.getId());
            ud.setRoles(u.getRoles());
            ud.setAccountNonExpired(u.isAccountNonExpired());
            ud.setAccountNonLocked(u.isAccountNonLocked());
            ud.setAttempts(u.getAttempts());
            ud.setCreatedAt(u.getCreatedAt());
            ud.setUpdatedAt(u.getUpdatedAt());
            ud.setEnabled(u.isEnabled());
            ud.setEmail(u.getEmail());
            ud.setCredentialsNonExpired(u.isCredentialsNonExpired());
            if (ui.isPresent()) {
                UserInfo userInfo = ui.get();
                ud.setFirstName(userInfo.getFirstName());
                ud.setLastName(userInfo.getLastName());
                ud.setAddress(userInfo.getAddress());
                ud.setGender(userInfo.getGender());
                ud.setPhone(userInfo.getPhone());
                ud.setAvatar(userInfo.getAvatar());
            }
            list.add(ud);
        }

        return list;
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> user = usersRepository.findById(id);
        if (user.isPresent()) {
            userInfoRepository.deleteByUser(user.get());
            usersRepository.deleteById(id);
        }
    }

    @Override
    public void deleteUser(UserDetailDTO userDetailDTO) {
        Optional<User> u = usersRepository.findById(userDetailDTO.getId());
        if (u.isPresent()) {
            User user = u.get();
            userInfoRepository.deleteByUser(user);
            usersRepository.delete(user);
        }
    }


    @Override
    public UserDetailDTO createUser(UserDetailDTO userDetailDTO) {
        User user = new User();
        setUser(userDetailDTO, user);
        usersRepository.save(user);
        UserInfo userInfo = new UserInfo();
        setUserInfo(userDetailDTO, user, userInfo);
        userInfoRepository.save(userInfo);
        return userDetailDTO;
    }

    @Override
    public UserDetailDTO updateUser(UserDetailDTO userDetailDTO) {
        Optional<User> u = usersRepository.findById(userDetailDTO.getId());
        if (u.isPresent()) {
            User user = u.get();
            setUser(userDetailDTO, user);
            usersRepository.save(user);

            Optional<UserInfo> ui = userInfoRepository.findByUser(user);
            UserInfo           userInfo;

            userInfo = ui.orElseGet(UserInfo::new);
            setUserInfo(userDetailDTO, user, userInfo);
            userInfoRepository.save(userInfo);
        }

        return userDetailDTO;
    }

    private void setUser(UserDetailDTO userDetailDTO, User user) {
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setRoles(Collections.singletonList(Role.ROLE_USER));
        user.setEnabled(userDetailDTO.isEnabled());
        user.setAccountNonLocked(userDetailDTO.isAccountNonLocked());
        user.setAccountNonExpired(userDetailDTO.isAccountNonExpired());
        user.setCredentialsNonExpired(userDetailDTO.isCredentialsNonExpired());

    }

    private void setUserInfo(UserDetailDTO userDetailDTO, User user, UserInfo userInfo) {
        userInfo.setAddress(userDetailDTO.getAddress());
        userInfo.setAvatar(userDetailDTO.getAvatar());
        userInfo.setFirstName(userDetailDTO.getFirstName());
        userInfo.setLastName(userDetailDTO.getLastName());
        userInfo.setAddress(userDetailDTO.getAddress());
        userInfo.setGender(userDetailDTO.getGender());
        userInfo.setPhone(userDetailDTO.getPhone());
        userInfo.setUser(user);

    }

    @Override
    public Iterable<UserDetailDTO> searchUserByAny(String key) {
        return null;
    }

    @Override
    public UserDetailDTO blockUser(UserDetailDTO userDetailDTO) {
        Optional<User> u = usersRepository.findById(userDetailDTO.getId());
        if (u.isPresent()) {
            User user = u.get();
            user.setEnabled(false);
            usersRepository.save(user);
        }
        return userDetailDTO;
    }

    @Override
    public UserDetailDTO unblockUser(UserDetailDTO userDetailDTO) {
        Optional<User> u = usersRepository.findById(userDetailDTO.getId());
        if (u.isPresent()) {
            User user = u.get();
            user.setEnabled(true);
            usersRepository.save(user);
        }
        return userDetailDTO;
    }

    @Override
    public UserDetailDTO findUserById(Long id) {
        Optional<User> u = usersRepository.findById(id);
        if (u.isPresent()) {
            UserDetailDTO userDetailDTO = new UserDetailDTO();
            User          user          = u.get();
            userDetailDTO.setId(user.getId());
            setUser(userDetailDTO, user);

            Optional<UserInfo> ui = userInfoRepository.findByUser(user);
            if (ui.isPresent()) {
                UserInfo userInfo = ui.get();
                setUserInfo(userDetailDTO, user, userInfo);
            } else {
                setUserInfo(userDetailDTO, user, new UserInfo());
            }
            return userDetailDTO;
        }
        return null;
    }

    @Override
    public String resetPassword(Long id) {
        Optional<User> u = usersRepository.findById(id);
        if (u.isPresent()) {
            User user = u.get();
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            usersRepository.save(user);
        }
        return "OK";
    }

    @Override
    public Iterable<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Iterable<Tag> findAllTagsContains(String key) {
        return null;
    }

    @Override
    public Optional<Tag> findOne(String key) {
        return Optional.empty();
    }

    @Override
    public Tag createTag(String tag) {
        return null;
    }

    @Override
    public void deleteTag(String tag) {

    }

    @Override
    public void deleteTag(Tag tag) {

    }
}
