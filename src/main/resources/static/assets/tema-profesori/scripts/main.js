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
                    nrNenotati = 0;
                    nrNotati = 0;
                    ex++;

                    var a = document.createElement('a');
                    a.setAttribute('data-toggle', 'collapse');
                    a.setAttribute('data-target', `#collapsable-${ex}`);
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start focus-ex';

                    var div = document.createElement('div');
                    div.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = element.nume;

                    div.appendChild(h5);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.enunt;

                    var small2 = document.createElement('small');
                    small2.innerText = `Extensie: .${element.extensie}`;

                    a.appendChild(div);
                    a.appendChild(p);
                    a.appendChild(small2);

                    var collapse = document.createElement('div');
                    collapse.className = 'collapse';
                    collapse.setAttribute('id', `collapsable-${ex}`);

                    var card = document.createElement('div');
                    card.className = 'card card-body';

                    //building container and tables for graded/ungraded
                    //& plagiarism
                    var container = document.createElement('div');
                    container.className = 'container';

                    var row = document.createElement('div');
                    row.className = 'row justify-content-between';

                    var col = document.createElement('div');
                    col.className = 'col-md-6 students-container';
                    col.style = 'margin-top: 5px;';

                    var title = document.createElement('h5');
                    title.innerText = 'Teme încărcate';

                    col.appendChild(title);

                    //accordion for the graded/ungraded students
                    var accordionLevel1 = document.createElement('div');
                    accordionLevel1.setAttribute('id', `accordion-level1--${ex}`);

                    var innerCard = document.createElement('div');
                    innerCard.className = 'card';

                    var gradedTrigger = document.createElement('div');
                    gradedTrigger.setAttribute('id', `heading-graded-${ex}`);
                    gradedTrigger.className = 'list-group-item d-flex justify-content-between align-items-center';
                    gradedTrigger.setAttribute('data-toggle', 'collapse');
                    gradedTrigger.setAttribute('data-target', '#collapse-graded');
                    gradedTrigger.setAttribute('aria-expanded', 'true');
                    gradedTrigger.setAttribute('aria-controls', 'collapse-graded');
                    gradedTrigger.setAttribute('style', 'cursor: pointer; border: none;');
                    gradedTrigger.innerText = 'Studenti notați';

                    var nPill = document.createElement('span');
                    nPill.setAttribute('id', 'notati-pill');
                    nPill.className = 'badge badge-primary badge-pill';
                    // nPill.innerText = 'sconcs';

                    gradedTrigger.appendChild(nPill);
                    innerCard.appendChild(gradedTrigger);

                    var collapseLevel1 = document.createElement('div');
                    collapseLevel1.setAttribute('id', 'collapse-graded');
                    collapseLevel1.className = 'collapse';
                    collapseLevel1.setAttribute('aria-labelledby', `heading-graded-${ex}`);
                    collapseLevel1.setAttribute('data-parent', `accordion-level1--${ex}`);


                    var col2 = document.createElement('div');
                    col2.className = 'col-12';

                    var searchGraded = document.createElement('input');
                    searchGraded.setAttribute('id', 'srch1');
                    searchGraded.className = 'search search--grade';
                    searchGraded.type = 'text';
                    searchGraded.placeholder = 'Căutați după nume';
                    searchGraded.style = 'margin-top: 5px;';

                    col2.appendChild(searchGraded);
                    collapseLevel1.appendChild(col2);

                    innerCard.appendChild(collapseLevel1);

                    accordionLevel1.appendChild(innerCard);
                    col.appendChild(accordionLevel1);

                    row.appendChild(col);
                    container.appendChild(row);
                    card.appendChild(container);
                    
                    collapse.appendChild(card);
                    
                    group.appendChild(a);
                    group.appendChild(collapse);

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
                        li.innerText = `${element.nume} ${element.prenume}`;
                        a.setAttribute('ex', ex);
                        a.setAttribute('username', element.username);
                        a.appendChild(li);

                        collapseLevel1.appendChild(a);
                    });

                    nPill.innerText = nrNotati;

                    //building collapsable element for the
                    //ungraded students
                    var innerCard2 = document.createElement('div');
                    innerCard2.className = 'card';

                    var ungradedTrigger = document.createElement('div');
                    ungradedTrigger.setAttribute('id', `heading-ungraded-${ex}`);
                    ungradedTrigger.className = 'list-group-item d-flex justify-content-between align-items-center';
                    ungradedTrigger.setAttribute('data-toggle', 'collapse');
                    ungradedTrigger.setAttribute('data-target', '#collapse-ungraded');
                    ungradedTrigger.setAttribute('aria-expanded', 'true');
                    ungradedTrigger.setAttribute('aria-controls', 'collapse-ungraded');
                    ungradedTrigger.setAttribute('style', 'cursor: pointer; border: none;');
                    ungradedTrigger.innerText = 'Studenti nenotați';

                    var unPill = document.createElement('span');
                    unPill.setAttribute('id', 'nenotati-pill');
                    unPill.className = 'badge badge-primary badge-pill';

                    ungradedTrigger.appendChild(unPill);
                    innerCard2.appendChild(ungradedTrigger);

                    var collapseLevel12 = document.createElement('div');
                    collapseLevel12.setAttribute('id', 'collapse-ungraded');
                    collapseLevel12.className = 'collapse';
                    collapseLevel12.setAttribute('aria-labelledby', `heading-ungraded-${ex}`);
                    collapseLevel12.setAttribute('data-parent', `accordion-level1--${ex}`);


                    var col3 = document.createElement('div');
                    col3.className = 'col-12';

                    var searchUngraded = document.createElement('input');
                    searchUngraded.setAttribute('id', 'srch2');
                    searchUngraded.className = 'search search--grade';
                    searchUngraded.type = 'text';
                    searchUngraded.placeholder = 'Căutați după nume';
                    searchUngraded.style = 'margin-top: 5px;';

                    col3.appendChild(searchUngraded);
                    collapseLevel12.appendChild(col3);

                    innerCard2.appendChild(collapseLevel12);

                    accordionLevel1.appendChild(innerCard2);

                    //append ungraded students
                    var ungraded = element.studentiNenotati;
                    ungraded.forEach((element, index) => {
                        nrNenotati++;

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
                        li.innerText = `${element.nume} ${element.prenume}`;
                        a.setAttribute('ex', ex);
                        a.setAttribute('username', element.username);
                        a.appendChild(li);

                        collapseLevel12.appendChild(a);
                    });
                    unPill.innerText = nrNenotati;

                    element.temePlagiate.sort(function (a, b) {
                        return a - b;
                    });

                    var col4 = document.createElement('div');
                    col4.className = 'col-md-5 plagiarism-container';
                    col4.style = 'margin-top: 5px;';

                    var title2 = document.createElement('h5');
                    title2.innerText = 'Plagiat';

                    col4.appendChild(title2);

                    var searchPlag = document.createElement('input');
                    searchPlag.className = 'search search--plag';
                    searchPlag.type = 'text';
                    searchPlag.placeholder = 'Căutați după nume';

                    col4.appendChild(searchPlag);

                    var tablePlagiat = document.createElement('table');
                    tablePlagiat.id = 'plag-table';
                    tablePlagiat.className = 'table';

                    col4.appendChild(tablePlagiat);

                    row.appendChild(col4);

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

                // var exEvt = document.getElementsByClassName('focus-ex');
                // for (let index = 0; index < exEvt.length; index++) {
                //     const element = exEvt[index];

                //     element.onclick = () => {
                //         var showElem = element.parentNode.childNodes[4];
                //         var show = document.getElementsByClassName('show');

                //         for (let index2 = 0; index2 < show.length; index2++) {
                //             const element2 = show[index2];

                //             element2.classList = 'collapse';
                //         }

                //         showElem.classList.add('show');
                //     }
                    
                // }
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