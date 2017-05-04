import React from 'react';
import ReactDOM from 'react-dom';
import {MuiThemeProvider} from 'material-ui';
import Menubar from '../views/Menubar';
import PathSelector from '../views/PathSelector';
import injectTapEventPlugin from 'react-tap-event-plugin';

import {dialog} from 'electron';
import FileList from "../views/FileList";
import NoFile from "../views/NoFile";

import GlobalSnack from "../views/GlobalSnack"
import Editors from "../views/Editors";
injectTapEventPlugin();

window.E = {};

//路径不存在，主动选择
if (!window.localStorage.getItem("path")) {
	ReactDOM.render(<PathSelector callback={load}/>, document.getElementById('dialog'));
} else {
	load();
}


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
						<Editors/>
					</div>
				</div>
				<GlobalSnack/>
			</div>
		</MuiThemeProvider>
	);

	ReactDOM.render(<App />, document.getElementById('app'));
}

window.document.body.oncontextmenu = e => {
	let event = document.createEvent('Events');
	event.initEvent("click", true, false);

	window.document.body.dispatchEvent(event)
};
