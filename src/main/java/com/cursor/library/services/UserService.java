package com.cursor.library.services;

import com.cursor.library.daos.UserDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder encoder;
    static AccessDeniedException ACCESS_DENIED = new AccessDeniedException("Access denied");

    public UserService(UserDao userDao, @Lazy BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public UserDetails login(String userName, String password) throws AccessDeniedException {
        var user = userDao.findByUserName(userName).orElseThrow(() -> ACCESS_DENIED);
        if (!encoder.matches(password, user.getPassword())) throw ACCESS_DENIED;
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUserName(username).orElseThrow();
    }
}
