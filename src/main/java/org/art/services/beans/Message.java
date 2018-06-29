package org.art.services.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.art.entities.User;

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
