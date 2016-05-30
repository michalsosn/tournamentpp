/**
 * Created by dpp on 5/7/16.
 */



$(window).load(function(){
    var rounds;

    rounds = [

        [

            {
                player1: { name: "Majkelus", winner: true, ID: 111},
                player2: { name: "Danielus", ID: 211}
            },

            {
                player1: { name: "Sebastianus", ID: 311},
                player2: { name: "Łukaszus", winner: true, ID: 411}
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111}
            },
            {
                player2: { name: "Łukaszus", winner: true, ID: 411}
            }
        ],[]

    ];

    rounds2 = [

        [

            {
                player1: { name: "Sebastianus", winner: true, ID: 311},
                player2: { name: "Danielus", ID: 211}
            },

            {
                player1: { name: "Majkelus", ID: 111},
                player2: { name: "Łukaszus", winner: true, ID: 411}
            }
        ],
        [
            {player1: { name: "Sebastianus", winner: true, ID: 311}},
            {player2: { name: "Łukaszus", winner: true, ID: 411}}
        ],[]

    ];

    var titles = ['Runda 1'];
    var titles2 = ['Runda 2'];
    $(".brackets").brackets({
        titles: titles,
        rounds: rounds,
        color_title: 'black',
        border_color: 'gray',
        color_player: 'black',
        bg_player: 'white',
        color_player_hover: 'lightGray',
        bg_player_hover: 'gray',
        border_radius_player: '10px',
        border_radius_lines: '10px',
    });

    $(".brackets2").brackets({
        titles: titles2,
        rounds: rounds2,
        color_title: 'black',
        border_color: 'gray',
        color_player: 'black',
        bg_player: 'white',
        color_player_hover: 'lightGray',
        bg_player_hover: 'gray',
        border_radius_player: '10px',
        border_radius_lines: '10px',
    });
    $(".separator-brackets.rd-2").remove();
});/**
 * Created by Daniel on 2016-05-30.
 */
