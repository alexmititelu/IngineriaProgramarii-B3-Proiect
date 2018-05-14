  $('document').ready(function(){

    $('.menu').click(function () {
            $('.responsive-menu').toggleClass('toggle');
    });
    var materiiLen, myList, i;
    var materii =[{
                    numematerie: 'Matematica',
                    teme: [{nota:5, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:9, numeTema: 'Tema3'}]
                  }, 
                  {
                      numematerie: 'IP',
                      teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'IP',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  }
  ];
  materiiLen=materii.length;
  var theList=document.createElement("UL"); var listElement, textNode;
    for(i=0;i<materiiLen;++i){
        listElement=document.createElement("LI");
        textNode=document.createTextNode(materii[i].numematerie);
        listElement.appendChild(textNode);
        theList.appendChild(listElement);
    }
    document.getElementById("lista_materii").appendChild(theList);

    function tableCreate(indexMaterii) {
        var tbl = document.createElement("table");
        tbl.className = "table table-hover table-responsive";
        var tblHead = document.createElement("thead");
        var tblBody = document.createElement("tbody");
        var row = document.createElement("tr");
        var noteLen=materii[indexMaterii].teme.length;
        for (var i = 0; i < noteLen; i++) {
            var cell = document.createElement("td");    
                var cellText = document.createTextNode(materii[indexMaterii].teme[i].numeTema); 

            cell.appendChild(cellText);
            row.appendChild(cell);
        }
        tblHead.appendChild(row);

        row = document.createElement("tr");
        for (var i = 0; i < noteLen; i++) {
            var cell = document.createElement("td");    
                var cellText = document.createTextNode(materii[indexMaterii].teme[i].nota); 

            cell.appendChild(cellText);
            row.appendChild(cell);
        }

      
            tblBody.appendChild(row);
        tbl.appendChild(tblHead);
        tbl.appendChild(tblBody);
        document.getElementById("tabel_note_teme").appendChild(tbl);
        tbl.setAttribute("border", "0");

        tbl.style.display='none';
    }

    for(i=0;i<materiiLen;++i){
        tableCreate(i);
    }

    var currentListEl=document.querySelector('ul');
    currentListEl.addEventListener('click', function(ev){
        if(ev.target.nodeName === 'LI') {
            for(i=0;i<materiiLen;++i){
                if(ev.target.innerHTML === materii[i].numematerie){
                    var selector=document.getElementById("tabel_note_teme");
                    var currentChild=selector.firstElementChild;
                    console.log(currentChild);
                    for(var j=0;j<i;++j){
                        currentChild.style.display='none';
                        currentChild=currentChild.nextSibling;
                        console.log(currentChild);
                    }
                    currentChild.style.display='block';
                    for(var j=i;j<materiiLen-1;++j){
                        currentChild=currentChild.nextSibling;
                        currentChild.style.display='none';
                        console.log(currentChild);
                    }
                }
            }
            
        }
    },
false)
    });