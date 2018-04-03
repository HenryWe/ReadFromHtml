import link.IProcess;
import link.ProcessImpl;
import util.RepositryReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private RepositryReader repositryReader = new RepositryReader();
    private IProcess process  = new ProcessImpl();

    public static void main(String[] args) {

        Main main = new Main();

        Map<String,List<String>> listMap = main.processHtmlFileLinks();
        main.saveUrlLinksToFile( listMap );
    }

    private Map<String,List<String>> processHtmlFileLinks() {

        Map<String,List<String>> listMap = new HashMap<>();

        String url = repositryReader.getHTMLFileUrl();

        if (!url.isEmpty()) {
            listMap.putAll(
                    process.urlLink(repositryReader.getHTMLFileUrl())
            );
        }
        else {
            logger.log(Level.INFO,"No URL File to read");
        }
        return listMap;
    }

    private void saveUrlLinksToFile(Map<String,List<String>> map) {

        String fileName = repositryReader.getOutputFile();

        if (!fileName.isEmpty()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

                map.entrySet().stream().forEach(e -> {
                   for(String link : e.getValue()) {
                       try {
                           bw.write(
                                   e.getKey().concat(" - ").concat(link).concat("\n")
                           );
                       } catch (IOException e1) {
                           logger.log(Level.INFO,e1.getMessage());
                       }
                   }
                });
            }
            catch (IOException e) { logger.log(Level.INFO,e.getMessage());
            }
        }
        else  logger.log(Level.INFO,"No file to save to specified");
    }
}
