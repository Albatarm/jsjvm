package com.didactilab.jsjvm.client.util;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class Iterables {

	private static class Reversed<T> implements Iterable<T> {

		private final List<T> other;

		public Reversed(List<T> other) {
			this.other = other;
		}
		
		@Override
		public Iterator<T> iterator() {
			final ListIterator<T> it = other.listIterator(other.size());
			return new Iterator<T>() {
				@Override
				public boolean hasNext() {
					return it.hasPrevious();
				}

				@Override
				public T next() {
					return it.previous();
				}
				
				@Override
				public void remove() {
					it.remove();
				}
			};
		}
		
	}
	
	public static <T> Iterable<T> reversed(List<T> l) {
		return new Reversed<T>(l);
	}
	
	public static <T> Iterable<T> reversed(final Deque<T> d) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return d.descendingIterator();
			}
			
			@Override
			public String toString() {
				return Iterables.toString(this);
			}
		};
	}
	
	public static <T> String toString(Iterable<T> i) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (T t : i) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(t);
		}
		sb.append("]");
		return sb.toString();
	}
	
	private Iterables() {
	}

}
