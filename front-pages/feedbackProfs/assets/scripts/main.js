/*dummy JSON:*/

var fromServer = [
    [
        {
            numeProfesor: 'Lect. Dr. Arusoaie Andrei',
            teme: [
                {
                    numeDisciplina: 'Programare functionala'
                },
                {
                    numeDisciplina: 'Ingineria programarii'
                },
                {
                    numeDisciplina: 'Proiectarea algoritmilor'
                }
            ]
        },
        {
            numeProfesor: 'Lect.dr. Birjoveanu Catalin',
            teme: [
                {
                    numeDisciplina: 'Smart Card-uri si Aplicatii'
                },
                {
                    numeDisciplina: 'Protocoale de securitate: modelare si verificare'
                },
                {
                    numeDisciplina: 'Fundamente algebrice ale informaticii'
                }
            ]
        },
        {
            numeProfesor: 'Lect.dr. Iftene Sorin ',
            teme: [
                {
                    numeDisciplina: 'Fundamente algebrice ale informaticii'
                },
                {
                    numeDisciplina: 'Introducere in criptografie'
                },
                {
                    numeDisciplina: 'Aspecte computationale in teoria numerelor'
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
        $('.spinner').addClass('none');
        $('.main-container').removeClass('none');

    }, 1200);

    var dataToReceive = fromServer;
    var selectMateria = document.getElementById('profesor');

    dataToReceive[0].forEach(element => {
        
        var option = document.createElement('option');

        option.value = element.numeProfesor;
        option.innerText = element.numeProfesor;

        selectMateria.appendChild(option);
    });

    var materiaSelectata;
     
    $(selectMateria).on('change', function() {
        materiaSelectata=(this.value);

        var selectDisciplina=document.getElementById('disciplina');

        while(selectDisciplina.firstChild)
        {
            selectDisciplina.removeChild(selectDisciplina.firstChild);
        }
        
        for(var i=0;i<dataToReceive[0].length;++i){
        
            if(dataToReceive[0][i].numeProfesor==materiaSelectata)
            {   
                for(var j=0;i<dataToReceive[0][i].teme.length;++j)
                {
                  var option=document.createElement('option');

                  option.value=dataToReceive[0][i].teme[j].numeDisciplina
                  option.innerText=dataToReceive[0][i].teme[j].numeDisciplina;
                  
                  selectDisciplina.appendChild(option);
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
            err.innerText = "Nu v-ati exprimat parerea!";

            var alertImg=document.getElementsByClassName('alertImage');
         
            //$('alertImg').attr("src","./images/siren1.png");
            // alertImg.setAttribute("src","./images/siren1.png");
            // alertImg.setAttribute("alt","alert symbol");

            ok = 0;
        }
        
        var select1 = document.getElementById('profesor').value;
        var select2 = document.getElementById('disciplina').value;

        if (select1 === '' ) {
            err.innerText = "Nu ati ales profesorul!";
            ok = 0;
        }
        if (select2 === '') {
            err.innerText = "Nu ati ales disciplina!";
            ok = 0;
        }

        var dataToSend = {
            "profesor": select1,
            "disciplina": select2,
            "descriere": descriere
        }

        if (ok) {
            var button = document.getElementById('btn');

            button.setAttribute('disabled', 'disabled');
            

            $.ajax({
                type: 'POST',
                url: 'http://127.0.0.1:8080/validateFeedBack',
                data: dataToSend,
                success: function (data) {
                    if (data === 'nu') {
                        err.innerText = "S-a produs o eroare..";

                        button.removeAttribute('disabled');
                        button.innerText = 'Send';

                    } else {
                        err.innerText = "FeedBack a fost trimis! Multumim!";

                        button.removeAttribute('disabled');
                        button.innerText = 'Send';
                    }
                    console.log(data);
                }
            });
        }
    }
});