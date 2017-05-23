'use strict';

import React from 'react';
import ReactDOM from 'react-dom';

const findDOMNode = ReactDOM.findDOMNode;
import className from 'classnames';
import debounce from 'lodash.debounce';


function normalizeLineEndings(str) {
	if (!str) return str;
	return str.replace(/\r\n|\r/g, '\n');
}

function isEqual(thing1, thing2) {
	if (thing1 === thing2) {
		return true;
	} else if (Number.isNaN(thing1) && Number.isNaN(thing2)) {
		return true;
	} else if (Array.isArray(thing1) && Array.isArray(thing2)) {
		return arraysEqual(thing1, thing2);
	} else if (typeof thing1 === 'object' && typeof thing2 === 'object') {
		return objectsEqual(thing1, thing2);
	} else {
		return false;
	}
}

function arraysEqual(array1, array2) {
	if (array1.length !== array2.length) {
		return false;
	} else {
		return array1.every(function (item, index) {
			return isEqual(array1[index], array2[index]);
		});
	}
}

function objectsEqual(obj1, obj2) {
	if (obj1.constructor !== obj2.constructor) {
		return false;
	}
	var obj1Keys = Object.keys(obj1);
	var obj2Keys = Object.keys(obj2);
	if (!arraysEqual(obj1Keys.sort(), obj2Keys.sort())) {
		return false;
	}
	return obj1Keys.every(function (key) {
		return isEqual(obj1[key], obj2[key]);
	});
}

export default class CodeMirror extends React.Component {
	displayName = 'CodeMirror';

	getCodeMirrorInstance() {
		return this.props.codeMirrorInstance || window.CodeMirror;
	}

	componentWillMount() {
		this.componentWillReceiveProps = debounce(this.componentWillReceiveProps, 0);
	}

	componentDidMount() {
		var textareaNode = findDOMNode(this.refs.textarea);
		var codeMirrorInstance = this.getCodeMirrorInstance();
		this.codeMirror = codeMirrorInstance.fromTextArea(textareaNode, this.props.options);
		this.codeMirror.on('change', (a, b) => this.codemirrorValueChanged(a, b));
		this.codeMirror.on('scroll', cm => this.scrollChanged(cm));
		this.codeMirror.setValue(this.props.defaultValue || this.props.value || '');

		this.codeMirror.setOption('lint', {options: {esversion: 6, asi: true}})


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
					this.setOptionIfChanged(optionName, nextProps.options[optionName]);
				}
			}
		}
	}

	setOptionIfChanged(optionName, newValue) {
		var oldValue = this.codeMirror.getOption(optionName);
		if (!isEqual(oldValue, newValue)) {
			this.codeMirror.setOption(optionName, newValue);
		}
	}

	getCodeMirror() {
		return this.codeMirror;
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
		const editorClassName = className('ReactCodeMirror--focused', this.props.className);
		return React.createElement(
			'div',
			{ className: editorClassName },
			React.createElement('textarea', { ref: 'textarea', name: this.props.path, defaultValue: this.props.value, autoComplete: 'off' })
		);
	}
}
