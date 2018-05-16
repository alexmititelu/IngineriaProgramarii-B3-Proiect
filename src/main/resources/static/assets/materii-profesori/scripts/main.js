$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
    });

    $.ajax({
        type: 'GET',
        url: '/materii_json',
        success: data => {
            if (data) {
                var group = document.getElementById('group');

                data.forEach(element => {
                    var a = document.createElement('a');
                    a.href = `/materii/${element.titlu}`;
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var div = document.createElement('div');
                    div.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = element.titlu;

                    var small1 = document.createElement('small');
                    small1.innerText = '3 days ago';

                    div.appendChild(h5);
                    div.appendChild(small1);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.descriere;

                    var small2 = document.createElement('small');
                    small2.innerText = `An: ${element.an} | `;

                    var small3 = document.createElement('small');
                    small3.innerText = `Semestru: ${element.semestru}`;

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

        var nume = document.getElementById('nume');
        var descriere = document.getElementById('descriere');
        var an = document.getElementById('an');
        var semestru = document.getElementById('semestru');

        var err = document.getElementById('err');
        var btn = document.getElementById('btn');

        var data = {
            numeMaterie: nume.value,
            an: parseInt(an.value),
            semestru: parseInt(semestru.value),
            descriere: descriere.value
        };

        err.innerHTML = '&nbsp;';
        nume.setAttribute('disabled', 'disabled');
        descriere.setAttribute('disabled', 'disabled');
        an.setAttribute('disabled', 'disabled');
        semestru.setAttribute('disabled', 'disabled');
        btn.setAttribute('disabled', 'disabled');
        btn.innerText = 'Se valideaza..';

        $.ajax({
            type: 'POST',
            url: '/createMaterie',
            data: data,
            success: data => {
                if (data === 'valid') {
                    err.classList.add('success');
                    err.innerHTML = 'Materia a fost adaugata cu succes!';

                    setTimeout(() => {
                        window.location.href = window.location.href;
                    }, 400);
                } else {
                    err.classList.remove('success');
                    err.innerHTML = data;

                    nume.removeAttribute('disabled');
                    descriere.removeAttribute('disabled');
                    an.removeAttribute('disabled');
                    semestru.removeAttribute('disabled');
                    btn.removeAttribute('disabled');
                    btn.innerText = 'Creare materie';
                }
            }
        });
    }
});