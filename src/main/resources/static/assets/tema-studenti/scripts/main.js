$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
    });

    var group = document.getElementById('group');

    var exercitii = 0;

    var totalLines;

    var continut1 = [];
    var continut2 = [];

    var totalLines2;

    var continut11 = [];
    var continut22 = [];

    var rowStart1, rowEnd1;
    var rowStart2, rowEnd2;

    var okViz = false;

    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }

        color += '40';

        return color;
    }

    var sub = document.getElementById('subscribe');
    sub.style.display = 'none';

    var sub2 = document.getElementById('no-subscribe');
    sub2.style.display = 'none';

    var loc = window.location.href.split('/');

    var location = `${loc[0]}//${loc[2]}/${loc[3]}/${loc[4]}`;

    $.ajax({
        type: 'GET',
        url: `${location}/is-subscribed`,
        success: data => {
            if (data === 'YES') {
                sub.style.display = 'unset';
            } else {
                sub2.style.display = 'unset';
            }

            var sub11 = document.getElementById('sub');
            var subb11 = document.getElementById('subb');

            sub11.onclick = event => {
                event.preventDefault();

                sub.style.display = 'none';
                sub2.style.display = 'unset';



                $.ajax({
                    type: 'POST',
                    url: `${location}/unsubscribe`,
                    success: data => {
                        if (data === 'valid') {
                            var subscribeStatus = 'Te-ai dezabonat cu succes!';

                            var p = document.getElementById('subscribe-status');
                            p.innerText = subscribeStatus;
                            setTimeout(() => {
                                p.innerText = '';
                            }, 2000);

                        } else {
                            var subscribeStatus = 'Dezabonarea a eșuat. Reîncearcă.';
                            var p = document.getElementById('subscribe-status');
                            p.innerText = subscribeStatus;
                            setTimeout(() => {
                                p.innerText = '';
                            }, 2000);
                            sub2.style.display = 'none';
                            sub.style.display = 'unset';
                        }
                    }
                });
            }

            subb11.onclick = event => {
                event.preventDefault();

                sub2.style.display = 'none';
                sub.style.display = 'unset';

                $.ajax({
                    type: 'POST',
                    url: `${location}/subscribe`,
                    success: data => {
                        if (data === 'valid') {
                            var subscribeStatus = 'Te-ai abonat cu succes!';

                            var p = document.getElementById('subscribe-status');
                            p.innerText = subscribeStatus;
                            setTimeout(() => {
                                p.innerText = '';
                            }, 2000);

                        } else {
                            var subscribeStatus = 'Abonarea a eșuat. Reîncearcă.';
                            var p = document.getElementById('subscribe-status');
                            p.innerText = subscribeStatus;
                            setTimeout(() => {
                                p.innerText = '';
                            }, 2000);
                            sub2.style.display = 'unset';
                            sub.style.display = 'none';
                        }
                    }
                });
            }
        }
    });

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
        url: `${window.location.href}/student_info_json`,
        success: data => {
            if (data.length > 0) {
                console.log(data);

                data.forEach(element => {
                    exercitii++;
                    var a = document.createElement('a');
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var flex = document.createElement('div');
                    flex.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('p');
                    h5.className = 'mb-1';
                    h5.innerText = `${exercitii}. ${element.enunt}`;

                    flex.appendChild(h5);
                    a.appendChild(flex);

                    var small = document.createElement('small');
                    small.innerText = `Extensie acceptată: .${element.extensie} \xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0`;

                    var nrUploads = document.createElement('small');
                    nrUploads.innerText = `Studenti ce au uploadat tema: ${element.nrUploads} \xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0`;

                    var medie = document.createElement('small');
                    medie.innerText = `Medie note: ${element.medieGlobala}`;

                    var nrStudentiNotati = document.createElement('small');
                    nrStudentiNotati.innerText = `Numarul de studenti notati momentan: ${element.nrStudentiNotati} \xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0`;

                    var hr = document.createElement('hr');

                    a.appendChild(small);
                    a.appendChild(nrUploads);
                    a.appendChild(nrStudentiNotati);
                    a.appendChild(medie);
                    a.appendChild(hr);

                    var container = document.createElement('div');
                    container.className = 'container';

                    var row = document.createElement('div');
                    row.className = 'row';

                    var col1 = document.createElement('div');
                    col1.className = 'col-sm-6';

                    var row2 = document.createElement('div');
                    row2.className = 'row';

                    if (element.uploaded) {
                        var btnValid = document.createElement('button');
                        btnValid.className = 'btn btn-success solutie';
                        btnValid.style = 'margin: 5px 0';
                        btnValid.innerText = 'Vezi soluție';
                        btnValid.name = exercitii;
                        btnValid.setAttribute('role', 'button');
                        btnValid.setAttribute('data-toggle', 'modal');
                        btnValid.setAttribute('data-target', '#exampleModalCenter');

                        row2.appendChild(btnValid);

                        col1.appendChild(row2);
                    } else {
                        var form = document.createElement('form');
                        var input = document.createElement('input');
                        input.type = 'file';
                        input.className = 'exFile';
                        input.name = 'file';
                        input.setAttribute('name2', exercitii);
                        input.setAttribute('extensie', element.extensie);

                        form.appendChild(input);

                        row2.appendChild(form);

                        var row3 = document.createElement('div');
                        row3.className = 'row';

                        var btnUpload = document.createElement('button');
                        btnUpload.className = 'btn btn-primary upload';
                        btnUpload.style = 'margin: 5px 0';
                        btnUpload.innerText = 'Incarcă';
                        btnUpload.setAttribute('exercitiu', exercitii);
                        btnUpload.setAttribute('extensie', element.extensie);

                        row3.appendChild(btnUpload);

                        col1.appendChild(row2);
                        col1.appendChild(row3);
                    }

                    row.appendChild(col1);

                    var col2 = document.createElement('div');
                    col2.className = 'col-sm-6 text-right';
                    col2.innerHTML = `Notă: ${element.nota}`;

                    row.appendChild(col2);

                    container.appendChild(row);

                    a.appendChild(container);

                    group.appendChild(a);
                });

                var solutie = document.getElementsByClassName('solutie');
                var table = document.getElementById('tableModel');

                for (let index = 0; index < solutie.length; index++) {
                    const element = solutie[index];

                    element.onclick = () => {
                        var line1 = 0;

                        while (table.firstChild) {
                            table.removeChild(table.firstChild);
                        }

                        $.ajax({
                            type: 'POST',
                            url: `${window.location.href}/continut_fisier`,
                            data: {
                                username: '-',
                                nrExercitiu: parseInt(element.attributes.name.value)
                            },
                            success: data => {
                                var nr = 0;

                                var curColor;
                                var curComment;

                                continut1 = [];
                                continut2 = [];

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

                                                var div = document.createElement('div');
                                                div.classList = 'commentSol';

                                                var p = document.createElement('p');
                                                p.innerText = continut2[ind];

                                                div.appendChild(p);

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

                                                    var div = document.createElement('div');
                                                    div.classList = 'commentSol';

                                                    var p = document.createElement('p');
                                                    p.innerText = continut2[ind];

                                                    div.appendChild(p);

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
                                                }
                                            } else {
                                                line1++;

                                                var tr = document.createElement('tr');

                                                var th = document.createElement('th');
                                                th.innerText = line1;
                                                th.style = 'width: 50px;cursor: pointer;';

                                                var td = document.createElement('td');

                                                var pre = document.createElement('pre');
                                                pre.innerText = element.lineValue;

                                                td.appendChild(pre);

                                                continut1.push(element.lineValue);
                                                continut2.push('');

                                                tr.appendChild(th);
                                                tr.appendChild(td);

                                                table.appendChild(tr);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }

                var upload = document.getElementsByClassName('upload');
                var err = document.getElementById('err');

                for (let index = 0; index < upload.length; index++) {
                    const element = upload[index];

                    element.onclick = () => {
                        var input = element.parentNode.parentNode.childNodes[0].childNodes[0].childNodes[0];

                        var form = element.parentNode.parentNode.childNodes[0].childNodes[0];

                        err.innerText = '';
                        err.innerHTML = '&nbsp;';

                        element.setAttribute('disabled', 'disabled');

                        if (input.files.length > 0) {
                            var file = input.files[0];

                            var nameFile = file.name;
                            var extension = nameFile.split('.');

                            var realExtension = extension[extension.length - 1];

                            if (element.attributes.extensie.value !== realExtension) {
                                err.innerText = 'Ai incărcat un fișier cu o extensie greșită.';
                            } else {
                                var data = new FormData(form);

                                $.ajax({
                                    type: 'POST',
                                    url: `${window.location.href}/upload/${element.attributes.exercitiu.value}`,
                                    data: data,
                                    processData: false,
                                    contentType: false,
                                    enctype: 'multipart/form-data',
                                    success: data => {
                                        if (data === 'valid') {
                                            element.classList.remove('btn-primary');
                                            element.classList.add('btn-success');

                                            element.innerText = 'Succes';

                                            setTimeout(() => {
                                                window.location.href = window.location.href;
                                            }, 300);
                                        } else {
                                            element.classList.remove('btn-primary');
                                            element.classList.add('btn-danger');

                                            element.innerText = data;

                                            setTimeout(() => {
                                                element.innerText = 'Incearcă din nou';
                                                element.removeAttribute('disabled');
                                            }, 3000);
                                        }
                                    }
                                });
                            }
                        } else {
                            err.innerText = 'Nu ai selectat niciun fișier pentru încărcare.';
                        }
                    }
                }
            }
        }
    });

    $.ajax({
        type: 'GET',
        url: `${window.location.href}/public-solutions`,
        success: data => {
            if (data.length > 0) {
                var containerMain = document.getElementById('solutiiContainer');

                var exercitiu = 0;

                var title = document.createElement('h4');
                title.innerText = 'Soluții publice';
                title.style = 'text-align : center';

                containerMain.appendChild(title);

                var row = document.createElement('div');
                row.classList = 'row row justify-content-center';

                containerMain.appendChild(row);


                var nrSolutii = 0;

                data.forEach(element => {
                    exercitiu++;
                    nrSolutii += element.length;

                    if (element.length > 0) {
                        var col = document.createElement('div');
                        col.classList = 'col-sm-4';

                        var h5 = document.createElement('h5');
                        h5.innerText = `Exercițiul ${exercitiu}`;

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