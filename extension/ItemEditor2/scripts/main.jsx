import React from 'react';
import PropTypes from 'prop-types';
import ReactDOM from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Menubar from '../views/Menubar';
import PathSelector from '../views/PathSelector';
import injectTapEventPlugin from 'react-tap-event-plugin';
 
import {dialog} from 'electron';
import FileList from "../views/FileList";
import NoFile from "../views/NoFile";
injectTapEventPlugin();

//路径不存在，主动选择
if(!window.localStorage.getItem("path")){
    ReactDOM.render(<PathSelector callback={load}/>, document.getElementById('dialog'));
}else{
	load();
}

window.currentEditor = null;

function load() {
	const App = () => (
		<MuiThemeProvider>
			<div>
				<Menubar />
				<div className="container">
					<div className="file-list">
						<FileList/>
					</div>
					<div className="editor-outer">
						{window.currentEditor == null ? <NoFile/> : ""}
					</div>
				</div>
			</div>
		</MuiThemeProvider>
	);

	ReactDOM.render(<App />, document.getElementById('app'));
}
