import java.time.LocalDate;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kontoart;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

/**
 * Testprogramm für Konten
 * @author Doro
 *
 */
public class KontenSpielereien {

	/**
	 * Testprogramm für Konten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
	String sprache = "Deutsch";
		switch(sprache) {
			case "Deutsch" -> System.out.println("Guten Tag");
			default -> System.out.println("Hello");
		}
		String eingabe = "FESTGELDKONTO";
		
		Kontoart art = Kontoart.valueOf(eingabe);
		System.out.println(art.name() + " " + art.ordinal() + " " + art.getWerbetext());
		
		for(Kontoart a: Kontoart.values())
		{
			System.out.println(a.name() + ": " + a.getWerbetext());
		}
			
		
		Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));

		Girokonto meinGiro = new Girokonto(ich, 1234, 1000.0);
		//meinGiro.einzahlen(50);
		System.out.println(meinGiro);
		
		Sparbuch meinSpar = new Sparbuch(ich, 9876);
		meinSpar.einzahlen(50);
		try
		{
			boolean hatGeklappt = meinSpar.abheben(70);
			System.out.println("Abhebung hat geklappt: " + hatGeklappt);
			System.out.println(meinSpar);
		}
		catch (GesperrtException e)
		{
			System.out.println("Zugriff auf gesperrtes Konto - Polizei rufen!");
		}
		
		Konto meins = new Girokonto();
		System.out.println(meins.toString());
			//--> Objekt entscheidet, nicht die Variable
		System.out.println("...........................");
		//meins.ausgeben();
			//--> Objekt entscheidet, nicht die Variable
		System.out.println("...........................");
	}

}
