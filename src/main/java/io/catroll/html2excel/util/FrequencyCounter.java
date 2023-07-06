package io.catroll.html2excel.util;

public class FrequencyCounter {
    public static int countNo(String paragraph, String keyword) {
        // TODO: lower case?
        paragraph = paragraph.toLowerCase();
        keyword = keyword.toLowerCase();
        int No = 0;

        int index = paragraph.indexOf(keyword);
        while (index != -1) {
            No++;
            index = paragraph.indexOf(keyword, index + keyword.length());
        }
        return No;
    }
}
