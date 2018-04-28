$(document).ready(function() {

    var err = document.getElementById('err');
    var btn = document.getElementById('sub');

    var inputs = document.getElementsByTagName('input');

    var form = document.getElementById('form-register');

    var urlParameter = new URLSearchParams(window.location.search).get('userId');

    // console.log(urlParameter.get('userId'));

    var token = {
        'value': urlParameter
    }

    var userEmail;

    $.ajax({
        async: true,
        type: 'POST',
        url: 'http://127.0.0.1:3000/token',
        data: token,
        success: function (data) {
            // console.log('respons: ' + data);

            if (data) {
                inputs[0].value = data;

                userEmail = data;

                form.classList.remove('loading');
                for (let index = 1; index < inputs.length; index++) {
                    const input = inputs[index];
                    
                    input.removeAttribute("disabled");
                }

                btn.classList.remove('disabled');
                btn.classList.add('active');
                btn.innerText = 'Register';
                btn.removeAttribute("disabled");
            } else {
                form.style.display = "none";
                document.getElementById('p1').innerText = "Account doesn't exists!";
            }
        },
        error: function (xhr, status, error) {
            console.log('Error: ' + error.message);
        },
    });

    var constraints = {
        username: {
            format: {
            pattern: "[a-z0-9]+",
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

        if (document.getElementById('username').value.length > 0 && document.getElementById('password').value.length > 0 && document.getElementById('re-password').value.length > 0) {
            var ok = true;

            var user = document.getElementById('username').value;
            
            if (user) {
                if (validate({username: user}, constraints)) {
                    var set = validate({username: user}, constraints);
                    err.innerText = set['username'][0];
    
                    ok = false;
                } else {
                    err.innerText = '';
    
                    ok = true;
                }
            }
    
            var pw = document.getElementById('password').value;
            var pw2 = document.getElementById('re-password').value;
    
            if (pw) {
                if (validate({password: pw}, constraints)) {
                    var set = validate({password: pw}, constraints);
                    err.innerText = set['password'][0];
    
                    ok = false;
                } else {
                    err.innerText = '';
    
                    ok= true;
                }
            }
    
            if (pw2) {
                if (validate({password: pw}, constraints)) {
                    var set = validate({password: pw}, constraints);
                    err.innerText = set['password'][0];
    
                    ok = false;
                } else {
                    err.innerText = '';
    
                    ok = true;
                }
            }
    
            if (pw && pw2) {
                if (pw !== pw2) {
                    err.innerText = 'Passwords not match';
    
                    ok = false;
                }
                else {
                    err.innerText = '';
    
                    ok= true;
                }
            }
    
            if (ok) {
                for (let index = 1; index < inputs.length; index++) {
                    const input = inputs[index];
                    
                    input.setAttribute("disabled", "disabled");
                }
    
                btn.setAttribute('disabled', 'disabled');
                btn.classList.remove('active');
                btn.classList.add('disabled');
    
                btn.innerText = 'Verify..';
    
                var hashPassword = SHA1(document.getElementById('password').value);
    
                var user = {
                    'email': userEmail,
                    'username': document.getElementById('username').value,
                    'password': hashPassword
                }
    
                $.ajax({
                    async: true,
                    type: 'POST',
                    url: 'http://127.0.0.1:3000/accept',
                    data: user,
                    success: function (data) {
                        // console.log('respons: ' + data);
            
                        if (data) {
                            if (data === 'error') {
                                for (let index = 1; index < inputs.length; index++) {
                                    const input = inputs[index];
                                    
                                    input.value = null;
                                    input.removeAttribute("disabled");
                                }
                                
                                btn.removeAttribute('disabled');
                                btn.classList.remove('disabled');
                                btn.classList.add('active');
                    
                                btn.innerText = 'Register';
    
                                err.innerText = 'You have some errors!';
    
                                setTimeout(() => {
                                    window.location.reload();
                                }, 600);
                            }

                            if (data === 'email') {
                                err.innerText = 'You have already registered!';
    
                                setTimeout(() => {
                                    window.location.href = '/';
                                }, 600);
                            }

                            if (data === 'user') {
                                for (let index = 1; index < inputs.length; index++) {
                                    const input = inputs[index];
                                    
                                    input.value = null;
                                    input.removeAttribute("disabled");
                                }
                                
                                btn.removeAttribute('disabled');
                                btn.classList.remove('disabled');
                                btn.classList.add('active');
                    
                                btn.innerText = 'Register';
    
                                err.innerText = 'This username already exists!';
                            }
                        } else {
                            err.innerText = 'Register success!';
    
                            setTimeout(() => {
                                window.location.href = '/';
                            }, 600);
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