function enableStylesheet(node) {
  node.disabled = false;
}

function disableStylesheet(node) {
  node.disabled = true;
}

let isDark = true

function changeTheme() {
  if (isDark) {
    document
      .querySelectorAll('link[rel=stylesheet].dark')
      .forEach(disableStylesheet)
    document
      .querySelectorAll('link[rel=stylesheet].light')
      .forEach(enableStylesheet)
    isDark = false
  } else {
    document
      .querySelectorAll('link[rel=stylesheet].light')
      .forEach(disableStylesheet)
    document
      .querySelectorAll('link[rel=stylesheet].dark')
      .forEach(enableStylesheet)
    isDark = true
  }

}