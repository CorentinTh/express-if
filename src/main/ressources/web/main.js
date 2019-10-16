const view1 = document.getElementById('view1');
const view2 = document.getElementById('view2');
const view3 = document.getElementById('view3');
const roomNameEl = document.getElementById('room-name');
const userListEl = document.getElementById('user-list');
const roomListEl = document.getElementById('rooms-list');
const loginFormEl = document.getElementById('login-form');
const leaveRoomEl = document.getElementById('leave-room');
const messageListEl = document.getElementById('message-list');
const messageSendEl = document.getElementById('message-send');
const messageInputEl = document.getElementById('message-input');


function displayView(id) {
    console.log('display view', id);

    view1.className = 'pos' + (1 - id);
    view2.className = 'pos' + (2 - id);
    view3.className = 'pos' + (3 - id);
}

function joinRoom(roomName) {
    console.log('Joining room', roomName);
    console.log(window.javaConnector.onJoinRoom(roomName));
}

function addRoomList(roomList) {
    roomListEl.innerHTML = '';

    for (const {name, count} of roomList) {
        roomListEl.insertAdjacentHTML('beforeend', `
                <div class="room" onclick="joinRoom('${name}')">
                    <div class="img"><img src="chat.svg" alt=""></div>
                    <div>
                        <div class="name">${name}</div>
                        <div class="meta">${count} personne${count > 1 ? 's' : ''}</div>
                    </div>
                </div>`);
    }
}

function setRoomInfo({name, history, users}) {
    roomNameEl.innerText = name;

    users.forEach(addUser);
    history.forEach(addMessage);
}

function addUser(name) {
    if(!document.querySelector(`#user-list .user[data-name="${name}"]`)){
        userListEl.insertAdjacentHTML('beforeend', `
            <div class="user" data-name="${name}">
                <div class="image"><img src="https://api.adorable.io/avatars/285/${name}.png" alt=""></div>
                <div class="pseudo">${name}</div>
            </div>`)
    }
}

function removeUser(name) {
    try {
        document.querySelector(`#user-list .user[data-name="${name}"]`).remove();
    } catch (e) {
    }
}

function addMessage({user, content, date}) {
    const d = new Date(date);
    messageListEl.insertAdjacentHTML('beforeend', `
            <div class="message">
                <div class="user"><img src="https://api.adorable.io/avatars/285/${user}.png" alt=""></div>
                <div class="content">${content}</div>
                <div class="date">${d.getHours() + ':' + `${d.getMinutes()}`.padStart(2, '0')}</div>
            </div>`);
}

function sendMessage() {
    const content = messageInputEl.value;
    messageInputEl.value = '';

    console.log(window.javaConnector.onNewMessage(content));
}

loginFormEl.onsubmit = (e) => {
    e.preventDefault();

    const firstname = document.getElementById('firstname').value;
    const lastname = document.getElementById('lastname').value;
    const url = document.getElementById('host').value;

    console.log(firstname);
    console.log(lastname);
    console.log(url);

    const [host, port] = url.split(':');
    console.log(host);
    console.log(port);

    console.log(window.javaConnector.onInit(firstname, lastname, host, parseInt(port, 10)));

    return false;
};

leaveRoomEl.onclick = () => {
    console.log('onLeave cliked');
    messageListEl.innerHTML = '';
    userListEl.innerHTML = '';
    console.log(window.javaConnector.onLeaveRoom());
};

messageInputEl.onkeyup = ev => ev.keyCode === 13 ? sendMessage() : null;
messageSendEl.onclick = sendMessage;

// displayView(3);
console.log('JS working');
