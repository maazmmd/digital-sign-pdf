package signature;

import com.itextpdf.text.pdf.security.MakeSignature;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author mohammedmaaz
 *
 */

public class Signature {
	public static void main(String[] args) throws Exception {
		String params = "PFX=/pathTo/lib/apples.pfx;PASSPFX=emudhra;SOURCE=/pathTo/lib/example.pdf;TARGET=/pathTo/lib/signedPDF-example.pdf;VISIBLE=true;PAGE=1;POSITION=0;REASON=I am the author of this document;LOCATION=Bangalore;URLIMAGE=/pathTo/lib/logo.png;MARGINLEFT=415.0F;MARGINBOTTOM=180.0F";
		Certificate[] chain = null;
		PrivateKey pk = null;
		if (args.length > 0) {
			Arguments attr = new Arguments(args[0]);
			BouncyCastleProvider provider = new BouncyCastleProvider();
			Security.addProvider((Provider) provider);
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			try {
				ks.load(new FileInputStream(attr.getPfx()), attr.getPassPfx());
				String alias = ks.aliases().nextElement();
				pk = (PrivateKey) ks.getKey(alias, attr.getPassPfx());
				chain = ks.getCertificateChain(alias);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			Sign app = new Sign();
			app.setSignatureVisible(attr.getVisible());
			app.setPage(attr.getPage());
			app.setReason(attr.getReason());
			app.setLocation(attr.getLocation());
			app.setUrlImage(attr.getUrlImage());
			app.setPrefix(attr.getPrefix());
			app.setPosition(attr.getPosition());
			app.setMarginLeft(attr.getMarginLeft());
			app.setMarginBottom(attr.getMarginBottom());
			app.signing(attr.getSource(), attr.getTarget(), chain, pk, "SHA-256", provider.getName(),
					MakeSignature.CryptoStandard.CMS);
		} else {
			throw new Exception("No arguments found");
		}
	}
}
