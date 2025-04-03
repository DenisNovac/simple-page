
const host = 'http://localhost:8081'
let c = 0

function pressButton() {
  // show date and increase counter

  document.getElementById('date').innerHTML = "Browser says: " + b(Date())
  c++
  document.getElementById('counter').innerHTML = 'Wow it was pressed ' + b(c) + ' times!'

  // send request to server

  var request
  request = new XMLHttpRequest()
  request.open("GET", host + "/time")
  request.send()

  request.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200)
      document.getElementById('from_server').innerHTML = "Scala server says: " + b(this.responseText)
  }
}

function b(text) {
  return `<b>${text}</b>`
}