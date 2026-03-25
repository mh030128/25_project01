package com.jin.project01;

public class Study {
    public static void main(String[] args) {

        String s = "user@gmail.com";
        String result = s.substring(s.indexOf("@") + 1);
        System.out.println("문제1: " + result);

        String s2 = "report.final.pdf";
        int ex2 = s2.lastIndexOf(".");
        String result2 = s2.substring(0, ex2);
        System.out.println("문제2: " + result2);

        String url = "https://api.test.com/v1/users";
        int start = url.indexOf("://") + 3;     // 8 api
//        System.out.println(start);
//        int last = url.lastIndexOf("/");
//        int secondLast = url.lastIndexOf("/", last - 1);
        int endUrl = url.indexOf("/", start);
        //System.out.println(endUrl);
        String result3 = url.substring(start, endUrl);
        System.out.println("문제3: " + result3);

        String path = "/home/user/docs/file.txt";
        int lastPath = path.lastIndexOf("/");
        int secondLastPath = path.lastIndexOf("/", lastPath - 1);
//        System.out.println(secondLastPath);
        String result4 = path.substring(secondLastPath + 1, lastPath);
        System.out.println("문제4: " + result4);

        String s3 = "a/b/c/d/e";
        int lastS3 = s3.lastIndexOf("/");
        //System.out.println(lastS3);
        int secondLastS3 = s3.lastIndexOf("/", lastS3 - 1);
        //System.out.println(secondLastS3);
        String result5 = s3.substring(secondLastS3 + 1, lastS3);
        System.out.println("문제5: " + result5);
    }
}
