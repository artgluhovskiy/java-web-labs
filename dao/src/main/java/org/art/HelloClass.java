package org.art;

import org.art.dao.UserDao;
import org.art.dao.exceptions.DAOSystemException;
import org.art.entities.Role;
import org.art.entities.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class HelloClass {
    public static void main(String[] args) throws DAOSystemException {
        User user1 = new User(3L, "Josh", "Williams", "user_login", LocalDate.now().toString(), Role.USER);
        ApplicationContext context = new ClassPathXmlApplicationContext("dao.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);
        System.out.println(userDao);
        User savedUser = userDao.save(user1);
        System.out.println(savedUser);

    }
}
