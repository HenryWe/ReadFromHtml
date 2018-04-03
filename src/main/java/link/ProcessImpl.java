package link;

import base.ReadFromHtml;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessImpl extends ReadFromHtml implements IProcess {

    private static final Logger logger = Logger.getLogger(Process.class.getName());

    @Override
    public Map<String,List<String>> urlLink(String url) {
        return urlLinks(readHtmlFromURL(url));
    }

    private Map<String,List<String>> urlLinks(List<String> urlList ) {
        final ExecutorService executorPool = Executors.newFixedThreadPool(20);
        Collection<Task> runnableTasks = new ArrayList<>();
        for (String url : urlList) {
            if (!url.isEmpty()) runnableTasks.add(new Task(url));
        }
        Map<String,List<String>> listMap = new HashMap<>();
        try {
            List<Future<Map<String,List<String>>>> list = executorPool.invokeAll(runnableTasks);
            for(Future<Map<String,List<String>>> fut : list){
                listMap.putAll(fut.get());
            }
        } catch (InterruptedException e) { logger.log(Level.INFO, e.getMessage());
        } catch (ExecutionException e) { logger.log(Level.INFO, e.getMessage());
        } finally {
            executorPool.shutdown();
        }
        return listMap;
    }
}
