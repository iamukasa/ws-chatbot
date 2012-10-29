package de.vlad.supportcenter.model.enums;


/**
 * The Enum Theme.
 */
public enum Theme {
	
	/** The REINDEER. */
	REINDEER, 
 /** The RUNO. */
 RUNO, 
 /** The CHAMELEON. */
 CHAMELEON;
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
    public String toString() {
        if (this.equals(REINDEER)) {
            return "Reindeer";
        }
        if (this.equals(RUNO)) {
            return "Runo";
        }
        if (this.equals(CHAMELEON)) {
            return "Chameleon";
        }
        return "";
    }
}
