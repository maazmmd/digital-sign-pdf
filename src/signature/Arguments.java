/**
 * 
 */
package signature;

/**
 * @author mohammedmaaz
 *
 */

public final class Arguments {
	private String[] arr;

	private String pfx;

	private char[] passPfx;

	private String source;

	private String target;

	private Boolean visible = Boolean.valueOf(false);

	private int page;

	private String reason;

	private String location;

	private String urlImage;

	private int position = 0;
	
	private float marginLeft = 0.0F;
	
	private float marginBottom = 0.0F;


	private String prefix = "CCFF:Signature";

	public Arguments(String datos) {
		this.arr = datos.split(";");
		execute();
	}

	private void execute() {
		for (String index : this.arr) {
			String[] datos = index.split("=");
			String key = (datos.length >= 0) ? datos[0] : "";
			String value = (datos.length >= 1) ? datos[1] : "";
			switch (key) {
			case "PFX":
				this.pfx = value;
			case "PASSPFX":
				this.passPfx = value.toCharArray();
				break;
			case "SOURCE":
				this.source = value;
				break;
			case "TARGET":
				this.target = value;
				break;
			case "PAGE":
				this.page = Integer.parseInt(value.equals("") ? "1" : value);
				break;
			case "REASON":
				this.reason = value.replace("_", " ");
				break;
			case "LOCATION":
				this.location = value.replace("_", " ");
				break;
			case "URLIMAGE":
				this.urlImage = value;
				break;
			case "POSITION":
				this.position = Integer.parseInt(value.equals("") ? "0" : value);
				break;
			case "VISIBLE":
				this.visible = Boolean.valueOf(Boolean.parseBoolean(value));
				break;
			case "PREFIX":
				this.prefix = value;
				break;
			case "MARGINLEFT":
				this.marginLeft = Float.parseFloat(value);
				break;
			case "MARGINBOTTOM":
				this.marginBottom = Float.parseFloat(value);
				break;
			}
		}
	}

	public String getPfx() {
		return this.pfx;
	}

	public char[] getPassPfx() {
		return this.passPfx;
	}

	public String getSource() {
		return this.source;
	}

	public String getTarget() {
		return this.target;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public int getPage() {
		return this.page;
	}

	public String getReason() {
		return this.reason;
	}

	public String getLocation() {
		return this.location;
	}

	public String getUrlImage() {
		return this.urlImage;
	}

	public int getPosition() {
		return this.position;
	}

	public String getPrefix() {
		return this.prefix;
	}
	
	public float getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}

	public float getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}

	public String toString() {
		return "\n" + this.pfx + "\n" + this.passPfx + "\n" + this.source + "\n" + this.target + "\n" + this.urlImage
				+ "\n" + this.page + "\n" + this.position + "\n" + this.marginLeft + "\n" + this.marginBottom;
	}
}
