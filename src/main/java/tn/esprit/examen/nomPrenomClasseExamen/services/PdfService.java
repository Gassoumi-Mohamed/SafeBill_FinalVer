package tn.esprit.examen.nomPrenomClasseExamen.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateFacturePDF(Facture facture,byte[] qrCodeImage) throws Exception {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        // --- Logo ---
        Image logo = Image.getInstance("src/main/resources/images/logo.png");
        logo.scaleToFit(300, 200); // Taille du logo
        logo.setAlignment(Element.ALIGN_CENTER); // Centrer le logo
        document.add(logo);


        // --- En-tête ---
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Facture n°" + facture.getNumero(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph intro = new Paragraph(
                "Merci pour votre confiance envers SafeBill.\n" +
                        "Veuillez trouver ci-dessous le détail de votre facture.", headerFont
        );
        intro.setAlignment(Element.ALIGN_LEFT);
        document.add(intro);
        document.add(Chunk.NEWLINE);

        // --- Infos client et facture ---
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("Client:");
        table.addCell(facture.getUser().getNom());
        table.addCell("Montant total:");
        table.addCell(facture.getMontant() + " TND");
        table.addCell("Date d’émission:");
        table.addCell(facture.getDateEmission().toString());

        document.add(table);
        document.add(Chunk.NEWLINE);

        // --- QR Code ---
        if (qrCodeImage != null) {
            Image qr = Image.getInstance(qrCodeImage);
            qr.setAlignment(Element.ALIGN_CENTER);
            qr.scaleAbsolute(250, 250);
            document.add(qr);

            Paragraph qrText = new Paragraph(
                    "Scannez le QR code ci-dessus pour vérifier l’authenticité de cette facture.",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)
            );
            qrText.setAlignment(Element.ALIGN_CENTER);
            document.add(qrText);
        }

        document.add(Chunk.NEWLINE);

        // --- Pied de page ---
        Paragraph footer = new Paragraph(
                "SafeBill – Plateforme de facturation sécurisée\nwww.safebill.com",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)
        );
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return out.toByteArray();
    }
}
