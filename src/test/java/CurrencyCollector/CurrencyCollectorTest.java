package CurrencyCollector;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.junit.Test;

public class CurrencyCollectorTest {

	private static CurrencyCollector cc = new CurrencyCollector();

	@Test
	public void testGetDownloadLink() throws FileNotFoundException, IOException {
		assertNotNull(
				cc.getDownloadLink(System.getProperty("user.dir") + "/src/test/resources/testcurrency-download-link"));
		assertEquals(
				"http://apilayer.net/api/live?access_key=b014d02cc96b4faeea282020d15091f4&currencies=AED,AFN,ALL,AMD,ANG,AOA,ARS,AUD,AWG,AZN,BAM,BBD,BDT,BGN,BHD,BIF,BMD,BND,BOB,BRL,BSD,BTC,BTN,BWP,BYR,BZD,CAD,CDF,CHF,CLF,CLP,CNY,COP,CRC,CUP,CVE,CZK,DJF,DKK,DOP,DZD,EEK,EGP,ERN,ETB,EUR,FJD,FKP,GBP,GEL,GGP,GHS,GIP,GMD,GNF,GTQ,GYD,HKD,HNL,HRK,HTG,HUF,IDR,ILS,IMP,INR,IQD,IRR,ISK,JEP,JMD,JOD,JPY,KES,KGS,KHR,KMF,KPW,KRW,KWD,KYD,KZT,LAK,LBP,LKR,LRD,LSL,LTL,LVL,LYD,MAD,MDL,MGA,MKD,MMK,MNT,MOP,MRO,MTL,MUR,MVR,MWK,MXN,MYR,MZN,NAD,NGN,NIO,NOK,NPR,NZD,OMR,PAB,PEN,PGK,PHP,PKR,PLN,PYG,QAR,RON,RSD,RUB,RWF,SAR,SBD,SCR,SDG,SEK,SGD,SHP,SLL,SOS,SRD,STD,SVC,SYP,SZL,THB,TJS,TMT,TND,TOP,TRY,TTD,TWD,TZS,UAH,UGX,USD,UYU,UZS,VEF,VND,VUV,WST,XAF,XAG,XAU,XCD,XDR,XOF,XPF,YER,ZAR,ZMK,ZMW,ZWL",
				cc.getDownloadLink(System.getProperty("user.dir") + "/src/test/resources/testcurrency-download-link"));
	}

	@Test
	public void testCurrencyToFile() throws UnknownHostException, FileNotFoundException, UnsupportedEncodingException, IOException {
		File f = new File("testValutes.data");
		assertTrue(cc.currencyToFile(f.getName()));
		f.delete();
	}
}
