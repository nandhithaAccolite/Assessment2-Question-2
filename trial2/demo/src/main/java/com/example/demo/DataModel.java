package com.example.demo;

import java.util.Date;

public class DataModel {
    private String date;
    private String month;
    private String team;
    private String panelName;
    private String round;
    private String skill;
    private String time;
    private String candidateCurrentLocation;
    private String candidatePreferredLocation;
    private String CandidateName;

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", team='" + team + '\'' +
                ", panelName='" + panelName + '\'' +
                ", round='" + round + '\'' +
                ", skill='" + skill + '\'' +
                ", time='" + time + '\'' +
                ", candidateCurrentLocation='" + candidateCurrentLocation + '\'' +
                ", candidatePreferredLocation='" + candidatePreferredLocation + '\'' +
                ", CandidateName='" + CandidateName + '\'' +
                '}';
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCandidateCurrentLocation() {
        return candidateCurrentLocation;
    }

    public void setCandidateCurrentLocation(String candidateCurrentLocation) {
        this.candidateCurrentLocation = candidateCurrentLocation;
    }

    public String getCandidatePreferredLocation() {
        return candidatePreferredLocation;
    }

    public void setCandidatePreferredLocation(String candidatePreferredLocation) {
        this.candidatePreferredLocation = candidatePreferredLocation;
    }

    public String getCandidateName() {
        return CandidateName;
    }

    public void setCandidateName(String candidateName) {
        CandidateName = candidateName;
    }


}