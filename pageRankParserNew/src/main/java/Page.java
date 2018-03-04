import java.util.HashSet;
import java.util.Set;

class Page {

    private final Set<String> pageRankLinks;
    private String currentPage;
    private String description;

    Page(String currentPage, String description) {
        this.currentPage = currentPage;
        this.description = description;
        pageRankLinks = new HashSet<>();
    }

    public String getCurrentPageLink() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getPageRankLinks() {
        return pageRankLinks;
    }

    public void addAdjacentLinkToPage(String link) {
        pageRankLinks.add(link);
    }

}
