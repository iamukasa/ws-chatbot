package de.vlad.supportcenter.model.enums;


/**
 * The Enum SystemStatus.
 */
public enum SystemStatus {
	
	/** The OFFLINE. */
	OFFLINE, 
 /** The ONLINE. */
 ONLINE, 
 /** The NO t_ exists. */
 NOT_EXISTS;
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
    public String toString() {
        if (this.equals(OFFLINE)) {
            return "Offline";
        }
        if (this.equals(ONLINE)) {
            return "Online";
        }
        return "";
    }
}
