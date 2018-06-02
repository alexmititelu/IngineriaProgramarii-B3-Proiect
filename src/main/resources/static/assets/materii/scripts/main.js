$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
    });

    $.ajax({
        type: 'GET',
        url: '/materii_json',
        success: data => {
            if (data) {
                var group = document.getElementById('group');

                data.forEach(element => {
                    var a = document.createElement('a');
                    a.href = `/materii/${element.titlu}`;
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var div = document.createElement('div');
                    div.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = element.titlu;

                    var small1 = document.createElement('small');


                    div.appendChild(h5);
                    div.appendChild(small1);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.descriere;

                    var small2 = document.createElement('small');
                    small2.innerText = `An: ${element.an} | `;

                    var small3 = document.createElement('small');
                    small3.innerText = `Semestru: ${element.semestru} | `;

                    var small4 = document.createElement('small');
                    small4.innerText = `Numarul de abonati la materie: ${element.numberOfSubscribedStudents}`;

                    a.appendChild(div);
                    a.appendChild(p);
                    a.appendChild(small2);
                    a.appendChild(small3);
                    a.appendChild(small4)

                    group.appendChild(a);
                });

                var filter = document.getElementById('filter');
                filter.onclick = () => {
                    var groupItems = document.getElementById('group').childNodes;

                    var an = document.getElementById('selectAn').value;
                    var semestru = document.getElementById('selectSemestru').value;

                    for (let index = 1; index < groupItems.length; index++) {
                        const element = groupItems[index];

                        var anItemBrut = element.childNodes[2].innerText;
                        var anItem = anItemBrut.split(' ')[1];

                        var semestruItemBrut = element.childNodes[3].innerText;
                        var semestruItem = semestruItemBrut.split(' ')[1];

                        element.style.display = 'unset';

                        if (an === '1') {
                            if (anItem !== '1') {
                                element.style.display = 'none';
                            }
                        } else if (an === '2') {
                            if (anItem !== '2') {
                                element.style.display = 'none';
                            }
                        } else if (an === '3') {
                            if (anItem !== '3') {
                                element.style.display = 'none';
                            }
                        }

                        if (semestru === '1') {
                            if (semestruItem !== '1') {
                                element.style.display = 'none';
                            }
                        } else if (semestru === '2') {
                            if (semestruItem !== '2') {
                                element.style.display = 'none';
                            }
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