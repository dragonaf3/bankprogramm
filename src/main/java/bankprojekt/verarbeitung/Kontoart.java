package bankprojekt.verarbeitung;

/**
 * stellt die von der Bank angebotenen Kontoarten dar
 */
public enum Kontoart {
	/** Girokonto */
	GIROKONTO("ganz hoher Dispo"),
	/** Sparbuch */
	SPARBUCH("ganz vioele Zinsen"),
	/** Festgeldkonto */
	FESTGELDKONTO("kommt später...");
	
	private final String werbetext;
	
	Kontoart(String werbung)   //ist automatisch private
	{
		this.werbetext = werbung;
	}

	/**
	 * liefert den Werbetext
	 * @return the werbetext
	 */
	public String getWerbetext() {
		return werbetext;
	}
	
	
}
