/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itextpdf.capstone_360;
import java.util.ArrayList;
import java.lang.Math;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;

/**
 *
 * @author mezin
 */
public class Student {
    private String fname, lname, email, teamName, faculty;
    private int teamNum;
    private double technicalAverageC, analyticalAverageC, communicationAverageC, performanceAverageC, overallAverageC;
    private ArrayList<Feedback> review = new ArrayList<Feedback>();
    private ArrayList<Feedback> providedReviews = new ArrayList<Feedback>();
    private String prevTech1, prevAna1, prevCom1, prevPart1, prevPerf1;
    private String prevTech2, prevAna2, prevCom2, prevPart2, prevPerf2;

    public Student (){
        
    }
    
    public Student (String fname, String lname, String email, int teamNum, String teamName, String faculty){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.teamNum = teamNum;
        this.teamName = teamName;
        this.faculty = faculty;
    }
    
    
    
    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }
    
    public String getName(){
        return fname + " " + lname;
    }
            

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    public ArrayList<Feedback> getReview() {
        /*for(Feedback f: review){
            if()
        }*/
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(ArrayList<Feedback> review) {
        this.review = review;
    }
    
    public void addToReview(Feedback f){
        review.add(f);
    }

    /**
     * @return the providedReviews
     */
    public ArrayList<Feedback> getProvidedReviews() {
        return providedReviews;
    }

    /**
     * @param providedReviews the providedReviews to set
     */
    public void setProvidedReviews(ArrayList<Feedback> providedReviews) {
        this.providedReviews = providedReviews;
    }
    
    public void addToProvidedReviews(Feedback f){
        providedReviews.add(f);
    }

    /**
     * @return the teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the teamNum
     */
    public int getTeamNum() {
        return teamNum;
    }

    /**
     * @param teamNum the teamNum to set
     */
    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }
    
    public double[] TechAvg (){
        double[] resultTech = new double[2];
        int sum = 0;
        int count = 0;
        for (Feedback f: review){
            if(f.getReceiver().equalsIgnoreCase(f.getProvider())){
                resultTech[0] = f.getTechnical();
            }
            else {
                sum += f.getTechnical();
                count++;
            }

        }
        double avg = sum/(double)count;
        avg = (double) Math.round(avg * 100)/100;
        resultTech[1] = avg;
        return resultTech;
    }
    
    public double[] AnaAvg (){
        double[] resultAna = new double[2];
        int sum = 0;
        int count = 0;
        for (Feedback f: review){
            if(f.getReceiver().equalsIgnoreCase(f.getProvider())){
                resultAna[0] = f.getAnalytical();
            }
            else {
                sum += f.getAnalytical();
                count++;
            }

        }
        double avg = sum/(double)count;
        avg = (double) Math.round(avg * 100)/100;
        resultAna[1] = avg;
        return resultAna;
    }
    
    public double[] ComAvg (){
        double[] resultCom = new double[2];
        int sum = 0;
        int count = 0;
        for (Feedback f: review){
            if(f.getReceiver().equalsIgnoreCase(f.getProvider())){
                resultCom[0] = f.getCommunication();
            }
            else {
                sum += f.getCommunication();
                count++;
            }

        }
        double avg = sum/(double)count;
        avg = (double) Math.round(avg * 100)/100;
        resultCom[1] = avg;
        return resultCom;
    }
    
    public double[] PartAvg (){
        double[] resultPart = new double[2];
        int sum = 0;
        int count = 0;
        for (Feedback f: review){
            if(f.getReceiver().equalsIgnoreCase(f.getProvider())){
                resultPart[0] = f.getParticipation();
            }
            else {
                sum += f.getParticipation();
                count++;
            }

        }
        double avg = sum/(double)count;
        avg = (double) Math.round(avg * 100)/100;
        resultPart[1] = avg;
        return resultPart;
    }
    
    public double[] PerfAvg (){
        double[] resultPerf = new double[2];
        int sum = 0;
        int count = 0;
        for (Feedback f: review){
            if(f.getReceiver().equalsIgnoreCase(f.getProvider())){
                resultPerf[0] = f.getPerformance();
            }
            else {
                sum += f.getPerformance();
                count++;
            }

        }
        double avg = sum/(double)count;
        avg = (double) Math.round(avg * 100)/100;
        resultPerf[1] = avg;
        return resultPerf;
    }
    
    public List AllStrength (){
        String output = "";
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");
        for(Feedback f: review){
            if(f.getReceiver().equalsIgnoreCase(f.getProvider()) && (f.getStrength().length() > 0 || f.getStrength().equalsIgnoreCase("N/A"))){
                list.add(new ListItem("Personal Review: " + f.getStrength() + "\n"));
            }
            else if (f.getStrength().length() > 0 || f.getStrength().equalsIgnoreCase("N/A")){
                //output += f.getStrength() + "\n";
                list.add(new ListItem(f.getStrength() + "\n"));
            }
        }
        //return output;
        return list;
    }
    
    public List AllImprove (){
        String output = "";
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");
        for(Feedback f: review){
            if (f.getReceiver().equalsIgnoreCase(f.getProvider()) && (f.getImprove().length() > 0 || f.getImprove().equalsIgnoreCase("N/A"))){
                list.add(new ListItem("Personal Review: " + f.getImprove() + "\n"));
            }
            else if (f.getImprove().length() > 0 || f.getImprove().equalsIgnoreCase("N/A")){
                //output += f.getImprove() + "\n"; 
                list.add(new ListItem(f.getImprove() + "\n"));
            }
        }
        //return output;
        return list;
    }
    
    public List AllAdditional (){
        String output = "";
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");
        for(Feedback f: review){
            if (f.getReceiver().equalsIgnoreCase(f.getProvider()) && (f.getAdditional().length() > 0|| f.getAdditional().equalsIgnoreCase("n/a"))){
                list.add(new ListItem("Personal Reivew: " + f.getAdditional() + "\n"));
            }
            else if (f.getAdditional().length() > 0|| f.getAdditional().equalsIgnoreCase("n/a")){
                //output += f.getAdditional() + "\n";
                list.add(new ListItem(f.getAdditional() + "\n"));
            }
        }
        return list;
        //return output;
    }

    /**
     * @return the prevTech
     */
    public String getPrevTech1() {
        return prevTech1;
    }

    /**
     * @param prevTech the prevTech to set
     */
    public void setPrevTech1(String prevTech) {
        this.prevTech1 = prevTech;
    }

    /**
     * @return the prevAna
     */
    public String getPrevAna1() {
        return prevAna1;
    }

    /**
     * @param prevAna the prevAna to set
     */
    public void setPrevAna1(String prevAna) {
        this.prevAna1 = prevAna;
    }

    /**
     * @return the prevCom
     */
    public String getPrevCom1() {
        return prevCom1;
    }

    /**
     * @param prevCom the prevCom to set
     */
    public void setPrevCom1(String prevCom) {
        this.prevCom1 = prevCom;
    }

    /**
     * @return the prevPart
     */
    public String getPrevPart1() {
        return prevPart1;
    }

    /**
     * @param prevPart the prevPart to set
     */
    public void setPrevPart1(String prevPart) {
        this.prevPart1 = prevPart;
    }

    /**
     * @return the prevPerf
     */
    public String getPrevPerf1() {
        return prevPerf1;
    }

    /**
     * @param prevPerf the prevPerf to set
     */
    public void setPrevPerf1(String prevPerf) {
        this.prevPerf1 = prevPerf;
    }

        /**
     * @return the prevTech
     */
    public String getPrevTech2() {
        return prevTech2;
    }

    /**
     * @param prevTech the prevTech to set
     */
    public void setPrevTech2(String prevTech) {
        this.prevTech2 = prevTech;
    }

    /**
     * @return the prevAna
     */
    public String getPrevAna2() {
        return prevAna2;
    }

    /**
     * @param prevAna the prevAna to set
     */
    public void setPrevAna2(String prevAna) {
        this.prevAna2 = prevAna;
    }

    /**
     * @return the prevCom
     */
    public String getPrevCom2() {
        return prevCom2;
    }

    /**
     * @param prevCom the prevCom to set
     */
    public void setPrevCom2(String prevCom) {
        this.prevCom2 = prevCom;
    }

    /**
     * @return the prevPart
     */
    public String getPrevPart2() {
        return prevPart2;
    }

    /**
     * @param prevPart the prevPart to set
     */
    public void setPrevPart2(String prevPart) {
        this.prevPart2 = prevPart;
    }

    /**
     * @return the prevPerf
     */
    public String getPrevPerf2() {
        return prevPerf2;
    }

    /**
     * @param prevPerf the prevPerf to set
     */
    public void setPrevPerf2(String prevPerf) {
        this.prevPerf2 = prevPerf;
    }
    
    /**
     * @return the faculty
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
