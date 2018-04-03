package link;

import base.ReadFromHtml;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class Task extends ReadFromHtml implements Callable<Map<String,List<String>>> {

    private static final Logger logger = Logger.getLogger(link.Task.class.getName());

    private String url = null;
    public Task(String url) {
        this.url = url;
    }

    @Override
    public Map<String,List<String>> call() {
        Map<String,List<String>> hmList = new HashMap<>();
        try{
            hmList.put(url,getUniqueLinksWithTotals(readHtmlFromURL(url)));

        }catch(Exception e){ logger.log(Level.INFO,e.getMessage());
        }
        return hmList;
    }

    private  List<String> getUniqueLinksWithTotals(List<String> urlList) {

        Set<String> uniqueSet = new HashSet<>(urlList);
        List<String> uniqueList = uniqueSet.stream().map( link -> {
            return link.concat(" : ( "+
                    Integer.valueOf(Collections.frequency(urlList, link)).toString() +
                    " )"
            );
        }).collect(Collectors.toList());
        return uniqueList;
    }
}
