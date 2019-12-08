/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itextpdf.capstone_360;

public class Feedback {
    private Student provider, receiver;
    private String strength, improve, additional;
    private String technical, analytical, communication, participation, performance;
    /*private String date;*/
    
    public Feedback(Student provider, Student receiver, String technical, String analytical, String communication, String participation, String performance, 
            String strength, String improve, String additional /*, String date*/){
        this.provider = provider;
        this.receiver = receiver;
        this.technical = technical;
        this.analytical = analytical;
        this.communication = communication;
        this.participation = participation;
        this.performance = performance;
        this.strength = strength;
        this.improve = improve;
        this.additional = additional;
        /*this.date = date;*/
        
        
    }

    /**
     * @return the provider
     */
    public String getProvider() {
        return provider.getName();
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(Student provider) {
        this.provider = provider;
    }

    /**
     * @return the receiver
     */
    public String getReceiver() {
        return receiver.getName();
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(Student receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the strength
     */
    public String getStrength() {
        String result;
        if (strength.equalsIgnoreCase("n/a") || strength.equalsIgnoreCase("none")) {
            result = "";
        }
        else{
            result = strength;
        }
        
        return result;
    }

    /**
     * @param strength the strength to set
     */
    public void setStrength(String strength) {
        this.strength = strength;
    }

    /**
     * @return the improve
     */
    public String getImprove() {
        String result;
        if (improve.equalsIgnoreCase("n/a") || improve.equalsIgnoreCase("none")) {
            result = "";
        }
        else{
            result = improve;
        }
        
        return result;
    }

    /**
     * @param improve the improve to set
     */
    public void setImprove(String improve) {
        this.improve = improve;
    }

    /**
     * @return the additional
     */
    public String getAdditional() {
        String result;
        if (additional.equalsIgnoreCase("n/a") || additional.equalsIgnoreCase("none")) {
            result = "";
        }
        else{
            result = additional;
        }
        
        return result;
    }

    /**
     * @param additional the additional to set
     */
    public void setAdditional(String additional) {
        this.additional = additional;
    }

    /**
     * @return the technical
     */
    public int getTechnical() {
        int num = 0;
        if(technical.equalsIgnoreCase("excellent")){
            num = 5;
        }
        else if (technical.equalsIgnoreCase("very good")){
            num = 4;
        }
        else if (technical.equalsIgnoreCase("satisfactory")){
            num = 3;
        }
        else if (technical.equalsIgnoreCase("fair")){
            num = 2;
        }
        else if (technical.equalsIgnoreCase("poor")){
            num = 1;
        }
        return num;
    }

    /**
     * @param technical the technical to set
     */
    public void setTechnical(String technical) {
        this.technical = technical;
    }

    /**
     * @return the analytical
     */
    public int getAnalytical() {
        int num = 0;
        if(analytical.equalsIgnoreCase("excellent")){
            num = 5;
        }
        else if (analytical.equalsIgnoreCase("very good")){
            num = 4;
        }
        else if (analytical.equalsIgnoreCase("satisfactory")){
            num = 3;
        }
        else if (analytical.equalsIgnoreCase("fair")){
            num = 2;
        }
        else if (analytical.equalsIgnoreCase("poor")){
            num = 1;
        }
        return num;
    }

    /**
     * @param analytical the analytical to set
     */
    public void setAnalytical(String analytical) {
        this.analytical = analytical;
    }

    /**
     * @return the communication
     */
    public int getCommunication() {
        int num = 0;
        if(communication.equalsIgnoreCase("excellent")){
            num = 5;
        }
        else if (communication.equalsIgnoreCase("very good")){
            num = 4;
        }
        else if (communication.equalsIgnoreCase("satisfactory")){
            num = 3;
        }
        else if (communication.equalsIgnoreCase("fair")){
            num = 2;
        }
        else if (communication.equalsIgnoreCase("poor")){
            num = 1;
        }
        return num;
    }

    /**
     * @param communication the communication to set
     */
    public void setCommunication(String communication) {
        this.communication = communication;
    }

    /**
     * @return the participation
     */
    public int getParticipation() {
        int num = 0;
        if(participation.equalsIgnoreCase("excellent")){
            num = 5;
        }
        else if (participation.equalsIgnoreCase("very good")){
            num = 4;
        }
        else if (participation.equalsIgnoreCase("satisfactory")){
            num = 3;
        }
        else if (participation.equalsIgnoreCase("fair")){
            num = 2;
        }
        else if (participation.equalsIgnoreCase("poor")){
            num = 1;
        }
        return num;
    }

    /**
     * @param participation the participation to set
     */
    public void setParticipation(String participation) {
        this.participation = participation;
    }

    /**
     * @return the performance
     */
    public int getPerformance() {
        int num = 0;
        if(performance.equalsIgnoreCase("excellent")){
            num = 5;
        }
        else if (performance.equalsIgnoreCase("very good")){
            num = 4;
        }
        else if (performance.equalsIgnoreCase("satisfactory")){
            num = 3;
        }
        else if (performance.equalsIgnoreCase("fair")){
            num = 2;
        }
        else if (performance.equalsIgnoreCase("poor")){
            num = 1;
        }
        return num;
    }

    /**
     * @param performance the performance to set
     */
    public void setPerformance(String performance) {
        this.performance = performance;
    }

    /**
     * @return the date
     */
    /*public String getDate() {
        return date;
    }*/

    /**
     * @param date the date to set
     */
    /*public void setDate(String date) {
        this.date = date;
    }*/
    

}
