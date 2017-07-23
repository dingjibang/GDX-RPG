import { AppContainer } from 'react-hot-loader';

import React from 'react';
import ReactDOM from 'react-dom';
import PathSelector from 'views/PathSelector';
import injectTapEventPlugin from 'react-tap-event-plugin';


import Confirm from "views/overwrite-material-ui/Confirm";
import Root from "views/Root";

injectTapEventPlugin();

window.E = {
	/**Window.E.confirm("询问", "确定要删除么") or Window.E.confirm("确定要删除么")*/
	confirm: (msg, callback) => {
		ReactDOM.render(<Confirm msg={msg} callback={callback}/>, document.getElementById("dialog"));
	},
	__seq: 0,
	seq: () => E.__seq++,
};

//路径不存在，主动选择
if (!window.localStorage.getItem("path")) {
	ReactDOM.render(<PathSelector callback={load}/>, document.getElementById("dialog"));
} else {
	load();
}


function load() {
	ReactDOM.render((
		<AppContainer>
			<Root/>
		</AppContainer>
	), document.getElementById("app"));


	if (module.hot) {
		module.hot.accept('views/Root', () => {
			const NextRoot = require('views/Root');
			ReactDOM.render((
				<AppContainer>
					<NextRoot/>
				</AppContainer>
			), document.getElementById("app"));
		});
	}



}

window.document.body.oncontextmenu = e => {
	let event = document.createEvent('Events');
	event.initEvent("click", true, false);

	window.document.body.dispatchEvent(event)
};
