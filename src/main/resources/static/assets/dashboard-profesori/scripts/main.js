$(document).ready(function () {
    $('.menu').click(function () {
        $('.responsive-menu').toggleClass('toggle');
    });

    var btn = document.getElementById("logOutBtn");
    btn.onclick = () => {
        $.ajax({
            type: "POST",
            url: "/sign-out",
            success: data => {
                if (data === "valid") {
                    window.location.href = "/";
                }
            }
        })
    };

    $.ajax({
        type: 'GET',
        url: '/materii_json_prof',
        success: data => {
            if (data) {
                var group = document.getElementById('group');

                if (data.lenght > 0) {
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
                } else {
                    var h4 = document.createElement('h4');

                    h4.innerText = 'Nu administrați nicio materie. Creați una sau cereți permisiuni de la un profesor ce administrează deja o materie.';

                    group.appendChild(h4);
                }
            }
        }
    });
});