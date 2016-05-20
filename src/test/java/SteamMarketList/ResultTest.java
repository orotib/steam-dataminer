package SteamMarketList;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class ResultTest {
	private static Result re = new Result(Arrays.asList("5", "3"), Arrays.asList("55", "33"));

	@Test
	public void testGetIds() {
		assertEquals(Arrays.asList("5", "3"), re.getIds());
		assertEquals("5", re.getIds().get(0));
		assertEquals("3", re.getIds().get(1));
	}

	@Test
	public void testSetIds() {
		re.setIds(Arrays.asList("1", "2"));
		assertEquals(Arrays.asList("1", "2"), re.getIds());
	}

	@Test
	public void testGetCurrencies() {
		assertEquals(Arrays.asList("55", "33"), re.getCurrencies());
		assertEquals("55", re.getCurrencies().get(0));
		assertEquals("33", re.getCurrencies().get(1));
	}
	
	@Test
	public void testSetCurrencies() {
		re.setCurrencies(Arrays.asList("11", "22"));
		assertEquals(Arrays.asList("11", "22"), re.getCurrencies());
	}
	
	@Test
	public void testToString() {
		assertEquals("[55, 33]", re.toString());
	}
}
