$(document).ready(function () {
    $('.menu').click(function () {
        $('.responsive-menu').toggleClass('toggle');
    });

    var date_input = $('input[name="date"]');
    var options = {
        format: 'dd/mm/yyyy',
        todayHighlight: true,
        autoclose: true,
    };
    date_input.datepicker(options);

    var location = window.location.href;

    $.ajax({
        type: 'GET',
        url: location + '/teme_json',
        success: data => {
            if (data) {
                var group = document.getElementById('group');

                data.forEach(element => {
                    var a = document.createElement('a');
                    a.href = `/materii/${element.numeTema}`;
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var div = document.createElement('div');
                    div.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = element.numeTema;

                    var small1 = document.createElement('small');
                    var deadline = element.deadline;
                    small1.innerText = `Deadline: ${deadline}`;

                    div.appendChild(h5);
                    div.appendChild(small1);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.enunt;

                    var small2 = document.createElement('small');
                    small2.innerText = `Numar exercitii: ${element.nrExercitii} | `;

                    var small3 = document.createElement('small');
                    small3.innerText = `Extensie fisier: .${element.extensieFisier}`;

                    a.appendChild(div);
                    a.appendChild(p);
                    a.appendChild(small2);
                    a.appendChild(small3);

                    group.appendChild(a);
                });
            }
        }
    });



    var form = document.getElementById('form');

    form.onsubmit = event => {
        event.preventDefault();

        var numeMaterie = document.getElementById('numeMaterie').innerText;
        var nume = document.getElementById('nume');
        var enunt = document.getElementById('enunt');
        var deadline = document.getElementById('deadline');
        var nrExercitii = document.getElementById('nrExercitii');
        var extensie = document.getElementById('extensie');

        var err = document.getElementById('err');
        var btn = document.getElementById('btn');

        var data = {
            numeMaterie: numeMaterie,
            numeTema: nume.value,
            deadline: deadline.value,
            enunt: enunt.value,
            nrExercitii: parseInt(nrExercitii.value),
            extensieFisierAcceptat: extensie.value
        };

        err.innerHTML = '&nbsp;';
        nume.setAttribute('disabled', 'disabled');
        enunt.setAttribute('disabled', 'disabled');
        deadline.setAttribute('disabled', 'disabled');
        nrExercitii.setAttribute('disabled', 'disabled');
        extensie.setAttribute('disabled', 'disabled');
        btn.setAttribute('disabled', 'disabled');
        btn.innerText = 'Se valideaza..';

        $.ajax({
            type: 'POST',
            url: window.location.href + '/createTema',
            data: data,
            success: data => {
                if (data === 'valid') {
                    err.classList.add('success');
                    err.innerHTML = 'Tema a fost adaugata cu succes!';

                    setTimeout(() => {
                        window.location.href = window.location.href;
                    }, 400);
                } else {
                    err.classList.remove('success');
                    err.innerHTML = data;

                    nume.removeAttribute('disabled');
                    enunt.removeAttribute('disabled');
                    deadline.removeAttribute('disabled');
                    nrExercitii.removeAttribute('disabled');
                    extensie.removeAttribute('disabled');
                    btn.removeAttribute('disabled');
                    btn.innerText = 'Creare materie';
                }
            }
        });
    }

});