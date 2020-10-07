package com.httq.services.user;

import com.httq.config.JwtTokenProvider;
import com.httq.dto.user.UserResponseDTO;
import com.httq.exception.CustomException;
import com.httq.model.Role;
import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.repository.UserInfoRepository;
import com.httq.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private static final int MAX_ATTEMPTS = 6;

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;


	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void save(User users) {
		userRepository.save(users);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void resetFailAttempts(String email) {
		Optional<User> user = findByEmail(email);
		if (user.isPresent()) {
			User users = user.get();
			users.setAttempts(0);
			users.setAccountNonLocked(true);
			save(users);
		}
	}

	@Override
	public void updateFailAttempts(String email) throws LockedException {
		Optional<User> user = findByEmail(email);
		if (user.isPresent()) {
			User users    = user.get();
			int  attempts = users.getAttempts();
			users.setAttempts(++attempts);

			if (attempts >= MAX_ATTEMPTS) {
				users.setAccountNonLocked(false);
			}

			save(users);

			if (attempts >= MAX_ATTEMPTS) {
				throw new LockedException("User Account is locked!");
			}
		}
	}

	public String signin(String email, String password) throws CustomException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			Optional<User> optionalUser = userRepository.findByEmail(email);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				return jwtTokenProvider.createToken(email, user.getRoles());
			}
		} catch (AuthenticationException ignored) {
		}
		throw new CustomException("Invalid email/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	public String signup(User user) throws CustomException {
		if (!userRepository.existsByEmail(user.getEmail())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			user.setRoles(Collections.singletonList(Role.ROLE_USER));

			user.setEnabled(true);
			user.setAccountNonLocked(true);
			user.setAccountNonExpired(true);
			user.setCredentialsNonExpired(true);

			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public User search(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (!user.isPresent()) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user.get();
	}

	public UserResponseDTO getInfo(User user) {
		UserResponseDTO userResponse = new UserResponseDTO();
		userResponse.setId(user.getId());
		userResponse.setEmail(user.getEmail());
		userResponse.setRoles(user.getRoles());

		Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUser(user);
		if (optionalUserInfo.isPresent()) {
			UserInfo userInfo = optionalUserInfo.get();
			userResponse.setLastName(userInfo.getLastName());
			userResponse.setFirstName(userInfo.getFirstName());
			userResponse.setAddress(userInfo.getAddress());
			userResponse.setPhone(userInfo.getPhone());
		}
		return userResponse;
	}

	public UserResponseDTO myInfo(HttpServletRequest req) {
		User user = userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)))
		                          .get();
		return this.getInfo(user);
	}

	public UserResponseDTO getInfoById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.map(this::getInfo)
		                   .orElse(null);
	}

	public String refresh(String email) throws CustomException {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return jwtTokenProvider.createToken(email, user.getRoles());
		}
		throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
	}
}
