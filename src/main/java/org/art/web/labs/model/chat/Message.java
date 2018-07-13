package org.art.web.labs.model.chat;

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
