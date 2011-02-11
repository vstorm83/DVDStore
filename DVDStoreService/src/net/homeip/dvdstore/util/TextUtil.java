/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.util;

import java.util.StringTokenizer;

/**
 *
 * @author VU VIET PHUONG
 */
public class TextUtil {

    /**
     * normalize text
     * @param text
     * @return null nếu text null
     */
    public static String normalize(String text) {
        if (text == null) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(text);
        StringBuilder result = new StringBuilder();
        while (token.hasMoreTokens()) {
            result.append(token.nextToken());
            result.append(" ");
        }
        return result.toString().trim();
    }

    public static String getFileNameFormURL(String movPicURL) {
        if (movPicURL == null) {
            return null;
        }
        int idx = movPicURL.lastIndexOf("\\");
        if (idx == -1) {
            idx = movPicURL.lastIndexOf("/");
        }
        if (idx == -1 || idx == movPicURL.length() - 1) {
            return null;
        }
        return movPicURL.substring(idx + 1);
    }

    public static String transformMovDesc(String movDesc, String imgDir) {
        if (movDesc == null) {
            return null;
        }
        StringBuilder movDescBuilder = new StringBuilder(movDesc);

        int firstScrIdx;
        int lastScrIdx;
        String scrStr;
        String fileName;
        for (int imgIdx = movDescBuilder.indexOf("<img"); imgIdx != -1;
                imgIdx = movDescBuilder.indexOf("<img", imgIdx + 1)) {
            firstScrIdx = movDescBuilder.indexOf("src=\"", imgIdx);
            if (firstScrIdx == -1) {
                continue;
            }
            firstScrIdx += 5;

            lastScrIdx = movDescBuilder.indexOf("\"", firstScrIdx);
            if (lastScrIdx == -1) {
                continue;
            }
            scrStr = movDescBuilder.substring(firstScrIdx, lastScrIdx);
            fileName = getFileNameFormURL(scrStr);
            if (fileName == null) {
                continue;
            }
            movDescBuilder.replace(firstScrIdx, lastScrIdx, imgDir + fileName);
        }
        return movDescBuilder.toString();
    }    

    public static String transformURLSpace(String url) {
        if (url == null) {
            return null;
        }
        return url.replaceAll("\\s", "%20");
    }

    public static boolean containsIgnoreCase(String source, String target) {
        if (source == null || target == null) {
            return true;
        }
        return normalize(source).toLowerCase().contains(normalize(target.toLowerCase()));
    }
}
