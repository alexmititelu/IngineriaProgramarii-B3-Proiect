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
        url: '/subscribed-subjects',
        success: data => {
            var materii = document.getElementById('materii-abonate');

            data.forEach(element => {
                var a = document.createElement('a');
                a.href = `/materii/${element.titlu}`;
                a.classList = 'list-group-item list-group-item-action flex-column align-items-start';

                var title = document.createElement('div');
                title.classList = 'd-flex w-100 justify-content-between';

                var h5 = document.createElement('h5');
                h5.classList = 'mb-1';

                h5.innerText = element.titlu;

                var small = document.createElement('small');

                small.innerText = `An: ${element.an} | Semestru: ${element.semestru}`;

                title.appendChild(h5);
                title.appendChild(small);

                var p = document.createElement('p');
                p.classList = 'mb-1';

                p.innerText = element.descriere;

                a.appendChild(title);
                a.appendChild(p);

                materii.appendChild(a);
            });
        }
    });

    $.ajax({
        type: 'GET',
        url: '/notifications',
        success: data => {
            console.log(data);
        }
    });
});