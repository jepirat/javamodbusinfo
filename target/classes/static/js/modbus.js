let url = '/modbus'

async function sendRequest(url) {
   // let userRoles = []
    //  let row = ['id', 'login', 'firstName', 'lastName', 'password', 'authorities']
    let response = await fetch(url)
    let js = await response.json()
}

const timer = ms => new Promise(res => setTimeout(res, ms))
async function getTemperature() {
    while (true) {
        let response = await fetch('/temperature')
        let js = await response.json()
        console.log(js)
        document.querySelector('#temperature').innerHTML = 'Сейчас температура = ' + js + ' &#8451;';
        await timer(1000)
    }
}

 getTemperature()


async function getHumidity() {
    // while (true) {
    //     let response = await fetch('/humidity')
    //     let js = await response.json()
    //     console.log(js)
    //     document.querySelector('#humidity').innerHTML = js;
    //     await timer(1000)
    // }
}