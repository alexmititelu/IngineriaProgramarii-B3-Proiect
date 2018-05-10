var temeMaterii = [
    {
        numeMaterie: 'Mate',
        teme: [
            {
                numeTema: 'T1'
            },
            {
                numeTema: 'T2'
            },
            {
                numeTema: 'T3'
            }
        ]
    },
    {
        numeMaterie: 'BD',
        teme: [
            {
                numeTema: 'T1'
            },
            {
                numeTema: 'T2'
            },
            {
                numeTema: 'T3'
            }
        ]
    },
    {
        numeMaterie: 'IP',
        teme: [
            {
                numeTema: 'T1'
            },
            {
                numeTema: 'T2'
            },
            {
                numeTema: 'T3'
            }
        ]
    }
];

$(document).ready(function () {

    var select = document.getElementById('materie');

    temeMaterii.forEach(element => {
        
        var option = document.createElement('option');

        option.value = element.numeMaterie;
        option.innerText = element.numeMaterie;

        option.setAttribute('disabled', 'disabled');

        select.appendChild(option);

        for (var i = 0; i < element.teme.length; i++) {
            var option2 = document.createElement('option');

            option2.value = element.teme[i].numeTema;
            option2.innerText = element.teme[i].numeTema;

            select.appendChild(option2);
        }
        
    });

    var form = document.getElementById('form');

    form.onsubmit = function (event) {
        event.preventDefault();

        var ok = 1;

        var err = document.getElementById('err');
        err.innerText = "";

        var descriere = document.getElementById('descriere').value;

        if (descriere.length < 1) {
            err.innerText = "Completeaza motivul mai intai..";
            ok = 0;
        }
        
        var select = document.getElementById('materie').value;

        if (select === '') {
            err.innerText = "Alege materia..";
            ok = 0;
        }

        var dataToSend = {
            // materie: materie,
            "numeTema": select,
            "descriere": descriere
        }

        if (ok) {
            var button = document.getElementById('btn');

            button.setAttribute('disabled', 'disabled');
            button.innerText = 'Validating..';

            $.ajax({
                type: 'POST',
                url: 'http://127.0.0.1:8080/validateContestatie',
                data: dataToSend,
                success: function (data) {
                    if (data === 'nu') {
                        err.innerText = "S-a produs o eroare..";

                        button.removeAttribute('disabled');
                        button.innerText = 'Send';

                    } else {
                        err.innerText = "contestatia s-a efectuat cu succes!";

                        button.removeAttribute('disabled');
                        button.innerText = 'Send';
                    }

                    console.log(data);
                    
                }
            });

            
            
        }


    }
    
});