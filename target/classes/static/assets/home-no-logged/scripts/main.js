$(document).ready(function () {
    $('.menu').click(function () {
        $('.responsive-menu').toggleClass('toggle');
    });

    var loginForm = document.getElementById('formLogin');

    loginForm.onsubmit = event => {
        event.preventDefault();

        var usr = document.getElementById('username');
        var pwd = document.getElementById('password');

        var errLogin = document.getElementById('errLogin');
        errLogin.innerHTML = '&nbsp;';

        var user = {
            username: usr.value,
            password: pwd.value
        }

        usr.setAttribute('disabled', 'disabled');
        pwd.setAttribute('disabled', 'disabled');

        var btn = document.getElementById('btn-log');
        btn.setAttribute('disabled', 'disabled');
        btn.innerText = 'Se valideaza..';

        $.ajax({
            type: 'POST',
            url: '/login',
            data: user,
            success: data => {
                if (data === 'valid') {
                    errLogin.classList.add('success');
                    errLogin.innerText =
                        'Te-ai logat cu succes, vei fi redirectionat catre pagina principala!';

                    btn.innerText = 'Succes';

                    setTimeout(() => {
                        window.location.href = '/';
                    }, 400);
                } else {
                    errLogin.classList.remove('success');
                    errLogin.innerText = data;

                    usr.removeAttribute('disabled');
                    pwd.removeAttribute('disabled');
                    btn.removeAttribute('disabled');
                    btn.innerText = 'Log In';
                }
            }
        });
    }


    var reg = document.getElementById('register-btn');

    reg.onclick = event => {
        event.preventDefault();

        var title = document.getElementById('titleForm');
        title.innerText = 'Register';

        var login = document.getElementById('formLogin');
        login.hidden = true;

        var row = document.getElementsByClassName('log-row');
        for (let index = 0; index < row.length; index++) {
            const element = row[index];
            element.hidden = true;
        }

        var log = document.getElementById('reg-row');
        log.classList.remove('none');

        var registerForm = document.getElementById('formRegister');
        registerForm.classList.remove('none');
    }


    var log = document.getElementById('login-btn');

    log.onclick = event => {
        event.preventDefault();

        var title = document.getElementById('titleForm');
        title.innerText = 'Login';

        var login = document.getElementById('formLogin');
        login.hidden = false;

        var log = document.getElementById('reg-row');
        log.classList.add('none');

        var row = document.getElementsByClassName('log-row');
        for (let index = 0; index < row.length; index++) {
            const element = row[index];
            element.hidden = false;
        }

        var registerForm = document.getElementById('formRegister');
        registerForm.classList.add('none');
    }

    var regForm = document.getElementById('formRegister');

    regForm.onsubmit = event => {
        event.preventDefault();



        var nr_matricol = document.getElementById('nr_matricol');
        nr_matricol.setAttribute('disabled', 'disabled');

        var user = {
            nr_matricol: nr_matricol.value
        }

        var errReg = document.getElementById('errRegister');
        errReg.innerHTML = '&nbsp;';

        var btnReg = document.getElementById('btn-reg');
        btnReg.innerText = 'Se valideaza..';
        btnReg.setAttribute('disabled', 'disabled');

        $.ajax({
            type: 'POST',
            url: '/register',
            data: user,
            success: data => {
                if (data === 'valid') {
                    errReg.classList.add('success');
                    errReg.innerText =
                        'Te-ai inregistrat cu succes, vei primi in scurt timp un email pe adresa asociata numarului matricol pentru a continua inregistrarea!';

                    btnReg.innerText = 'Succes';

                    var foot = document.getElementById('foot');
                    foot.hidden = true;
                } else {
                    errReg.classList.remove('success');
                    errReg.innerText = data;

                    btnReg.innerText = 'Register';
                    btnReg.removeAttribute('disabled');

                    nr_matricol.removeAttribute('disabled');
                }
            }
        });
    }
});