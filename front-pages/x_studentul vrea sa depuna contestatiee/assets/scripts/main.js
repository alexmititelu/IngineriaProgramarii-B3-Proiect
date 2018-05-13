/*dummy JSON:*/

var fromServer = [
    [
        {
            numeMaterie: 'Matematica',
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
                    numeTema: 'T4'
                },
                {
                    numeTema: 'T5'
                },
                {
                    numeTema: 'T6'
                }
            ]
        },
        {
            numeMaterie: 'IP',
            teme: [
                {
                    numeTema: 'T7'
                },
                {
                    numeTema: 'T8'
                },
                {
                    numeTema: 'T9'
                }
            ]
        }
    ]
,
    [{
        nume: 'Popescu',
        prenume: 'Costin',
        email: 'popescucostin@gmail.com',
        imagineProfil: '/profilePicture.jpg'
    }]
]

$(document).ready(function () {

	$(document).ready(function () {
    
        $('.menu').click(function () {
        $('.responsive-menu').toggleClass('toggle');
    });
});

    // $.ajax({

    //     type: 'GET',
    //     url: 'http://127.0.0.1:8080/infoForContestatie',
    //     success: function (data) {
            
    //         if(data){
                
    //             dataToReceive=data;
    //         }
    //         else{
                
    //             var serverResponse=document.getElementsByClassName('loading-text')[0];

    //             serverResponse.innerText='server error';
    //         }
    //     }
    // });

    setTimeout(() => {  /* to simulate server response delay */
            
        $('.loading-container').addClass('none');
        $('.main-container').removeClass('none');

    }, 1200);

    var dataToReceive = fromServer;
    var selectMateria = document.getElementById('materia');

    dataToReceive[0].forEach(element => {
        
        var option = document.createElement('option');

        option.value = element.numeMaterie;
        option.innerText = element.numeMaterie;

        selectMateria.appendChild(option);
    });

    var materiaSelectata;
     
    $(selectMateria).on('change', function() {
        materiaSelectata=(this.value);

        var selectTema=document.getElementById('tema');

        while(selectTema.firstChild)
        {
            selectTema.removeChild(selectTema.firstChild);
        }
        
        for(var i=0;i<dataToReceive[0].length;++i){
        
            if(dataToReceive[0][i].numeMaterie==materiaSelectata)
            {   
                for(var j=0;i<dataToReceive[0][i].teme.length;++j)
                {
                  var option=document.createElement('option');

                  option.value=dataToReceive[0][i].teme[j].numeTema
                  option.innerText=dataToReceive[0][i].teme[j].numeTema;
                  
                  selectTema.appendChild(option);
                }
            }
        }
    })
    
    var form = document.getElementById('form');

    form.onsubmit = function (event) {
        event.preventDefault();

        var ok = 1;

        var err = document.getElementById('err');
        var descriere = document.getElementById('descriere').value;

        err.innerText = "";
        
        if (descriere.length < 1) {
            err.innerText = "Nu ati formulat contestatia!";

            var alertImg=document.getElementsByClassName('alertImage');
         
            //$('alertImg').attr("src","./images/siren1.png");
            // alertImg.setAttribute("src","./images/siren1.png");
            // alertImg.setAttribute("alt","alert symbol");

            ok = 0;
        }
        
        var select1 = document.getElementById('materie').value;
        var select2 = document.getElementById('tema').value;

        if (select1 === '' ) {
            err.innerText = "Nu ati ales materia!";
            ok = 0;
        }
        if (select2 === '') {
            err.innerText = "Nu ati ales tema!";
            ok = 0;
        }

        var dataToSend = {
            "materie": select1,
            "numeTema": select2,
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