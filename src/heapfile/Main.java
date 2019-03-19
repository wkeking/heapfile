package heapfile;

import heapfile.utils.DateUtil;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        String s = "08/11/2017 07:12:02 PM";
        long l = DateUtil.strToLong (s);
        System.out.println(l);
        String s1 = DateUtil.longToStr (l);
        System.out.println(s1);
    }
}
