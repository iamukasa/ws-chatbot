package de.vlad.supportcenter.model.enums;


/**
 * The Enum ChatStatus.
 */
public enum ChatStatus {
	
	/** The I n_ progress. */
	IN_PROGRESS, 
 /** The CLOSED. */
 CLOSED, 
 /** The WAITING. */
 WAITING;
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
    public String toString() {
        if (this.equals(IN_PROGRESS)) {
            return "In Bearbeitung";
        }
        if (this.equals(CLOSED)) {
            return "Geschlossen";
        }
        if (this.equals(WAITING)) {
            return "Warten";
        }
        return "";
    }
}
