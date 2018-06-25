// $(document).ready(function () {
//
//     $('button.send-message').click(function () {
//         sendMessage($('input.message-input'));
//     });
//
//     function sendMessage(element) {
//         var message = $(element).val();
//         var parent = element.parents('.chat-room');
//         $.ajax({
//             type: 'post',
//             dataType: "json",
//             data: {message: message, chatRoom: chatRoom},
//             url: contextPath + '/sendMessage.do'
//         }).done(function (data) {
//             var info = data;
//             console.log('\'Done block\'. The message was successfully sent. Message: ' + info);
//             if (info.responseStatus === 'OK') {
//                 $('.chat-error-status').addClass('success').html('Your message was successfully sent!');
//                 $('input.message-input').val('');
//                 addNewMessage(data.message);
//             } else {
//                 $('.chat-error-status').addClass('fail').html('Your message submission failed! Try again please.');
//             }
//         }).fail(function (data) {
//             var info = data;
//             console.log('Message submission failed. Data: ' + info);
//         })
//     }
//
//     function addNewMessage(message) {
//         var messageNode = createMessageNode(message);
//         $('.chat-text-area').append(messageNode);
//     }
//
//     function createMessageNode(message) {
//         var divWrapper = $('<div></div>');
//         divWrapper.text(message.userName + ': ' + message.message);
//         divWrapper.addClass('message-item');
//         return divWrapper;
//     }
//
//     function initMessageArea() {
//         var chatRoom = 'Chat Room 1';
//         $.ajax({
//             type: 'post',
//             dataType: "json",
//             data: {chatRoom: chatRoom},
//             url: contextPath + '/initMessages.do'
//         }).done(function (data) {
//             var info = data;
//             console.log('\'Done block\'. The messages were successfully retrieved from the server. Messages: ' + info);
//             if (info.responseStatus === 'OK') {
//                 info.messages.forEach(addNewMessage);
//             }
//         }).fail(function (data) {
//             var info = data;
//             console.log('Messages retrieving failed. Data: ' + info);
//         })
//     }
//
//     if ($('.chat').length !== 0) {
//         //We are on the page with chat
//         var parentUl = $('#chat-rooms-drop-down');
//         parentUl.find('li').click(function (event) {
//             var selectedRoomId = event.currentTarget.getAttribute('data-chat-room');
//             $('.chat-room').each(function (index, chatRoom) {
//                 var chatRoomid = chatRoom.getAttribute('data-chat-room');
//                 if (chatRoomid.localeCompare(selectedRoomId) === 0) {
//                     chatRoom.classList.remove('hidden');
//                 } else {
//                     chatRoom.classList.add('hidden');
//                 }
//             })
//         });
//     }
// });