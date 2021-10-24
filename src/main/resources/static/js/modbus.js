let url = '/modbus'

async function sendRequest(url) {
    let response = await fetch(url)
    let js = await response.json()
}

const timer = ms => new Promise(res => setTimeout(res, ms))
async function getTemperature() {
    while (true) {
        let response = await fetch('/data')
        let data = await response.json()
        document.querySelector('#temperature').innerHTML = 'Сейчас температура воздуха дома = ' + (data[0] / 10.0) + ' &#8451;';
        document.querySelector('#humidity').innerHTML = 'Влажность воздуха дома  = ' + (data[1] / 10.0) + '%';
        await timer(1000)
    }
}

 getTemperature()
