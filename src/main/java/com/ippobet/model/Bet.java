package com.ippobet.model;

public class Bet {
    private String homeTeam;
    private String awayTeam;
    private String outcome;
    private double odd;


    public Bet(String homeTeam, String awayTeam, String outcome, double odd)
    {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.outcome = outcome;
        this.odd = odd;
    }


    public String getHomeTeam()
    {
        return homeTeam;
    }


    public String getAwayTeam()
    {
        return awayTeam;
    }


    public String getOutcome()
    {
        return outcome;
    }


    public double getOdd()
    {
        return odd;
    }
}
