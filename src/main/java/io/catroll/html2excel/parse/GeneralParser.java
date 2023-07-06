package io.catroll.html2excel.parse;

import io.catroll.html2excel.Constant;
import io.catroll.html2excel.util.FrequencyCounter;
import org.apache.commons.validator.GenericValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class GeneralParser  {
    private Document document = null;
    public void setDocument(Document document) {this.document = document;}
    private String titleClass = null;
    public void setTitleClass(String titleClass) {this.titleClass = titleClass;}
    private String subTitleClass = null;
    public void setSubTitleClass(String subTitleClass) {this.subTitleClass = subTitleClass;}
    private String textClass = null;
    public void setTextClass(String textClass) {this.textClass = textClass;}

    public List<String> parseTitle() {
        Elements p6Elements = document.getElementsByClass(titleClass);
        ArrayList<String> titleList = new ArrayList<>();
        for (Element element: p6Elements) {
            Elements realTitle = element.select("b");
//            System.out.println(realTitle.text().toString());
            titleList.add(realTitle.text());
        }
//        System.out.println(titleList.size());
//        100
        return titleList;
    }


    public List<String> parsePublisher() {
        // the standard: 33
        // scmp.com: 67
        Elements p7Elements = document.getElementsByClass(subTitleClass);
        ArrayList<String> publisherList = new ArrayList<>();
        for (Element element : p7Elements) {
            Elements maybePublisher = element.select("span");
            if (maybePublisher.text().equals("scmp.com")
                    || maybePublisher.text().equals("The Standard")
                    || maybePublisher.text().equals("Asia Times Online")
            ) {
                publisherList.add(maybePublisher.text());
            }
        }
//        System.out.println(publisherList);
//        System.out.println(publisherList.size());
        return publisherList;
    }


    public List<String> parseDate() {
        Elements p7Elements = document.getElementsByClass(subTitleClass);
        ArrayList<String> dateList = new ArrayList<>();
        for (Element element : p7Elements) {
            Elements maybeDate = element.select("span");
            String maybeDateText = maybeDate.text();
            if (GenericValidator.isDate(maybeDateText,
                    "dd MMMM yyyy", false)) {
                dateList.add(maybeDateText);
            }
        }
//        System.out.println(dateList);
//        System.out.println(dateList.size());
        return dateList;
    }

    public List<String> parseText() {
        Element body = document.body();
        ArrayList<String> textList = new ArrayList<>();
        boolean textFlag = false;
        String currText = "";
        for (Element pElement : body.children()) {
            if (pElement.className().equals(textClass)) {
                if (!textFlag) {
                    currText = "";
                    textFlag = true;
                }
                currText += pElement.select("span").text();
                currText += " ";
            } else {
                if (textFlag) {
                    textList.add(currText);
                }
                textFlag = false;
            }
        }
//        System.out.println(textList.size());
//        System.out.println(textList);
        return textList;
    }

    public List<String> parseWordCount() {

        Elements p7Elements = document.getElementsByClass(subTitleClass);
        ArrayList<String> wordCountList = new ArrayList<>();
        for (Element element : p7Elements) {
            Elements maybeWordCount = element.select("span");
            String maybeWordCountText = maybeWordCount.text();
            if (maybeWordCountText.contains("words")) {
                wordCountList.add(maybeWordCountText.substring(0,
                        maybeWordCountText.length() - 6));
            }
        }
        return wordCountList;
    }

    public void countFrequencyHelper(String keyword,
                                     Map<String, List<ArrayList<String>>> frequencyMap
    ) {

        Iterator<String> it1 = parseText().iterator();
        Iterator<String> it2 = parseWordCount().iterator();
        ArrayList<ArrayList<String>> currKeywordFreqList = new ArrayList<>();
        while (it1.hasNext() && it2.hasNext()) {
            // actually end at the same time
            String text = it1.next();
            String wordCount = it2.next();
            ArrayList<String> noAndFreq = new ArrayList<>();
            int no = FrequencyCounter.countNo(text, keyword);
            int wordCountInt = Integer.parseInt(wordCount);
            double freq = ((double) no) / ((double) wordCountInt   );
            DecimalFormat df = new DecimalFormat("#.#########");
            df.setRoundingMode(RoundingMode.CEILING);
            noAndFreq.add(Integer.toString(no));
            noAndFreq.add(df.format(freq));
            currKeywordFreqList.add(noAndFreq);
        }
        frequencyMap.put(keyword, currKeywordFreqList);
    }

    public Map<String, List<ArrayList<String>>> countFrequency() {
        HashMap<String, List<ArrayList<String>>> frequencyMap = new HashMap<>();
//        keyword(15)   100         2
        for (String keyword : Constant.KEYWORDS) {
            countFrequencyHelper(keyword, frequencyMap);
        }
        return frequencyMap;
    }

}
