package com.httq.services.userinfo;

import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.services.IGeneralService;

import java.util.Optional;

public interface UserInfoService extends IGeneralService<UserInfo> {
	Optional<UserInfo> findByUser(User user);
}
