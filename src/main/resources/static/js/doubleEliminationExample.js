/**
 * Created by dpp on 5/7/16.
 */



$(window).load(function(){
    var rounds;
    var rounds2;
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
            },
            {
                player2: { name: "Łukaszus", winner: true, ID: 411}
            },
            {
                player1: { name: "Janus", winner: true, ID: 511},
            },
            {
                player1: { name: "Marcinus", winner: true, ID: 711},
            }
        ],[],[]
    ];

    rounds2 = [
        [

            {
                player1: { name: "Majkelus", winner: true, ID: 111},
                player2: { name: "Łukaszus", ID: 411}
            },

            {
                player1: { name: "Janus", ID: 511},
                player2: { name: "Marcinus", winner: true, ID: 711}
            },
            {
                player1: { name: "Danielus", winner: true, ID: 211},
                player2: { name: "Sebastianus", ID: 311}
            },
            {
                player1: { name: "Kamilus", ID: 611},
                player2: { name: "Mateuszus",  winner: true, ID: 811 }
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111}
            },

            {
                player2: { name: "Marcinus", winner: true, ID: 711}
            },
            {
                player1: { name: "Danielus", winner: true, ID: 211}
            },
            {
                player2: { name: "Mateuszus",  winner: true, ID: 811 }
            }
        ],[],[]
    ];

    rounds3 = [
        [

            {
                player1: { name: "Majkelus", winner: true, ID: 111},
                player2: { name: "Marcinus", ID: 711}
            },

            {
                player1: { name: "Danielus", ID: 211},
                player2: { name: "Łukaszus", winner: true, ID: 411}
            },
            {
                player1: {name: "Mateuszus", winner: true, ID: 911},
                player2: {name: "Janus", ID: 511}
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111},
            },

            {
                player2: { name: "Łukaszus", winner: true, ID: 411}
            },
            {
                player1: {name: "Mateuszus", winner: true, ID: 911},
            }
        ],[],[]
    ];

    rounds4 = [
        [

            {
                player1: { name: "Majkelus", winner: true, ID: 111}
            },

            {
                player1: { name: "Danielus", winner: true, ID: 211},
                player2: { name: "Mateuszus", ID: 811}
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111},
            },

            {
                player2: { name: "Danielus", winner: true, ID: 211}
            }
        ],[],[]
    ];

    rounds5 = [
        [

            {
                player1: { name: "Majkelus", winner: true, ID: 111}
            },

            {
                player1: { name: "Danielus", ID: 211},
                player2: { name: "Marcinus", winner: true, ID: 711}
            }
        ],
        [
            {
                player1: { name: "Majkelus", winner: true, ID: 111}
            },

            {
                player2: { name: "Marcinus", winner: true, ID: 711}
            }
        ],[],[]
    ];

    rounds6 = [
        [

            {
                player1: { name: "Majkelus", ID: 111},
                player2: { name: "Marcinus", winner: true, ID: 711}
            }
        ],
        [
            {
                player2: { name: "Marcinus", winner: true, ID: 711}
            }
        ],[],[]
    ];

    var titles = ['Round 1'];
    var titles2 = ['Round 2'];
    var titles3 = ['Round 3'];
    var titles4 = ['Round 4'];
    var titles5 = ['Round 5'];
    var titles6 = ['Round 6'];
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
    $(".brackets3").brackets({
        titles: titles3,
        rounds: rounds3,
        color_title: 'black',
        border_color: 'gray',
        color_player: 'black',
        bg_player: 'white',
        color_player_hover: 'lightGray',
        bg_player_hover: 'gray',
        border_radius_player: '10px',
        border_radius_lines: '10px',
    });

    $(".brackets4").brackets({
        titles: titles4,
        rounds: rounds4,
        color_title: 'black',
        border_color: 'gray',
        color_player: 'black',
        bg_player: 'white',
        color_player_hover: 'lightGray',
        bg_player_hover: 'gray',
        border_radius_player: '10px',
        border_radius_lines: '10px',
    });

    $(".brackets5").brackets({
        titles: titles5,
        rounds: rounds5,
        color_title: 'black',
        border_color: 'gray',
        color_player: 'black',
        bg_player: 'white',
        color_player_hover: 'lightGray',
        bg_player_hover: 'gray',
        border_radius_player: '10px',
        border_radius_lines: '10px',
    });


    $(".brackets6").brackets({
        titles: titles6,
        rounds: rounds6,
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
