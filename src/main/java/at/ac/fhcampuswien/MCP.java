package at.ac.fhcampuswien;

import at.ac.fhcampuswien.newsanalyzer.ctrl.NewsApiException;
import at.ac.fhcampuswien.newsanalyzer.ui.UserInterface;

public class MCP {
    public static void main(String[] args) throws NewsApiException {
        UserInterface ui = new UserInterface();
        ui.start();

        /*try{								//code where exception might occur
            //System.out.println("Test");
            UserInterface ui = new UserInterface();
            ui.start();
        } catch(NewsApiException e){		//handles exception
            System.out.println(e);
        }*/
    }
}
