package com.cursor.library;

import com.cursor.library.daos.UserDao;
import com.cursor.library.models.User;
import com.cursor.library.models.UserPermission;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Set;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class LibraryApplication {
    private final UserDao userDao;
    private final BCryptPasswordEncoder encoder;

    public LibraryApplication(UserDao userDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }


    @PostConstruct
    public void addUsers() {
        var user1 = new User();
        user1.setUserName("Igor");
        user1.setPassword(encoder.encode("pass"));
        user1.setPermissions(Set.of(UserPermission.ROLE_READ, UserPermission.ROLE_WRITE));
        userDao.save(user1);
        var user2 = new User();
        user2.setUserName("Ivan");
        user2.setPassword(encoder.encode("word"));
        user2.setPermissions(Set.of(UserPermission.ROLE_ADMIN));
        userDao.save(user2);
    }
}