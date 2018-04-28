$(document).ready(function() {

    var err = document.getElementById('err');
    var btn = document.getElementById('sub');

    var constraints = {
        email: {
            format: {
            pattern: "[a-z0-9@.]+",
            flags: "i",
            message: "can only contain a-z and 0-9",
            },
            length: {
                minimum: 6,
                message: "to short"
            }
        },
        password: {
            format: {
            pattern: "[a-z0-9]+",
            flags: "i",
            message: "can only contain a-z and 0-9",
            },
            length: {
                minimum: 6,
                message: "to short"
            }
        }
    };

    btn.onclick = function (event) {
        event.preventDefault();

        var userEmail = document.getElementById('email').value;
        var userPassword = document.getElementById('password').value;

        if (userEmail.length > 0 && userPassword.length > 0) {
            var ok = true;

            if (validate({email: userEmail}, constraints)) {
                var set = validate({email: userEmail}, constraints);
                err.innerText = set['email'][0];

                ok = false;
            } else {
                err.innerText = '';

                ok = true;
            }

            if (validate({password: userPassword}, constraints)) {
                var set = validate({password: userPassword}, constraints);
                err.innerText = set['password'][0];

                ok = false;
            } else {
                err.innerText = '';

                ok= true;
            }

            if (ok) {
                var email = document.getElementById('email');
                var pass = document.getElementById('password');

                var hashPassword = SHA1(pass.value);

                email.setAttribute("disabled", "disabled");
                pass.setAttribute("disabled", "disabled");

                btn.setAttribute('disabled', 'disabled');
                btn.classList.remove('active');
                btn.classList.add('disabled');
    
                btn.innerText = 'Verify..';

                var user = {
                    'username': email.value,
                    'password': hashPassword
                }

                $.ajax({
                    async: true,
                    type: 'POST',
                    url: 'http://127.0.0.1:8080/validateLogin',
                    data: user,
                    success: function (data) {
                        // console.log('respons: ' + data);
            
                        if (data) {
                            if (data === 'error') {
                                btn.classList.remove('disabled');
                                btn.classList.add('active');
                                btn.innerText = 'Register';
                                btn.removeAttribute("disabled");

                                err.innerText = 'A aparut o eroare!';

                                email.removeAttribute('disabled');
                                pass.removeAttribute('disabled');

                                email.value = '';
                                pass.value = '';
                            }

                            if (data === 'email') {
                                btn.classList.remove('disabled');
                                btn.classList.add('active');
                                btn.innerText = 'Register';
                                btn.removeAttribute("disabled");

                                err.innerText = 'Email sau parola incorecta!';

                                email.removeAttribute('disabled');
                                pass.removeAttribute('disabled');

                                email.value = '';
                                pass.value = '';
                            }
                        } else {
                            err.innerText = 'Logare cu succes, vei fi redirectionat..';
                            window.location.href = '/';
                        }
                    },
                    error: function (xhr, status, error) {
                        console.log('Error: ' + error.message);
                    },
                });
            }
        } else {
            err.innerText = "Complete all fields!"
        }
    }

    

});