package SteamMarketList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Steam Piac adatait kinyerő és kezelő osztály.
 */
public class SteamMarketList {

	private static Logger logger = LoggerFactory.getLogger(SteamMarketList.class);
	/**
	 * A pénznemek azonosítóit és értékeit tároló {@link Map}.
	 */
	private Map<String, Double> currencies;

	/**
	 * Beolvassa a fájlból a vizsgálandó linkeket.
	 * 
	 * @param filename
	 *            a linkeket tartalmazó fájl elérési útvonala
	 * @return linkek listája
	 * @throws FileNotFoundException
	 *             ha nem létezne a paraméterül kapott fájl
	 * @throws IOException
	 *             ha bármi történne a beolvasás során
	 */
	public List<String> getMarketLinks(String filename) throws FileNotFoundException, IOException {
		List<String> tmp = new ArrayList<String>();

		FileReader fileReader;
		fileReader = new FileReader(filename);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		while ((line = bufferedReader.readLine()) != null)
			tmp.add(line);

		// Collections.shuffle(tmp);
		return tmp;
	}

	/**
	 * Visszaadja a pénznemek azonosítóit és értékeit tároló {@link Map}-jét.
	 * 
	 * @return a pénznemek azonosítóit és értékeit tároló {@link Map}
	 */
	public Map<String, Double> getCurrencies() {
		return currencies;
	}

	/**
	 * Beállítja a pénznemek azonosítóit és értékeit tároló {@code Map}-et.
	 * 
	 * @param currencies
	 *            a pénznemek azonosítóit és értékeit tároló {@code Map}
	 */
	public void setCurrencies(Map<String, Double> currencies) {
		this.currencies = currencies;
	}

	/**
	 * Visszaadja a kapott linkről az aktuális ajánlatokat.
	 * 
	 * @param link
	 *            a vizsgálandó link
	 * @return {@link Result} az ajánlatokat tároló osztály
	 * @throws IOException
	 *             ha bármi történne adatkérés alatt
	 * @see <a href=
	 *      "http://steamcommunity.com/market/listings/730/AWP%20%7C%20Elite%20Build%20(Battle-Scarred)">
	 *      Egy példalink</a>
	 */
	public Result getInfoFromLink(String link) throws IOException {
		List<String> ids = new ArrayList<String>();
		List<String> currencies = new ArrayList<String>();

		Document doc = null;
		doc = Jsoup.connect(link).timeout(10 * 1000).get();

		for (String s : doc.data().split("\n")) {
			if (s.contains("currencyid"))
				for (String ss : s.split("currencyid"))
					ids.add(ss.substring(3, 7));
		}

		Elements values = doc.getElementsByClass("market_table_value");
		for (Element e : values)
			for (Element ee : e.select("span"))
				if (ee.className().equals("market_listing_price market_listing_price_with_fee"))
					currencies.add(ee.text());

		if (ids.size() == 0)
			return new Result(null, null);
		ids.remove(0);
		return new Result(ids, currencies);
	}

