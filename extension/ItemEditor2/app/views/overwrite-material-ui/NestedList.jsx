import React, {Children, isValidElement, cloneElement} from 'react';
import PropTypes from 'prop-types';
import List from 'material-ui/List/List';

const NestedList = (props) => {
	const {
		children,
		open,
		nestedLevel,
		style,
	} = props;

	return (
		<List style={{...style, "display": open ? "block" : "none"}}>
			{Children.map(children, (child) => {
				return isValidElement(child) ? (
					cloneElement(child, {
						nestedLevel: nestedLevel + 1,
					})
				) : child;
			})}
		</List>
	);
};

NestedList.propTypes = {
	children: PropTypes.node,
	nestedLevel: PropTypes.number.isRequired,
	open: PropTypes.bool.isRequired,
	/**
	 * Override the inline-styles of the root element.
	 */
	style: PropTypes.object,
};

export default NestedList;