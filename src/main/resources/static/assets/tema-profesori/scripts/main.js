$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
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

    // currect/post-public-solution
    // nrExercitiu, username

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

    var listG = document.getElementById('listG');
    var addNota = document.getElementById('addNota');

    $.ajax({
        type: 'GET',
        url: `${window.location.href}/getNotite`,
        success: data => {
            if (data.length > 0) {
                data.forEach(element => {
                    var a = document.createElement('a');
                    a.classList = 'list-group-item list-group-item-action flex-column align-items-start';

                    var div = document.createElement('div');
                    div.classList = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.classList = 'mb-1';
                    h5.innerText = `${element.nume}`;

                    div.appendChild(h5);

                    a.appendChild(div);

                    var p = document.createElement('p');
                    p.classList = 'mb-1';
                    p.innerText = `${element.continut}`;

                    a.appendChild(p);

                    listG.insertBefore(a, addNota);
                });
            }
        }
    });

    var adauga = document.getElementById('adaugaNota');

    adauga.onclick = () => {
        var text = document.getElementById('notita').value;
        var status = document.getElementById('status');

        status.innerText = '';

        adauga.setAttribute('disabled', 'disabled');

        data = {
            continut: text
        }

        $.ajax({
            type: 'POST',
            url: `${window.location.href}/adaugaNotita`,
            data: data,
            success: data => {
                if (data === 'valid') {
                    adauga.innerText = 'Succes';
                    status.innerText = 'Notița a fost adăugată cu succes';
                    adauga.classList = 'btn btn-success';

                    setTimeout(() => {
                        window.location.href = window.location.href;
                    }, 500);
                } else {
                    adauga.innerText = 'Eroare';
                    status.innerText = data;
                    adauga.classList = 'btn btn-danger';

                    setTimeout(() => {
                        adauga.removeAttribute('disabled');
                    }, 400);
                }
            }
        });
    }

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

                    //accordion > card > card-header(id) > a
                    //                 > collapse > card-body > accordion--level2 > (innerCard > innerCard-header(id) > a) + collapse > innerCard-body

                    var accordion = document.createElement('div');
                    accordion.setAttribute('id', `accordion-${ex}`);

                    var card = document.createElement('div');
                    card.className = 'card';

                    var cardHeader = document.createElement('div');
                    cardHeader.setAttribute('id', `heading-${ex}`);

                    var a = document.createElement('a');
                    a.setAttribute('data-toggle', 'collapse');
                    a.setAttribute('data-target', `#collapsable-${ex}`);
                    a.setAttribute('aria-expanded', 'false');
                    a.setAttribute('aria-controls', `collapsable-${ex}`);
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

                    cardHeader.appendChild(a);
                    card.appendChild(cardHeader);

                    var collapse = document.createElement('div');
                    collapse.classList = 'collapse';
                    collapse.setAttribute('id', `collapsable-${ex}`);
                    collapse.setAttribute('aria-labelledby', `#heading-${ex}`);
                    collapse.setAttribute('data-parent', `#accordion-${ex}`);
                    collapse.style = 'margin-top: 15px ; margin-bottom: 15px; padding-left: 10px; padding-right: 10px;';

                    var cardBody = document.createElement('div');
                    cardBody.classList = 'card-body';
                    cardBody.style = 'padding: 0;';

                    //accordion for the graded/ungraded students
                    var accordionLevel1 = document.createElement('div');
                    accordionLevel1.setAttribute('id', `accordion-level1--${ex}`);

                    var innerCard = document.createElement('div');
                    innerCard.className = 'card';

                    var innerCardHeader = document.createElement('div');
                    innerCardHeader.setAttribute('id', `heading-graded-${ex}`);

                    var gradedTrigger = document.createElement('a');
                    gradedTrigger.setAttribute('id', `heading-graded-${ex}`);
                    gradedTrigger.className = 'list-group-item d-flex justify-content-between align-items-center';
                    gradedTrigger.setAttribute('data-toggle', 'collapse');
                    gradedTrigger.setAttribute('data-target', `#collapse-graded${ex}`);
                    gradedTrigger.setAttribute('aria-expanded', 'false');
                    gradedTrigger.setAttribute('aria-controls', `collapse-graded${ex}`);
                    gradedTrigger.setAttribute('style', 'cursor: pointer; border: none;');
                    gradedTrigger.innerText = 'Studenți notați';

                    var innerCollapse = document.createElement('div');
                    innerCollapse.setAttribute('id', `collapse-graded${ex}`);
                    innerCollapse.classList = 'collapse';
                    innerCollapse.setAttribute('aria-labelledby', `heading-graded-${ex}`);
                    innerCollapse.setAttribute('data-parent', `#accordion-level1--${ex}`);

                    var innerCardBody = document.createElement('div');
                    innerCardBody.classList = 'list-group-item justify-content-between align-items-center card-body';

                    var accordionLevel12 = document.createElement('div');
                    accordionLevel12.setAttribute('id', `accordion-level12--${ex}`);

                    var innerCard2 = document.createElement('div');
                    innerCard2.className = 'card';

                    var innerCardHeader2 = document.createElement('div');
                    innerCardHeader2.setAttribute('id', `heading-ungraded-${ex}`);

                    var ungradedTrigger = document.createElement('a');
                    ungradedTrigger.setAttribute('id', `heading-ungraded-${ex}`);
                    ungradedTrigger.className = 'list-group-item d-flex justify-content-between align-items-center';
                    ungradedTrigger.setAttribute('data-toggle', 'collapse');
                    ungradedTrigger.setAttribute('data-target', `#collapse-ungraded-${ex}`);
                    ungradedTrigger.setAttribute('aria-expanded', 'false');
                    ungradedTrigger.setAttribute('aria-controls', `collapse-ungraded-${ex}`);
                    ungradedTrigger.setAttribute('style', 'cursor: pointer; border: none;');
                    ungradedTrigger.innerText = 'Studenți nenotați';

                    var innerCollapse2 = document.createElement('div');
                    innerCollapse2.setAttribute('id', `collapse-ungraded-${ex}`);
                    innerCollapse2.classList = 'collapse';
                    innerCollapse2.setAttribute('aria-labelledby', `heading-ungraded-${ex}`);
                    innerCollapse2.setAttribute('data-parent', `#accordion-level12--${ex}`);

                    var innerCardBody2 = document.createElement('div');
                    innerCardBody2.classList = 'list-group-item justify-content-between align-items-center card-body';

                    var nPill = document.createElement('span');
                    nPill.setAttribute('id', 'notati-pill');
                    nPill.className = 'badge badge-primary badge-pill';

                    gradedTrigger.appendChild(nPill);

                    var searchGraded = document.createElement('input');
                    searchGraded.setAttribute('id', 'srch1');
                    searchGraded.className = 'search search--grade';
                    searchGraded.type = 'text';
                    searchGraded.placeholder = 'Căutați după nume';
                    searchGraded.style = 'margin-top: 5px;';

                    innerCardBody.appendChild(searchGraded);

                    var unPill = document.createElement('span');
                    unPill.setAttribute('id', 'nenotati-pill');
                    unPill.className = 'badge badge-primary badge-pill';

                    ungradedTrigger.appendChild(unPill);

                    var searchUngraded = document.createElement('input');
                    searchUngraded.setAttribute('id', 'srch2');
                    searchUngraded.className = 'search search--grade';
                    searchUngraded.type = 'text';
                    searchUngraded.placeholder = 'Căutați după nume';
                    searchUngraded.style = 'margin-top: 5px;';

                    innerCardBody2.appendChild(searchUngraded);

                    innerCollapse2.appendChild(innerCardBody2);
                    innerCardHeader2.appendChild(ungradedTrigger);
                    innerCard2.appendChild(innerCardHeader2);
                    innerCard2.appendChild(innerCollapse2);
                    accordionLevel12.appendChild(innerCard2);

                    innerCollapse.appendChild(innerCardBody);
                    innerCardHeader.appendChild(gradedTrigger);
                    innerCard.appendChild(innerCardHeader);
                    innerCard.appendChild(innerCollapse);
                    accordionLevel1.appendChild(innerCard);
                    cardBody.appendChild(accordionLevel1);
                    cardBody.appendChild(accordionLevel12);

                    var col = document.createElement('div');
                    col.className = 'col-md-6 students-container';
                    col.style = 'margin-top: 5px;';
                    col.innerHTML = '<h5>Teme încărcate</h5>';

                    var row = document.createElement('div');
                    row.classList = 'row';
                    row.style = 'justify-content: space-around;';

                    row.appendChild(col);
                    col.appendChild(cardBody);
                    collapse.appendChild(row);
                    card.appendChild(collapse);
                    accordion.appendChild(card);

                    group.appendChild(accordion);

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

                                    continut1 = [];
                                    continut2 = [];

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
                                                                    err.innerText = 'Comentariu șters cu succes!';
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
                                                        btn.innerText = 'Șterge comentariu';
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
                                                                        err.innerText = 'Comentariu șters cu succes!';
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
                                                        inp2.placeholder = 'Rând terminal';

                                                        var input = document.createElement('textarea');
                                                        input.style.width = '100%';

                                                        var button = document.createElement('button');
                                                        button.id = `bt${ind}`;
                                                        button.innerText = 'Adaugă comentariu';
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
                                                                        err.innerText = 'Comentariu adăugat cu succes!';
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

                                        var addSolutie = document.getElementById('publicTema');

                                        addSolutie.setAttribute('username', username);
                                        addSolutie.setAttribute('exercitiu', nrExercitiu);
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

                        innerCardBody.appendChild(a);
                    });

                    nPill.innerText = nrNotati;

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

                                        var addSolutie = document.getElementById('publicTema');

                                        addSolutie.setAttribute('username', username);
                                        addSolutie.setAttribute('exercitiu', nrExercitiu);
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

                        innerCardBody2.appendChild(a);
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

                    searchGraded.onkeyup = function () {
                        // Declare variables
                        var filter, ul, li, a, i;
                        filter = searchGraded.value.toUpperCase();
                        ul = this.parentElement;
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

                    searchUngraded.onkeyup = function () {
                        // Declare variables
                        var filter, ul, li, a, i;
                        filter = searchUngraded.value.toUpperCase();
                        ul = this.parentElement;
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

                    searchPlag.onkeyup = function () {

                        var value = $(this).val().toLowerCase();

                        $("#plag-table tr").filter(function () {
                            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                        });
                    };
                });
            }
        }
    });


    var addSolutie = document.getElementById('publicTema');

    addSolutie.onclick = () => {
        var username = addSolutie.attributes.username.value;
        var nrExercitiu = addSolutie.attributes.exercitiu.value;

        var err = document.getElementById('errNota');

        err.innerText = '';

        $.ajax({
            type: 'POST',
            url: `${window.location.href}/post-public-solution`,
            data: {
                username: username,
                nrExercitiu: nrExercitiu
            },
            success: data => {
                if (data === 'valid') {
                    err.innerText = 'Solutia a fost adaugata cu succes!';
                    err.style.color = 'green';

                    setTimeout(() => {
                        window.location.href = window.location.href;
                    }, 500);
                } else {
                    err.innerText = data;
                    err.style.color = 'red';
                }
            }
        });
    }

    $.ajax({
        type: 'GET',
        url: `${window.location.href}/public-solutions`,
        success: data => {
            if (data.length > 0) {
                var containerMain = document.getElementById('solutiiContainer');

                var exercitiu = 0;

                var title = document.createElement('h4');
                title.innerText = 'Solutii publice exercitii';
                title.style = 'text-align : center';

                containerMain.appendChild(title);

                var row = document.createElement('div');
                row.classList = 'row';

                containerMain.appendChild(row);

                var nrSolutii = 0;

                data.forEach(element => {
                    exercitiu++;
                    nrSolutii += element.length;

                    if (element.length > 0) {
                        var col = document.createElement('div');
                        col.classList = 'col-sm-4';

                        var h5 = document.createElement('h5');
                        h5.innerText = `Exercitiul ${exercitiu}`;

                        col.appendChild(h5);

                        element.forEach(solutie => {
                            var div = document.createElement('div');

                            var a = document.createElement('a');
                            a.href = '#exampleModalCenter2';
                            a.setAttribute('data-toggle', 'modal');
                            a.classList = 'public-btn';
                            a.setAttribute('username', solutie.usernameStudent);
                            a.setAttribute('exercitiu', exercitiu);

                            var button = document.createElement('button');
                            button.classList = 'btn btn-success';
                            button.style = 'margin: 5px 0;'
                            button.innerText = `${solutie.numeStudent} ${solutie.prenumeStudent}`;

                            a.appendChild(button);

                            div.appendChild(a);

                            var i = document.createElement('i');
                            i.classList = 'fas fa-minus-square stergeSol';
                            i.style = 'font-size: 32px; transform: translate(25%, 25%); cursor: pointer; color: red;';
                            i.setAttribute('username', solutie.usernameStudent);
                            i.setAttribute('exercitiu', exercitiu);

                            div.appendChild(i);

                            col.appendChild(div);
                        });

                        row.appendChild(col);
                    }
                });

                if (nrSolutii === 0) {
                    var noPublicSolutions = document.createElement('p');
                    noPublicSolutions.innerText = 'Nicio soluție publică momentan';
                    noPublicSolutions.style = 'text-align : center';
                    containerMain.appendChild(noPublicSolutions);
                }

                var btns = document.getElementsByClassName('public-btn');
                var table = document.getElementById('tableModel2');

                for (let index = 0; index < btns.length; index++) {
                    const element = btns[index];

                    element.onclick = () => {
                        var line = 0;

                        while (table.firstChild) {
                            table.removeChild(table.firstChild);
                        }

                        $.ajax({
                            type: 'POST',
                            url: `${window.location.href}/continut_fisier`,
                            data: {
                                username: element.attributes.username.value,
                                nrExercitiu: parseInt(element.attributes.exercitiu.value)
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
                }

                var del = document.getElementsByClassName('stergeSol');

                for (let index = 0; index < del.length; index++) {
                    const element = del[index];

                    console.log(element.attributes.username.value, element.attributes.exercitiu.value, `${window.location.href}/delete-public-solution`);

                    element.onclick = () => {
                        $.ajax({
                            type: 'POST',
                            url: `${window.location.href}/delete-public-solution`,
                            data: {
                                username: element.attributes.username.value,
                                nrExercitiu: parseInt(element.attributes.exercitiu.value)
                            },
                            success: data => {
                                if (data === 'valid') {
                                    element.parentElement.style.display = 'none';
                                }
                            }
                        });
                    }
                }
            }
        }
    });
    notaBtn.onclick = () => {
        err.innerText = '';
        var punctaj = notaValue.value;
        if (punctaj < 0 || punctaj > 10) {
            err.innerText = 'Notă invalidă';
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
                        notaBtn.innerText = 'Notă adăugată cu succes';

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