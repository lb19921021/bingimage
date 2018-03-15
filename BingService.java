package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.framework.util.GsonUntil;
import com.framework.util.HttpClientImpl;
import com.framework.util.HttpFactory;

@Service
public class BingService {
	private static String url;

	@PostConstruct
	private void inti() {
		getUrl();
	}

	@Scheduled(cron = "0 1 0 * * ? ")
	private void getUrl() {
		HttpClientImpl http = HttpFactory.getHttpClient();
		String str = http
				.get("http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN");
		Map<String, Object> map = GsonUntil.toMap(str);
		List<Object> images = (List<Object>) map.get("images");
		Map<String, Object> data = (Map<String, Object>) images.get(0);
		String u = data.get("urlbase").toString();
		url = "http://cn.bing.com" + u + "_1920x1080.jpg";
	}

	public String getimage() {
		return url;
	}


}
