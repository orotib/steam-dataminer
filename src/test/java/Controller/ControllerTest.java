package Controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {

	private static Controller co = new Controller();

	@Test
	public void testGetLinkToReadable() {
		assertEquals("StatTrak|P250|Hive|MinimalWear", co.getLinkToReadable(
				"http://steamcommunity.com/market/listings/730/StatTrak%E2%84%A2%20P250%20%7C%20Hive%20%28Minimal%20Wear%29"));
		assertEquals("DesertEagle|Corinthian|Field-Tested", co.getLinkToReadable(
				"http://steamcommunity.com/market/listings/730/Desert%20Eagle%20%7C%20Corinthian%20%28Field-Tested%29"));
	}

	@Test
	public void testPrintDataToScreen() {
		assertEquals("1: 106,67% 1,50 1,60 DesertEagle|Corinthian|Field-Tested", co.dataToScreen(1, 1.5, 1.6,
				"http://steamcommunity.com/market/listings/730/Desert%20Eagle%20%7C%20Corinthian%20%28Field-Tested%29"));

	}

	@Test
	public void testWaitSeconds() {
		assertTrue(co.waitSeconds(0, false));
	}
}
