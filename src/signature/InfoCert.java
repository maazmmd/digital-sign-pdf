package signature;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * @author mohammedmaaz
 *
 */

public class InfoCert {
	private String C;

	private String O;

	private String OID;

	private String OU;

	private String T;

	private String ST;

	private String L;

	private String SERIALNUMBER;

	private String SURNAME;

	private String GIVENNAME;

	private String CN;

	private String EMAILADDRESS;

	public String getC() {
		return this.C;
	}

	public String getO() {
		return this.O;
	}

	public String getOID() {
		return this.OID;
	}

	public String getOU() {
		return this.OU;
	}

	public String getT() {
		return this.T;
	}

	public String getST() {
		return this.ST;
	}

	public String getL() {
		return this.L;
	}

	public String getSERIALNUMBER() {
		return this.SERIALNUMBER;
	}

	public String getSURNAME() {
		return this.SURNAME;
	}

	public String getGIVENNAME() {
		return this.GIVENNAME;
	}

	public String getCN() {
		return this.CN;
	}

	public String getEMAILADDRESS() {
		return this.EMAILADDRESS;
	}

	public InfoCert(Certificate[] chain) {
		X509Certificate cert = (X509Certificate) chain[0];
		String[] subject = cert.getSubjectDN().toString().split(",");
		for (String sub : subject) {
			String[] dd = sub.split("=");
			String key = dd[0].trim();
			String value = dd[1];
			switch (key) {
			case "C":
				this.C = value;
				break;
			case "O":
				this.O = value;
				break;
			case "OID":
				this.OID = value;
				break;
			case "OU":
				this.OU = value;
				break;
			case "T":
				this.T = value;
				break;
			case "ST":
				this.ST = value;
			case "L":
				this.L = value;
				break;
			case "SERIALNUMBER":
				this.SERIALNUMBER = value;
				break;
			case "SURNAME":
				this.SURNAME = value;
				break;
			case "GIVENNAME":
				this.GIVENNAME = value;
				break;
			case "CN":
				this.CN = value;
				break;
			case "EMAILADDRESS":
				this.EMAILADDRESS = value;
				break;
			}
		}
	}
}
