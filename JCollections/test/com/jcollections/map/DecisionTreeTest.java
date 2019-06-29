package com.jcollections.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DecisionTreeTest {
	
	private Map<String, String> data;
	
	@Before
	public void setup() {
		data = new DecisionTree<String>();
	}
	
	@Test
	public void testEmpty() {
		assertTrue(data.isEmpty());
	}
	
	@Test
	public void testAddOne() {
		data.put("alma", "piros");
		
		assertEquals(data.size(), 1);
	}
	
	@Test
	public void testContainsKey() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		assertTrue(data.containsKey("alma"));
		assertTrue(data.containsKey("szilva"));
		
		assertFalse(data.containsKey("alm"));
		assertFalse(data.containsKey(null));
	}
	
	@Test
	public void testContainsValue() {
		data.put("alma", "piros");
		data.put("almakukac", "sarga");
		
		assertTrue(data.containsValue("piros"));
		assertTrue(data.containsValue("sarga"));
		
		assertFalse(data.containsKey("piro"));
		assertFalse(data.containsValue(null));
	}
	
	@Test
	public void testPutAndGet() {
		data.put("alma", "piros");
		data.put("almakukac", "sarga");
		
		assertTrue(data.get("alma").equals("piros"));
		assertTrue(data.get("almakukac").equals("sarga"));
		
		assertNull(data.get("szilva"));
	}
	
	@Test
	public void testClear() {
		data.put("alma", "piros");
		
		assertEquals(data.size(), 1);
		
		data.clear();
		
		assertTrue(data.isEmpty());
	}
	
	@Test
	public void tesKeySet() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		Set<String> keys = data.keySet();
		
		assertEquals(keys.size(), 2);
		assertTrue(keys.contains("alma"));
		assertTrue(keys.contains("szilva"));
	}
	
	@Test
	public void testValues() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		Collection<String> vals = data.values();
		
		assertEquals(vals.size(), 2);
		assertTrue(vals.contains("piros"));
		assertTrue(vals.contains("kek"));
	}
	
	@Test
	public void testRemove() {
		data.put("alma", "piros");
		data.put("al", "kek");
		
		Collection<String> vals = data.values();
		
		assertEquals(vals.size(), 2);
		assertTrue(vals.contains("piros"));
		
		data.remove("alma");
		
		vals = data.values();
		
		assertEquals(vals.size(), 1);
		assertFalse(vals.contains("piros"));
		assertTrue(vals.contains("kek"));
		
		assertTrue(data.containsKey("al"));
		assertFalse(data.containsKey("alma"));
		
		data.clear();
		
		data.put("alma", "piros");
		data.put("al", "kek");
		
		vals = data.values();
		
		assertEquals(vals.size(), 2);
		assertTrue(vals.contains("piros"));
		
		data.remove("al");
		
		vals = data.values();
		
		assertEquals(vals.size(), 1);
		assertTrue(vals.contains("piros"));
	}
	
	@Test
	public void removeFromEmpty() {
		String tmp = data.remove("test");
		
		assertNull(tmp);
	}
	
	@Test
	public void testEntrySet() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		Set<Entry<String, String>> es = data.entrySet();
		
		assertEquals(es.size(), 2);
		for (Entry<String, String> it : es) {
			if (it.getKey().equals("alma"))
				assertTrue(it.getValue().equals("piros"));
			if (it.getKey().equals("szilva"))
				assertTrue(it.getValue().equals("kek"));
		}
	}
	
	@Test
	public void removeInvalidKey() {
		data.put("alma", "piros");
		data.put("szilva", "kek");
		
		assertNull(data.remove("alfa"));
	}
	
	@Test
	public void putAll() {
		Map<String, String> toPut = new HashMap<>();
		toPut.put("alma", "piros");
		toPut.put("szilva", "kek");
		
		data.putAll(toPut);
		
		assertEquals(data.size(), 2);
		assertTrue(data.containsKey("alma"));
		assertTrue(data.containsKey("szilva"));
		assertTrue(data.containsValue("piros"));
		assertTrue(data.containsValue("kek"));
	}
	
	@Test
	public void testIsEmpty() {
		assertTrue(data.isEmpty());
		
		data.put("alma", "piros");
		assertFalse(data.isEmpty());
	}

}
