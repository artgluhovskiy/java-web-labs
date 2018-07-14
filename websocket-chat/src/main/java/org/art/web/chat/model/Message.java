package org.art.web.chat.model;

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
