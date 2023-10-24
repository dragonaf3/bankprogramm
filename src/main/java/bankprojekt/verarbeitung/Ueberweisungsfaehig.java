package bankprojekt.verarbeitung;

/**
 * Dieses Interface zeigt an, dass mit einem 
 * implementierenden Konto Überweisungen
 * durchgeführt werden können
 * @author Dorothea Hubrich
 *
 */
public interface Ueberweisungsfaehig {
    /**
     * bucht den angegebenen Betrag von this als Überweisung ab, 
     * falls es nicht gesperrt ist und alle kontospezifischen 
     * Regeln für die Überweisung eingehalten werden.
     * Am Empfängerkonto wird keine Änderung vorgenommen, da davon ausgegangen wird, dass dieses sich
     * bei einer anderen Bank befindet.
     * @param betrag double
     * @param empfaenger String
     * @param nachKontonr int
     * @param nachBlz int
     * @param verwendungszweck String
     * @return boolean true, wenn die Überweisungsabbuchung ausgeführt wurde,
     *                 false, wenn nicht (weil kontospezifische Regeln verletzt wurden, 
     *                 	     z.B. Kontostand reicht nicht aus)
     * @throws GesperrtException wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der Betrag negativ bzw. NaN bzw. unendlich ist oder
     * 					empfaenger oder verwendungszweck null ist
     */
    public boolean ueberweisungAbsenden(double betrag, 
    		String empfaenger, long nachKontonr, 
    		long nachBlz, String verwendungszweck) 
    				throws GesperrtException;
    
    /**
     * this empfängt den angegebenen betrag per Überweisung
     * @param betrag double
     * @param vonName String
     * @param vonKontonr int
     * @param vonBlz int
     * @param verwendungszweck String
     * @throws IllegalArgumentException wenn der Betrag negativ bzw. NaN bzw. unendlich ist oder
     * 									vonName oder verwendungszweck null ist
     */
    public void ueberweisungEmpfangen(double betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck);
}
