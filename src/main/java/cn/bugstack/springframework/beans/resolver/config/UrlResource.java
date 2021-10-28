package cn.bugstack.springframework.beans.resolver.config;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource{
    private final URL url;

    public UrlResource(URL url){
        this.url = url;
    }
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        try {
            return urlConnection.getInputStream();
        }
        catch (IOException e){
            if(urlConnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlConnection).disconnect();
            }
            throw e;
        }
    }
}
