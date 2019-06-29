package com.jcollections.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.jcollections.map.DecisionTree;

public class DecisionTreeTest {
	
	private Map<String, String> data;
	
	@Before
	public void setup() {
		data = new DecisionTree<String>();
	}
	
	@Test
	public void testEmpty() {
		assertTrue(data.size() == 0);
	}
	
	@Test
	public void testAddOne() {
		data.put("alma", "piros");
		
		assertTrue(data.size() == 1);
	}
	
	@Test
	public void testContainsKey() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		assertTrue(data.containsKey("alma"));
		assertTrue(data.containsKey("szilva"));
		
		assertFalse(data.containsKey("alm"));
	}
	
	@Test
	public void testContainsValue() {
		data.put("alma", "piros");
		data.put("almakukac", "sarga");
		
		assertTrue(data.containsValue("piros"));
		assertTrue(data.containsValue("sarga"));
		
		assertFalse(data.containsKey("piro"));
	}
	
	@Test
	public void testPutAndGet() {
		data.put("alma", "piros");
		data.put("almakukac", "sarga");
		
		assertTrue(data.get("alma").equals("piros"));
		assertTrue(data.get("almakukac").equals("sarga"));
		
		assertTrue(data.get("szilva") == null);
	}
	
	@Test
	public void testClear() {
		data.put("alma", "piros");
		
		assertTrue(data.size() == 1);
		
		data.clear();
		
		assertTrue(data.size() == 0);
	}
	
	@Test
	public void tesKeySet() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		Set<String> keys = data.keySet();
		
		assertTrue(keys.size() == 2);
		assertTrue(keys.contains("alma"));
		assertTrue(keys.contains("szilva"));
	}
	
	@Test
	public void testValues() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		Collection<String> vals = data.values();
		
		assertTrue(vals.size() == 2);
		assertTrue(vals.contains("piros"));
		assertTrue(vals.contains("kek"));
	}
	
	@Test
	public void testRemove() {
		data.put("alma", "piros");
		data.put("al", "kek");
		
		Collection<String> vals = data.values();
		
		assertTrue(vals.size() == 2);
		assertTrue(vals.contains("piros"));
		
		data.remove("alma");
		
		vals = data.values();
		
		assertTrue(vals.size() == 1);
		assertFalse(vals.contains("piros"));
		assertTrue(vals.contains("kek"));
		
		assertTrue(data.containsKey("al"));
		assertFalse(data.containsKey("alma"));
		
		data.clear();
		
		data.put("alma", "piros");
		data.put("al", "kek");
		
		vals = data.values();
		
		assertTrue(vals.size() == 2);
		assertTrue(vals.contains("piros"));
		
		data.remove("al");
		
		vals = data.values();
		
		assertTrue(vals.size() == 1);
		assertTrue(vals.contains("piros"));
	}
	
	public void testEntrySet() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		Set<Entry<String, String>> es = data.entrySet();
		
		assertTrue(es.size() == 2);
		for (Entry<String, String> it : es) {
			if (it.getKey().equals("alma"))
				assertTrue(it.getValue().equals("piros"));
			if (it.getKey().equals("szilva"))
				assertTrue(it.getValue().equals("kek"));
		}
	}

}
