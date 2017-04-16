import { app, BrowserWindow } from 'electron';

let mainWindow = null;

app.on('window-all-closed', () => {
  if (process.platform != 'darwin') {
    app.quit();
  }
});

app.on('ready', () => {
  mainWindow = new BrowserWindow({width: 1280, height: 720});
  //mainWindow.setMenu(null);
  mainWindow.loadURL('file://' + __dirname + '/index.html');
  mainWindow.on('closed', () => {
    mainWindow = null;
  });
});
