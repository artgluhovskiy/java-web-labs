package org.art.java_web.labs.services.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.art.java_web.labs.entities.chat.User;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String messageText;
    private String creationDate;
    private User from;
    private User to;
}
