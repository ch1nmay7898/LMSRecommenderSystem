import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripperByArea;



public class PDFParser {
	public static void main(String[] args) throws IOException {
		PDFParser readPdf = new PDFParser();
		readPdf.parsePDF("/Users/christina/Downloads/pdfparser/docs/SudhakarGVVNProfile.pdf");
		//readPdf.parsePDF("/Users/bhabani/Downloads/PrachiSahooProfile.pdf");
		//System.out.println(readPdf.experience);
		//System.out.println(readPdf.isMonthandYear("April 1999"));
	}

		public void parsePDF(String path_to_file) throws InvalidPasswordException, IOException {
		try (PDDocument document = PDDocument.load(new File(path_to_file))) {

			document.getClass();

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				StyledPDFTextStripper tStripper = new StyledPDFTextStripper();

				String pdfFileInText = tStripper.getText(document);
			
				// split by whitespace
				String lines[] = pdfFileInText.split("\\r?\\n");
				for (String line : lines) {
					 System.out.println(line);
					//processLine(line);
				}

			}

		}
	}
}
