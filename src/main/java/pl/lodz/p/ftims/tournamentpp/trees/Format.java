package pl.lodz.p.ftims.tournamentpp.trees;

/**
 * Created by Daniel on 2016-05-14.
 */
public enum Format {
    ROUND_ROBIN("Round robin"),
    SINGLE_ELIMINATION("Single elimination"),
    DOUBLE_ELIMINATION("Double elimination");
   
    private final String niceName;
    
    private Format(String niceName) {
        this.niceName = niceName;
    }

    public String getNiceName() {
        return niceName;
    }
}
