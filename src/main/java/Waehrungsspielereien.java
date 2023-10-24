//import java.time.LocalDate;
//
//import bankprojekt.verarbeitung.GesperrtException;
//import bankprojekt.verarbeitung.Girokonto;
//import bankprojekt.verarbeitung.Konto;
//import bankprojekt.verarbeitung.Kunde;
//import bankprojekt.verarbeitung.Sparbuch;
//import bankprojekt.verarbeitung.Waehrung;
//
///**
// * spielt ein wenig mit den Währungsmethoden des Bankprojektes herum
// * @author Doro
// *
// */
//public class Waehrungsspielereien {
//
//	/**
//	 * ein kleines Programm mit 2 Konten in verschiedenen Währungen
//	 * @param args wird nicht verwendet
//	 */
//	public static void main(String[] args)  {
//		try {
//			Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));
//			Konto meinKonto = new Girokonto(ich, 1234, 1000.0);
//
//			meinKonto.waehrungswechsel(Waehrung.BGN);
//			System.out.println("Nach Währungswechsel nach BGN: " + meinKonto);
//			meinKonto.einzahlen(1000, Waehrung.EUR);
//			System.out.println("1000 EUR eingezahlt: " + meinKonto);
//			meinKonto.einzahlen(1000, Waehrung.DKK);
//			System.out.println("1000 DKK eingezahlt: " + meinKonto);
//
//			meinKonto.waehrungswechsel(Waehrung.MKD);
//			System.out.println("Nach Währungswechsel nach MKD: " + meinKonto);
//			boolean hatGeklappt;
//			hatGeklappt = meinKonto.abheben(1000);
//			System.out.println("1000 MKD abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
//			hatGeklappt = meinKonto.abheben(1000, Waehrung.EUR);
//			System.out.println("1000 EUR abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
//			hatGeklappt = meinKonto.abheben(1000, Waehrung.DKK);
//			System.out.println("1000 DKK abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
//
//			meinKonto = new Sparbuch(ich, 9876);
//			meinKonto.waehrungswechsel(Waehrung.BGN);
//			System.out.println("Nach Währungswechsel nach BGN: " + meinKonto);
//			meinKonto.einzahlen(1000, Waehrung.EUR);
//			System.out.println("1000 EUR eingezahlt: " + meinKonto);
//			meinKonto.einzahlen(1000, Waehrung.DKK);
//			System.out.println("1000 DKK eingezahlt: " + meinKonto);
//
//			meinKonto.waehrungswechsel(Waehrung.MKD);
//			System.out.println("Nach Währungswechsel nach MKD: " + meinKonto);
//			hatGeklappt = meinKonto.abheben(1000);
//			System.out.println("1000 MKD abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
//			hatGeklappt = meinKonto.abheben(1000, Waehrung.EUR);
//			System.out.println("1000 EUR abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
//			hatGeklappt = meinKonto.abheben(1000, Waehrung.DKK);
//			System.out.println("1000 DKK abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
//		} catch (GesperrtException e)
//		{}  //nichts tun, tritt hier nicht auf
//	}
//
//}
