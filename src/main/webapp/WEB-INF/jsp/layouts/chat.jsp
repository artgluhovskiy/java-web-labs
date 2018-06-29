<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container item-container chat">
    <h3 class="item-header">Simple Chat App</h3>
    <div class="dropdown chat-rooms">
        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
            Chat rooms
            <span class="caret"></span>
        </button>
        <ul id="chat-rooms-drop-down" class="dropdown-menu">
            <li data-chat-room-id="chat-room-1" data-chat-room="chat-room-1"><a href="#">Chat room 1</a></li>
            <li data-chat-room-id="chat-room-2" data-chat-room="chat-room-2"><a href="#">Chat room 2</a></li>
        </ul>
    </div>

    <div data-chat-room="chat-room-1" class="row hidden chat-room">
        <div class="col message-input-block">
            <form>
                Enter your message: <input class="message-input" type="text" name="message">
                <div class="chat-error-status"></div>
                <button type="button" class="btn btn-primary btn-md send-message">Submit</button>
            </form>
        </div>
        <div class="col">
            <div class="chat-text-area">
                <div class="chat-room-header">Chat room 1:</div>
            </div>
        </div>
    </div>

    <div data-chat-room="chat-room-2" class="row hidden chat-room">
        <div class="col message-input-block">
            <form>
                Enter your message: <input class="message-input" type="text" name="message">
                <div class="chat-error-status"></div>
                <button type="button" class="btn btn-primary btn-md send-message">Submit</button>
            </form>
        </div>
        <div class="col">
            <div class="chat-text-area">
                <div class="chat-room-header">Chat room 2:</div>
            </div>
        </div>
    </div>
</div>
