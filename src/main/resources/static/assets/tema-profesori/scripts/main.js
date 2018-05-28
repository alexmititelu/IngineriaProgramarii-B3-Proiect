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

    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }

        color += '40';

        return color;
    }

    var continut1 = [];
    var continut2 = [];

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

    var totalLines = 0;

    var rowStart, rowEnd;

    var okViz = false;

    $.ajax({
        type: 'GET',
        url: `${window.location.href}/profesor_info`,
        success: data => {
            if (data) {
                var group = document.getElementById('group');
                
                data.forEach((element, exercitiu) => {
                    ex++;
                    var card = document.createElement('div');
                    // a.href = `#collapsable-${ex}`;
                    card.className = 'list-group-item list-group-item-action flex-column align-items-start card';

                    var cardHeader = document.createElement('div');
                    cardHeader.className = 'd-flex w-100 justify-content-between card-header';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = element.nume;

                    var small1 = document.createElement('small');
                    small1.innerText = '3 days ago';

                    var btnToggleCollapse = document.createElement('button');
                    btnToggleCollapse.className = 'btn btn-link';
                    btnToggleCollapse.setAttribute('data-toggle', 'collapse');

                    cardHeader.appendChild(h5);
                    cardHeader.appendChild(small1);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.enunt;

                    var small2 = document.createElement('small');
                    small2.innerText = `Extensie: .${element.extensie}`;

                    card.appendChild(div);
                    card.appendChild(p);
                    card.appendChild(small2);
                    
                    group.appendChild(a);

                    //append graded students
                    var graded = element.studentiNotati;
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
                                    var nr = 0;

                                    var curColor;
                                    var curComment;

                                    totalLines = 0;

                                    if (data.length > 0) {
                                        data.forEach((element, ind) => {
                                            totalLines++;
                                            if (nr > 0) {
                                                line++;

                                                var tr = document.createElement('tr');

                                                var th = document.createElement('th');
                                                th.innerText = line;
                                                th.style = `width: 30px; background: ${curColor};cursor: pointer;`;
                                                th.classList = 'rowSol';
                                                th.setAttribute('role', 'button');
                                                th.setAttribute('data-toggle', 'popover');
                                                th.setAttribute('data-trigger', 'focus');
                                                th.setAttribute('title', 'Comeptariu');
                                                th.setAttribute('data-content', curComment);

                                                th.setAttribute('startr', rowStart);
                                                th.setAttribute('endr', rowEnd);

                                                var td = document.createElement('td');

                                                var pre = document.createElement('pre');
                                                pre.innerText = element.lineValue;

                                                td.appendChild(pre);

                                                continut1.push(element.lineValue);
                                                continut2.push(curComment);

                                                tr.appendChild(th);
                                                tr.appendChild(td);

                                                table.appendChild(tr);

                                                nr--;

                                                th.onclick = () => {
                                                    var index = parseInt(th.innerText);
                                                    var cont = th.parentElement.parentElement.childNodes[parseInt(th.attributes.endr.value) - 1].childNodes[1];

                                                    var btn = document.createElement('button');
                                                    btn.innerText = 'Sterge comentariu';
                                                    btn.classList = 'btn btn-danger';

                                                    var err = document.createElement('p');

                                                    btn.setAttribute('startr', parseInt(th.attributes.startr.value));
                                                    btn.setAttribute('endr', parseInt(th.attributes.endr.value));

                                                    var div = document.createElement('div');
                                                    div.classList = 'commentSol';

                                                    var p = document.createElement('p');
                                                    p.innerText = continut2[ind];

                                                    div.appendChild(p);
                                                    div.appendChild(btn);
                                                    div.appendChild(err);

                                                    var pre = document.createElement('pre');
                                                    pre.innerHTML = `${continut1[parseInt(th.attributes.endr.value) - 1]}\n`;

                                                    if (!okViz) {
                                                        cont.innerHTML = '';
                                                        cont.appendChild(pre);

                                                        cont.appendChild(div);
                                                        okViz = true;
                                                    } else {
                                                        cont.innerText = '';

                                                        cont.appendChild(pre);

                                                        okViz = false;
                                                    }

                                                    btn.onclick = () => {
                                                        var loc = window.location.href.split('/compara')[0];

                                                        $.ajax({
                                                            type: 'POST',
                                                            url: `${loc}/stergeComentariu`,
                                                            data: {
                                                                username: username,
                                                                nrExercitiu: nrExercitiu,
                                                                startRow: parseInt(btn.attributes.startr.value),
                                                                endRow: parseInt(btn.attributes.endr.value)
                                                            },
                                                            success: data => {
                                                                if (data === 'valid') {
                                                                    err.style.color = 'green';
                                                                    err.innerText = 'Comentariu sters cu succes!';
                                                                    setTimeout(() => {
                                                                        window.location.href = window.location.href
                                                                    }, 1000);
                                                                } else {
                                                                    err.style.color = 'red';
                                                                    err.innerText = data;
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            } else {
                                                if (element.comment) {
                                                    nr = parseInt(element.commentedLines);

                                                    rowStart = ind + 1;
                                                    rowEnd = rowStart + element.commentedLines - 1;

                                                    var color = getRandomColor();
                                                    curColor = color;

                                                    curComment = element.comment;

                                                    line++;

                                                    var tr = document.createElement('tr');

                                                    var th = document.createElement('th');
                                                    th.innerText = line;
                                                    th.style = `width: 30px; background: ${curColor};cursor: pointer;`;
                                                    th.classList = 'rowSol';
                                                    th.setAttribute('role', 'button');
                                                    th.setAttribute('data-toggle', 'popover');
                                                    th.setAttribute('data-trigger', 'focus');
                                                    th.setAttribute('title', 'Comeptariu');
                                                    th.setAttribute('data-content', curComment);

                                                    th.setAttribute('startr', rowStart);
                                                    th.setAttribute('endr', rowEnd);

                                                    var td = document.createElement('td');

                                                    var pre = document.createElement('pre');
                                                    pre.innerText = element.lineValue;

                                                    td.appendChild(pre);

                                                    continut1.push(element.lineValue);
                                                    continut2.push(curComment);

                                                    tr.appendChild(th);
                                                    tr.appendChild(td);

                                                    table.appendChild(tr);

                                                    nr--;

                                                    th.onclick = () => {
                                                        var index = parseInt(th.innerText);
                                                        var cont = th.parentElement.parentElement.childNodes[parseInt(th.attributes.endr.value) - 1].childNodes[1];

                                                        var btn = document.createElement('button');
                                                        btn.innerText = 'Sterge comentariu';
                                                        btn.classList = 'btn btn-danger';

                                                        var err = document.createElement('p');

                                                        btn.setAttribute('startr', parseInt(th.attributes.startr.value));
                                                        btn.setAttribute('endr', parseInt(th.attributes.endr.value));

                                                        var div = document.createElement('div');
                                                        div.classList = 'commentSol';

                                                        var p = document.createElement('p');
                                                        p.innerText = continut2[ind];

                                                        div.appendChild(p);
                                                        div.appendChild(btn);
                                                        div.appendChild(err);

                                                        var pre = document.createElement('pre');
                                                        pre.innerHTML = `${continut1[parseInt(th.attributes.endr.value) - 1]}\n`;

                                                        if (!okViz) {
                                                            cont.innerHTML = '';
                                                            cont.appendChild(pre);

                                                            cont.appendChild(div);
                                                            okViz = true;
                                                        } else {
                                                            cont.innerText = '';

                                                            cont.appendChild(pre);

                                                            okViz = false;
                                                        }

                                                        btn.onclick = () => {
                                                            var loc = window.location.href.split('/compara')[0];

                                                            $.ajax({
                                                                type: 'POST',
                                                                url: `${loc}/stergeComentariu`,
                                                                data: {
                                                                    username: username,
                                                                    nrExercitiu: nrExercitiu,
                                                                    startRow: parseInt(btn.attributes.startr.value),
                                                                    endRow: parseInt(btn.attributes.endr.value)
                                                                },
                                                                success: data => {
                                                                    if (data === 'valid') {
                                                                        err.style.color = 'green';
                                                                        err.innerText = 'Comentariu sters cu succes!';
                                                                        setTimeout(() => {
                                                                            window.location.href = window.location.href
                                                                        }, 1000);
                                                                    } else {
                                                                        err.style.color = 'red';
                                                                        err.innerText = data;
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                } else {
                                                    line++;

                                                    var tr = document.createElement('tr');

                                                    var th = document.createElement('th');
                                                    th.classList = 'th-hover';
                                                    th.innerHTML = line + '<i class="far fa-plus-square" style="margin-left: 5px;"></i>';
                                                    th.style = 'width: 60px;cursor: pointer;';

                                                    var td = document.createElement('td');

                                                    var pre = document.createElement('pre');
                                                    pre.innerText = element.lineValue;

                                                    td.appendChild(pre);

                                                    continut1.push(element.lineValue);
                                                    continut2.push('');

                                                    tr.appendChild(th);
                                                    tr.appendChild(td);

                                                    table.appendChild(tr);

                                                    th.onclick = () => {
                                                        var index = parseInt(th.innerText);
                                                        var cont = th.parentElement.parentElement.childNodes[ind].childNodes[1];

                                                        var inp1 = document.createElement('input');
                                                        inp1.value = index;
                                                        inp1.setAttribute('disabled', 'disabled');

                                                        var inp2 = document.createElement('input');
                                                        inp2.placeholder = 'Rand terminal';

                                                        var input = document.createElement('textarea');
                                                        input.style.width = '100%';
                                                        
                                                        var button = document.createElement('button');
                                                        button.id = `bt${ind}`;
                                                        button.innerText = 'Adauga comentariu';
                                                        button.classList = 'btn btn-primary';

                                                        var err = document.createElement('p');

                                                        var div = document.createElement('div');
                                                        div.classList = 'commentSol';

                                                        var p = document.createElement('p');
                                                        p.innerText = continut2[ind];

                                                        var div = document.createElement('div');
                                                        div.classList = 'commentSol';

                                                        div.appendChild(inp1);
                                                        div.appendChild(inp2);
                                                        div.appendChild(input);
                                                        div.appendChild(button);
                                                        div.appendChild(err);

                                                        var pre = document.createElement('pre');
                                                        pre.innerHTML = `${continut1[ind]}\n`;

                                                        if (!th.classList.contains('viz2')) {
                                                            cont.innerText = "";
                                                            cont.appendChild(pre);

                                                            cont.appendChild(div);
                                                            th.classList.add('viz2');
                                                        } else {
                                                            cont.innerText = '';
                                                            cont.appendChild(pre);
                                                            th.classList.remove('viz2');
                                                        }

                                                        button.onclick = () => {
                                                            $.ajax({
                                                                type: 'POST',
                                                                url: `${window.location.href}/adaugaComentariu`,
                                                                data: {
                                                                    nrExercitiu: nrExercitiu,
                                                                    username: usernameCurrent,
                                                                    startRow: parseInt(inp1.value),
                                                                    endRow: parseInt(inp2.value),
                                                                    comentariu: input.value
                                                                },
                                                                success: data => {
                                                                    if (data === 'valid') {
                                                                        err.style.color = 'green';
                                                                        err.innerText = 'Comentariu adaugat cu succes!';
                                                                        setTimeout(() => {
                                                                            window.location.href = window.location.href
                                                                        }, 1000);
                                                                    } else {
                                                                        err.style.color = 'red';
                                                                        err.innerText = data;
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            }
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
                                            td.innerText = element.lineValue;

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

                    element.temePlagiate.sort(function (a, b) {
                        return a - b;
                    }).reverse();

                    var tablePlagiat = document.getElementById('plag-table');
                    element.temePlagiate.forEach(plagiat => {
                        var row = document.createElement('tr');
                        row.classList = 'rowPlagiat';
                        row.setAttribute('username1', plagiat.username1);
                        row.setAttribute('username2', plagiat.username2);
                        row.setAttribute('exercitiu', exercitiu + 1);

                        var th1 = document.createElement('th');
                        th1.innerText = `${plagiat.nume1} ${plagiat.prenume1}`;

                        var th2 = document.createElement('th');
                        th2.innerText = `${plagiat.nume2} ${plagiat.prenume2}`;

                        var th3 = document.createElement('th');
                        th3.innerText = `${plagiat.procentPlagiarism} %`;
                        th3.style.width = '70px';

                        row.appendChild(th1);
                        row.appendChild(th2);
                        row.appendChild(th3);

                        tablePlagiat.appendChild(row);
                    });

                    var rowsPlagiat = document.getElementsByClassName('rowPlagiat');
                    for (let index = 0; index < rowsPlagiat.length; index++) {
                        const element = rowsPlagiat[index];

                        element.onclick = () => {
                            window.location.href = `${window.location.href}/compara?username1=${element.attributes.username1.value}&username2=${element.attributes.username2.value}&nrExercitiu=${element.attributes.exercitiu.value}`;
                        }
                    }
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

    var downloadLink = document.getElementById('download-csv');
    downloadLink.setAttribute('href', `${window.location.href}/raport.csv`);

});