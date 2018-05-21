$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
    });

    var group = document.getElementById('group');

    var exercitii = 0;

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
                        var input = document.createElement('input');
                        input.type = 'file';
                        input.className = 'exFile';
                        input.name = exercitii;
                        input.setAttribute('extensie', element.extensie);

                        row2.appendChild(input);

                        var row3 = document.createElement('div');
                        row3.className = 'row';

                        var btnUpload = document.createElement('button');
                        btnUpload.className = 'btn btn-primary upload';
                        btnUpload.style = 'margin: 5px 0';
                        btnUpload.innerText = 'Incarca';
                        btnUpload.setAttribute('exercitiu', exercitii);

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
                        var line = 0;

                        for (let index = 1; index < table.childNodes.length; index++) {
                            const element = table.childNodes[index];

                            table.removeChild(element);
                        }

                        $.ajax({
                            type: 'POST',
                            url: `${window.location.href}/continut_fisier`,
                            data: {
                                username: '-',
                                nrExercitiu: parseInt(element.name)
                            },
                            success: data => {
                                if (data.length > 0) {

                                    data.forEach(element => {
                                        line++;

                                        var tr = document.createElement('tr');

                                        var th = document.createElement('th');
                                        th.innerText = line;

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
                }

                var upload = document.getElementsByClassName('upload');
                var err = document.getElementById('err');

                for (let index = 0; index < upload.length; index++) {
                    const element = upload[index];

                    element.onclick = () => {
                        var input = element.parentNode.parentNode.childNodes[0].childNodes[0];
                        err.innerText = '';
                        err.innerHTML = '&nbsp;';

                        if (input.files.length > 0) {
                            var file = input.files[0];

                            var nameFile = file.name;
                            var extension = nameFile.split('.');

                            var realExtension = extension[extension.length - 1];

                            if (element.extensie !== realExtension) {
                                err.innerText = 'Ai incarcat un fisier cu o extensie gresita.';
                            } else {
                                $.ajax({
                                    type: 'POST',
                                    url: `${window.location.href}/upload`,
                                    data: {
                                        nrExercitiu: element.exercitiu,
                                        file: file
                                    },
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
});