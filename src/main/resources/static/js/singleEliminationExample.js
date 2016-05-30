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
            },
            {
                player1: { name: "Janus", winner: true, ID: 511},
                player2: { name: "Kamilus", ID: 611}
            },
            {
                player1: { name: "Marcinus", winner: true, ID: 711},
                player2: { name: "Mateuszus", ID: 811 }
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111},
                player2: { name: "Łukaszus", ID: 411}
            },

            {
                player1: { name: "Janus", winner: true, ID: 511},
                player2: { name: "Marcinus", ID: 711}
            },
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111},
                player2: { name: "Janus", ID: 511}
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111},
            }
        ]

    ];

    var titles = ['Round 1', 'Round 2', 'Final', 'Winner'];
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

});