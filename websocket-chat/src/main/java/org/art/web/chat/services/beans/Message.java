package org.art.web.chat.services.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.art.web.chat.model.User;

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
