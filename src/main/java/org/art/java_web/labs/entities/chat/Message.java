package org.art.java_web.labs.entities.chat;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Message {

    private String userName;
    private String chatRoom;
    private String date;
    private String message;
}
