// since there are other .onload in other files we use add event listener
window.addEventListener("load", function () {
  console.log("onload")
  let currentTheme = getThemeCookieOrSetDark()
  useTheme(currentTheme)
}, false);

const cookieName = 'simple_page_theme_dark'

function getThemeCookieOrSetDark() {
  let cookie = decodeURIComponent(document.cookie)
  let ca = cookie.split(';')
  let theme = ca.find((s) => s.includes(cookieName))

  var currentTheme

  if (theme == undefined) {
    document.cookie = `${cookieName}=1;`
    currentTheme = 1
  } else {
    currentTheme = theme.split('=')[1]
  }

  console.log(theme)
  console.log(currentTheme)

  return parseInt(currentTheme)
}

function useTheme(num) {
  console.log("Use theme " + num)
  switch (num) {
    case 0: // light
      document
        .querySelectorAll('link[rel=stylesheet].dark')
        .forEach(disableStylesheet)
      document
        .querySelectorAll('link[rel=stylesheet].light')
        .forEach(enableStylesheet)
      break
    case 1: // dark
      document
        .querySelectorAll('link[rel=stylesheet].light')
        .forEach(disableStylesheet)
      document
        .querySelectorAll('link[rel=stylesheet].dark')
        .forEach(enableStylesheet)
      break
  }
}


function changeTheme() {
  let currentTheme = getThemeCookieOrSetDark()

  switch (currentTheme) {
    case 0:
      console.log("switching to dark")
      document.cookie = `${cookieName}=1;`
      useTheme(1)
      break
    case 1:
      console.log("switching to light")
      document.cookie = `${cookieName}=0;`
      useTheme(0)
      break
    default:
      console.log("uh oh")
  }
}

function enableStylesheet(node) {
  node.disabled = false;
}

function disableStylesheet(node) {
  node.disabled = true;
}
