import React from 'react';
import {MuiThemeProvider} from 'material-ui';
import Menubar from "./Menubar";
import FileList from "./FileList";
import Editors from "./Editors";
import GlobalSnack from "./GlobalSnack";

export default class Root extends React.Component {
	
	render() {
		return (
			<div>
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
			</div>
		);
	}
}