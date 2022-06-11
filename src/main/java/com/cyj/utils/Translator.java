package com.cyj.utils;

import org.jsoup.Jsoup;

import java.io.IOException;


public class Translator {

//    public static void main(String[] args) throws IOException {
//        System.out.println(translator("I contacted several of the UK Support teams in order to verify the use cases provided by the NA support teams. They confirmed that several of the use cases were also available in the Guided Judgement package. To illustrate the use cases, I created a step-by-step walkthrough (with screenshots) to share with the Support teams’ manager. "));
//    }
    public static String translator(String content) throws IOException {

           String url = "https://www.youdao.com/w/" + content + "/#keyfrom=dict2.top";
           String text = Jsoup.connect(url).get().select("div[class=trans-container]").get(0).text();
           return text;

    }

}
