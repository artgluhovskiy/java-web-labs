$(document).ready(function () {

    $('button.send-message').click(function (event) {
        var btnElement = event.target;
        sendMessage(btnElement);
    });

    function sendMessage(btnElement) {
        var parentRoomEl = $(btnElement).parents('.chat-room'),
            chatRoomId = parentRoomEl.attr('data-chat-room'),
            textAreaEl = parentRoomEl.find('.chat-text-area'),
            inputElement = $(btnElement).siblings('input[type="text"]'),
            errorArea = $(btnElement).siblings('.chat-error-status'),
            message = $(inputElement).val();
        if (!chatRoomId) {
            chatRoomId = '';
        }
        $.ajax({
            type: 'post',
            dataType: "json",
            data: {message: message, chatRoom: chatRoomId},
            url: contextPath + '/sendMessage.do'
        }).done(function (data) {
            var info = data;
            console.log('\'Done block\'. The message was successfully sent. Message: ' + info);
            if (info.responseStatus === 'OK') {
                $(errorArea).addClass('success').html('Your message was successfully sent!');
                $(inputElement).val('');
                addNewMessage(data.message, textAreaEl);
            } else {
                $(errorArea).addClass('fail').html('Your message submission failed! Try again please.');
            }
        }).fail(function (data) {
            console.log('Message submission failed. Data: ' + data);
        })
    }

    function addNewMessage(message, textAreaEl) {
        var messageNode = createMessageNode(message);
        $(textAreaEl).append(messageNode);
    }

    function createMessageNode(message) {
        var divWrapper = $('<div></div>');
        divWrapper.text(message.userName + ' >>   ' + message.message);
        divWrapper.addClass('message-item');
        return divWrapper;
    }

    function initMessageArea() {
        var chatRoom = 'Chat Room 1';
        $.ajax({
            type: 'post',
            dataType: "json",
            data: {chatRoom: chatRoom},
            url: contextPath + '/initMessages.do'
        }).done(function (data) {
            var info = data;
            console.log('\'Done block\'. The messages were successfully retrieved from the server. Messages: ' + info);
            if (info.responseStatus === 'OK') {
                info.messages.forEach(addNewMessage);
            }
        }).fail(function (data) {
            var info = data;
            console.log('Messages retrieving failed. Data: ' + info);
        })
    }

    var parentUl = $('#chat-rooms-drop-down');
    parentUl.find('li').click(function (event) {
        var selectedRoomId = event.currentTarget.getAttribute('data-chat-room');
        $('.chat-room').each(function (index, chatRoom) {
            var chatRoomid = chatRoom.getAttribute('data-chat-room');
            if (chatRoomid.localeCompare(selectedRoomId) === 0) {
                chatRoom.classList.remove('hidden');
            } else {
                chatRoom.classList.add('hidden');
            }
        })
    });
});