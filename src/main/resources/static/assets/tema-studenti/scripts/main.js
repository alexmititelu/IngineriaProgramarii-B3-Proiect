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
        type: 'GET',
        url: `${window.location.href}/student_info_json`,
        success: data => {
            if (data.length > 0) {
                data.forEach(element => {
                    exercitii++;
                    var a = document.createElement('a');
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var flex = document.createElement('div');
                    flex.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = `${exercitii}. ${element.enunt}`;

                    flex.appendChild(h5);
                    a.appendChild(flex);

                    var small = document.createElement('small');
                    small.innerText = `Extensie acceptata: .${element.extensie}`;

                    var hr = document.createElement('hr');

                    a.appendChild(small);
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
                        btnValid.innerText = 'Vezi solutie';
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
                        btnUpload.innerText = 'Incarca';
                        btnUpload.setAttribute('exercitiu', exercitii);
                        btnUpload.setAttribute('extensie', element.extensie);

                        row3.appendChild(btnUpload);

                        col1.appendChild(row2);
                        col1.appendChild(row3);
                    }

                    row.appendChild(col1);

                    var col2 = document.createElement('div');
                    col2.className = 'col-sm-6 text-right';
                    col2.innerHTML = `Nota: ${element.nota}`;

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
                                            td.innerText = element.lineValue;

                                            continut1.push(element.lineValue);
                                            continut2.push(curComment);

                                            tr.appendChild(th);
                                            tr.appendChild(td);

                                            table.appendChild(tr);

                                            nr--;

                                            th.onclick = () => {
                                                var index = parseInt(th.innerText);
                                                var cont = th.parentElement.parentElement.childNodes[ind].childNodes[1];

                                                if (!th.classList.contains('viz')) {
                                                    cont.innerText += `\n${continut2[ind]}\n`;
                                                    th.classList.add('viz');
                                                } else {
                                                    cont.innerText = continut1[ind];
                                                    th.classList.remove('viz');
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
                                                td.innerText = element.lineValue;

                                                continut1.push(element.lineValue);
                                                continut2.push(curComment);

                                                tr.appendChild(th);
                                                tr.appendChild(td);

                                                table.appendChild(tr);

                                                nr--;

                                                th.onclick = () => {
                                                    var index = parseInt(th.innerText);
                                                    var cont = th.parentElement.parentElement.childNodes[ind].childNodes[1];

                                                    if (!th.classList.contains('viz')) {
                                                        cont.innerText += `\n${continut2[ind]}\n`;
                                                        th.classList.add('viz');
                                                    } else {
                                                        cont.innerText = continut1[ind];
                                                        th.classList.remove('viz');
                                                    }
                                                }
                                            } else {
                                                line1++;

                                                var tr = document.createElement('tr');

                                                var th = document.createElement('th');
                                                th.innerText = line1;
                                                th.style = 'width: 50px;cursor: pointer;';

                                                var td = document.createElement('td');
                                                td.innerText = element.lineValue;

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

                        if (input.files.length > 0) {
                            var file = input.files[0];

                            var nameFile = file.name;
                            var extension = nameFile.split('.');

                            var realExtension = extension[extension.length - 1];

                            if (element.attributes.extensie.value !== realExtension) {
                                err.innerText = 'Ai incarcat un fisier cu o extensie gresita.';
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

                                            element.innerText = 'Incearca din nou';
                                        }
                                    }
                                });
                            }
                        } else {
                            err.innerText = 'Nu ai selectat niciun fisier pentru upload.';
                        }
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