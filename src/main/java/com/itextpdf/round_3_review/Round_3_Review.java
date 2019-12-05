package com.itextpdf.round_3_review;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;

public class Round_3_Review {
       
    public static void main(String[] args) throws IOException {
        //welcome
        System.out.println("Welcome to the 360 Capstone Review Program. Let's get started...");
        
        //create scanner object
        Scanner scan =  new Scanner(System.in);
        System.out.print("Scanner object created");
        
        //new edits with making flexible for users:
        //Names for users:
        String user_1_name = "Kat";
        String user_2_name = "Rachel";        
        //Letter for users: 
        String user_1_letter = user_1_name.substring(0,1);
        String user_2_letter = user_2_name.substring(0,1);
        //base path for more flexibility
        String user_1_base_path = "C:\\Users\\Kat\\Box Sync\\";
        String user_2_base_path = "C:\\Users\\mezin\\Box\\";
        
        String CSVFILENAME = "", PREVIOUSROUNDSCSV = "", PREVIOUSROUNDSCSV2 = "", base_path = "";
        
        System.out.print("Who is running the application: " + user_1_name +" or " + user_2_name + " ? " + user_1_letter + "/" + user_2_letter + " ");
        String user = scan.nextLine();
        while (!user.equalsIgnoreCase(user_2_letter) && !user.equalsIgnoreCase(user_1_letter)){
            System.out.print("Please enter a valid option: " + user_1_letter + " or " + user_2_letter + " ");
            user = scan.nextLine();
        }
        if (user.equalsIgnoreCase(user_2_letter)){
            base_path = user_2_base_path;
        }
        else if (user.equalsIgnoreCase(user_1_letter)){
            base_path = user_1_base_path;
        }
        
        final String DELIMITER = "#";
        System.out.println("Delimiter set.");
        
        //ask for the round number to input
        System.out.print("What is the round number? ");
        String roundNum = scan.nextLine();
        System.out.print("Are you sure you mean round " + roundNum + "? y/n ");
        String confirmation = scan.nextLine();
        if (confirmation.equalsIgnoreCase("y")){
            System.out.println("Proceed");
        }
        else{
            System.out.println("Please enter the round number you meant: ");
            roundNum = scan.nextLine();
        }
        System.out.print("What year is it: ");
        String year = scan.nextLine();
        System.out.print("Fall or Spring semester? Fall/Spring: ");
        String semester = scan.nextLine();
        if(semester.equalsIgnoreCase("fall")){
            semester = "Fall";
        }
        else if(semester.equalsIgnoreCase("spring")){
            semester = "Spring";
        }
        else{
            semester = "ERROR WITH SEMESTER";
        }
        
        //make base_path more detailed
        base_path += year + " " + semester + " " + "Capstone\\";
        CSVFILENAME = base_path + "Rounds\\Round " + roundNum + "\\Program\\" + year + " " + semester + " " + "Round " + roundNum + " Data.csv";
        PREVIOUSROUNDSCSV = base_path + "Rounds\\Previous Data\\Round1.csv";
        PREVIOUSROUNDSCSV2 = base_path + "Rounds\\Previous Data\\Round2.csv";
        
        System.out.println("File name string created.");        
        System.out.println("Previous round file names created.");
        
/* ----------------------------------------------------------------------------------------------------------------------------------------------*/        
        //create the array of students called roster, calls method that creates the student objects from passing in the CSV file
        System.out.println("Starting to create student roster. Please wait.");
        ArrayList<Student> roster = createStudents(CSVFILENAME, DELIMITER);
        System.out.println("Student roster successfully created.");

        //calls create feedback array, sends the csv file location and the roster populated with student objects
        System.out.println("Starting to create feedback for each student. Please wait.");
        createFeedback(CSVFILENAME, roster, DELIMITER);
        System.out.println("Feedback for each student successfully created.");
        //creates the teams from calling the team array...not really used yet in this version of code
        System.out.println("Creating teams. Please wait.");
//not needed for now        ArrayList<Team> teamList = createTeams(roster, CSVFILENAME, DELIMITER);
        
        //create the csv file for when there are multiple rounds to be done
        CreateCSV (roster, roundNum, base_path /*, teamList*/);
        //read in the csv file created from previous rounds
        if(roundNum.equalsIgnoreCase("2")){
            ReadRoundsCSV(PREVIOUSROUNDSCSV, roster, DELIMITER);
        }
        else if (roundNum.equalsIgnoreCase("3")){
            ReadRoundsCSV(PREVIOUSROUNDSCSV, roster, DELIMITER);
            ReadRoundsCSV2(PREVIOUSROUNDSCSV2, roster, DELIMITER);
        }
        //final step to create the documents that are saved in the shared folder
        System.out.println("Creating the PDFs. Please wait.");
        CreateDocs(CSVFILENAME, roster, /*teamList,*/ roundNum, semester, year, base_path);
        System.out.println("PDFs successfully created.");
        
        System.out.println("Thank you.");
    }
    
    
    public static ArrayList<Student> createStudents (String csvFileName, String delimiter){
        //create the roster ArrayList to store the Student objects
        ArrayList <Student> roster = new ArrayList <Student>();
        System.out.println("Array created.");
            
        final String DELIMITER = delimiter;
        
        //contain in try-catch statement 
        try {
            //create file object
            File file = new File(csvFileName);
            System.out.print("--File made.");
            //Scanner objects to read the file twice
            Scanner createObjects = new Scanner (file); //create the objects
            System.out.println("--Scanner object created.");
            //read first line into string -- not needed
            String nextLine = createObjects.nextLine();
            System.out.println("--ignore line.");
            //read second line into a string -- use to create variables to determine column name
            nextLine = createObjects.nextLine();
            //split string into an array by the delimiter
            System.out.print("--Important line read.");
            String[] studentLine = nextLine.split(delimiter);
            int numOfCols = studentLine.length;
            System.out.println("--Important line split.");
            
            //create variables to store column numbers of the defining student object titles
            int locationLast = -1, locationFirst = -1, locationEmail = -1, locationTeamName = -1, locationTeamNum = -1, locationFaculty = -1;
            
            for (int i = 0; i < numOfCols; i++){
                //saves column location of last name 
                if (studentLine[i].contains("Recipient Last Name")){
                    locationLast = i;
                    System.out.println("--variable for last name created."); //test
                }
                //saves column location of first name
                else if (studentLine[i].contains("Recipient First Name")){
                    locationFirst = i;
                    System.out.println("--variable for first name created.");
                }
                //saves column location of email
                else if (studentLine[i].contains("Recipient Email")){
                    locationEmail = i;
                    System.out.println("--variable for email created.");
                }
                //saves column location of team number
                else if (studentLine[i].contains("TEAMNUM")){
                    locationTeamNum = i;
                    // check System.out.println(locationTeamNum);
                    System.out.println("--variable for team number created.");
                }
                //saves column location of team name 
                else if (studentLine[i].contains("TEAMNAME")){
                    locationTeamName = i;
                    System.out.println("--variable for team name created.");
                }
                else if (studentLine[i].contains("FACULTYSPONSOR")){
                    locationFaculty = i;
                    System.out.println("--variable for faculty sponsor created.");
                }
            }
            //read the other line that is a third line that contains additional metadata
            nextLine = createObjects.nextLine();
            System.out.println("--read unimportant line.");
            boolean again = true;
            
            while(again){
                //checks to see if there is another line after current 
                if(createObjects.hasNext()){
                    nextLine = createObjects.nextLine();
                }
                else{
                    break;
                }
                //splits line based on list separator DELIMITER, makes sure that has the correct number of columns 
                studentLine = nextLine.split(DELIMITER, numOfCols);
                while(studentLine.length < numOfCols){
                    nextLine = nextLine + createObjects.nextLine();
                    System.out.println(nextLine);
                    studentLine = nextLine.split(DELIMITER, numOfCols);
                }
                
                //check System.out.println(count++ + " " );
                //check System.out.println(studentLine.length + " ");
                
                //split the read in line into studentLine
                studentLine = nextLine.split(DELIMITER, numOfCols);
                
                //create the student object with the personal information and self review
                String lname = studentLine[locationLast];
                 System.out.println(lname); //check
                String fname = studentLine[locationFirst];
                //check System.out.println(fname);
                String email = studentLine[locationEmail];
                //check System.out.println(email);
                //String teamNumString = studentLine[locationTeamNum];
                //int teamNum = teamNumString;
                //check System.out.println(locationTeamNum);
                //check System.out.println(studentLine.length);
                //check System.out.println(studentLine[locationTeamNum]);
                int teamNum = Integer.parseInt(studentLine[locationTeamNum]);
                //check System.out.println(teamNum);
                String teamName = studentLine[locationTeamName];
                //check System.out.println(teamName);
                String faculty = studentLine[locationFaculty];

                //create the object
                System.out.println("Making the student object now...");
                Student s = new Student(fname, lname, email, teamNum, teamName, faculty);
                //save the object to the arraylist - student object added to roster
                roster.add(s);  
                System.out.println("--Student object created successfully for " + fname + " " + lname +".");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Round_3_Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        //returns completed student roster
        return roster;
    } 
    
    public static void createFeedback(String csvFileName, ArrayList<Student> roster, String delimiter){
        File file = new File(csvFileName);
        System.out.append("--File created.");
        final String DELIMITER = delimiter;
        System.out.println("--Delimiter created.");
        
        try {
            //Scanner objects to read the file twice
            Scanner createObjects = new Scanner (file); //create the objects
            System.out.println("--Scanner object created.");
            //read first line into string -- not needed
            String nextLine = createObjects.nextLine();
            //read second line into a string -- use to create variables to determine column name
            nextLine = createObjects.nextLine();
            //split string into an array by the delimiter "/"
            String[] feedbackLine = nextLine.split(DELIMITER);
            int numOfCols = feedbackLine.length;
            System.out.println("--Determine number of feedback columns from split.");
            //create variables to store column numbers of the defining student object titles
            int locationLast = -1, locationFirst = -1 /*, locationDate = -1*/;
            
            //creates arrays to hold locations of each column containing information relevant to the reviewed information groups 
            int[] reviewedNames = new int[7];
            int[] locationTech = new int[7];
            int[] locationAna = new int[7];
            int[] locationCom = new int[7];
            int[] locationPart = new int[7];
            int[] locationPerf = new int[7];
            int[] locationWell = new int[7];
            int[] locationImpr = new int[7];
            int[] locationAdd = new int[7];
            
            System.out.println("--Created arrays for each feedback.");
            
            //determine column numbers for each of the below information categories
            System.out.println("--Enter into locating loop. Please wait.");
            for (int i = 0; i < numOfCols; i++){
                if (feedbackLine[i].contains("Recipient Last Name")){
                    locationLast = i;
                    System.out.println("----Last name located.");
                }
                else if (feedbackLine[i].contains("Recipient First Name")){
                    locationFirst = i;
                    System.out.println("----First name located.");
                }
                /*else if (feedbackLine[i].contains("End Date")){
                    locationDate = i;
                    System.out.println("----End date located.");
                }*/
                else if (feedbackLine[i].contains("technology") /*&& checkArray(locationTech, i)*/){
//                    for (int j = 0; j < locationTech.length; j++){
//                        if(locationTech[j] == 0){
//                            locationTech[j] = i;
//                        }
//                        break;
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    //this determines what number of the array is being saved to make sure that each column is being saved for each group
                    locationTech[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----technology located.");
                }
                else if (feedbackLine[i].contains("analytical")){
//                    for (int j = 0; j < locationAna.length; j++){
//                        if(locationAna[j] == 0){
//                            locationAna[j] = i;
//                        }                     
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    locationAna[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----analytical located.");
                }
                else if (feedbackLine[i].contains("communication")){
//                    for (int j = 0; j < locationCom.length; j++){
//                        if(locationCom[j] == 0){
//                            locationCom[j] = i;
//                        }
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    locationCom[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----communication located.");
                }
                else if (feedbackLine[i].contains("participation")){
//                    for (int j = 0; j < locationPart.length; j++){
//                        if(locationPart[j] == 0){
//                            locationPart[j] = i;
//                        }
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    locationPart[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----participation located.");
                }
                else if (feedbackLine[i].contains("performance")){
//                    for (int j = 0; j < locationPerf.length; j++){
//                        if(locationPerf[j] == 0){
//                            locationPerf[j] = i;
//                        }
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    locationPerf[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----performance located.");
                }
                else if (feedbackLine[i].contains("strengths")){
//                    for (int j = 0; j < locationWell.length; j++){
//                        if(locationWell[j] == 0){
//                            locationWell[j] = i;
//                        }
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    locationWell[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----strengths located.");
                }
                else if (feedbackLine[i].contains("growth")){
//                    for (int j = 0; j < locationImpr.length; j++){
//                        if(locationImpr[j] == 0){
//                            locationImpr[j] = i;
//                        }
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    locationImpr[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----growth located.");
                }
                else if (feedbackLine[i].contains("Comments")){
//                    for (int j = 0; j < locationAdd.length; j++){
//                        if(locationAdd[j] == 0){
//                            locationAdd[j] = i;
//                        }
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                    locationAdd[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                    System.out.println("----comments located.");
                }
                              
                else if (feedbackLine[i].contains("teammate") /*&& checkArray(reviewedNames, i, countNames)*/ ){
//                    countNames++;
//                    for (int j = 0; j < reviewedNames.length; j++){
//                        if(reviewedNames[j] == 0){
//                            reviewedNames[j] = i;
//                        }
//                    }
//                    System.out.println(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()));
                      reviewedNames[Integer.parseInt(feedbackLine[i].substring(feedbackLine[i].length()-1,feedbackLine[i].length()))] = i;
                      System.out.println("----teammate located.");
                }
            }
                        
            System.out.println("--Finished scanning important line and assigning location variables.");
            
            //read the other line that is a third line that contains additional metadata
            nextLine = createObjects.nextLine();
            
            boolean again = true;

            System.out.println("--Start creating feedback objects now. Entering loop. Please wait.");
            while(again){
                //make sure has another line
                if(createObjects.hasNext()){
                    nextLine = createObjects.nextLine();
                }
                else{
                    
                    break;
                }
                //split the feedback on the correct DELIMITER and has correct number of columns 
                feedbackLine = nextLine.split(DELIMITER, numOfCols);
                while(feedbackLine.length < numOfCols){
                    nextLine = nextLine + createObjects.nextLine();
                    feedbackLine = nextLine.split(DELIMITER, numOfCols);
                }
                
                //System.out.println(count++ + " " );
                //System.out.println(feedbackLine.length + " ");
                
                //split the read in line into feedbackline
                feedbackLine = nextLine.split(DELIMITER,numOfCols);
                
                //provider location out here to reduce redundancy 
                String provider = feedbackLine[locationFirst] + " " + feedbackLine[locationLast]; 
                //System.out.println("Provider: " + provider);
                
                for (int i = 0; i < 7; i++){
                    //create variables to contain feedback review data responses, save the responses into variables
                    System.out.println("Provider: " + provider);
                    String receiver;
                    if (i == 0){
                        receiver = provider;
                        System.out.println("Receiver: " + receiver);
                    }
                    else {
                        //System.out.println(feedbackLine.length);
                        receiver = feedbackLine[reviewedNames[i]];
                        System.out.println("Receiver: " + receiver);
                    }
                    //checks -- System.out.println(locationTech.length);
                    //checks -- System.out.println(i);
                    String technical = feedbackLine[locationTech[i]];
                    System.out.println("Technical: " + technical);
                    String analytical = feedbackLine[locationAna[i]];
                    System.out.println("Analytical: " + analytical);
                    String communication = feedbackLine[locationCom[i]];
                    System.out.println("Communication: " + communication);
                    String participation = feedbackLine[locationPart[i]];
                    System.out.println("Participation: " + participation);
                    String performance = feedbackLine[locationPerf[i]];
                    System.out.println("Performance: " + performance);
                    String strength = feedbackLine[locationWell[i]];
                    System.out.println("Strength: " + strength);
                    String improve = feedbackLine[locationImpr[i]];
                    System.out.println("Improvements: " + improve);
                    String additional = feedbackLine[locationAdd[i]];
                    System.out.println("Additional Comments: " + additional);
                    /*String date = feedbackLine[locationDate];
                    System.out.println("Date Submitted: " + date);*/
                    
                    System.out.println(); //blank line;
                    
                    //creates temp student object to be used 
                    Student sProvider = new Student();
                    for(Student s: roster){
                        if(s.getName().equalsIgnoreCase(provider)){
                            sProvider = s;
                        }
                    }
                    Student sReceiver = new Student();
                    for(Student s: roster){
                        if(s.getName().equalsIgnoreCase(receiver)){
                            sReceiver = s;
                        }
                    }
                    
                    Feedback f = new Feedback (sProvider, sReceiver, technical, analytical, communication, participation, performance, strength, improve, additional /*, date*/);
                    
                    sProvider.addToProvidedReviews(f);
                    sReceiver.addToReview(f);
                    
                    System.out.println("----Successfully created feedback object for: " + receiver + " given by " + provider + ".");
                }
                System.out.println("--All feedback successfully given.");
            }
          
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Round_3_Review.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //not really used 
    public static boolean checkArray (int[] array, int i, int count){
        boolean result = false;
        for (int x = 0; x < array.length; x++){
            if(array[count] != i){
                result = true;
            }
        }
        return result;
    }
    //not really used 
    public static boolean checkArray (int[] array, int i){
        boolean result = false;
        for (int x = 0; x < array.length; x++){
            if (array[x] == i){
                result = false;
            }
            else if (array[x] != i && array[x] == 0){
                result = true;
                System.out.println("The else if that would result in a true ran.");
            }
            else{
                result = false;
                System.out.println("Error within the method checkArray.");
            }
        }
        return result;
    }
    
    //not really used yet 
    public static ArrayList<Team> createTeams (ArrayList<Student> roster, String csvFileName, String delimiter){
        //create the teams ArrayList to store the team objects
        ArrayList <Team> teamList = new ArrayList <Team>();
        System.out.println("--team array created.");
        final String DELIMITER = delimiter;
        System.out.println("--delimiter set.");
        try {
            File file = new File(csvFileName);
            
            //Scanner objects to read the file twice
            Scanner createObjects = new Scanner (file); //create the objects
            System.out.println("--scanner ibject created.");
            //read first line into string -- not needed
            String nextLine = createObjects.nextLine();
            //read second line into a string -- use to create variables to determine column name
            nextLine = createObjects.nextLine();
            //split string into an array by the delimiter "/"
            String[] teamLine = nextLine.split(DELIMITER);
            
            int numOfCols = teamLine.length;
            
            //create variables to store column numbers of the defining student object titles
            int locationFaculty = -1, locationMeetPlace = -1, locationTeamName = -1, locationTeamNum = -1;
            
            for (int i = 0; i < teamLine.length; i++){
                if (teamLine[i].contains("faculty sponsor")){
                    locationFaculty = i;
                }
                else if (teamLine[i].contains("meeting place")){
                    locationMeetPlace = i;
                }
                else if (teamLine[i].contains("TEAMNUM")){
                    locationTeamNum = i;
                }
                else if (teamLine[i].contains("TEAMNAME")){
                    locationTeamName = i;
                }
            }
            //read the other line that is a third line that contains additional metadata
            nextLine = createObjects.nextLine();
            
            //copy over
            
            boolean again = true;
            
            int count = 0;
            
            while(again){
                
                if(createObjects.hasNext()){
                    nextLine = createObjects.nextLine();
                }
                else{
                    break;
                }
                
                teamLine = nextLine.split(DELIMITER, numOfCols);
                while(teamLine.length < numOfCols){
                    nextLine = nextLine + createObjects.nextLine();
                    teamLine = nextLine.split(DELIMITER, numOfCols);
                }
                
                //check System.out.println(count++ + " " );
                //check System.out.println(teamLine.length + " ");
                
                //split the read in line into studentLine
                teamLine = nextLine.split(DELIMITER,numOfCols);
            
                
                //create the student object with the personal information and self review
                String faculty = teamLine[locationFaculty];
                String meetPlace = teamLine[locationMeetPlace];
                int teamNum = Integer.parseInt(teamLine[locationTeamNum]);
                String teamName = teamLine[locationTeamName];

                boolean alreadyCreated = false;
                for(Team t: teamList){
                    if(t.getTeamNum() == teamNum){
                        alreadyCreated = true;
                    }
                }
                
                if(alreadyCreated != true){
                    //create the object
                    Team t = new Team(faculty, meetPlace, teamNum, teamName);
                    //save the object to the arraylist
                    teamList.add(t); 
                } 
            }    
                
            //now add students to the teams
            for(Student s: roster){
                for(Team t: teamList){
                    if(s.getTeamNum() == t.getTeamNum()){
                        t.addTeam(s);
                    }
                }
            } 
                
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Round_3_Review.class.getName()).log(Level.SEVERE, null, ex);
        }
       return teamList; 
    }
    
    //creates the actual documents being used and shared 
    public static void CreateDocs (String csvfilename, ArrayList<Student> roster, /*ArrayList<Team> teamList,*/ String roundNum, String semester, String year, String base_path)throws IOException {
                
        for (Student s: roster){            
            String dest = base_path + s.getFaculty() + "\\" + s.getTeamName() + "\\Round " + roundNum + "\\" + s.getLname() + s.getFname() + ".pdf";
//"C:\\Users\\Kat\\Box Sync\\2019 Fall Capstone\\" + s.getFaculty() + "\\" + s.getTeamName() + "\\Round " + roundNum + "\\" + s.getLname() + s.getFname() + ".pdf";
//"C:\\Users\\mezin\\Box\\2019 Fall Capstone\\" + s.getFaculty() + "\\" + s.getTeamName() + "\\Round " + roundNum + "\\" + s.getLname() + s.getFname() + ".pdf";
            
            //saved in two parts: the average and the personal review 
            double tech[] = s.TechAvg();
            double ana[] = s.AnaAvg();
            double com[] = s.ComAvg();
            double part[] = s.PartAvg();
            double perf[] = s.PerfAvg();
            
            //initialize pdf writer
            FileOutputStream fos = new FileOutputStream(dest);
            PdfWriter writer = new PdfWriter(fos);

            //initialize pdf document
            PdfDocument pdf = new PdfDocument(writer);

            //initialize document
            Document document = new Document (pdf);
            
            //gives identifying information
            document.add(new Paragraph(s.getName() + "\n" + s.getEmail() + "\n" + s.getTeamName() + "\n" + year + " " + semester + " Round " + roundNum + " Capstone 360 Review"));
            
            //start actual review info
            document.add(new Paragraph ("\nSkills Review"));
            
            //create a list 
            List listAverage = new List()
                    .setSymbolIndent(12)
                    .setListSymbol("\u2022");
                       
            if(roundNum.equals("3")){
                listAverage.add(new ListItem("Skill Set:\t\tPersonal\t\tAverage\t\tRound 1\t\tRound 2"));
                listAverage.add(new ListItem("Technical:\t\t\t" + tech[0] + "\t\t\t" + tech[1] + "\t\t\t\t" + s.getPrevTech1() + "\t\t\t\t" + s.getPrevTech2()));
                listAverage.add(new ListItem("Analytical:\t\t\t" + ana[0] + "\t\t\t" + ana[1] + "\t\t\t\t" + s.getPrevAna1() + "\t\t\t\t" + s.getPrevAna2()));
                listAverage.add(new ListItem("Communication:\t" + com[0] + "\t\t\t" + com[1] + "\t\t\t\t" + s.getPrevCom1() + "\t\t\t\t" + s.getPrevCom2()));
                listAverage.add(new ListItem("Participation:\t\t" + part[0] + "\t\t\t" + part[1] + "\t\t\t\t" + s.getPrevPart1() + "\t\t\t\t" + s.getPrevPart2()));
                listAverage.add(new ListItem("Performance:\t\t" + perf[0] + "\t\t\t" + perf[1] + "\t\t\t\t" + s.getPrevPerf1() + "\t\t\t\t" + s.getPrevPerf2()));
            }
            else if (roundNum.equals("2")){
                listAverage.add(new ListItem("Skill Set:\t\tPersonal\t\tAverage\t\tRound 1"));
                listAverage.add(new ListItem("Technical:\t\t\t" + tech[0] + "\t\t\t" + tech[1] + "\t\t\t\t" + s.getPrevTech1()));
                listAverage.add(new ListItem("Analytical:\t\t\t" + ana[0] + "\t\t\t" + ana[1] + "\t\t\t\t" + s.getPrevAna1()));
                listAverage.add(new ListItem("Communication:\t" + com[0] + "\t\t\t" + com[1] + "\t\t\t\t" + s.getPrevCom1()));
                listAverage.add(new ListItem("Participation:\t\t" + part[0] + "\t\t\t" + part[1] + "\t\t\t\t" + s.getPrevPart1()));
                listAverage.add(new ListItem("Performance:\t\t" + perf[0] + "\t\t\t" + perf[1] + "\t\t\t\t" + s.getPrevPerf1()));
            }
            else {
                listAverage.add(new ListItem("Skill Set:\t\tPersonal\t\tAverage"));
                listAverage.add(new ListItem("Technical:\t\t\t" + tech[0] + "\t\t\t" + tech[1]));
                listAverage.add(new ListItem("Analytical:\t\t\t" + ana[0] + "\t\t\t" + ana[1]));
                listAverage.add(new ListItem("Communication:\t" + com[0] + "\t\t\t" + com[1]));
                listAverage.add(new ListItem("Participation:\t\t" + part[0] + "\t\t\t" + part[1]));
                listAverage.add(new ListItem("Performance:\t\t" + perf[0] + "\t\t\t" + perf[1]));
            }
            document.add(listAverage);
            
            document.add(new Paragraph("\nExcellent = 5 | Very Good = 4 | Satisfactory = 3 | Fair = 2 | Poor = 1"));
            
            document.add(new Paragraph ("\nStrengths:\n"));
            document.add(s.AllStrength());
            
            document.add(new Paragraph ("\nImprovements:\n"));
            document.add(s.AllImprove());           

            document.add(new Paragraph ("\nAdditional Comments:\n"));
            document.add(s.AllAdditional());           

            //close doc
            document.close();
            
            System.out.println("Document created for " + s.getName() + " successfully.");
        } 
    }
    
    public static void CreateCSV (ArrayList<Student> roster, String roundNum, String base_path /*, ArrayList<Team> teamList*/) throws IOException{
        
        String dest = base_path + "Rounds\\Previous Data\\Round" + roundNum + ".csv";
//"C:\\Users\\Kat\\Box Sync\\2019 Fall Capstone\\Rounds\\Previous Data\\Round" + roundNum + ".csv";
// "C:\\Users\\mezin\\Box\\2019 Fall Capstone\\Rounds\\Previous Data\\Round" + roundNum + ".csv";
                
        try (PrintWriter writer = new PrintWriter(new File(dest))){
        
            StringBuilder sb = new StringBuilder();
            sb.append("Name");
            sb.append('#');
            sb.append("Email");
            sb.append('#');
            sb.append("Technical");
            sb.append('#');
            sb.append("Analytical");
            sb.append('#');
            sb.append("Communication");
            sb.append('#');
            sb.append("Participation");
            sb.append('#');
            sb.append("Performance");
            sb.append('\n');

            //writer.write(sb.toString());
            System.out.println("Column Headers");
            
            for (Student s : roster){
                
                double tech[] = s.TechAvg();
                double ana[] = s.AnaAvg();
                double com[] = s.ComAvg();
                double part[] = s.PartAvg();
                double perf[] = s.PerfAvg();
                
                sb.append(s.getName());
                sb.append('#');
                sb.append(s.getEmail());
                sb.append('#');
                sb.append(tech[1]);
                sb.append('#');
                sb.append(ana[1]);
                sb.append('#');
                sb.append(com[1]);
                sb.append('#');
                sb.append(part[1]);
                sb.append('#');
                sb.append(perf[1]);
                sb.append('\n');
                
                //writer.write(sb.toString());
            }
            
            System.out.println("CSV successfully printed");
            
            writer.write(sb.toString());
            
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }              
    }
    
    public static void ReadRoundsCSV (String PREVIOUSROUNDSCSV, ArrayList<Student> roster, String delimiter){
        final String DELIMITER = delimiter;
        
        //contain in try-catch statement 
        try {
            //create file object
            File file = new File(PREVIOUSROUNDSCSV);
            
            //Scanner objects to read the file twice
            Scanner createObjects = new Scanner (file); //create the objects
            
            //read first line into string -- use to create variables to determine column name
            String nextLine = createObjects.nextLine();
            
            //split string into an array by the delimiter
            String[] studentLine = nextLine.split(delimiter);
            int numOfCols = studentLine.length;
            
            //create variables to store column numbers of the defining student object titles
            int locationName = -1, locationEmail = -1, locationTech = -1, locationAna = -1, locationCom = -1, locationPart = -1, locationPerf = -1;
            
            for (int i = 0; i < numOfCols; i++){
                //saves column location of name 
                if (studentLine[i].contains("Name")){
                    locationName = i;
                }
                //saves column location of email
                else if (studentLine[i].contains("Email")){
                    locationEmail = i;
                }
                //saves column location of techincal average
                else if (studentLine[i].contains("Technical")){
                    locationTech = i;
                }
                else if (studentLine[i].contains("Analytical")){
                    locationAna = i;
                }
                else if (studentLine[i].contains("Communication")){
                    locationCom = i;
                }
                else if (studentLine[i].contains("Participation")){
                    locationPart = i;
                }
                else if (studentLine[i].contains("Performance")){
                    locationPerf = i;
                }
            }
            
            boolean again = true;
            
            while(again){
                //checks to see if there is another line after current 
                if(createObjects.hasNext()){
                    nextLine = createObjects.nextLine();
                }
                else{
                    break;
                }
                //splits line based on list separator DELIMITER, makes sure that has the correct number of columns 
                studentLine = nextLine.split(DELIMITER, numOfCols);
                while(studentLine.length < numOfCols){
                    nextLine = nextLine + createObjects.nextLine();
                    studentLine = nextLine.split(DELIMITER, numOfCols);
                }
                
                //check System.out.println(count++ + " " );
                //check System.out.println(studentLine.length + " ");
                
                //split the read in line into studentLine
                studentLine = nextLine.split(DELIMITER, numOfCols);
                
                //create the student object with the personal information and self review
                String name = studentLine[locationName];
                String email = studentLine[locationEmail];
                //String teamNumString = studentLine[locationTeamNum];
                //int teamNum = teamNumString;
                //check System.out.println(locationTeamNum);
                //check System.out.println(studentLine.length);
                //check System.out.println(studentLine[locationTeamNum]);
                String tech = studentLine[locationTech];
                String ana = studentLine[locationAna];
                String com = studentLine[locationCom];
                String part = studentLine[locationPart];
                String perf = studentLine[locationPerf];                

                for (Student s: roster){
                    if (s.getName().equals(name)){
                        s.setPrevTech1(tech);
                        s.setPrevAna1(ana);
                        s.setPrevCom1(com);
                        s.setPrevPart1(part);
                        s.setPrevPerf1(perf);
                    }
                }
            }
            //for(Student s: roster){
            //System.out.println(s.getPrevTech());}
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Round_3_Review.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void ReadRoundsCSV2 (String PREVIOUSROUNDSCSV2, ArrayList<Student> roster, String delimiter){
        final String DELIMITER = delimiter;
        
        //contain in try-catch statement 
        try {
            //create file object
            File file = new File(PREVIOUSROUNDSCSV2);
            
            //Scanner objects to read the file twice
            Scanner createObjects = new Scanner (file); //create the objects
            
            //read first line into string -- use to create variables to determine column name
            String nextLine = createObjects.nextLine();
            
            //split string into an array by the delimiter
            String[] studentLine = nextLine.split(delimiter);
            int numOfCols = studentLine.length;
            
            //create variables to store column numbers of the defining student object titles
            int locationName = -1, locationEmail = -1, locationTech = -1, locationAna = -1, locationCom = -1, locationPart = -1, locationPerf = -1;
            
            for (int i = 0; i < numOfCols; i++){
                //saves column location of name 
                if (studentLine[i].contains("Name")){
                    locationName = i;
                }
                //saves column location of email
                else if (studentLine[i].contains("Email")){
                    locationEmail = i;
                }
                //saves column location of techincal average
                else if (studentLine[i].contains("Technical")){
                    locationTech = i;
                }
                else if (studentLine[i].contains("Analytical")){
                    locationAna = i;
                }
                else if (studentLine[i].contains("Communication")){
                    locationCom = i;
                }
                else if (studentLine[i].contains("Participation")){
                    locationPart = i;
                }
                else if (studentLine[i].contains("Performance")){
                    locationPerf = i;
                }
            }
            
            boolean again = true;
            
            while(again){
                //checks to see if there is another line after current 
                if(createObjects.hasNext()){
                    nextLine = createObjects.nextLine();
                }
                else{
                    
                    break;
                }
                //splits line based on list separator DELIMITER, makes sure that has the correct number of columns 
                studentLine = nextLine.split(DELIMITER, numOfCols);
                while(studentLine.length < numOfCols){
                    nextLine = nextLine + createObjects.nextLine();
                    studentLine = nextLine.split(DELIMITER, numOfCols);
                }
                
                //check System.out.println(count++ + " " );
                //check System.out.println(studentLine.length + " ");
                
                //split the read in line into studentLine
                studentLine = nextLine.split(DELIMITER, numOfCols);
                
                //create the student object with the personal information and self review
                String name = studentLine[locationName];
                String email = studentLine[locationEmail];
                //String teamNumString = studentLine[locationTeamNum];
                //int teamNum = teamNumString;
                //check System.out.println(locationTeamNum);
                //check System.out.println(studentLine.length);
                //check System.out.println(studentLine[locationTeamNum]);
                String tech = studentLine[locationTech];
                String ana = studentLine[locationAna];
                String com = studentLine[locationCom];
                String part = studentLine[locationPart];
                String perf = studentLine[locationPerf];                


                for (Student s: roster){
                    if (s.getName().equals(name)){
                        s.setPrevTech2(tech);
                        s.setPrevAna2(ana);
                        s.setPrevCom2(com);
                        s.setPrevPart2(part);
                        s.setPrevPerf2(perf);
                    }
                }
            }
            //for(Student s: roster){
            //System.out.println(s.getPrevTech());}
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Round_3_Review.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}