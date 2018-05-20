$(document).ready(function () {
    $('.menu').click(function () {
        $('.responsive-menu').toggleClass('toggle');
    });

    var form = document.getElementById('form');

    form.onsubmit = event => {
        event.preventDefault();

        var email = document.getElementById('emailForm');
        var username = document.getElementById('usernameForm');
        var password = document.getElementById('passwordForm');
        var password2 = document.getElementById('password2Form');

        var err = document.getElementById('err');
        var btn = document.getElementById('btn');

        var data = {
            email: email.value,
            username: username.value,
            password: password.value
        };

        err.innerHTML = '&nbsp;';
        username.setAttribute('disabled', 'disabled');
        password.setAttribute('disabled', 'disabled');
        password2.setAttribute('disabled', 'disabled');
        btn.setAttribute('disabled', 'disabled');
        btn.innerText = 'Se valideaza..';

        $.ajax({
            type: 'POST',
            url: window.location.href,
            data: data,
            success: data => {
                if (data === 'valid') {
                    err.classList.add('success');
                    err.innerHTML = 'Contul a fost creat cu succes, vei fi redirectionat catre pagina principala!';

                    setTimeout(() => {
                        window.location.href = '/';
                    }, 400);
                } else {
                    err.classList.remove('success');
                    err.innerHTML = data;

                    username.removeAttribute('disabled');
                    password.removeAttribute('disabled');
                    password2.removeAttribute('disabled');
                    btn.removeAttribute('disabled');
                    btn.innerText = 'Creare cont';
                }
            }
        });
    }
});