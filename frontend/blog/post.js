


function send(form) {

  let json = {
    "title": form.title.value,
    "text": form.text.value
  }

  var request
  request = new XMLHttpRequest()
  request.open("POST", host + "/blog/entry/")
  request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  request.send(JSON.stringify(json))

  request.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      let j = JSON.parse(this.responseText)
      //alert(j.id)
      window.location.assign("./list.html")
    }
  }
}