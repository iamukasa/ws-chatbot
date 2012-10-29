package de.vlad.supportcenter.model.enums;


/**
 * The Enum TicketStatus.
 */
public enum TicketStatus {
	
	/** The OPEN. */
	OPEN,
/** The CLOSED. */
CLOSED,
/** The ANSWERED. */
ANSWERED,
/** The I n_ progress. */
IN_PROGRESS,
/** The UNDEFINED. */
UNDEFINED;
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
    public String toString() {
        if (this.equals(OPEN)) {
            return "Offen";
        }
        if (this.equals(CLOSED)) {
            return "Geschlossen";
        }
        if (this.equals(ANSWERED)) {
            return "Beantwortet";
        }
        if (this.equals(IN_PROGRESS)) {
            return "In Bearbeitung";
        }
        if(this.equals(UNDEFINED)) {
        	return "undefined";
        }
        return "";
    }
}
