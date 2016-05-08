/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.ftims.tournamentpp.trees;

import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;

/**
 *
 * @author r4pt0r
 */
public interface TournamentFormat {
    public RoundEntity prepareRound(TournamentFormat tournament);
    
}
