$(document).ready(function () {
    var form = document.getElementById('form');

    form.onsubmit = event => {
        event.preventDefault();

        var err = document.getElementById('err');
        var email = document.getElementById('emailForm');
        var username = document.getElementById('usernameForm');
        var password = document.getElementById('passwordForm');
        var password2 = document.getElementById('password2Form');

        var btn = document.getElementById('btn');

        username.setAttribute('disabled', 'disabled');
        password.setAttribute('disabled', 'disabled');
        password2.setAttribute('disabled', 'disabled');

        btn.setAttribute('disabled', 'disabled');
        btn.innerText = 'Se valideaza..';

        err.innerHTML = '&nbsp;';

        var user = {
            email: email.value,
            username: username.value,
            password: password.value
        };

        var location = window.location.href;
        var rez = location.split('/');

        $.ajax({
            type: 'POST',
            url: `/register/${rez[rez.length - 1]}`,
            data: user,
            success: data => {
                if (data === 'valid') {
                    btn.innerText = 'Success';

                    err.classList.add('success');
                    err.innerHTML = 'Contul a fost creat cu success, te vor redirectiona cate pagina principala.';

                    setTimeout(() => {
                        window.location.href = '/';
                    }, 400);

                } else {
                    username.removeAttribute('disabled');
                    password.removeAttribute('disabled');
                    password2.removeAttribute('disabled');

                    btn.removeAttribute('disabled');
                    btn.innerText = 'Creare cont';

                    err.classList.remove('success');
                    err.innerHTML = data;
                }
            }
        });
    }
});