package io.catroll.html2excel.parse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface Parser {

    List<String> parseTitle();

    List<String> parsePublisher();

    List<String> parseDate();

    List<String> parseText();

    List<String> parseWordCount();

    Map<String, List<ArrayList<String>>> countFrequency();
}
