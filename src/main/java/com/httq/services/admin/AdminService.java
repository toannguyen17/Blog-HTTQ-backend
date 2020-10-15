package com.httq.services.admin;

import com.httq.dto.report.ReportDTO;
import com.httq.dto.user.UserDetailDTO;
import com.httq.model.Tag;
import com.httq.model.User;

import java.util.Optional;

public interface AdminService {
    Iterable<UserDetailDTO> findAllUser();
    UserDetailDTO findById(Long id);
    void deleteUserById(Long id);
    void deleteUser(UserDetailDTO userDetailDTO);
    UserDetailDTO createUser(UserDetailDTO userDetailDTO);
    Iterable<UserDetailDTO> searchUserByAny(String key);
    UserDetailDTO blockUser(UserDetailDTO userDetailDTO);
    UserDetailDTO unblockUser(UserDetailDTO userDetailDTO);
    UserDetailDTO findUserById(Long id);
    String resetPassword(Long id);

    Iterable<Tag> findAllTags();
    Iterable<Tag> findAllTagsContains(String key);
    Optional<Tag> findOne (String key);
    Tag createTag(String tag);
    void deleteTag(String tag);
    void deleteTag(Tag tag);

    ReportDTO getReport();

}
