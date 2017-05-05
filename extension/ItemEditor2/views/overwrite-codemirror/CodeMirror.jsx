'use strict';
import PropTypes from 'prop-types';

import React from 'react';
import ReactDOM from 'react-dom';

const findDOMNode = ReactDOM.findDOMNode;
import className from 'classnames';
import debounce from 'lodash.debounce';

require("codemirror/mode/javascript/javascript")
require("codemirror/addon/hint/show-hint")
require("codemirror/addon/hint/javascript-hint")

require("codemirror/addon/lint/lint")
require("codemirror/addon/lint/javascript-lint")



function normalizeLineEndings(str) {
	if (!str) return str;
	return str.replace(/\r\n|\r/g, '\n');
}

export default class CodeMirror extends React.Component {
	displayName = 'CodeMirror'

	getCodeMirrorInstance() {
		return this.props.codeMirrorInstance || require('codemirror');
	}

	componentWillMount() {
		this.componentWillReceiveProps = debounce(this.componentWillReceiveProps, 0);
	}

	componentDidMount() {
		var textareaNode = findDOMNode(this.refs.textarea);
		var codeMirrorInstance = this.getCodeMirrorInstance();
		this.codeMirror = codeMirrorInstance.fromTextArea(textareaNode, this.props.options);
		this.codeMirror.on('change', (a, b) => this.codemirrorValueChanged(a, b));
		this.codeMirror.on('focus', () => this.focusChanged(true));
		this.codeMirror.on('blur', () => this.focusChanged(false));
		this.codeMirror.on('scroll', cm => this.scrollChanged(cm));
		this.codeMirror.setValue(this.props.defaultValue || this.props.value || '');
		this.codeMirror.refresh()
	}

	componentWillUnmount() {
		// is there a lighter-weight way to remove the cm instance?
		if (this.codeMirror) {
			this.codeMirror.toTextArea();
		}
	}

	componentWillReceiveProps(nextProps) {
		if (this.codeMirror && nextProps.value !== undefined && normalizeLineEndings(this.codeMirror.getValue()) !== normalizeLineEndings(nextProps.value)) {
			if (this.props.preserveScrollPosition) {
				var prevScrollPosition = this.codeMirror.getScrollInfo();
				this.codeMirror.setValue(nextProps.value);
				this.codeMirror.scrollTo(prevScrollPosition.left, prevScrollPosition.top);
			} else {
				this.codeMirror.setValue(nextProps.value);
			}
		}
		if (typeof nextProps.options === 'object') {
			for (var optionName in nextProps.options) {
				if (nextProps.options.hasOwnProperty(optionName)) {
					this.codeMirror.setOption(optionName, nextProps.options[optionName]);
				}
			}
		}
	}

	getCodeMirror() {
		return this.codeMirror;
	}

	focus() {
		if (this.codeMirror) {
			this.codeMirror.focus();
		}
	}

	focusChanged(focused) {
		this.props.onFocusChange && this.props.onFocusChange(focused);
	}

	scrollChanged(cm) {
		this.props.onScroll && this.props.onScroll(cm.getScrollInfo());
	}

	codemirrorValueChanged(doc, change) {
		if (this.props.onChange && change.origin !== 'setValue') {
			this.props.onChange(doc.getValue(), change);
		}
	}

	render() {
		var editorClassName = className('ReactCodeMirror', 'ReactCodeMirror--focused', this.props.className);
		return React.createElement(
			'div',
			{ className: editorClassName },
			React.createElement('textarea', {ref: 'textarea', name: this.props.path, defaultValue: this.props.value, autoComplete: 'off' })
		);
	}
};
