$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
    });

    $(".search--grade").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $(".student-list li").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    $(".search--plag").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#plag-table tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    var usernameCurrent;
    var nrExercitiu;

    var ex = 0;

    var err = document.getElementById('errNota');
    var notaValue = document.getElementById('notaValue');
    var notaBtn = document.getElementById('notaUpdate');

    var notatiPill = document.getElementById('notati-pill');
    var nenotatiPill = document.getElementById('nenotati-pill');

    var nrNenotati = 0;
    var nrNotati = 0;

    $.ajax({
        type: 'GET',
        url: `${window.location.href}/profesor_info`,
        success: data => {
            if (data) {
                var group = document.getElementById('group');

                data.forEach(element => {
                    ex++;
                    var a = document.createElement('a');
                    // a.href = `/materii/${element.titlu}`;
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var div = document.createElement('div');
                    div.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = element.nume;

                    var small1 = document.createElement('small');
                    small1.innerText = '3 days ago';

                    div.appendChild(h5);
                    div.appendChild(small1);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.enunt;

                    var small2 = document.createElement('small');
                    small2.innerText = `Extensie: .${element.extensie}`;

                    a.appendChild(div);
                    a.appendChild(p);
                    a.appendChild(small2);
                    group.appendChild(a);

                    //append graded students
                    var graded = element.studentiNotati;
                    // console.log(graded);
                    graded.forEach(element => {
                        nrNotati++;

                        var a = document.createElement('a');
                        a.href = '#exampleModalCenter';
                        a.setAttribute('data-toggle', 'modal');

                        var table = document.getElementById('tableModel');
                        var username = element.username;
                        a.onclick = () => {
                            var line = 0;

                            while (table.firstChild) {
                                table.removeChild(table.firstChild);
                            }

                            usernameCurrent = a.attributes.username.value;
                            nrExercitiu = a.attributes.ex.value;

                            notaValue.value = element.nota;

                            $.ajax({
                                type: 'POST',
                                url: `${window.location.href}/continut_fisier`,
                                data: {
                                    username: username,
                                    nrExercitiu: parseInt(a.attributes.ex.value)
                                },
                                success: data => {
                                    if (data.length > 0) {
                                        data.forEach(element => {
                                            line++;

                                            var tr = document.createElement('tr');

                                            var th = document.createElement('th');
                                            th.innerText = line;
                                            th.style = 'width: 50px;'

                                            var td = document.createElement('td');
                                            td.innerText = element;

                                            tr.appendChild(th);
                                            tr.appendChild(td);

                                            table.appendChild(tr);
                                        });
                                    }
                                }
                            });
                        }

                        var li = document.createElement('li');
                        li.className = 'list-item d-flex justify-content-between align-items-center';
                        li.innerText = `${element.nume} ${element.prenume} | ex. ${ex}`;
                        a.setAttribute('ex', ex);
                        a.setAttribute('username', element.username);
                        a.appendChild(li);

                        var list = document.getElementById("collapseOne");
                        list.appendChild(a);
                    });

                    //append ungraded students
                    var ungraded = element.studentiNenotati;

                    ungraded.forEach((element, index) => {
                        nrNenotati++;

                        var a = document.createElement('a');
                        a.href = '#exampleModalCenter';
                        a.setAttribute('data-toggle', 'modal');
                        // a.innerText = `${element.nume} ${element.prenume}`;

                        var table = document.getElementById('tableModel');
                        var username = element.username;
                        a.onclick = () => {
                            var line = 0;

                            while (table.firstChild) {
                                table.removeChild(table.firstChild);
                            }

                            usernameCurrent = a.attributes.username.value;
                            nrExercitiu = a.attributes.ex.value;

                            $.ajax({
                                type: 'POST',
                                url: `${window.location.href}/continut_fisier`,
                                data: {
                                    username: username,
                                    nrExercitiu: parseInt(a.attributes.ex.value)
                                },
                                success: data => {
                                    if (data.length > 0) {
                                        data.forEach(element => {
                                            line++;

                                            var tr = document.createElement('tr');

                                            var th = document.createElement('th');
                                            th.innerText = line;
                                            th.style = 'width: 50px;'

                                            var td = document.createElement('td');
                                            td.innerText = element;

                                            tr.appendChild(th);
                                            tr.appendChild(td);

                                            table.appendChild(tr);
                                        });
                                    }
                                }
                            });
                        }

                        var li = document.createElement('li');
                        li.className = 'list-item d-flex justify-content-between align-items-center';
                        li.innerText = `${element.nume} ${element.prenume} | ex. ${ex}`;
                        a.setAttribute('ex', ex);
                        a.setAttribute('username', element.username);
                        a.appendChild(li);

                        var list = document.getElementById("collapseThree");
                        list.appendChild(a);
                    });
                });

                notatiPill.innerText = nrNotati;
                nenotatiPill.innerText = nrNenotati;
            }
        }
    });



    notaBtn.onclick = () => {
        err.innerText = '';
        var punctaj = notaValue.value;
        if (punctaj < 0 || punctaj > 10) {
            err.innerText = 'Nota invalida';
        } else {
            $.ajax({
                type: 'POST',
                url: `${window.location.href}/update_nota`,
                data: {
                    username: usernameCurrent,
                    nrExercitiu: nrExercitiu,
                    nota: punctaj
                },
                success: data => {
                    if (data === 'valid') {
                        notaBtn.classList = 'btn btn-success';
                        notaBtn.innerText = 'Nota adaugata cu success';

                        setTimeout(() => {
                            window.location.href = window.location.href;
                        }, 300);
                    } else {
                        notaBtn.classList = 'btn btn-danger';
                        err.innerText = data;
                    }
                }
            });
        }
    }

    var search1 = document.getElementById('srch1');

    search1.onkeyup = () => {
        // Declare variables
        var filter, ul, li, a, i;
        filter = search1.value.toUpperCase();
        ul = document.getElementById("collapseOne");
        a = ul.getElementsByTagName('a');

        // Loop through all list items, and hide those who don't match the search query
        for (i = 0; i < a.length; i++) {
            li = a[i].getElementsByTagName("li")[0];
            if (li.innerHTML.toUpperCase().indexOf(filter) > -1) {
                a[i].style.display = "";
            } else {
                a[i].style.display = "none";
            }
        }
    }

    var search2 = document.getElementById('srch2');

    search2.onkeyup = () => {
        // Declare variables
        var filter, ul, li, a, i;
        filter = search2.value.toUpperCase();
        ul = document.getElementById("collapseThree");
        a = ul.getElementsByTagName('a');

        // Loop through all list items, and hide those who don't match the search query
        for (i = 0; i < a.length; i++) {
            li = a[i].getElementsByTagName("li")[0];
            if (li.innerHTML.toUpperCase().indexOf(filter) > -1) {
                a[i].style.display = "";
            } else {
                a[i].style.display = "none";
            }
        }
    }
});