package base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class ReadFromHtml {

    private static final Logger logger = Logger.getLogger(ReadFromHtml.class.getName());

    public List<String> readHtmlFromURL(String url) {

        List<String> urlLinkList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements imports = doc.select("a[href]");
            urlLinkList.addAll(
                    imports.stream().map( el ->
                            el.attr("abs:href").toString()
                    ).collect(
                            Collectors.toList()
                    )
            );
        } catch (IOException e) { logger.log(Level.INFO, e.getMessage());
        }

        return urlLinkList;
    }
}
