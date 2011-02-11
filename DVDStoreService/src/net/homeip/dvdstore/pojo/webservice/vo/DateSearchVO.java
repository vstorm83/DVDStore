/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo.webservice.vo;

import java.sql.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author VU VIET PHUONG
 */
public class DateSearchVO implements Comparable<DateSearchVO> {

    private int month;
    private int year;

    public Date toDate(boolean startDate) {
        int mTemp = startDate ? month - 1 : month;
        month = month < 0 ? 0 : month;
        year = year < 1753 || year > 9999 ? 1753 : year;

        return new Date(new GregorianCalendar(year, mTemp, 1, 0, 0, 0).getTimeInMillis());
    }

    public int compareTo(DateSearchVO other) {
        if (other == null ||
                year > other.getYear() ||
                (year == other.getYear() && month > other.getMonth())) {
            return 1;
        } else if (year == other.getYear() && month == other.getMonth()) {
            return 0;
        } else {
            return -1;
        }
    }

    public DateSearchVO() {
    }

    public DateSearchVO(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
