package org.art.java_web.labs.services.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_web.labs.entities.Message;
import org.art.java_web.labs.entities.Role;
import org.art.java_web.labs.entities.User;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatImpl implements Chat {

    private static final Logger LOG = LogManager.getLogger(ChatImpl.class);

    private final Map<Long, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    public void initChatImpl() {
        //Chat Rooms Initialization
        ChatRoom chatRoom1 = new ChatRoom(1L, "Chat Room 1");
        ChatRoom chatRoom2 = new ChatRoom(2L, "Chat Room 2");

        User user1 = new User(100L, "Emma", "Wotson", "login_login",
                LocalDate.now().toString(), Role.USER);
        User user2 = new User(101L, "Harry", "Potter", "login_login_login",
                LocalDate.now().toString(), Role.USER);

        chatRoom1.getUsers().add(user1);
        chatRoom2.getUsers().add(user2);

        Message message1 = new Message(user1.getFullName(), "Chat Room 1", LocalDate.now().toString(), "Hello!!!");
        Message message2 = new Message(user2.getFullName(), "Chat Room 2", LocalDate.now().toString(), "Hello!!!");

        chatRoom1.getMessages().add(message1);
        chatRoom2.getMessages().add(message2);

        chatRooms.put(1L, chatRoom1);
        chatRooms.put(2L, chatRoom2);
    }

    @Override
    public boolean subscribe(User user, Long roomId) {
        LOG.debug("In 'ChatImpl': subscribe(...). New user subscribed: " + user);
        ChatRoom chatRoom = chatRooms.get(roomId);
        if (chatRoom != null) {
            Set<User> users = chatRoom.getUsers();
            return users != null && users.add(user);
        } else {
            return false;
        }
    }

    @Override
    public boolean isUserSubscribed(User user, Long roomId) {
        ChatRoom room = chatRooms.get(roomId);
        if (room != null) {
            return room.getUsers().contains(user);
        }
        return false;
    }

    @Override
    public boolean unSubscribe(User user, Long roomId) {
        ChatRoom room = chatRooms.get(roomId);
        if (room != null) {
            return room.getUsers().remove(user);
        }
        return false;
    }

    @Override
    public void receiveMessage(User user, Long roomId, Message message) {
        LOG.debug("In 'ChatImpl': receiveMessage(...). Message received: " + message);
        if (!isUserSubscribed(user, roomId)) {
            subscribe(user, roomId);
        }
        ChatRoom room = chatRooms.get(roomId);
        if (room != null) {
            room.getMessages().add(message);
        }
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public List<Message> getMessages(User user, Long roomId) {
        List<Message> messages = Collections.emptyList();
        if (!isUserSubscribed(user, roomId)) {
            subscribe(user, roomId);
        }
        ChatRoom room = chatRooms.get(roomId);
        if (room != null) {
            messages = room.getMessages();
        }
        return messages;
    }

    @Override
    public void addChatFilter(ChatFilter chatFilter) {

    }

    public int getSubscribedUsersNumber() {
        Optional<Integer> optional = chatRooms.values()
                .stream()
                .map((chatRoom) -> chatRoom.getUsers().size())
                .reduce((a, b) -> a + b);
        if (optional.isPresent()) {
            return optional.get();
        }
        return 0;
    }

    public Set<ChatRoom> getChatRooms() {
        return new HashSet<>(chatRooms.values());
    }
}
