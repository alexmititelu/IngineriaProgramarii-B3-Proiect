$(document).ready(function() {

    var constraints = {
        input: {
            format: {
            pattern: "[a-z0-9]+",
            flags: "i",
            message: "can only contain a-z and 0-9",
            },
            length: {
                is: 15,
                message: "matricol incorrect"
            }
        }
    };

    var btn = document.getElementById('sub');

    btn.onclick = function (event) {
        event.preventDefault();

        var inp = document.getElementById('matricol');
        var value = inp.value;
        var err = document.getElementById('err');

        if (validate({input: value}, constraints)) {

            var set = validate({input: value}, constraints);
            err.innerText = set['input'][0];

        } else {

            this.setAttribute("disabled", true);
            this.classList.remove('active');
            this.classList.add('disabled');
            this.innerText = 'Verify..';

            inp.setAttribute("disabled","disabled");

            var button = this;

            err.innerText = '';
            
            var user = {
                'matricol': value
            }
            
            $.ajax({
                async: true,
                type: 'POST',
                url: '/verifCreditans',
                data: user,
                success: function (data) {
                    // console.log('respons: ' + data);

                    if (data === 'error') {
                        err.innerText = data;
                        this.innerText = 'Continua';
                        setTimeout(function () {
                            window.location.href = '/register';
                        }, 2000);
                    } else if (data === 'not exists') {
                        err.innerText = 'You entered an invalid matricol!';

                        button.removeAttribute("disabled");
                        button.classList.remove('disabled');
                        button.classList.add('active');
                        button.innerText = 'Continua';

                        inp.removeAttribute("disabled");
                    } else if (data === 'success') {
                        err.innerText = 'An email was send to your email address for continue your registration!';

                        button.innerText = 'Check the email inbox..';
                    }
                },
                error: function (xhr, status, error) {
                    console.log('Error: ' + error.message);
                },
            });
        }
        
    }
    
});