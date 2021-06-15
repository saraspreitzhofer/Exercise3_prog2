package at.ac.fhcampuswien.newsanalyzer.ui;


import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
import at.ac.fhcampuswien.newsapi.enums.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class UserInterface 
{
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1() {
		//Category Health, Corona
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("corona")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.health)
				.setLanguage(Language.de)
				.createNewsApi();
		ctrl.process(newsApi);
}

	public void getDataFromCtrl2() {
		// TODO implement me
		//Category Technology, Apple
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("apple")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.technology)
				.setLanguage(Language.de)
				.createNewsApi();
		ctrl.process(newsApi);
	}

	public void getDataFromCtrl3()  {
		// TODO implement me
		//Category Business, Language English, Workplace
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("workplace")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.business)
				.setLanguage(Language.en)
				.createNewsApi();
		ctrl.process(newsApi);

	}
	
	public void getDataForCustomInput1()  {
		// TODO implement me
		//Category Sports, Query User Input
		Scanner scan = new Scanner(System.in);
		System.out.print("Q: ");
		String q = scan.nextLine();

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ(q)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.sports)
				.setLanguage(Language.de)
				.createNewsApi();
		ctrl.process(newsApi);
	}

	public static Country checkInputCountry (String test) {
		for (Country c : Country.values()) {
			if (c.name().equals(test)) {
				return c;
			}
		}
		return null;
	}
	public static Category checkInputCategory (String test) {
		for (Category c : Category.values()) {
			if (c.name().equals(test)) {
				return c;
			}
		}
		return null;
	}
	public static Language checkInputLanguage (String test) {
		for (Language c : Language.values()) {
			if (c.name().equals(test)) {
				return c;
			}
		}
		return null;
	}

	public void getDataForCustomInput2()  {
		// TODO implement me
		//User Input
		Scanner scan = new Scanner(System.in);

		System.out.print("Q: ");
		String q = scan.nextLine();

		System.out.println("Valid countries: " + Arrays.toString(Country.values()));
		System.out.print("Country: ");
		String country = scan.nextLine();
		Country inputCountry = checkInputCountry(country);
		if(inputCountry == null){
			System.out.println("Wrong country");
			getDataForCustomInput2();
		}

		System.out.println("Valid categories: " + Arrays.toString(Category.values()));
		System.out.print("Category: ");
		String category = scan.nextLine();
		Category inputCategory = checkInputCategory(category);
		if(inputCategory == null){
			System.out.println("Wrong category");
			getDataForCustomInput2();
		}

		System.out.println("Valid languages: " + Arrays.toString(Language.values()));
		System.out.print("Language: ");
		String language = scan.nextLine();
		Language inputLanguage = checkInputLanguage(language);
		if(inputLanguage == null){
			System.out.println("Wrong language");
			getDataForCustomInput2();
		}

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ(q)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(inputCountry)
				.setSourceCategory(inputCategory)
				.setLanguage(inputLanguage)
				.createNewsApi();
		ctrl.process(newsApi);
	}


	public void start () {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitle("Wählen Sie aus:");
		menu.insert("a", "Category Health, Corona", this::getDataFromCtrl1);
		menu.insert("b", "Category Technology, Apple", this::getDataFromCtrl2);
		menu.insert("c", "Category Business, Language English, Workplace", this::getDataFromCtrl3);
		menu.insert("d", "Category Sports, Query User Input",this::getDataForCustomInput1);
		menu.insert("e", "User Input",this::getDataForCustomInput2);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}
