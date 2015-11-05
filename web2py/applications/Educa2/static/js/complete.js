/**
 * Created by leonardo on 04/11/15.
 */

$('#Palavra').keyup(function(event) {
    var dInput = this.value;
    //var dInput = event.keyCode;

    console.log(dInput);
     $('.resposta').append('<label class="checkbox-inline no_indent"><input type="checkbox" ' +
     'id="'+dInput+'" value="'+dInput+'"> '+dInput+'</label>'); //add input box
});