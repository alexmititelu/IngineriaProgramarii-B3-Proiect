var data=[
    [
        {
            nume: 'asd',
            prenume: 'asd',
            profile: 'sdad',
        }
    ],
    [
        {
            nume:'Ciobaca',
            prenume:'Stefan',
            email:'ciobaca.stefan@gmail.com'
        },
        {
            nume:'Radulescu',
            prenume: 'Bogdan',
            email:'bogdan.radulescu@gmail.com'
        },
        {
            nume:'Iacob',
            prenume: 'Florin',
            email:'florin.iacob@gmail.com'
        },
        {
            nume:'Manolache',
            prenume: 'Cristian',
            email:'cristian.manolache@gmail.com'
        },
        {
            nume:'Masalagiu',
            prenume: 'Cristian',
            email:'cristian.masalagiu@gmail.com'
        },
        {
            nume:'Alboaie',
            prenume: 'Lenuta',
            email:'lenuta.alboaie@gmail.com'
        }
    ]
];

    $(document).ready(function () {

        var datePrimite;

        // $.ajax({
        //     type: 'GET',
        //     url: '/GetDataForEmailProfesor',
        //     success: function(data){
        //         datePrimite=data;
        //     }
        // });


        datePrimite=data;


        var select=document.getElementById('prof');
        for(var i=0;i<datePrimite[1].length;i++){
            var option=document.createElement('option');


            option.value=datePrimite[1][i].email;
            option.innerText=`${datePrimite[1][i].nume} ${datePrimite[1][i].prenume} - ${datePrimite[1][i].email}`;

            select.appendChild(option);

        }

        setTimeout(() => {
            $('.loading-container').css('display','none');
            $('.main-container').css('display', 'unset');
        }, 1300);


        var submit=document.getElementById('message');
        
        submit.onsubmit=function(event){
            event.preventDefault();

            var err=document.getElementById('err');
            err.innerText='';
            var ok=1;
            if(select.value===''){
                err.innerText="Nu ai selectat profesorul";
                ok=0;
            }

            var message=document.getElementById('mesaj').value;

            if(message.length<1){
                err.innerText="Nu ai scris mesajul";
                ok=0;
            }

            if(ok){
                var dataToSend={
                    email: select.value,
                    message : message
                }

                var button=document.getElementById('btn');
                button.innerText="Waiting..";
                button.setAttribute('disabled','disabled');

                // $.ajax({
                //     type: 'POST',
                //     url: '/sendDataToMessageProf',
                //     data: dataToSend,
                //     success: function(data){
                //         if(data==='error'){
                //             button.innerText="Send message";
                //             button.removeAttribute('disabled');
                //             err.innerText="Something happened";
                //         }
                //         else {
                //             button.innerText="Send message";
                //             button.removeAttribute('disabled');
                //             err.innerText="Cererea a fost trimisa";
                //         }
                //     }
                // })

                // setTimeout(() => {
                //     button.innerText="Send message";
                //     button.removeAttribute('disabled');
                //     err.innerText="Something happened";
                // }, 400);

                setTimeout(() => {
                    button.innerText="Send message";
                    button.removeAttribute('disabled');
                    err.innerText="Cererea a fost trimisa";
                }, 400);
            }
                


        }

    });
    
    

