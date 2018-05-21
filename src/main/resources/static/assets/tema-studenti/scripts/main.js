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
                exercitii++;

                data.forEach(element => {
                    var a = document.createElement('a');
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var flex = document.createElement('div');
                    flex.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = `${exercitii}.`;

                    flex.appendChild(h5);
                    a.appendChild(flex);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.enunt;

                    var small = document.createElement('small');
                    small.innerText = `Extensie acceptata: .${element.extensie}`;

                    var hr = document.createElement('hr');

                    a.appendChild(p);
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

                    var input = document.createElement('input');
                    input.type = 'file';
                    input.className = 'exFile';

                    row2.appendChild(input);

                    var row3 = document.createElement('div');
                    row3.className = 'row';

                    var btnUpload = document.createElement('button');
                    btnUpload.className = 'btn btn-primary';
                    btnUpload.style = 'margin: 5px 0';

                    row3.appendChild(btnUpload);

                    col1.appendChild(row2);
                    col1.appendChild(row3);

                    row.appendChild(col1);

                    var col2 = document.createElement('div');
                    col2.className = 'col-sm-6 text-right';
                    col2.innerHTML = `Nota: ${element.nota}`;

                    row.appendChild(col2);
                });
            }
        }
    });
});