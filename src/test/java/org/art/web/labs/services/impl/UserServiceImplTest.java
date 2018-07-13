package org.art.web.labs.services.impl;

import org.art.web.labs.dao.UserDao;
import org.art.web.labs.dao.exceptions.DAOSystemException;
import org.art.web.labs.model.chat.Role;
import org.art.web.labs.model.chat.User;
import org.art.web.labs.services.UserService;
import org.art.web.labs.services.exceptions.ServiceBusinessException;
import org.art.web.labs.services.exceptions.ServiceSystemException;
import org.art.web.labs.utils.ClassAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.art.web.labs.services.tdd.harmcrest.StartWith.startWith;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    @DisplayName("Mock analysis via ClassAnalyzer")
    void test1() {
        ClassAnalyzer analyzer = new ClassAnalyzer();
        //Mock analysis
        analyzer.analyze(userDao);
    }

    @Test
    @DisplayName("Simple Mockito test")
    void test2() {

        @SuppressWarnings("unchecked")
        List<String> list = mock(List.class);

        when(list.get(anyInt())).thenReturn("A");
        when(list.get(eq(1))).thenReturn("B");

        ArgumentMatcher<Integer> matcher = integer -> integer % 3 == 0;
        when(list.get(intThat(matcher))).thenReturn("C");

        for (int i = 0; i < 10; i++) {
            System.out.println("list.get(" + i + "): " + list.get(i));
        }
    }

    @Test
    @DisplayName("Simple Mockito list test (with order)")
    void test3() {

        @SuppressWarnings("unchecked")
        List<String> list = mock(List.class);

        list.add("A");
        list.remove("C");
        list.add("B");

        InOrder inOrder = inOrder(list);
        inOrder.verify(list).add("A");
        inOrder.verify(list).remove("C");
        inOrder.verify(list).add("B");
        verifyNoMoreInteractions(list);
    }

    @Test
    @DisplayName("Custom harmcrest matcher test")
    void test4() {

        List<String> list1 = new ArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");

        List<String> list2 = new ArrayList<>();
        list2.add("A");
        list2.add("A");
        list2.add("C");

        assertAll(() -> assertThat(list1, startWith("A", "B")),
                  () -> assertThat(list2, startWith("A", "A")));
    }

    @Test
    @DisplayName("Simple user service Mockito test")
    void test5() throws DAOSystemException, ServiceBusinessException, ServiceSystemException {

        //Arrange stage
        String userLogin = "user_login_1";
        User user = new User(3L, "Harry", "Potter", userLogin, "22.03.18", Role.USER);

        when(userDao.getUserByLogin(userLogin)).thenReturn(user);

        //Act stage
        userService.getUserByLogin(userLogin);

        //Assert stage
        verify(userDao, times(1)).getUserByLogin(userLogin);
        verifyNoMoreInteractions(userDao);
    }
}
