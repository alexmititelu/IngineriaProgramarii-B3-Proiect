$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
    });

    var location = window.location.href.split('/compara?')[0];

    var url = new URL(window.location.href);

    var username1 = url.searchParams.get('username1');
    var username2 = url.searchParams.get('username2');
    var nrExercitiu = url.searchParams.get('nrExercitiu');

    var line1 = 0;
    var line2 = 0;

    var table1 = document.getElementById('tableModel1');
    var table2 = document.getElementById('tableModel2');

    var totalLines;

    var continut1 = [];
    var continut2 = [];

    var totalLines2;

    var continut11 = [];
    var continut22 = [];

    var rowStart1, rowEnd1;
    var rowStart2, rowEnd2;

    var okViz = false;
    var okViz2 = false;

    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }

        color += '40';

        return color;
    }

    $.ajax({
        type: 'POST',
        url: `${location}/continut_fisier`,
        data: {
            username: username1,
            nrExercitiu: parseInt(nrExercitiu)
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
                        line1++;

                        var tr = document.createElement('tr');

                        var th = document.createElement('th');
                        th.innerText = line1;
                        th.style = `width: 50px; background: ${curColor};cursor: pointer;`;
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
                        pre.innerHTML = element.lineValue;

                        td.appendChild(pre);

                        continut1.push(element.lineValue);
                        continut2.push(curComment);

                        tr.appendChild(th);
                        tr.appendChild(td);

                        table1.appendChild(tr);

                        nr--;

                        th.onclick = () => {
                            var index = parseInt(th.innerText);
                            var cont = th.parentElement.parentElement.childNodes[parseInt(th.attributes.endr.value)].childNodes[1];

                            var btn = document.createElement('button');
                            btn.innerText = 'Sterge comentariu';
                            btn.classList = 'btn btn-danger';
                            btn.setAttribute('startr', parseInt(th.attributes.startr.value));
                            btn.setAttribute('endr', parseInt(th.attributes.endr.value));

                            var err = document.createElement('p');

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
                                        username: username1,
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

                            line1++;

                            var tr = document.createElement('tr');

                            var th = document.createElement('th');
                            th.innerText = line1;
                            th.style = `width: 60px; background: ${curColor};cursor: pointer;`;
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
                            pre.innerHTML = element.lineValue;

                            td.appendChild(pre);
                            continut1.push(element.lineValue);
                            continut2.push(curComment);

                            tr.appendChild(th);
                            tr.appendChild(td);

                            table1.appendChild(tr);

                            nr--;

                            th.onclick = () => {
                                var index = parseInt(th.innerText);
                                var cont = th.parentElement.parentElement.childNodes[parseInt(th.attributes.endr.value)].childNodes[1];

                                var btn = document.createElement('button');
                                btn.classList = 'btn btn-danger';
                                btn.innerText = 'Sterge comentariu';

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
                                    // cont.appendChild(btn);
                                    // cont.appendChild(err);
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
                                            username: username1,
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
                            line1++;

                            var tr = document.createElement('tr');

                            var th = document.createElement('th');
                            th.classList = 'th-hover';
                            th.innerHTML = line1 + '<i class="far fa-plus-square" style="margin-left: 5px;"></i>';
                            th.style = 'width: 50px;cursor: pointer;';

                            var td = document.createElement('td');

                            var pre = document.createElement('pre');
                            pre.innerHTML = element.lineValue;

                            td.appendChild(pre);

                            continut1.push(element.lineValue);
                            continut2.push('');

                            tr.appendChild(th);
                            tr.appendChild(td);

                            table1.appendChild(tr);

                            th.onclick = () => {
                                var index = parseInt(th.innerText);
                                var cont = th.parentElement.parentElement.childNodes[ind + 1].childNodes[1];

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
                                    var loc = window.location.href.split('/compara')[0];

                                    $.ajax({
                                        type: 'POST',
                                        url: `${loc}/adaugaComentariu`,
                                        data: {
                                            nrExercitiu: nrExercitiu,
                                            username: username1,
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

    $.ajax({
        type: 'POST',
        url: `${location}/continut_fisier`,
        data: {
            username: username2,
            nrExercitiu: parseInt(nrExercitiu)
        },
        success: data => {
            var nr = 0;

            var curColor;
            var curComment;

            totalLines2 = 0;
            if (data.length > 0) {
                data.forEach((element, ind) => {
                    totalLines2++;
                    if (nr > 0) {
                        line2++;

                        var tr = document.createElement('tr');

                        var th = document.createElement('th');
                        th.innerText = line2;
                        th.style = `width: 50px; background: ${curColor};cursor: pointer;`;
                        th.classList = 'rowSol';
                        th.setAttribute('role', 'button');
                        th.setAttribute('data-toggle', 'popover');
                        th.setAttribute('data-trigger', 'focus');
                        th.setAttribute('title', 'Comeptariu');
                        th.setAttribute('data-content', curComment);

                        th.setAttribute('startr', rowStart2);
                        th.setAttribute('endr', rowEnd2);

                        var td = document.createElement('td');

                        var pre = document.createElement('pre');
                        pre.innerHTML = element.lineValue;

                        td.appendChild(pre);

                        continut11.push(element.lineValue);
                        continut22.push(curComment);

                        tr.appendChild(th);
                        tr.appendChild(td);

                        table2.appendChild(tr);

                        nr--;

                        th.onclick = () => {
                            var index = parseInt(th.innerText);
                            var cont = th.parentElement.parentElement.childNodes[parseInt(th.attributes.endr.value)].childNodes[1];

                            var btn = document.createElement('button');
                            btn.innerText = 'Sterge comentariu';
                            btn.classList = 'btn btn-danger';
                            btn.setAttribute('startr', parseInt(th.attributes.startr.value));
                            btn.setAttribute('endr', parseInt(th.attributes.endr.value));

                            var err = document.createElement('p');

                            var div = document.createElement('div');
                            div.classList = 'commentSol';

                            var p = document.createElement('p');
                            p.innerText = continut22[ind];

                            div.appendChild(p);
                            div.appendChild(btn);
                            div.appendChild(err);

                            var pre = document.createElement('pre');
                            pre.innerHTML = `${continut11[parseInt(th.attributes.endr.value) - 1]}\n`;

                            if (!okViz2) {
                                cont.innerHTML = '';
                                cont.appendChild(pre);

                                cont.appendChild(div);
                                okViz2 = true;
                            } else {
                                cont.innerText = '';
                                cont.appendChild(pre);

                                okViz2 = false;
                            }

                            btn.onclick = () => {
                                var loc = window.location.href.split('/compara')[0];

                                $.ajax({
                                    type: 'POST',
                                    url: `${loc}/stergeComentariu`,
                                    data: {
                                        username: username2,
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

                            rowStart2 = ind + 1;
                            rowEnd2 = rowStart2 + element.commentedLines - 1;

                            var color = getRandomColor();
                            curColor = color;

                            curComment = element.comment;

                            line2++;

                            var tr = document.createElement('tr');

                            var th = document.createElement('th');
                            th.innerText = line2;
                            th.style = `width: 50px; background: ${curColor};cursor: pointer;`;
                            th.classList = 'rowSol';
                            th.setAttribute('role', 'button');
                            th.setAttribute('data-toggle', 'popover');
                            th.setAttribute('data-trigger', 'focus');
                            th.setAttribute('title', 'Comeptariu');
                            th.setAttribute('data-content', curComment);

                            th.setAttribute('startr', rowStart2);
                            th.setAttribute('endr', rowEnd2);

                            var td = document.createElement('td');

                            var pre = document.createElement('pre');
                            pre.innerHTML = element.lineValue;

                            td.appendChild(pre);

                            continut11.push(element.lineValue);
                            continut22.push(curComment);

                            tr.appendChild(th);
                            tr.appendChild(td);

                            table2.appendChild(tr);

                            nr--;

                            th.onclick = () => {
                                var index = parseInt(th.innerText);
                                var cont = th.parentElement.parentElement.childNodes[parseInt(th.attributes.endr.value)].childNodes[1];

                                var btn = document.createElement('button');
                                btn.classList = 'btn btn-danger';
                                btn.innerText = 'Sterge comentariu';

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
                                pre.innerHTML = `${continut11[parseInt(th.attributes.endr.value) - 1]}\n`;

                                if (!okViz2) {
                                    cont.innerHTML = '';
                                    cont.appendChild(pre);

                                    cont.appendChild(div);
                                    okViz2 = true;
                                } else {
                                    cont.innerText = '';
                                    cont.appendChild(pre);

                                    okViz2 = false;
                                }

                                btn.onclick = () => {
                                    var loc = window.location.href.split('/compara')[0];

                                    $.ajax({
                                        type: 'POST',
                                        url: `${loc}/stergeComentariu`,
                                        data: {
                                            username: username2,
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
                            line2++;

                            var tr = document.createElement('tr');

                            var th = document.createElement('th');
                            th.classList = 'th-hover';
                            th.innerHTML = line2 + '<i class="far fa-plus-square" style="margin-left: 5px;"></i>';
                            th.style = 'width: 60px;cursor: pointer;';

                            var td = document.createElement('td');

                            var pre = document.createElement('pre');
                            pre.innerHTML = element.lineValue;

                            td.appendChild(pre);

                            continut11.push(element.lineValue);
                            continut22.push('');

                            tr.appendChild(th);
                            tr.appendChild(td);

                            table2.appendChild(tr);

                            th.onclick = () => {
                                var index = parseInt(th.innerText);
                                var cont = th.parentElement.parentElement.childNodes[ind + 1].childNodes[1];

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

                                div.appendChild(inp1);
                                div.appendChild(inp2);
                                div.appendChild(input);
                                div.appendChild(button);
                                div.appendChild(err);

                                var pre = document.createElement('pre');
                                pre.innerHTML = `${continut11[ind]}\n`;

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
                                    var loc = window.location.href.split('/compara')[0];

                                    $.ajax({
                                        type: 'POST',
                                        url: `${loc}/adaugaComentariu`,
                                        data: {
                                            nrExercitiu: nrExercitiu,
                                            username: username2,
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

    var mainRow = document.getElementById('mainRow');
    var nume1 = document.getElementById('name1').innerText;
    var nume2 = document.getElementById('name2').innerText;

    $.ajax({
        type: 'POST',
        url: `${location}/hot_zones`,
        data: {
            username1: username1,
            username2: username2,
            nrExercitiu: nrExercitiu
        },
        success: data => {
            data.forEach(element => {
                var div = document.createElement('div');
                div.classList = 'col-12 text-center row-plag';
                div.innerText = `${nume1} [${element.student1[0]}-${element.student1[1]}] | ${nume2} [${element.student2[0]}-${element.student2[1]}] ${element.procent}%`;
                div.setAttribute('f1', parseInt(element.student1[0]) + 1);
                div.setAttribute('f2', parseInt(element.student1[1]) + 2);
                div.setAttribute('l1', parseInt(element.student2[0]) + 1);
                div.setAttribute('l2', parseInt(element.student1[1]) + 2);

                mainRow.appendChild(div);
            });

            var plag = document.getElementsByClassName('row-plag');

            for (let index = 0; index < plag.length; index++) {
                const element = plag[index];

                element.onclick = () => {
                    var f1 = parseInt(element.attributes.f1.value);
                    var f2 = parseInt(element.attributes.f2.value);
                    var l1 = parseInt(element.attributes.l1.value);
                    var l2 = parseInt(element.attributes.l2.value);

                    for (let remove1 = 0; remove1 < table1.childNodes.length; remove1++) {
                        const element = table1.childNodes[remove1];

                        if (element.classList === 'light') {
                            element.classList = '';
                        }
                    }

                    for (let index2 = f1; index2 < f2; index2++) {
                        const element = table1.childNodes[index2 - 1];

                        element.classList.add('light');
                    }

                    for (let remove1 = 0; remove1 < table1.childNodes.length; remove1++) {
                        const element = table2.childNodes[remove1];

                        if (element.classList === 'light') {
                            element.classList = '';
                        }
                    }

                    for (let index2 = l1; index2 < l2; index2++) {
                        const element = table2.childNodes[index2 - 1];

                        element.classList.add('light');
                    }

                    var scroll1 = table1.childNodes[f1 - 1];
                    var scroll2 = table2.childNodes[l1 - 1];

                    scroll1.scrollIntoView({
                        behavior: "auto",
                        block: "center"
                    });

                    scroll2.scrollIntoView({
                        behavior: "auto",
                        block: "center"
                    });
                }
            }
        }
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
});