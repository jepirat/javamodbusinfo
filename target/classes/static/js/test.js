const url = '/users';
let users = []
let buttonsIdsEdit = []
let buttonsIdsDelete = []

class User {
    id
    login
    firstName
    lastName
    password
    roles = []

    constructor(id, login, firstName, lastName, password, roles) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }
};

async function sendRequest(url) {
    let userRoles = []
    let row = ['id', 'login', 'firstName', 'lastName', 'password', 'authorities']
    let response = await fetch(url)
    let js = await response.json()

    for (let j = 0; j < js.length; j++) {
        let fields = []
        for (let i = 0; i < row.length; i++) {
            fields.push(js[j][row[i]])
        }

        for (let k = 0; k < js[j]['authorities'].length; k++) {
            userRoles.push(fields[fields.length - 1][k]['name'])
        }

        let user = new User()
        user.id = fields[0]
        user.login = fields[1]
        user.firstName = fields[2]
        user.lastName = fields[3]
        user.password = fields[4]
        user.roles = userRoles
        users.push(user)
        userRoles = []
    }
    setRowsAndButtons()
    scanUsers()
}
sendRequest(url)

const timer = ms => new Promise(res => setTimeout(res, ms))

async function scanUsers() {
    await sendRequest
    let userLength = users.length
    while (true) {
        if (userLength != users.length) {
            userLength = users.length
            $('#usersTable > tbody tr').remove();
            setRowsAndButtons();
        }
        await timer(1000)
    }
}

async function setRowsAndButtons() {
    await sendRequest
    let row = ['id', 'login', 'firstName', 'lastName', 'password', 'roles']
    let rowLength = row.length
    let usersLength = users.length

    for (let j = 0; j < usersLength; j++) {
        $('#usersTableBody:last-child').append(`<tr id="tr${j}"></tr>`);
        for (let i = 0; i < rowLength; i++) {
            $('#usersTableBody tr:last-child').append(`<td id="td${i}">${users[j][row[i]]}</td>`)
        }
        $('#usersTable > tbody tr:last-child')
            .append(`<td><button onclick="modalValues(this)" id="edit${j}" type="button" class="btn btn-primary editColor" data-toggle="modal" href="#tm">Edit</button></td>`)
        $('#usersTable > tbody tr:last-child')
            .append(`<td><button onclick="modalValues1(this)" id="delete${j}" type="button" class="btn btn-primary deleteColor" data-toggle="modal" href="#tm1">DELETE</button></td>`)

        buttonsIdsEdit.push('edit' + j)
        buttonsIdsDelete.push('delete' + j)
    }
}

async function modalValues(button) {
    await sendRequest;
    let buttonId = button
    let user = await users[await getIndex(buttonId, buttonsIdsEdit)];
    document.querySelector('#id').value =  user['id'];
    document.querySelector('#login').value =  user['login'];
    document.querySelector('#FirstName').value = user['firstName'];
    document.querySelector('#LastName').value =  user['lastName'];
    document.querySelector('#password').value = user['password'];
}

async function modalValues1(button) {
    await sendRequest;
    let buttonId = button
    let user = await users[await getIndex(buttonId, buttonsIdsDelete)];
    document.querySelector('#id1').value =  user['id'];
    document.querySelector('#login1').value =  user['login'];
    document.querySelector('#FirstName1').value = user['firstName'];
    document.querySelector('#LastName1').value =  user['lastName'];
    document.querySelector('#password1').value = user['password'];
}

async function editModal() {
    let userRespRoles = []
    let roles = []
    let role
    if (document.querySelector('input[name="r1"]:checked').value !== '' && document.querySelector('input[name="r1"]:checked').value !== null) {
        role =  document.querySelector('input[name="r1"]:checked').value;
    }

    if (role != null) {
        roles.push(role)
    }
    let user = new User()
    user.id = document.querySelector('#id').value
    user.login = document.querySelector('#login').value
    user.firstName = document.querySelector('#FirstName').value
    user.lastName = document.querySelector('#LastName').value
    user.password = document.querySelector('#password').value
    user.roles = roles

    let userFromUsers = await getUserFromUsers(user.id)
    let rolesFromUserFromArr = await userFromUsers['roles']

    if (role !== 'USER') {
        for (let f = 0; f < rolesFromUserFromArr.length; f++) {
            if (rolesFromUserFromArr[f] !== role) {
                roles.push(rolesFromUserFromArr[f])
            }
        }
    }

    let us2 = await fetch('/users', {
        method: "PUT",
        body: JSON.stringify(user),
        headers: {"Content-Type": "application/json; charset=utf8"}
    });
    let us3 = await us2.json()
    let userLength = users.length
    let userResp = new User()
    for (let a = 0; a < us3['roles'].length; a++) {
         userRespRoles.push(us3['authorities'][a]['name'])
    }

    userResp.id = us3['id']
    userResp.login = us3.login
    userResp.firstName = us3.firstName
    userResp.lastName = us3.lastName;
    userResp.password = us3.password
    userResp.roles = userRespRoles
    for (let i = 0; i < userLength; i++) {
        if (users[i].id === us3['id']) {
            users[i] = userResp
        }
    }
    $('#usersTable > tbody tr').remove();
    await setRowsAndButtons()
    $('#tm').modal('hide');
}


async function deleteUser() {
    let newUsers = []
    let usersLength = users.length
   let userId = document.querySelector('#id1').value
    let url = `/users/${userId}`
    let id = await fetch(url, {method: 'DELETE'})
    let id1 = await id.json()

    for (let i = 0; i < usersLength; i++) {
        if (users[i].id === id1) {
            continue
        }
        newUsers.push(users[i])
    }
    users = newUsers
    buttonsIdsEdit = []
    buttonsIdsDelete = []
    $('#tm1').modal('hide');
}

async function newUser() {
    let userRespRoles = []
    let roles = ['USER']
    let user = new User()
    user.id = document.querySelector('#idN').value
    user.login = document.querySelector('#loginN').value
    user.firstName = document.querySelector('#FirstNameN').value
    user.lastName = document.querySelector('#LastNameN').value
    user.password = document.querySelector('#passwordN').value
    user.roles = roles
    let us2 = await fetch('/users', {
        method: "POST",
        body: JSON.stringify(user),
        headers: {"Content-Type": "application/json; charset=utf8"}
    });
    let us3 = await us2.json()
    let userResp = new User()

    for (let a = 0; a < us3['roles'].length; a++) {
        userRespRoles.push(us3['authorities'][a]['name'])
    }
    userResp.id = us3['id']
    userResp.login = us3.login
    userResp.firstName = us3.firstName
    userResp.lastName = us3.lastName;
    userResp.password = us3.password
    userResp.roles = userRespRoles
    users.push(userResp)
    buttonsIdsEdit = []
    buttonsIdsDelete = []

}

async function getIndex(buttonId, buttonsIds) {
    let length = buttonsIds.length
    let bId = await buttonId.id
    for (let i = 0; i < length; i++) {
        if (bId === buttonsIds[i]) {
            return i
        }
    }
    return 'error'
}

async function getUserFromUsers(id) {
    let usersLength = users.length
    let userFromUsers
    for (let i = 0; i < usersLength; i++) {
        if (users[i].id == id) {
            userFromUsers = users[i]
        }
    }
    return userFromUsers
}