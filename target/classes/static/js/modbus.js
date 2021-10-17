let url = '/modbus'

async function sendRequest(url) {
   // let userRoles = []
    //  let row = ['id', 'login', 'firstName', 'lastName', 'password', 'authorities']
    let response = await fetch(url)
    let js = await response.json()
}

sendRequest()