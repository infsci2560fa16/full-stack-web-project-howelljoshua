
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
    
    firstname = profForm["firstname"].value;
    lastname = profForm["lastname"].value;
    instructiontype = profForm["instructiontype"].value;
    email = profForm["email"].value;
    password = profForm["password"].value;
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
    
    obj = JSON.parse(data);  //used to display the data by field
    
    document.getElementById("profText").innerHTML = 
            "Your profile has been created:" + "<br>" + 
            obj.firstname + " " + obj.lastname + "<br>" +
            obj.instructiontype + "<br>" +
            obj.zip + "<br>" +
            obj.guitartype + "<br>" +
            obj.genre + "<br>" +
            obj.agerange + "<br>" +
            obj.skill + "<br>" +
            obj.focus;  


    $.post( "saveuser", $( "#profForm" ).serialize() );  
    
    console.log(firstname, lastname);
    console.log(data);
}   



function login() {
    loginemail = profForm["loginemail"].value;
    loginpassword = profForm["loginpassword"].value;
    
    $.post( "login", $("#profForm").serialize() );
    
    logindata = JSON.stringify( 
       {"loginemail" : loginemail,
        "loginpassword" : loginpassword});
    
    loginobj = JSON.parse(logindata);
        
    document.getElementById("showtext").innerHTML = 
        "Email / Password:" + "<br>"+
        loginobj.loginemail + " / " + loginobj.loginpassword;
 
    console.log(loginemail, loginpassword);
    console.log(logindata);
    console.log($("#profForm").serialize());
    
}