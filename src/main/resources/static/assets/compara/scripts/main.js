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

    $.ajax({
        type: 'POST',
        url: `${location}/continut_fisier`,
        data: {
            username: username1,
            nrExercitiu: parseInt(nrExercitiu)
        },
        success: data => {
            if (data.length > 0) {
                data.forEach(element => {
                    line1++;

                    var tr = document.createElement('tr');

                    var th = document.createElement('th');
                    th.innerText = line1;
                    th.style = 'width: 50px;'

                    var td = document.createElement('td');
                    td.innerText = element;

                    tr.appendChild(th);
                    tr.appendChild(td);

                    table1.appendChild(tr);
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
            if (data.length > 0) {
                data.forEach(element => {
                    line2++;

                    var tr = document.createElement('tr');

                    var th = document.createElement('th');
                    th.innerText = line2;
                    th.style = 'width: 50px;'

                    var td = document.createElement('td');
                    td.innerText = element;

                    tr.appendChild(th);
                    tr.appendChild(td);

                    table2.appendChild(tr);
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
                div.setAttribute('f1', element.student1[0]);
                div.setAttribute('f2', element.student1[1]);
                div.setAttribute('l1', element.student2[0]);
                div.setAttribute('l2', element.student2[1]);

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

                        element.classList.remove('light');
                    }

                    for (let index2 = f1; index2 < f2; index2++) {
                        const element = table1.childNodes[index2 - 1];

                        element.classList.add('light');
                    }

                    for (let remove1 = 0; remove1 < table1.childNodes.length; remove1++) {
                        const element = table2.childNodes[remove1];

                        element.classList.remove('light');
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
});