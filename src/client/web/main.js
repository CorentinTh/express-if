const view1 = document.getElementById('view1');
const view2 = document.getElementById('view2');
const view3 = document.getElementById('view3');
const loginForm = document.getElementById('login-form');

console.log('JS working');

loginForm.onsubmit = (e) => {
    e.preventDefault();

    const firstname = document.getElementById('firstname').value;
    const lastname = document.getElementById('lastname').value;

    console.log(firstname);
    console.log(lastname);

    console.log(window.javaConnector.onLogin(firstname, lastname));

    return false;
};

function displayView(id) {
    view1.className = 'pos' + (1 - id);
    view2.className = 'pos' + (2 - id);
    view3.className = 'pos' + (3 - id);
}

// displayView(2)