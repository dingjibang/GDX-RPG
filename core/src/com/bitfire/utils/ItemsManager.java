/*******************************************************************************
 * Copyright 2012 bmanuel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bitfire.utils;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class ItemsManager<T extends Disposable> implements Iterable<T>, Disposable {
	private static final int ItemNotFound = -1;
	private final Array<T> items = new Array<T>();
	protected final Array<Boolean> owned = new Array<Boolean>();

	@Override
	public void dispose() {
		for( int i = 0; i < items.size; i++ ) {
			if( owned.get( i ) ) {
				items.get( i ).dispose();
			}
		}

		items.clear();
		owned.clear();
	}

	/** Add an item to the manager, if own is true the manager will manage the resource's lifecycle */
	public void add( T item, boolean own ) {
		if( item == null ) {
			return;
		}

		items.add( item );
		owned.add( own );
	}

	/** Add an item to the manager and transfer ownership to it */
	public void add( T item ) {
		add( item, true );
	}

	/** Returns the item at the specified index */
	public T get( int index ) {
		return items.get( index );
	}

	/** Returns the number of items managed by this instance */
	public int count() {
		return items.size;
	}

	/* Returns an iterator on the managed items */
	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

	/** Removes a previously added resource */
	public void remove( T item ) {
		int index = items.indexOf( item, true );
		if( index == ItemNotFound ) {
			return;
		}

		if( owned.get( index ) ) {
			items.get( index ).dispose();
		}

		items.removeIndex( index );
		owned.removeIndex( index );
		items.removeValue( item, true );
	}
}
