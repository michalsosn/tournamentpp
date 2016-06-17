package pl.lodz.p.ftims.tournamentpp.entities;

public class GameCompetitor {

    private String competitor1;
    private String competitor2;
    private long competitor1Id;
    private long competitor2Id;
    private String winner;
    private long winnerId;

    public String getCompetitor1() {
        return competitor1;
    }
    public void setCompetitor1(String competitor1) {
        this.competitor1 = competitor1;
    }
    public String getCompetitor2() {
        return competitor2;
    }
    public void setCompetitor2(String competitor2) {
        this.competitor2 = competitor2;
    }
    public long getCompetitor1Id() {
        return competitor1Id;
    }
    public void setCompetitor1Id(long competitor1Id) {
        this.competitor1Id = competitor1Id;
    }
    public long getCompetitor2Id() {
        return competitor2Id;
    }
    public void setCompetitor2Id(long competitor2Id) {
        this.competitor2Id = competitor2Id;
    }
    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }
    public long getWinnerId() {
        return winnerId;
    }
    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }
}
