package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import at.technikum_wien.tourplannerapi.repository.TourLogRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourLogRepository tourLogRepository;

    public byte[] generateSummaryReport() {
        List<Tour> tours = tourRepository.findAll();
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            //title
            Paragraph title = new Paragraph("Tour Summary Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            //table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 2, 2});

            // headers
            addTableHeader(table, "Tour Name");
            addTableHeader(table, "Avg. Distance");
            addTableHeader(table, "Avg. Duration");
            addTableHeader(table, "Avg. Rating");

            // rows
            for (Tour tour : tours) {
                List<TourLog> logs = tourLogRepository.findByTourId(tour.getId());

                double avgDist = logs.stream().mapToDouble(TourLog::getTotalDistance).average().orElse(0);
                double avgDur = logs.stream().mapToDouble(TourLog::getTotalDuration).average().orElse(0);
                double avgRating = logs.stream().mapToDouble(TourLog::getRating).average().orElse(0);

                addTableCell(table, tour.getTourName());
                addTableCell(table, String.format("%.2f", avgDist));
                addTableCell(table, String.format("%.2f", avgDur));
                addTableCell(table, String.format("%.2f", avgRating));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating summary report PDF", e);
        }

        return out.toByteArray();
    }

    public byte[] generateTourReport(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        List<TourLog> logs = tourLogRepository.findByTourId(tourId);

        // compute popularity and child-friendliness
        int popularity = logs.size();
        double avgDifficulty = logs.stream().mapToInt(TourLog::getDifficulty).average().orElse(0);
        double avgDuration = logs.stream().mapToDouble(TourLog::getTotalDuration).average().orElse(0);
        double childFriendliness = (5 - avgDifficulty) + (3 - avgDuration);
        if (childFriendliness < 0) childFriendliness = 0;

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            //title
            Paragraph title = new Paragraph("Tour Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // tour-info
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Name: " + tour.getTourName(), infoFont));
            document.add(new Paragraph("Description: " + tour.getDescription(), infoFont));
            document.add(new Paragraph("From: " + tour.getFromLocation(), infoFont));
            document.add(new Paragraph("To: " + tour.getToLocation(), infoFont));
            document.add(new Paragraph("Transport: " + tour.getTransportType(), infoFont));
            document.add(new Paragraph("Distance: " + String.format("%.2f", tour.getDistance()), infoFont));
            document.add(new Paragraph("Estimated Time: " + String.format("%.2f", tour.getEstimatedTime()), infoFont));
            document.add(new Paragraph("Popularity: " + popularity, infoFont));
            document.add(new Paragraph("Child Friendliness: " + String.format("%.2f", childFriendliness), infoFont));
            document.add(new Paragraph(" "));

            //logs header
            Paragraph logsTitle = new Paragraph("Tour Logs", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            logsTitle.setAlignment(Element.ALIGN_CENTER);
            logsTitle.setSpacingBefore(10f);
            document.add(logsTitle);
            document.add(new Paragraph(" "));

            // logs table
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 4, 2, 2, 2});

            addTableHeader(table, "Date");
            addTableHeader(table, "Comment");
            addTableHeader(table, "Difficulty");
            addTableHeader(table, "Duration");
            addTableHeader(table, "Rating");

            for (TourLog log : logs) {
                addTableCell(table, log.getDate().toString());
                addTableCell(table, log.getComment());
                addTableCell(table, String.valueOf(log.getDifficulty()));
                addTableCell(table, String.format("%.2f", log.getTotalDuration()));
                addTableCell(table, String.format("%.2f", (double) log.getRating()));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating tour report PDF", e);
        }

        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table, String headerText) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);

        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setPadding(6);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        header.setPhrase(new Phrase(headerText, font));
        table.addCell(header);
    }

    private void addTableCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(text));
        cell.setPadding(5);
        table.addCell(cell);
    }
}
