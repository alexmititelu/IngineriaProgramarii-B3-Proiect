$(document).ready(function(){

	var submitButton=document.getElementById('button');

	/*console.log(submitButton);*/

	submitButton.onclick = function(event){

		event.preventDefault();

		/*console.log(event);*/

		var getFileName=document.getElementById('imageFile').value;

		console.log(getFileName);

		var extension=getFileName.split('.');
		console.log(extension);
	
		var trueExtension=extension[extension.length-1];

		console.log(trueExtension);

		var errorParagraph=document.getElementById('error');
		errorParagraph.style.color='red';

		if(trueExtension!=="zip")
		{
			errorParagraph.innerText='Fisierul incarcat nu este .zip!';
		}
		else
		{
			errorParagraph.innerText='';

			var response={

				'imageName':getFileName
			};

			$.ajax({
				type:'POST',
				url:'/validate_zip',
				data:response,
				success:function(data){

					if(data==='eroare'){

						errorParagraph.innerText='Serverul a intampinat o eroare!<br>Te rog verifica din nou extenisa fisierului';
					}

					if(data==='success'){

						errorParagraph.innerText='Fisierul a fost incarcat cu succes';
						setTimeout(function(){

							window.location.href='/a.html';
						},1999)
					}
				},
				error: function(xhr){
        alert('Request Status: ' + xhr.status + ' Status Text: ' + xhr.statusText + ' ' + xhr.responseText);
    }
			});
		}




	};


});