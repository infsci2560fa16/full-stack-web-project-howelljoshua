      /* global profForm */

//When user clicks on button, toggles visible/invisible  
  function chooserBtn(){
    document.getElementById("beginDropdown").classList.toggle("show");


      //When user clicks away from dropdown menu, close menu
    window.onclick = function(event) {
      if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
          var openDropdown = dropdowns[i];
          if (openDropdown.classList.contains('show')) {
            openDropdown.classList.remove('show');
          }
        }
      }
    };
}


    //called by Save button on Profile form, parses values from fields, makes JSON obj...
    //Sends to REST svc, passed to JDBC
function save() {
    //$.post("saveuser", $("#profFrom").serialize() ); UNUSED 
    //profForm = document.getElementsById("profForm"); UNUSED
    
    firstname = profForm["firstname"].value;
    lastname = profForm["lastname"].value;
    instructiontype = profForm["instructiontype"].value;
    zip = profForm["zip"].value;
    guitartype = profForm["guitartype"].value;
    genre = profForm["genre"].value;
    agerange = profForm["agerange"].value;   
    skill = profForm["skill"].value;        
    focus = profForm["focus"].value; 
    
       
    data = JSON.stringify(
       {"firstname":firstname,
        "lastname":lastname,
        "instructiontype":instructiontype,
        "zip":zip,
        "guitartype":guitartype,
        "genre":genre,
        "agerange":agerange,
        "skill":skill,
        "focus":focus}        
    );
    
    //obj = JSON.parse(data);  //WAS USED TO DISPLAY THE DATA
    $.post("/saveuser",data);  //
    
    
/*
    $.ajax({
        url: '/saveuser',
        type: 'POST',
        data: data,
        processData: false
}   );  
*/


    document.getElementById("profText").innerHTML = "Sent to database";
            //obj.zip + "<br>" +    
            //obj.firstname + "<br>" +
            //obj.lastname + "<br>" +
            //obj.instructiontype + "<br>" +
            //obj.guitartype + "<br>" +
            //obj.genre + "<br>" +
            //obj.agerange + "<br>" +
            //obj.skill + "<br>" +
            //obj.focus;

    
   
    }    