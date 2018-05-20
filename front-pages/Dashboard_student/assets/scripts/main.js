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
                      numematerie: 'Ingineria Programarii',
                      teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  },
                  {
                    numematerie: 'Fundamente algebrice ale informaticii',
                    teme: [{nota:8, numeTema: 'Tema1'},{nota:7, numeTema: 'Tema2'},{nota:10, numeTema: 'Tema3'}]
                  }/*,
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
                  }*/
  ];


  function createAccordion(tipTema, i, myAccordion, idAccordionList){

    var indexul=i+1;
    var headOfPanel=document.createElement("div");
    headOfPanel.className="panel-heading"; headOfPanel.setAttribute("role", "tab"); headOfPanel.id=tipTema+materii[i].numematerie+indexul;
    var panelTitle=document.createElement("h4"); panelTitle.className="panel-title";
    var panelLink=document.createElement("a"); panelLink.className="collapsed list-group-item list-group-item-action flex-column align-items-start";
    panelLink.setAttribute("data-toggle","collapse");
    panelLink.setAttribute("aria-expanded","false");
    panelLink.setAttribute("aria-controls",tipTema+materii[i].numematerie+"collapse"+indexul); panelLink.href="#"+tipTema+materii[i].numematerie+"collapse"+indexul;
    var textItem1=document.createTextNode(materii[i].numematerie); textItem1.id="numeMat"; panelLink.appendChild(textItem1);
    var imageContent=document.createElement("i"); imageContent.className="rotatingImg fa fa-arrow-down js-rotate-if-collapsed";
    /*var imageItem=document.createElement("img"); imageItem.src="assets/images/downArrow.jpg"; imageItem.className="rotatingImg";
    imageContent.appendChild(imageItem);*/
    panelLink.appendChild(imageContent);
    panelTitle.appendChild(panelLink); headOfPanel.appendChild(panelTitle); myAccordion.appendChild(headOfPanel);
    var panelBody=document.createElement("div");
    panelBody.setAttribute("data-parent","#"+idAccordionList);
    panelBody.id=tipTema+materii[i].numematerie+"collapse"+indexul;
    panelBody.className="panel-collapse collapse"; panelBody.setAttribute("role", "tabpanel");
    panelBody.setAttribute("aria-labelledby",tipTema+materii[i].numematerie+indexul); 
    panelBody.setAttribute("aria-expanded","true");
    var homeworkList=document.createElement("ul"); homeworkList.className="list-group";
    var temeLen=materii[i].teme.length;
    
    for(var j=0;j<temeLen;++j){
        var listItem=document.createElement("li"); listItem.className="list-group-item";
        var textItem=document.createTextNode(materii[i].teme[j].numeTema);
        listItem.appendChild(textItem);
        homeworkList.appendChild(listItem);
    }
    panelBody.appendChild(homeworkList);
    myAccordion.appendChild(panelBody);
    return myAccordion;
  }

    materiiLen=materii.length;

    function createAccordionList(tipTema, indexOfHomework){
    //declarari pentru lista acordeoane
    var accordionList=document.createElement("div");
    accordionList.className="panel-group";
    accordionList.id="accordion"+indexOfHomework;
    accordionList.setAttribute("role", "tablist");
    accordionList.setAttribute("aria-multiselectable","true");
    //declarari pentru acordeon propriu-zis
    //creare acordeoane
    for(var i=0;i<materiiLen;++i){
        var myAccordion=document.createElement("div");
        myAccordion.className="panel panel-default";
        createAccordion(tipTema,i, myAccordion, accordionList.id);
        accordionList.appendChild(myAccordion);
    }


    document.getElementById(tipTema).appendChild(accordionList);

  }

  createAccordionList("temeNetrimise",1);
  createAccordionList("temeNecorectate",2);
  createAccordionList("temeCorectate",3);



    });