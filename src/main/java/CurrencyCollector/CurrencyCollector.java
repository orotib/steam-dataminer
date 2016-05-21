package CurrencyCollector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Controller.Controller;

/**
 * Valutaárfolyamok kezelését megvalósító osztály.
 * 
 * A {@link #valutes} statikus konstans a valutákat fogja tartalmazni az első
 * futtatás után.
 * 
 */
public class CurrencyCollector {

	/**
	 * Az első futtatás után a valuták értékeit tartalmazó fájl elérési
	 * útvonala.
	 */
	public static final String valutes = Controller.homedir + "/valutes.data";

	/**
	 * A {@code filename} paraméterül kapott fájlból kiolvassa a valutaértékeket
	 * tartalmazó linket.
	 * 
	 * @param filename
	 *            a link
	 * @return valutaértékeket tartalmazó szöveg
	 * @throws FileNotFoundException
	 *             ha nem létezik a paraméterül kapott fájl
	 * @throws IOException
	 *             ha bármi probléma történne beolvasás során
	 */
	@SuppressWarnings("resource")
	protected String getDownloadLink(String filename) throws FileNotFoundException, IOException {
		return new BufferedReader(new FileReader(filename)).readLine();
	}

	/**
	 * A pénznemek azonosítóit és értékeit tartalmazó {@code Map}-et adja
	 * vissza.
	 * 
	 * @param filename
	 *            a pénznemek azonosítóit és értékeit tartalmazó fájl elérési
	 *            útvonala
	 * @return pénznemek azonosítóit és értékeit tartalmazó {@code Map}
	 * @throws UnknownHostException
	 *             ha nincs internetkapcsolat
	 * @throws IOException
	 *             ha bármi probléma történne beolvasás során
	 */
	public Map<String, Double> getCurrencyMap(String filename) throws UnknownHostException, IOException {
		Map<String, Double> curMap = new HashMap<String, Double>();

		String newJson = IOUtils.toString(new URL(getDownloadLink(Controller.homedir + "/" + filename)));
		JsonElement element = new Gson().fromJson(newJson, JsonElement.class);
		JsonObject object = (JsonObject) element.getAsJsonObject().get("quotes");

		for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
			String curCode = entry.getKey().substring(3);
			Double curValue = entry.getValue().getAsDouble();
			curMap.put(curCode, curValue);
		}

		return curMap;
	}

	/**
	 * A pénznemek azonosítóit és értékeit írja a {@code filename} paraméterül
	 * kapott fájlba.
	 * 
	 * @param filename
	 *            a fájl neve
	 * @return az írás sikerességét jelzi
	 * @throws UnknownHostException
	 *             ha nincs internetkapcsolat
	 * @throws FileNotFoundException
	 *             ha nem létezik a paraméterül kapott fájl
	 * @throws UnsupportedEncodingException
	 *             ha nem támogatott a fájl kódolása
	 * @throws IOException
	 *             ha bármi probléma történne beolvasás során
	 */
	public boolean currencyToFile(String filename)
			throws UnknownHostException, FileNotFoundException, UnsupportedEncodingException, IOException {
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		for (Map.Entry<String, Double> entry : getCurrencyMap("currency-download-link").entrySet())
			writer.println(entry.getKey() + ";" + entry.getValue());
		writer.close();

		return true;
	}
}
