package com.jcollections.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DecisionTree<T> implements Map<String, T> {
	
	private T leaf = null;
	
	private Map<Character, DecisionTree<T>> children = new HashMap<>();
	
	private void add(String prefix, T leafObject) {
		if (prefix.length() > 0)
			get(prefix.charAt(0)).add(prefix.substring(1), leafObject);
		else
			leaf = leafObject;
	}
	
	private T get(String prefix) {
		if (prefix.length() > 0)
			if (children.containsKey(prefix.charAt(0)))
				return children.get(prefix.charAt(0)).get(prefix.substring(1));
			else
				return null;
		else
			return leaf;
	}
	
	private Set<T> values(String keysSoFar) {
		Set<T> ret = new HashSet<>();
		
		if (leaf != null)
			ret.add(leaf);
		
		for (Character it : children.keySet())
			ret.addAll(children.get(it).values((keysSoFar == null ? "" : keysSoFar) + it));
		
		return ret;
	}
	
	private DecisionTree<T> get(Character c) {
		if (!children.containsKey(c))
			children.put(c, new DecisionTree<T>());
		
		DecisionTree<T> ret = children.get(c);
		
		return ret;
	}
	
	private Set<String> keySet(String keysSoFar) {
		Set<String> ret = new HashSet<>();
		
		if (leaf != null)
			ret.add(keysSoFar);
		
		for (Character it : children.keySet())
			ret.addAll(children.get(it).keySet((keysSoFar == null ? "" : keysSoFar) + it));
		
		return ret;
	}
	
	private Set<Entry<String, T>> entrySet(String keysSoFar) {
		Set<Entry<String, T>> ret = new HashSet<>();
		
		if (keysSoFar != null && leaf != null)
			ret.add(new MapEntry(keysSoFar, leaf));
		
		for (Character it : children.keySet())
			ret.addAll(children.get(it).entrySet((keysSoFar == null ? "" : keysSoFar) + it));
		
		return ret;
	}
	
	public T removeValue(String targetKey) {
		T ret = leaf;
		
		if (targetKey.length() == 0)
			leaf = null;
		else
			if (children.containsKey(targetKey.charAt(0))) {
				DecisionTree<T> child = children.get(targetKey.charAt(0));
				String leftover = targetKey.substring(1);
				ret = child.removeValue(leftover);
			}
		
		return ret;
	}
	
	public void cleanupEmptyLeafPaths() {
		Set<ToRemove> toRemove = find("", "");
		
		for (ToRemove it : toRemove)
			cleanupEmptyPath(it.fullKey, it.emptyPart);
	}
	
	private Set<ToRemove> find(String pathSoFar, String empty) {
		Set<ToRemove> ret = new HashSet<>();
		
		if (children.isEmpty() && empty.length() > 0 && leaf == null)
					ret.add(new ToRemove(pathSoFar, empty));
		else {
			String nextEmpty = (leaf != null ? "" : empty);
			for (Character it : children.keySet()) {
				DecisionTree<T> child = children.get(it);
				ret.addAll(child.find(pathSoFar + it, nextEmpty + it));
			}
		}
		
		return ret;
	}
	
	private void cleanupEmptyPath(String fullKey, String subKey) {
		if (fullKey.equals(subKey))
			children.remove(fullKey.charAt(0));
		else
			children.get(fullKey.charAt(0)).cleanupEmptyPath(fullKey.substring(1), subKey);
	}
	

	@Override
	public int size() {
		int ret = 0;
		
		for (Character it : children.keySet())
			ret += children.get(it).size();
			
		if (leaf != null)
			ret++;
		
		return ret;
	}
	@Override
	public boolean isEmpty() {
		return children.size() == 0;
	}
	@Override
	public boolean containsKey(Object key) {
		if (key != null)
			return get(key.toString()) != null;
		
		return false;
	}
	@Override
	public boolean containsValue(Object value) {
		if (value != null && value.equals(leaf))
			return true;
		
		boolean tmp = false;
		
		for (DecisionTree<T> it : children.values())
			if (it.containsValue(value))
				tmp = true;
		
		return tmp;
	}
	@Override
	public T get(Object key) {
		return get(key.toString());
	}
	@Override
	public T put(String key, T value) {
		add(key, value);
		return value;
	}
	@Override
	public T remove(Object key) {
		T ret = removeValue(key.toString());
		
		cleanupEmptyLeafPaths();
		
		return ret;
	}
	@Override
	public void putAll(Map<? extends String, ? extends T> m) {
		m.keySet().stream().forEach(K -> add(K, m.get(K)));
	}
	@Override
	public void clear() {
		children = new HashMap<>();
		leaf = null;
	}
	@Override
	public Set<String> keySet() {
		return keySet(null);
	}
	@Override
	public Collection<T> values() {
		return values(null);
	}
	@Override
	public Set<Entry<String, T>> entrySet() {
		return entrySet(null);
	}
	
	
	
	
	private class MapEntry implements Entry<String, T> {
		private String key;
		private T value;
		
		public MapEntry(String key, T value) {
			this.key = key;
			setValue(value);
		}
		
		@Override
		public String getKey() {
			return key;
		}
		@Override
		public T getValue() {
			return value;
		}
		@Override
		public T setValue(T value) {
			this.value = value;
			return value;
		}
	}
	
	private class ToRemove {
		private String fullKey;
		private String emptyPart;
		
		public ToRemove(String fullKey, String emptyPart) {
			this.fullKey = fullKey;
			this.emptyPart = emptyPart;
		}
	}
}
