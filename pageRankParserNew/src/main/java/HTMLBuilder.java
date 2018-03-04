import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

class HTMLBuilder {

    private static final String HTML_FILE_LOCATION = "src/main/resources/matrix.html";

    void buildMatrixAndShow(final Set<Page> pages) {
        String htmlInStringView = getViewOfPageRankMatrix(pages);
        writeStringToHTMLFile(htmlInStringView);
        showHTMLFileInBrowser();
    }

    private String getViewOfPageRankMatrix(final Set<Page> pages) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>" +
                "<HEAD>\n" +
                "<LINK href=\"matrix.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "</HEAD>\n" +
                "<body>\n" +
                "<table>\n" +
                "<tr>" +
                "<th></th>");
        for (Page page : pages) {
            htmlBuilder.append("<th>").append(page.getDescription()).append("</th>");
        }
        htmlBuilder.append("</tr>");
        Set<String> links;
        for (Page page : pages) {
            htmlBuilder.append("<tr>");
            htmlBuilder.append("<th>").append(page.getDescription()).append("</th>");
            links = page.getPageRankLinks();
            for (Page innerPage : pages) {
                if (links.contains(innerPage.getCurrentPageLink())) {
                    htmlBuilder.append("<td class=\"adjacent\">");
                    htmlBuilder.append(1);
                } else {
                    htmlBuilder.append("<td>");
                    htmlBuilder.append(0);
                }
                htmlBuilder.append("</td>");
            }
            htmlBuilder.append("</tr>");
        }
        htmlBuilder.append("</table>" +
                "</body>" +
                "</html>");
        return htmlBuilder.toString();
    }

    private void writeStringToHTMLFile(final String htmlText) {
        try (PrintWriter out = new PrintWriter(HTML_FILE_LOCATION)) {
            out.print(htmlText);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showHTMLFileInBrowser() {
        File htmlFile = new File(HTML_FILE_LOCATION);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
