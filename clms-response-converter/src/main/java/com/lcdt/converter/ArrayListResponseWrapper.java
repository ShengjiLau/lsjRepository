package com.lcdt.converter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ss on 2017/11/3.
 */
public class ArrayListResponseWrapper<E> extends ArrayList<E> implements ResponseData {
	public ArrayListResponseWrapper(int initialCapacity) {
		super(initialCapacity);
	}

	public ArrayListResponseWrapper(Collection<? extends E> c) {
		super(c);
	}
}
