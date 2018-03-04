import java.util.Set;

public class Starter {

    public static void main(String[] args) {
        new Starter().createPageRankMatrix();
    }

    private void createPageRankMatrix() {
        //getting adjacent pages of base url;
        HTMLParser htmlParser = new HTMLParser();
        Set<Page> pagesForPageRank = htmlParser.getAdjacencyPages();

        //create view of pages' info we've got
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        htmlBuilder.buildMatrixAndShow(pagesForPageRank);
    }


}
