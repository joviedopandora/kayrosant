/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(main);
function main() {
    $("#btn-producciones").click(function () {
        //alert("Producciones called.");
        
        $("#login").removeClass("login");
        $("#login").removeClass("loginKids");
         $("#login").addClass("loginProducciones");
    });

    $("#btn-kids").click(function () {
        $("#login").removeClass("login");
        $("#login").removeClass("loginProducciones");
        $("#login").addClass("loginKids");
       
       // alert("KIDS called.");
    });
}



