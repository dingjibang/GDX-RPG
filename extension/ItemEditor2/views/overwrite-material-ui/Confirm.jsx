import React from 'react';
import {Dialog} from 'material-ui';
import {FlatButton} from 'material-ui';
import {MuiThemeProvider} from 'material-ui';

export default class Confirm extends React.Component {

	state = {open: true}
	
	close = flag => {
		this.setState({open: false});

		if(this.props.callback)
			this.props.callback(flag);
	}
	
	componentWillReceiveProps() {
		this.setState({open: true});
	}

	render() {
		
		const actions = [
			<FlatButton
				label="确定"
				primary={true}
				onClick={() => this.close(true)}
			/>,
			<FlatButton
				label="取消"
				primary={true}
				onClick={() => this.close(false)}
			/>,
		];
		
		return (
			<MuiThemeProvider>
				<Dialog
					actions={actions}
					modal={false}
					open={this.state.open}
					onRequestClose={() => this.close(false)}
				>
					{this.props.msg}
				</Dialog>
			</MuiThemeProvider>
		);
	}
}