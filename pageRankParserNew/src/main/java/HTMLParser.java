import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

class HTMLParser {

    private static final String BASE_URL = "https://kpfu.ru/";

    private static final String URL_MASK = "kpfu.ru";

    private static final int ADJACENCY_MATRIX_SIZE = 100;

    Set<Page> getAdjacencyPages() {
        Set<Page> pages = getRelevantPagesByUrl(BASE_URL);
        findAdjacencyBetweenRelevantPages(pages);
        return pages;
    }

    private Set<Page> getRelevantPagesByUrl(final String baseUrl) {
        Document doc;
        Set<Page> pages = new LinkedHashSet<>();
        try {
            doc = Jsoup.connect(baseUrl).get();

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements links = doc.select("a[href], option[data-link]");
            String stringLink;
            Element link;
            for (int i = 0; i < links.size() && pages.size() < ADJACENCY_MATRIX_SIZE; i++) {
                link = links.get(i);
                stringLink = getStringLink(link);
                if (stringLink.contains(URL_MASK)) {
                    if (link.text().isEmpty()) {
                        pages.add(new Page(stringLink, String.format("%1$d. <a href=\"%2$s\">%2$s</a>", pages.size() + 1, stringLink)));
                    } else {
                        pages.add(new Page(
                                stringLink,
                                String.format("%1$d. <a href=\"%2$s\">%3$s</a>", pages.size() + 1, stringLink, link.text())
                        ));
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pages;
    }

    private String getStringLink(final Element link) {
        String stringLink = link.attr("href");
        if (stringLink.isEmpty()) {
            return link.attr("data-link");
        }
        return stringLink;
    }

    private Set<String> getAllLinksFromUrl(final String baseUrl) {
        Document doc;
        Set<String> stringLinks = new HashSet<>();
        try {
            doc = Jsoup.connect(baseUrl).get();

            // get all stringLinks
            Elements links = doc.select("a[href], option[data-link]");
            for (Element link : links) {
                String stringLink = getStringLink(link);
                if (stringLink.contains(URL_MASK)) {
                    stringLinks.add(stringLink);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stringLinks;
    }

    private List<String> getLinksFromPages(final Set<Page> pages) {
        List<String> links = new ArrayList<>(pages.size());
        for (Page page : pages) {
            links.add(page.getCurrentPageLink());
        }
        return links;
    }

    private void findAdjacencyBetweenRelevantPages(final Set<Page> pageRankList) {
        List<String> pageRankStringLinks = getLinksFromPages(pageRankList);
        for (Page page : pageRankList) {
            for (String innerPageLink : getAllLinksFromUrl(page.getCurrentPageLink())) {
                if (pageRankStringLinks.contains(innerPageLink)) {
                    page.addAdjacentLinkToPage(innerPageLink);
                }
            }
        }
    }

}
