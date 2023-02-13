package br.com.code7.financasbackend.resources.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;

/**
 * Useful utility methods for Collections Framework
 * 
 * @author
 */
public final class CollectionUtils {

	private CollectionUtils() {

	}

	/**
	 * Return {@code true} if the supplied Collection is {@code null} or empty.
	 * Otherwise, return {@code false}.
	 * 
	 * @param collection the Collection to check
	 * @return whether the given Collection is empty
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * Return {@code true} if the supplied Collection is not {@code null} or not
	 * empty. Otherwise, return {@code false}.
	 * 
	 * @param collection the Collection to check
	 * @return whether the given Collection is not empty
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * Return {@code true} if the supplied Map is {@code null} or empty. Otherwise,
	 * return {@code false}.
	 * 
	 * @param map the Map to check
	 * @return whether the given Map is empty
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * Return {@code true} if the supplied Map is not {@code null} or not empty.
	 * Otherwise, return {@code false}.
	 * 
	 * @param map the Map to check
	 * @return whether the given Map is not empty
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * Determine whether the given array is empty: i.e. {@code null} or of zero
	 * length.
	 * 
	 * @param array the array to check
	 * @see #isEmpty(Object)
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * Convert the given array (which may be a primitive array) to an object array
	 * (if necessary of primitive wrapper objects).
	 * <p>
	 * A {@code null} source value will be converted to an empty Object array.
	 * 
	 * @param source the (potentially primitive) array
	 * @return the corresponding object array (never {@code null})
	 * @throws IllegalArgumentException if the parameter is not an array
	 */
	public static Object[] toObjectArray(Object source) {
		if (source instanceof Object[]) {
			return (Object[]) source;
		}
		if (source == null) {
			return new Object[0];
		}
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: " + source);
		}
		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class<?> wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}

	/**
	 * Convert the supplied array into a List. A primitive array gets converted into
	 * a List of the appropriate wrapper type.
	 * <p>
	 * <b>NOTE:</b> Generally prefer the standard {@link Arrays#asList} method. This
	 * {@code arrayToList} method is just meant to deal with an incoming Object
	 * value that might be an {@code Object[]} or a primitive array at runtime.
	 * <p>
	 * A {@code null} source value will be converted to an empty List.
	 * 
	 * @param source the (potentially primitive) array
	 * @return the converted List result
	 * @see ObjectUtils#toObjectArray(Object)
	 * @see Arrays#asList(Object[])
	 */
	@SuppressWarnings("rawtypes")
	public static List arrayToList(Object source) {
		return Arrays.asList(toObjectArray(source));
	}

	/**
	 * Merge the given array into the given Collection.
	 * 
	 * @param array      the array to merge (may be {@code null})
	 * @param collection the target Collection to merge the array into
	 */
	@SuppressWarnings("unchecked")
	public static <E> void mergeArrayIntoCollection(Object array, Collection<E> collection) {
		Object[] arr = toObjectArray(array);
		for (Object elem : arr) {
			collection.add((E) elem);
		}
	}

	/**
	 * Merge the given Properties instance into the given Map, copying all
	 * properties (key-value pairs) over.
	 * <p>
	 * Uses {@code Properties.propertyNames()} to even catch default properties
	 * linked into the original Properties instance.
	 * 
	 * @param props the Properties instance to merge (may be {@code null})
	 * @param map   the target Map to merge the properties into
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> void mergePropertiesIntoMap(Properties props, Map<K, V> map) {
		if (props != null) {
			for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				Object value = props.get(key);
				if (value == null) {
					// Allow for defaults fallback or potentially overridden
					// accessor...
					value = props.getProperty(key);
				}
				map.put((K) key, (V) value);
			}
		}
	}

	/**
	 * Check whether the given Iterator contains the given element.
	 * 
	 * @param iterator the Iterator to check
	 * @param element  the element to look for
	 * @return {@code true} if found, {@code false} otherwise
	 */
	public static boolean contains(Iterator<?> iterator, Object element) {
		if (iterator != null) {
			while (iterator.hasNext()) {
				Object candidate = iterator.next();
				if (nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check whether the given Enumeration contains the given element.
	 * 
	 * @param enumeration the Enumeration to check
	 * @param element     the element to look for
	 * @return {@code true} if found, {@code false} otherwise
	 */
	public static boolean contains(Enumeration<?> enumeration, Object element) {
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				Object candidate = enumeration.nextElement();
				if (nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check whether the given Collection contains the given element instance.
	 * <p>
	 * Enforces the given instance to be present, rather than returning {@code true}
	 * for an equal element as well.
	 * 
	 * @param collection the Collection to check
	 * @param element    the element to look for
	 * @return {@code true} if found, {@code false} otherwise
	 */
	public static boolean containsInstance(Collection<?> collection, Object element) {
		if (collection != null) {
			for (Object candidate : collection) {
				if (candidate == element) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return {@code true} if any element in '{@code candidates}' is contained in
	 * '{@code source}'; otherwise returns {@code false}.
	 * 
	 * @param source     the source Collection
	 * @param candidates the candidates to search for
	 * @return whether any of the candidates has been found
	 */
	public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return false;
		}
		for (Object candidate : candidates) {
			if (source.contains(candidate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the first element in '{@code candidates}' that is contained in
	 * '{@code source}'. If no element in '{@code candidates}' is present in
	 * '{@code source}' returns {@code null}. Iteration order is {@link Collection}
	 * implementation specific.
	 * 
	 * @param source     the source Collection
	 * @param candidates the candidates to search for
	 * @return the first present object, or {@code null} if not found
	 */
	@SuppressWarnings("unchecked")

	public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return null;
		}
		for (Object candidate : candidates) {
			if (source.contains(candidate)) {
				return (E) candidate;
			}
		}
		return null;
	}

	/**
	 * Find a single value of the given type in the given Collection.
	 * 
	 * @param collection the Collection to search
	 * @param type       the type to look for
	 * @return a value of the given type found if there is a clear match, or
	 *         {@code null} if none or more than one such value found
	 */
	@SuppressWarnings("unchecked")

	public static <T> T findValueOfType(Collection<?> collection, Class<T> type) {
		if (isEmpty(collection)) {
			return null;
		}
		T value = null;
		for (Object element : collection) {
			if (type == null || type.isInstance(element)) {
				if (value != null) {
					// More than one value found... no clear single value.
					return null;
				}
				value = (T) element;
			}
		}
		return value;
	}

	/**
	 * Find a single value of one of the given types in the given Collection:
	 * searching the Collection for a value of the first type, then searching for a
	 * value of the second type, etc.
	 * 
	 * @param collection the collection to search
	 * @param types      the types to look for, in prioritized order
	 * @return a value of one of the given types found if there is a clear match, or
	 *         {@code null} if none or more than one such value found
	 */

	public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
		if (isEmpty(collection) || isEmpty(types)) {
			return null;
		}
		for (Class<?> type : types) {
			Object value = findValueOfType(collection, type);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * Determine whether the given Collection only contains a single unique object.
	 * 
	 * @param collection the Collection to check
	 * @return {@code true} if the collection contains a single reference or
	 *         multiple references to the same instance, {@code false} otherwise
	 */
	public static boolean hasUniqueObject(Collection<?> collection) {
		if (isEmpty(collection)) {
			return false;
		}
		boolean hasCandidate = false;
		Object candidate = null;
		for (Object elem : collection) {
			if (!hasCandidate) {
				hasCandidate = true;
				candidate = elem;
			} else if (candidate != elem) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Find the common element type of the given Collection, if any.
	 * 
	 * @param collection the Collection to check
	 * @return the common element type, or {@code null} if no clear common type has
	 *         been found (or the collection was empty)
	 */

	public static Class<?> findCommonElementType(Collection<?> collection) {
		if (isEmpty(collection)) {
			return null;
		}
		Class<?> candidate = null;
		for (Object val : collection) {
			if (val != null) {
				if (candidate == null) {
					candidate = val.getClass();
				} else if (candidate != val.getClass()) {
					return null;
				}
			}
		}
		return candidate;
	}

	/**
	 * Retrieve the last element of the given Set, using {@link SortedSet#last()} or
	 * otherwise iterating over all elements (assuming a linked set).
	 * 
	 * @param set the Set to check (may be {@code null} or empty)
	 * @return the last element, or {@code null} if none
	 * @since 5.0.3
	 * @see SortedSet
	 * @see LinkedHashMap#keySet()
	 * @see java.util.LinkedHashSet
	 */

	public static <T> T lastElement(Set<T> set) {
		if (isEmpty(set)) {
			return null;
		}
		if (set instanceof SortedSet) {
			return ((SortedSet<T>) set).last();
		}

		// Full iteration necessary...
		Iterator<T> it = set.iterator();
		T last = null;
		while (it.hasNext()) {
			last = it.next();
		}
		return last;
	}

	/**
	 * Retrieve the last element of the given List, accessing the highest index.
	 * 
	 * @param list the List to check (may be {@code null} or empty)
	 * @return the last element, or {@code null} if none
	 * @since 5.0.3
	 */

	public static <T> T lastElement(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return list.get(list.size() - 1);
	}

	/**
	 * Marshal the elements from the given enumeration into an array of the given
	 * type. Enumeration elements must be assignable to the type of the given array.
	 * The array returned will be a different instance than the array given.
	 */
	public static <A, E extends A> A[] toArray(Enumeration<E> enumeration, A[] array) {
		ArrayList<A> elements = new ArrayList<>();
		while (enumeration.hasMoreElements()) {
			elements.add(enumeration.nextElement());
		}
		return elements.toArray(array);
	}

	/**
	 * Determine if the given objects are equal, returning {@code true} if both are
	 * {@code null} or {@code false} if only one is {@code null}.
	 * <p>
	 * Compares arrays with {@code Arrays.equals}, performing an equality check
	 * based on the array elements rather than the array reference.
	 * 
	 * @param o1 first Object to compare
	 * @param o2 second Object to compare
	 * @return whether the given objects are equal
	 * @see Object#equals(Object)
	 * @see java.util.Arrays#equals
	 */
	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			return arrayEquals(o1, o2);
		}
		return false;
	}

	/**
	 * Compare the given arrays with {@code Arrays.equals}, performing an equality
	 * check based on the array elements rather than the array reference.
	 * 
	 * @param o1 first array to compare
	 * @param o2 second array to compare
	 * @return whether the given objects are equal
	 * @see #nullSafeEquals(Object, Object)
	 * @see java.util.Arrays#equals
	 */
	private static boolean arrayEquals(Object o1, Object o2) {
		if (o1 instanceof Object[] && o2 instanceof Object[]) {
			return Arrays.equals((Object[]) o1, (Object[]) o2);
		}
		if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
			return Arrays.equals((boolean[]) o1, (boolean[]) o2);
		}
		if (o1 instanceof byte[] && o2 instanceof byte[]) {
			return Arrays.equals((byte[]) o1, (byte[]) o2);
		}
		if (o1 instanceof char[] && o2 instanceof char[]) {
			return Arrays.equals((char[]) o1, (char[]) o2);
		}
		if (o1 instanceof double[] && o2 instanceof double[]) {
			return Arrays.equals((double[]) o1, (double[]) o2);
		}
		if (o1 instanceof float[] && o2 instanceof float[]) {
			return Arrays.equals((float[]) o1, (float[]) o2);
		}
		if (o1 instanceof int[] && o2 instanceof int[]) {
			return Arrays.equals((int[]) o1, (int[]) o2);
		}
		if (o1 instanceof long[] && o2 instanceof long[]) {
			return Arrays.equals((long[]) o1, (long[]) o2);
		}
		if (o1 instanceof short[] && o2 instanceof short[]) {
			return Arrays.equals((short[]) o1, (short[]) o2);
		}
		return false;
	}

	// -----------------------------------------------------------------------
	/**
	 * Reverses the order of the given array.
	 *
	 * @param array the array to reverse
	 */
	public static void reverseArray(final Object[] array) {
		int i = 0;
		int j = array.length - 1;
		Object tmp;

		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
	}

	/**
	 * Returns <code>true</code> iff all elements of {@code coll2} are also
	 * contained in {@code coll1}. The cardinality of values in {@code coll2} is not
	 * taken into account, which is the same behavior as
	 * {@link Collection#containsAll(Collection)}.
	 * <p>
	 * In other words, this method returns <code>true</code> iff the
	 * {@link #intersection} of <i>coll1</i> and <i>coll2</i> has the same
	 * cardinality as the set of unique values from {@code coll2}. In case
	 * {@code coll2} is empty, {@code true} will be returned.
	 * <p>
	 * This method is intended as a replacement for
	 * {@link Collection#containsAll(Collection)} with a guaranteed runtime
	 * complexity of {@code O(n + m)}. Depending on the type of {@link Collection}
	 * provided, this method will be much faster than calling
	 * {@link Collection#containsAll(Collection)} instead, though this will come at
	 * the cost of an additional space complexity O(n).
	 *
	 * @param coll1 the first collection, must not be null
	 * @param coll2 the second collection, must not be null
	 * @return <code>true</code> iff the intersection of the collections has the
	 *         same cardinality as the set of unique elements from the second
	 *         collection
	 * @since 4.0
	 */
	public static boolean containsAll(final Collection<?> coll1, final Collection<?> coll2) {
		if (coll2.isEmpty()) {
			return true;
		}
		final Iterator<?> it = coll1.iterator();
		final Set<Object> elementsAlreadySeen = new HashSet<>();
		for (final Object nextElement : coll2) {
			if (elementsAlreadySeen.contains(nextElement)) {
				continue;
			}

			boolean foundCurrentElement = false;
			while (it.hasNext()) {
				final Object p = it.next();
				elementsAlreadySeen.add(p);
				if (nextElement == null ? p == null : nextElement.equals(p)) {
					foundCurrentElement = true;
					break;
				}
			}

			if (!foundCurrentElement) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the size of the specified collection or {@code 0} if the collection
	 * is {@code null}.
	 *
	 * @param c the collection to check
	 * @return the size of the specified collection or {@code 0} if the collection
	 *         is {@code null}.
	 * @since 1.2
	 */
	public static int size(Collection c) {
		return c != null ? c.size() : 0;
	}

	/**
	 * Returns the size of the specified map or {@code 0} if the map is
	 * {@code null}.
	 *
	 * @param m the map to check
	 * @return the size of the specified map or {@code 0} if the map is
	 *         {@code null}.
	 * @since 1.2
	 */
	public static int size(Map m) {
		return m != null ? m.size() : 0;
	}

	/**
	 * Retrieves the first item from a collections.
	 *
	 * @param collection   the collection
	 * @param defaultValue the default value should not initial value exist
	 * @param <T>          the type of the collection
	 *
	 * @return the first instance of the collection else null if not present
	 */
	public static <T> T getFirstItem(final Collection<T> collection, final T defaultValue) {
		if (collection == null) {
			return defaultValue;
		}

		final Iterator<T> iterator = collection.iterator();
		return iterator.hasNext() ? iterator.next() : defaultValue;
	}

	/**
	 * Provides a consistent ordering over lists. First compares by the first
	 * element. If that element is equal, the next element is considered, and so on.
	 */
	public static <T extends Comparable<T>> int compareLists(List<T> list1, List<T> list2) {
		if (list1 == null && list2 == null)
			return 0;
		if (list1 == null || list2 == null) {
			throw new IllegalArgumentException();
		}
		int size1 = list1.size();
		int size2 = list2.size();
		int size = Math.min(size1, size2);
		for (int i = 0; i < size; i++) {
			int c = list1.get(i).compareTo(list2.get(i));
			if (c != 0)
				return c;
		}
		if (size1 < size2)
			return -1;
		if (size1 > size2)
			return 1;
		return 0;
	}

	/**
	 * Returns true iff l1 is a sublist of l (i.e., every member of l1 is in l, and
	 * for every e1 &lt; e2 in l1, there is an e1 &lt; e2 occurrence in l).
	 */
	public static <T> boolean isSubList(List<T> l1, List<? super T> l) {
		Iterator<? super T> it = l.iterator();
		for (T o1 : l1) {
			if (!it.hasNext()) {
				return false;
			}
			Object o = it.next();
			while ((o == null && !(o1 == null)) || (o != null && !o.equals(o1))) {
				if (!it.hasNext()) {
					return false;
				}
				o = it.next();
			}
		}
		return true;
	}

	/**
	 * Returns all objects in list1 that are not in list2.
	 *
	 * @param <T>   Type of items in the collection
	 * @param list1 First collection
	 * @param list2 Second collection
	 * @return The collection difference list1 - list2
	 */
	public static <T> Collection<T> diff(Collection<T> list1, Collection<T> list2) {
		Collection<T> diff = new ArrayList<>();
		for (T t : list1) {
			if (!list2.contains(t)) {
				diff.add(t);
			}
		}
		return diff;
	}
}