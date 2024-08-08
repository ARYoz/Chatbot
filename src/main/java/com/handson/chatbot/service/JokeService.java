package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import java.util.List;

@Service
public class JokeService{
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    @Autowired
    ObjectMapper om;


    public String getJokeId(String keyword) throws IOException {

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=" + keyword + "")
                .get()
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cache-control", "max-age=0")
                .addHeader("priority", "u=0, i")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        Jokeresponse res = om.readValue(response.body().string(), Jokeresponse.class);
        return res.getResult().get(0).getValue();


    }


    static class Jokeresponse {
        List<JokeresponseObject> result;

        public List<JokeresponseObject> getResult() {
            return result;
        }
    }
    static class JokeresponseObject {
        String value;

        public String getValue() {
            return value;
        }
    }




}
