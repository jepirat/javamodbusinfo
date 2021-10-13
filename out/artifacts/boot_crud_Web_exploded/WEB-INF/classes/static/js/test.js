const url = '/users';




async function sendRequest(url) {
    let row = ['login', 'firstName', 'lastName', 'password', 'roles']
   let response = await fetch(url)
   let js = await response.json()
    for (let i = 0; i < js.length; i++) {
        console.log(js.length)
        $('#usersTable > tbody tr:last-child').append(`<td id="${i}">${js[i][row[i]]}</td>`)
            .append(`<td id="${i}">${js[i][row[i]]}</td>`)
    }
}

sendRequest(url)
//console.log(js)

// let u = await sendRequest(url).then(r => {return r.json()})
// console.log('users', u)
