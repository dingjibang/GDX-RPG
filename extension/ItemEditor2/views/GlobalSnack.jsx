import React from 'react';
import Snackbar from 'material-ui/Snackbar';

export default class GlobalSnack extends React.Component {
	
	state = {open: false, text: "", actionName: "", callback: null, bind: null}

	constructor(props){
		super(props);

		E.snack = (text, actionName, callback, bind) => this.setState({text: text, actionName: actionName, callback: callback, bind: bind, open: true})
	}
	
	closeSnack() {
		this.setState({open: false, text: "", actionName: "", callback: null, bind: null});
	}

	callback() {
		if(this.state.callback){
			this.state.callback(this.state.bind);
			this.closeSnack();
		}
	}

	render() {
		return (
			<Snackbar
				className={"snack"}
				open={this.state.open}
				message={this.state.text}
				action={this.state.actionName}
				autoHideDuration={400000}
				onActionTouchTap={() => this.callback()}
				onRequestClose={() => this.closeSnack()}
			/>
		);
	}
}