	/**
	 * Kiolvassa a pénznemek azonosítóit és értékeit a {@code filename} fájlból.
	 * 
	 * @param filename
	 *            a valuták azonosítóit és értékeit tároló fájl elérési útvonala
	 * @return pénznemek azonosítóit és értékeit tároló {@link Map}
	 * @throws FileNotFoundException
	 *             ha nem létezne a paraméterül kapott fájl
	 * @throws IOException
	 *             ha bármi történne a beolvasás alatt
	 */
	public Map<String, Double> getValutes(String filename) throws FileNotFoundException, IOException {
		Map<String, Double> currencies = new HashMap<String, Double>();
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			String[] tmp = line.split(";");
			currencies.put(tmp[0], Double.parseDouble(tmp[1]));
		}
		return currencies;
	}

	/**
	 * Dollárra konvertálja az adott valutát.
	 * 
	 * @param price
	 *            a weblapon megjelenő érték
	 * @param id
	 *            a pénznem azonosító
	 * @return dollárra konvertált érték
	 */
	public double valuteToUsd(String price, String id) {
		if (price.contains("Sold"))
			return 5014.0;
		price = price.replace("-", "");
		switch (id) {
		case "2001":
			return Double.parseDouble(price.replace("$", "").replace("USD", "")) / currencies.get("USD");
		case "2002":
			return Double.parseDouble(price.replace("£", "")) / currencies.get("GBP");
		case "2003":
			return Double.parseDouble(price.replace("€", "").replace(",", ".")) / currencies.get("EUR");
		case "2004":
			return Double.parseDouble(price.replace("CHF", "")) / currencies.get("CHF");
		case "2005":
			return Double.parseDouble(price.split(" ")[0].replace(",", ".")) / currencies.get("RUB");
		case "2006":
			logger.debug("Unkown Currency ID - {}", id);
			return 2006.0;
		case "2007":
			return Double.parseDouble(price.split(" ")[1].replace(",", "")) / currencies.get("BRL") / 100;
		case "2008":
			return Double.parseDouble(price.split(" ")[1].replace(",", "").replace("¥", "")) / currencies.get("JPY");
		case "2009":
			return Double.parseDouble(price.replace(" kr", "").replace(".", "").replace(",", "."))
					/ currencies.get("SEK");
		case "2010":
			return Double.parseDouble(price.replace("Rp ", "").replace(" ", "")) / currencies.get("IDR");
		case "2011":
			return Double.parseDouble(price.replace("RM", "").replace(",", "")) / currencies.get("MYR");
		case "2012":
			return Double.parseDouble(price.replace("P", "").replace(",", "")) / currencies.get("PHP");
		case "2013":
			return Double.parseDouble(price.replace("S$", "")) / currencies.get("SGD");
		case "2014":
			return Double.parseDouble(price.replace("฿", "").replace(",", "")) / currencies.get("THB");
		case "2015":
			logger.debug("Unkown Currency ID - {}", id);
			return 2015.0;
		case "2016":
			return Double.parseDouble(price.replace("₩", "").replace(",", "")) / currencies.get("KRW");
		case "2017":
			return Double.parseDouble(price.split(" ")[0].replace(",", ".")) / currencies.get("TRY");
		case "2018":
			logger.debug("Unkown Currency ID - {}", id);
			return 2018.0;
		case "2019":
			return Double.parseDouble(price.replace("Mex$ ", "").replace(",", "")) / currencies.get("MXN");
		case "2020":
			return Double.parseDouble(price.split(" ")[1].replace(",", ".")) / currencies.get("CAD");
		case "2021":
			logger.debug("Unkown Currency ID - {}", id);
			return 2021.0;
		case "2022":
			return Double.parseDouble(price.replace("NZ$ ", "")) / currencies.get("NZD");
		case "2023":
			return Double.parseDouble(price.split(" ")[1].replace(",", "")) / currencies.get("CNY");
		case "2024":
			return Double.parseDouble(price.replace("₹ ", "").replace(",", "")) / currencies.get("INR");
		case "2025":
			return Double.parseDouble(price.replace("CLP$", "").replace(".", "").replace(",", "."))
					/ currencies.get("CLP");
		case "2026":
			return Double.parseDouble(price.replace("S/.", "")) / currencies.get("SGD");
		case "2027":
			return Double.parseDouble(price.replace("COL$", "").replace(".", "").replace(",", "."))
					/ currencies.get("COP");
		case "2028":
			return Double.parseDouble(price.replace("R ", "").replace(" ", "")) / currencies.get("ZAR");
		case "2029":
			return Double.parseDouble(price.replace("HK$", "").replace(",", "")) / currencies.get("HKD");
		case "2030":
			return Double.parseDouble(price.replace("NT$ ", "").replace(",", "")) / currencies.get("TWD");
		case "2031":
			return Double.parseDouble(price.replace(" SR", "")) / currencies.get("SAR");
		case "2032":
			return Double.parseDouble(price.replace(" AED", "")) / currencies.get("AED");
		default:
			logger.debug("Unkown Currency ID");
			break;
		}
		return -1.0;
	}
}
