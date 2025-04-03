window.onload = function () {

  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  var request
  request = new XMLHttpRequest()
  request.open("GET", host + "/blog/list")
  request.send()


  request.onreadystatechange = function () {
    const table = document.querySelector('#entries')

    if (this.readyState == 4 && this.status == 200) {
      let entries = JSON.parse(this.responseText)


      entries.forEach(entry => {
        const row = document.createElement('tr')

        //let id = cell(entry.id)
        let title = cell(entry.title)
        let ts = cell(entry.postedAt)

        let link = document.createElement('a')
        link.appendChild(document.createTextNode('link'))
        link.href = 'entry.html?id=' + entry.id

        linkCell = document.createElement('td')
        linkCell.appendChild(link)

        //row.appendChild(id)
        row.appendChild(title)
        row.appendChild(ts)
        row.appendChild(linkCell)

        table.appendChild(row)
      })
    }
  }
}

function cell(cellData) {
  var cell = document.createElement('td')
  cell.appendChild(document.createTextNode(cellData))
  return cell
}

function newEntry() {
  window.location.assign("./post.html")
}
