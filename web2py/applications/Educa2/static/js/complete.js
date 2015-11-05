/**
 * Created by leonardo on 04/11/15.
 */

$('#Palavra').keyup(function(event) {
    var dInput = this.value

    $('.resposta').html( '' );
    if(dInput.length < 1){
        $('.resposta').append('<p class="help-block">Digite a palavra. Depois desmarque as letras que deseja ocultar.</p>')
    }
    for (i = 0; i < dInput.length; i++) {
        $('.resposta').append('<label class="checkbox-inline no_indent"><input type="checkbox" ' +
        'id="Resposta" name="Resposta" checked="true" value="'+dInput[i]+'"> '+dInput[i]+'</label>'); //add input box
    }

});