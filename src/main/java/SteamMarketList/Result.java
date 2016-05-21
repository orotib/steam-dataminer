package SteamMarketList;

import java.util.List;

/**
 * Az azonosítók és valuták értékeit reprezentáló osztály.
 */
public class Result {
	/**
	 * Az azonosítókat tartalmazó lista.
	 */
	private List<String> ids;
	/**
	 * Az azonosítókhoz tartozó valuteértékeket tartalmazó lista.
	 */
	private List<String> currencies;

	/**
	 * A konstruktor ami beállítja az értékeket.
	 * 
	 * @param ids az azonosítókat tartalmazó lista
	 * @param currencies az valuteértékeket tartalmazó lista
	 */
	public Result(List<String> ids, List<String> currencies) {
		super();
		this.ids = ids;
		this.currencies = currencies;
	}

	/**
	 * Visszaadja az azonosítókat tartalmazó listát.
	 * 
	 * @return az azonosítókat tartalmazó lista
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * Beállítja az azonosítókat tartalmazó listát.
	 * 
	 * @param ids az azonosítókat tartalmazó lista
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**
	 * Visszaadja a valuteértékeket tartalmazó listát.
	 * 
	 * @return a valutaértékeket tartalmazó lista
	 */
	public List<String> getCurrencies() {
		return currencies;
	}

	/**
	 * Beállítja a valutaértékeket tartalmazó listát.
	 * 
	 * @param currencies a valutaértékeket tartalmazó lista
	 */
	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}

	@Override
	public String toString() {
		return currencies.toString();
	}
}
