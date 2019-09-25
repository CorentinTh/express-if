const view1 = document.getElementById('view1');
const view2 = document.getElementById('view2');
const view3 = document.getElementById('view3');
const loginFormEl = document.getElementById('login-form');
const roomListEl = document.getElementById('login-form');

console.log('JS working');

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
    displayView(2);

    return false;
};

function displayView(id) {
    view1.className = 'pos' + (1 - id);
    view2.className = 'pos' + (2 - id);
    view3.className = 'pos' + (3 - id);
}

function joinRoom(roomName){
    console.log('Joining room', roomName);
    console.log(window.javaConnector.onJoinRoom(roomName));
}

function addRoomList(roomList){
    roomListEl.insertAdjacentHTML('afterend', `
                <div class="room" onclick="joinRoom('room-1')">
                    <div class="img"><img src="chat.svg" alt=""></div>
                    <div>
                        <div class="name">room 1</div>
                        <div class="meta">4 persons</div>
                    </div>
                </div>`);

}

console.log('e');
// displayView(2);