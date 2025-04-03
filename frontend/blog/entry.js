window.onload = function () {

  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  var request
  request = new XMLHttpRequest()
  request.open("GET", host + "/blog/entry/" + urlParams.get('id'))
  request.send()


  request.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      let rt = this.responseText

      if (rt == "") {
        document.getElementById('title').innerHTML = "Not found"
      } else {
        let j = JSON.parse(rt)
        document.getElementById('title').innerHTML = j.title
        document.getElementById('posted').innerHTML = j.postedAt
        document.getElementById('text').innerHTML = j.text
      }
    }
  }
};