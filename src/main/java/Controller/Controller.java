package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CurrencyCollector.CurrencyCollector;
import SteamMarketList.Result;
import SteamMarketList.SteamMarketList;

/**
 * A Vezérlő osztály ami irányítja az adatok gyűjtését.
 * 
 * A {@code homedir} a tárolt adatok mappájának elérési útvonala.
 */
public class Controller {

	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	/**
	 * A tárolt adatok mappájának elérési útvonala.
	 */
	public static Path homedir;

	static {
		if (SystemUtils.IS_OS_WINDOWS) {
			homedir = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "Steam Data Miner");
		} else if (SystemUtils.IS_OS_LINUX) {
			homedir = Paths.get(System.getProperty("user.home"), ".steamdataminer");
		}
	}

	/**
	 * A képernyőre kiírandó olvasható szöveget adja vissza.
	 * 
	 * @param k
	 *            a vizsgált elem sorszáma
	 * @param value1
	 *            a vizsgált elem legolcsóbb ajánlata
	 * @param value2
	 *            a vizsgált elem második legolcsóbb ajánlata
	 * @param link
	 *            a vizsgált elem linkje
	 * @return a képernyőre kiírandó könnyen olvasható szöveg
	 */
	protected String dataToScreen(int k, double value1, double value2, String link) {
		DecimalFormat df = new DecimalFormat(".00");
		double percent = value2 / value1 * 100;
		StringBuilder sb = new StringBuilder();
		sb.append(k).append(": ").append(df.format(percent)).append("% ").append(df.format(value1)).append("€ ")
				.append(df.format(value2)).append("€").append(" ").append(getLinkToReadable(link));
		return sb.toString();
	}

	/**
	 * A {@code sec} által kapott értéknyi másodpercet vár.
	 * 
	 * Ha a {@code note} igaz, akkor a képernyőre írja a várakozás alatt eltelt
	 * időt.
	 * 
	 * @param sec
	 *            a várakozás hossza másodpercben
	 * @param note
	 *            a logolás jelző változó
	 * @return a várakozás sikerességét adja vissza
	 */
	protected boolean waitSeconds(int sec, boolean note) {
		for (int i = 0; i < sec; i++) {
			try {
				Thread.sleep(1000);
				if (note && i % 10 == 0)
					logger.trace(String.valueOf(i) + "s");
			} catch (InterruptedException e) {
				logger.trace("Várakozás megszakítva!");
				return false;
			}
		}
		return true;
	}

	/**
	 * A paraméterül kapott linket alakítja olvashatóvá.
	 * 
	 * @param link
	 *            az olvashatóvá alakítandó link
	 * @return az olvasható link
	 */
	protected String getLinkToReadable(String link) {
		return link.split("/")[6].replace("%E2%84%A2%20", "|").replace("%20%7C%20", "|").replace("%20%28", "|")
				.replace("%20", "").replace("%29", "");
	}

	/**
	 * A Vezérlő osztály {@code main} metódusa.
	 * 
	 * @param args
	 *            a muszáj args szöveges tömb
	 */
	public static void main(String[] args) {
		SteamMarketList sml = new SteamMarketList();
		CurrencyCollector cc = new CurrencyCollector();
		Controller controller = new Controller();
		final String linkFilename = homedir + "/links";
		int k = 0;

		while (true) {
			try {
				cc.currencyToFile(CurrencyCollector.valutes);
				logger.info("Sikeres valutaárfolyam mentés!");
			} catch (UnknownHostException e) {
				logger.error("Nincs internetkapcsolat!");
				controller.waitSeconds(60, false);
			} catch (FileNotFoundException e) {
				logger.error("Hiányzó fájl: currency-download-link");
				System.exit(-1);
			} catch (UnsupportedEncodingException e) {
				logger.error("Nem támogatott kódolás: currency-download-link");
				System.exit(-2);
			} catch (IOException e) {
				logger.error("IO kivétel!");
				System.exit(-4);
			}
			try {
				sml.setCurrencies(sml.getValutes(CurrencyCollector.valutes));
				logger.info("Valutaárfolyam beolvasása sikerült!");
			} catch (FileNotFoundException e) {
				logger.error("Fájl nem található: valutes.data");
				System.exit(-1);
			} catch (IOException e) {
				logger.error("IO kivétel!");
				System.exit(-2);
			}
			try {
				for (String str : sml.getMarketLinks(linkFilename)) {
					List<Double> priceList = new ArrayList<Double>();
					str = str.split(";")[1];
					Result ret = null;
					try {
						ret = sml.getInfoFromLink(str);
					} catch (Exception e) {
						ret = new Result(null, null);
					}
					if (ret.getCurrencies() == null) {
						logger.info("Túl gyors adatkérés!");
						controller.waitSeconds(120, true);
						continue;
					}
					for (int i = 0; i < ret.getIds().size(); i++) {
						priceList.add(sml.valuteToUsd(ret.getCurrencies().get(i), ret.getIds().get(i))
								* sml.getCurrencies().get("EUR"));
					}
					Collections.sort(priceList);
					logger.info(controller.dataToScreen(++k, priceList.get(0), priceList.get(1), str));

					if (priceList.size() > 2)
						if (priceList.get(0) * 1.25 < priceList.get(1))
							logger.info("--- BUYIT ---");
					controller.waitSeconds(13, false);
				}
			} catch (FileNotFoundException e) {
				logger.error("Fájl nem található: links");
				System.exit(-3);
			} catch (IOException e) {
				logger.error("IO kivétel!");
				System.exit(-4);
			}
		}
	}
}
