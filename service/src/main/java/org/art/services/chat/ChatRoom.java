package org.art.services.chat;

import lombok.Data;
import lombok.ToString;
import org.art.entities.Message;
import org.art.entities.User;

import java.util.*;

@Data
@ToString(exclude = "messages")
public class ChatRoom {

    public ChatRoom(Long chatRoomId, String chatRoomAlias) {
        this.chatRoomId = chatRoomId;
        this.chatRoomAlias = chatRoomAlias;
    }

    private Long chatRoomId;
    private String chatRoomAlias;
    private Set<User> users = Collections.synchronizedSet(new HashSet<>());
    private List<Message> messages = Collections.synchronizedList(new LinkedList<>());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoom)) return false;

        ChatRoom chatRoom = (ChatRoom) o;

        return chatRoomId != null ? chatRoomId.equals(chatRoom.chatRoomId) : chatRoom.chatRoomId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatRoomId != null ? chatRoomId.hashCode() : 0);
        return result;
    }
}
