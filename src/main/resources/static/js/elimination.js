/**
 * Created by dpp on 5/7/16.
 */



$(window).load(function(){
    var rounds;

    rounds = [

        [

            {
                player1: { name: "Majkelus", winner: true, ID: 111, points: 20},
                player2: { name: "Danielus", ID: 211, points: 15 }
            },

            {
                player1: { name: "Łukaszus", winner: true, ID: 112, points: 24 },
                player2: { name: "Kamilus", ID: 212, points: 11 }
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111, points: 211 },
                player2: { name: "Łukaszus", ID: 112 , points: 50}
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111, points: 211  }
            }
        ]
    ];

    var titles = ['Round', 'Final',  'Winner'];
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