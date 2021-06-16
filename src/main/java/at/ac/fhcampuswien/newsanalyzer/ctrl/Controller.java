package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "32a4f05cc6d14bc49d63351f23d209d6";  //TODO add your api key

	public void process(NewsApi newsApi) {
		System.out.println("Start process");

		/*URL url = new URL("http://example.com");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int status = con.getResponseCode();

		if(status == 400){
			throw new NewsApiException("Bad Request. Check your input parameters.");
		}
		if(status == 401){
			throw new NewsApiException("Unauthorized. Check your API key.");
		}
		if(status == 429){
			throw new NewsApiException("Too Many Requests. Back off for a while.");
		}
		if(status == 500){
			throw new NewsApiException("Server Error.");
		}
		//if(status != 200){
		//	throw new NewsApiException("Error");
		//}
		if(status == 200) {*/

		//TODO implement Error handling
		//TODO load the news based on the parameters
		List<Article> articles = new ArrayList<>();
		try{
			NewsResponse newsResponse = newsApi.getNews();
			if(newsResponse != null){
				articles = newsResponse.getArticles();
				if(articles.isEmpty()){
					throw new NewsApiException("No article found!");
				}
				articles.stream().forEach(article -> System.out.println(article.toString()));
			}
		} catch (NewsApiException e){
			System.out.println(e.getMessage());
			return;
		}

		//TODO implement methods for analysis
		System.out.println();

		try {
			//number of articles
			long numberOfArticles = articles.stream()
					.count();
			System.out.println("Number of Articles: " + numberOfArticles);

			//provider with most articles
			String providerWithMostArticles = articles.stream()
					.collect(Collectors.groupingBy(article -> article.getSource().getName(), Collectors.counting()))
					.entrySet()
					.stream()
					.max(Comparator.comparingInt(t -> t.getValue().intValue()))
					.get()
					.getKey();
			System.out.println("Provider with most articles: " + providerWithMostArticles);

			//author with shortest name
			List<String> authorsSorted = articles.stream()
					.map(Article::getAuthor)
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(String::length))
					//.findFirst();		//returns optional
					.collect(Collectors.toList());
			if (authorsSorted.size() > 0) {
				String authorWithShortestName = authorsSorted.get(0);
				System.out.println("Author with shortest name: " + authorWithShortestName);
			} else {
				System.out.println("No author found");
			}

			//titles sorted (length, alphabet)
			List<String> titlesSorted = articles.stream()
					.map(Article::getTitle)
					.sorted(Comparator.comparing(String::length)
							.reversed()
							.thenComparing(Comparator.naturalOrder()))
					.collect(Collectors.toList());
			System.out.println("Titles sorted: " + titlesSorted);
		}
		catch (Exception e){
			System.out.println("Error with streams: " + e.getMessage());
		}

		System.out.println();

		try{
			URL url = new URL(articles.get(0).getUrl());
			ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream("article.html");
			FileChannel fileChannel = fileOutputStream.getChannel();
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		}
		catch(FileNotFoundException e){
				System.out.println("Error: file not found!");
		}
		catch(Exception e){
			System.out.println("Error with downloading the file: " + e.getMessage());
		}

		System.out.println("End process");
	}

	public Object getData() {
		return null;
	}
}
