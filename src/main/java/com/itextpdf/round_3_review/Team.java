/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itextpdf.round_3_review;
import java.util.ArrayList;

/**
 *
 * @author mezin
 */
public class Team {
    
    private String teamName, faculty, meetPlace;
    private int teamNum;
    private ArrayList<Student> team = new ArrayList<Student>();
    
    
    public Team (){
        
    }
    
    public Team (String faculty, String meetPlace, int teamNum, String teamName){
        this.faculty = faculty;
        this.meetPlace = meetPlace;
        this.teamNum = teamNum;
        this.teamName = teamName;
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

    /**
     * @return the meetPlace
     */
    public String getMeetPlace() {
        return meetPlace;
    }

    /**
     * @param meetPlace the meetPlace to set
     */
    public void setMeetPlace(String meetPlace) {
        this.meetPlace = meetPlace;
    }
    
    /**
     * @return the team
     */
    public ArrayList<Student> getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(ArrayList<Student> team) {
        this.team = team;
    }
    
    public void addTeam(Student s){
        team.add(s);
    }
}
