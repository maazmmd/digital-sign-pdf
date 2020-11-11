package signature;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author mohammedmaaz
 *
 */
public class Sign {
	private int page = 1;

	private boolean SignatureVisible = true;

	private String urlImage = "";

	private Boolean isSigned = Boolean.valueOf(false);

	private String prefix = "CCFF:Signature";

	private ArrayList<String> fields = new ArrayList<>();

	private String reason = "I'm the author of this document";

	private String location = "Bangalore";

	private ArrayList<Rectangle> positions = new ArrayList<>();

	private int pos = 0;
	
	private float marginLeft = 0.0F;
	
	private float marginBottom = 0.0F;

	private Certificate[] chain;

	public void validateSignatures(PdfReader src) {
		this.fields = src.getAcroFields().getSignatureNames();
		this.isSigned = Boolean.valueOf((this.fields.size() > 0));
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setSignatureVisible(Boolean visible) {
		this.SignatureVisible = visible.booleanValue();
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setUrlImage(String url) {
		this.urlImage = url;
	}

	public void setPosition(int pos) {
		this.pos = pos;
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

	public ArrayList<Rectangle> getPositions() {
		return this.positions;
	}

	public void signing(String src, String dest, Certificate[] chain, PrivateKey pk, String digestAlgorithm,
			String provider, MakeSignature.CryptoStandard subfilter)
			throws GeneralSecurityException, IOException, DocumentException, Exception {
		this.chain = chain;
		InfoCert info = new InfoCert(chain);
		PdfReader reader = new PdfReader(src);
		FileOutputStream os = new FileOutputStream(dest);
		validateSignatures(reader);
		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
		// (reader, os, false, null, this.isSigned.booleanValue());
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setReason(this.reason);
		appearance.setLocation(this.location);
		if (this.SignatureVisible) {
			try {
				generatePositions(reader);
				appearance.setVisibleSignature(this.positions.get(this.pos), this.page,
						this.prefix + (this.fields.size() + 1));
			} catch (Exception ex) {
				generatePositions(reader);
				appearance.setVisibleSignature(this.positions.get(0), 1, this.prefix + (this.fields.size() + 1));
			}
			appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION);
			Font fontDescrip = new Font(Font.FontFamily.HELVETICA, 3.5F);
			appearance.setLayer2Font(fontDescrip);
			appearance.setLayer2Text("Digitally Signed By " + info.getO() + "\n" + "CN=" + info.getCN() + ", ST="
					+ info.getST() + ", C=" + info.getC() + "\nReason: " + appearance.getReason() + "\nLocation: "
					+ appearance.getLocation() + "\n" + (new Date()).toString());
			try {
				appearance.setSignatureGraphic(Image.getInstance(this.urlImage));
			} catch (Exception e) {
				throw new Exception("The image could not be found: " + this.urlImage);
			}
		}
		BouncyCastleDigest bouncyCastleDigest = new BouncyCastleDigest();
		PrivateKeySignature privateKeySignature = new PrivateKeySignature(pk, digestAlgorithm, provider);
		MakeSignature.signDetached(appearance, (ExternalDigest) bouncyCastleDigest,
				(ExternalSignature) privateKeySignature, chain, null, null, null, 40000, subfilter);
		this.positions.clear();
	}

	void generatePositions(PdfReader reader) {
		Rectangle rect = reader.getCropBox(this.page);
		float width = rect.getWidth();
		float height = rect.getHeight();
		float currentWidth = 108.0F;
		float currentHeight = 32.0F;
		int column = (int) (width / currentWidth);
		int row = (int) (height / currentHeight) - 1;
		float margLeft = this.marginLeft;
		float margBottom = this.marginBottom;
		float currentY = margBottom;
		for (int i = 0; i < row; i++) {
			float currentX = margLeft;
			for (int j = 0; j < column; j++) {
				this.positions
						.add(new Rectangle(currentX, currentY, currentX + currentWidth, currentY + currentHeight));
				currentX += currentWidth;
			}
			currentY += currentHeight;
		}
	}
}