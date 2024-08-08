package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AmazonService {
    public static final Pattern PRODUCT_PATTERN = Pattern.compile("<span class=\\\"a-size-medium a-color-base a-text-normal\\\">([^<]+)<\\/span> .*<span class=\\\"a-icon-alt\\\">([^<]+)<\\/span>.*<span class=\\\"a-offscreen\\\">([^<]+)<\\/span>");

    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword));
    }
    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + " - " + matcher.group(2) + ", price:" + matcher.group(3) + "<br>\n";
        }
        return res;
    }
    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.amazon.com/s?k=" + keyword + "%20touch&i=aps&ref=nb_sb_ss_pltr-xclick_2_4&crid=26WES1FM0U28O&sprefix=ipod%2Caps%2C208")
                .get()
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("cookie", "aws-ubid-main=478-3801554-4617048; aws-account-alias=995553441267; remember-account=false; regStatus=registered; awsc-uh-opt-in=null; noflush_awsccs_sid=861600df6a1382f1519bd5c7d30a4c15181d629ee759dd42217e329dde36ba79; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A995553441267%3Auser%2Faryo%22%2C%22alias%22%3A%22995553441267%22%2C%22username%22%3A%22aryo%22%2C%22keybase%22%3A%22%22%2C%22issuer%22%3A%22http%3A%2F%2Fsignin.aws.amazon.com%2Fsignin%22%2C%22signinType%22%3A%22PUBLIC%22%7D; session-id=132-7699504-8342615; session-id-time=2082787201l; i18n-prefs=USD; lc-main=he_IL; sp-cdn=\"L5Z9:IL\"; skin=noskin; ubid-main=134-4929159-4250863; session-token=xOSPKw8IeKVpIgiqF0lLP5epk+CDd84/+hzHkEVuaSC35t03n/44TNPHcf+aUCfqEbcAGwS2Wxps9nSiFrNV1HCvQiPTJLebIj/g73L16OvbAmbImlrwequX6YQNCvEBzCsgQvkI53J4BHx2DrcvCX+R5aaSEV62I5AI+h19006or1zAy1T5foJbPSnPc/c2kCHELGhdNH5n2I4X/uEKchCm65eshS52lLiMS/eO0Tu5Dav08JaOAselt66DE7DfgCDJsoUbiBVfDhE8Vvh7i1NC6kYYIPKAcYosN27aeq90xt5R/9x97RP0SiqlRFOm/MKZFZ50FTd/2f0KxtNJGOZVrkIeQD+I; csm-hit=tb:TJ5THCK5AVA1BXHK33FM+s-XYAJXJ0AZH6EP6NRG549|1721032590382&t:1721032590382&adb:adblk_no; session-token=GF9ABcrSUovdFnUYPINPtO+vlhiAv60OwUrPJWjl1iEDLdXAcgvRdNGQeWd7lttdToVVqRawafGGaqDCrKC/5oB2Jprbozg6MDT2rZMBtMxs/s9luOxk4j9KCdvX64OZVx2y/ThNw/fYBthq/ReMhNCS/jAB4osfGlvX5fWPf64U5Y8NiQpnIywqXp7MrAn7oJBRme6ZxAJmMe8ojP279zxC2xfR0yVPAxx2oy5nGYwNeLi7JX23VsQjfdSRNu4m1wnCSQ+/92B48kG59mydGyCapLEDPx4AMiTp9VCDpDs+afZWhwbF2KJ7upQaQv7P42FGB9K2Feg70DIw2/wxPmE6InihJdsy")
                .addHeader("device-memory", "8")
                .addHeader("downlink", "10")
                .addHeader("dpr", "1.25")
                .addHeader("ect", "4g")
                .addHeader("priority", "u=0, i")
                .addHeader("referer", "https://www.amazon.com/s?k=ipod&crid=2ALI38IP3TKA&sprefix=ipo%2Caps%2C267&ref=nb_sb_noss_2")
                .addHeader("rtt", "50")
                .addHeader("sec-ch-device-memory", "8")
                .addHeader("sec-ch-dpr", "1.25")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-ch-ua-platform-version", "\"15.0.0\"")
                .addHeader("sec-ch-viewport-width", "619")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .addHeader("viewport-width", "619")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}



