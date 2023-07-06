package io.catroll.html2excel;

import io.catroll.html2excel.parse.GeneralParser;
import io.catroll.html2excel.util.FileGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Application {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Document doc = getDocument("multipub_articles/multipub_articles/weighted-voting-right.html");
        // parser setting
        GeneralParser parser = new GeneralParser();
        parser.setDocument(doc);
        parser.setTitleClass("p6");
        parser.setSubTitleClass("p8");
        parser.setTextClass("p9");
        // generator setting
        FileGenerator generator = new FileGenerator();
        generator.setParser(parser);
        generator.generateFile();

    }

    private static Document getDocument(String path) throws URISyntaxException, IOException {
        File input = new File(path);
        return Jsoup.parse(input);
    }
}
