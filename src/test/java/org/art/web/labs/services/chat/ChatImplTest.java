package org.art.web.labs.services.chat;

import org.art.web.labs.model.chat.Role;
import org.art.web.labs.model.chat.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChatImplTest {

    static ChatImpl chat;

    @BeforeAll
    static void initAll() {
        chat = new ChatImpl();
        chat.initChatImpl();
    }

    @Test
    @DisplayName("User subscription test")
    void test1() {
        User user1 = new User(1L, "Alex", "Pupkin", "login1", LocalDateTime.now().toString(), Role.USER);
        User user2 = new User(2L, "Peter", "Slavskiy", "login2", LocalDateTime.now().toString(), Role.ADMIN);
        int initUserNum = chat.getSubscribedUsersNumber();
        chat.subscribe(user1, 1L);
        chat.subscribe(user2, 2L);
        assertEquals(initUserNum + 2, chat.getSubscribedUsersNumber());
    }

    @AfterAll
    static void tearDownAll() {

    }
}
