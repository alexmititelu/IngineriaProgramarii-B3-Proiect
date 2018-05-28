var serverMessage=["[404]: Pagina cautata nu a putut fi gasita..."];

$(document).ready(function () {

    var errorMsg=document.getElementById('serverMsg');  
    errorMsg.innerText=serverMessage[0];
});