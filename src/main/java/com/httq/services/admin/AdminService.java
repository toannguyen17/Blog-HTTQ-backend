package com.httq.services.admin;

import com.httq.dto.user.UserDetailDTO;
import com.httq.model.Tag;
import com.httq.model.User;

import java.util.Optional;

public interface AdminService {
    Iterable<UserDetailDTO> findAllUser();
    void deleteUserById(Long id);
    void deleteUser(UserDetailDTO userDetailDTO);
    UserDetailDTO createUser(UserDetailDTO userDetailDTO);
    UserDetailDTO updateUser(UserDetailDTO userDetailDTO);
    Iterable<UserDetailDTO> searchUserByAny(String key);

    Iterable<Tag> findAllTags();
    Iterable<Tag> findAllTagsContains(String key);
    Optional<Tag> findOne (String key);
    Tag createTag(String tag);
    void deleteTag(String tag);
    void deleteTag(Tag tag);

}
