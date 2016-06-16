package pl.lodz.p.ftims.tournamentpp.entities;

public class TournamentWinners extends TournamentEntity {

    private static final long serialVersionUID = 1L;

    private int winCount;

    public TournamentWinners(TournamentEntity tournamentEntity, int winCount) {
        setDescription(tournamentEntity.getDescription());
        setFormat(tournamentEntity.getFormat());
        setLocation(tournamentEntity.getLocation());
        setName(tournamentEntity.getName());
        setOrganizer(tournamentEntity.getOrganizer());
        setStartTime(tournamentEntity.getStartTime());
        this.winCount = winCount;
    }

    public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

}
