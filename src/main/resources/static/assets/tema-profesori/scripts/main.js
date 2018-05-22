$(document).ready(function () {
    $('.menu').click(() => {
        $('.responsive-menu').toggleClass('toggle');
    });

    $(".search--grade").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $(".student-list li").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
      });

      $(".search--plag").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#plag-table tr").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
      });

    $.ajax({
        type: 'GET',
        url: `${window.location.href}/profesor_info`,
        success: data => {
            if (data) {
                var group = document.getElementById('group');

                data.forEach(element => {
                    var a = document.createElement('a');
                    // a.href = `/materii/${element.titlu}`;
                    a.className = 'list-group-item list-group-item-action flex-column align-items-start';

                    var div = document.createElement('div');
                    div.className = 'd-flex w-100 justify-content-between';

                    var h5 = document.createElement('h5');
                    h5.className = 'mb-1';
                    h5.innerText = element.nume;

                    var small1 = document.createElement('small');
                    small1.innerText = '3 days ago';

                    div.appendChild(h5);
                    div.appendChild(small1);

                    var p = document.createElement('p');
                    p.className = 'mb-1';
                    p.innerText = element.enunt;

                    var small2 = document.createElement('small');
                    small2.innerText = `Extensie: ${element.extensie}`;

                    a.appendChild(div);
                    a.appendChild(p);
                    a.appendChild(small2);
                    group.appendChild(a);

                    //append graded students
                    var graded = element.studentiNotati;
                    graded.forEach(element => {
                        var a = document.createElement('a');
                        a.href = '#exampleModalCenter';
                        a.setAttribute('data-toggle', 'modal');
                        a.innerText = `${element.nume} ${element.prenume}`;

                        var table = document.getElementById('tableModel');
                        var username = element.username;
                        a.onclick = () => {
                            var line = 0;
    
                            while(table.firstChild) {
                                table.removeChild(table.firstChild);
                            }
    
                            $.ajax({
                                type: 'POST',
                                url: `${window.location.href}/continut_fisier`,
                                data: {
                                    username: username,
                                    nrExercitiu: parseInt(a.attributes.name.value)
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

                        var li = document.createElement('li');
                        li.appendChild(a);

                        var list = document.getElementById("graded");
                        list.appendChild(li);
                    });

                    //append ungraded students
                    var ungraded = element.studentiNenotati;
                    ungraded.forEach(element => {
                        var a = document.createElement('a');
                        a.href = '#exampleModalCenter';
                        a.setAttribute('data-toggle', 'modal');
                        a.innerText = `${element.nume} ${element.prenume}`;
                        
                        var table = document.getElementById('tableModel');
                        var username = element.username;
                        a.onclick = () => {
                            var line = 0;
    
                            while(table.firstChild) {
                                table.removeChild(table.firstChild);
                            }
    
                            $.ajax({
                                type: 'POST',
                                url: `${window.location.href}/continut_fisier`,
                                data: {
                                    username: username,
                                    nrExercitiu: 1
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

                        var li = document.createElement('li');
                        li.appendChild(a);

                        var list = document.getElementById("ungraded");
                        list.appendChild(li);
                    });
                });
            }
        }
    });

    var btn=document.getElementById("logOutBtn");
    btn.onclick=()=>{
        $.ajax({
            type:"GET",
            url: "/sign-out",
            success: data => {
                if(data==="valid"){
                    window.location.href="/";
                }
            }
        })
    };
});