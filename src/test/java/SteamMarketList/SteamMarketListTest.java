package SteamMarketList;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SteamMarketListTest {

	private static SteamMarketList sm = new SteamMarketList();

	@Test
	public void testGetMarketLinks() throws FileNotFoundException, IOException {

		assertNotNull(sm.getMarketLinks(System.getProperty("user.dir") + "/src/test/resources/testLinks"));
		assertEquals(
				Arrays.asList(
						"3.8;http://steamcommunity.com/market/listings/730/AK-47%20%7C%20Cartel%20%28Well-Worn%29"),
				sm.getMarketLinks(System.getProperty("user.dir") + "/src/test/resources/testLinks"));
	}

	@Test
	public void testGetCurrencies() {
		assertNull(sm.getCurrencies());
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("CNY", 6.525795);
		map.put("EUR", 0.883626);
		sm.setCurrencies(map);
		assertNotNull(sm.getCurrencies());
	}

	@Test
	public void testGetInfoFromLink() throws IOException {
		assertNotNull(sm.getInfoFromLink(
				"http://steamcommunity.com/market/listings/730/AWP%20%7C%20Elite%20Build%20(Battle-Scarred)"));
		assertNotNull(sm.getInfoFromLink(
				"http://steamcommunity.com/market/listings/730/AWP%20%7C%20Asiimov%20%28Factory%20New%29"));
	}

	@Test
	public void testGetValutes() throws FileNotFoundException, IOException {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("CNY", 6.525795);
		map.put("EUR", 0.883626);
		assertNotNull(sm.getValutes(System.getProperty("user.dir") + "/src/test/resources/testValutes.data"));
		assertEquals(map, sm.getValutes(System.getProperty("user.dir") + "/src/test/resources/testValutes.data"));
	}

	@Test
	public void testGetValuteToUsd() {
		assertEquals(1.697, sm.valuteToUsd("1,50€", "2003"), 0.05);
		assertEquals(1.7, sm.valuteToUsd("¥ 11.1", "2023"), 0.05);
		assertEquals(3.4, sm.valuteToUsd("¥ 22.2", "2023"), 0.05);
		assertEquals(5014.0, sm.valuteToUsd("Sold", null), 0.05);
		assertEquals(-1.0, sm.valuteToUsd("valami", "-1"), 0.05);
	}
}
