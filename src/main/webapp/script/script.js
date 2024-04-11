function autoTabDetalhamento(input, e)
{
    var ind = 0;
    var isNN = (navigator.appName.indexOf("Netscape")!=-1);
    var keyCode = (isNN) ? e.which : e.keyCode;

    if(keyCode == 13){
        if (!isNN) {
            e.keyCode = 9
        }else{
            e.which = 9
        }
        ind = 1;
        ind++;
    }
}

function autoTab(input, e)
{
    var ind = 0;
    var isNN = (navigator.appName.indexOf("Netscape")!=-1);
    var keyCode = (isNN) ? e.which : e.keyCode;
    
    if(keyCode == 13){
        if (!isNN) {
            window.event.keyCode = 0;
        } // evitar o beep
        ind = getIndex(input);
        ind++;
        
        if (input.form[ind].type == 'textarea') {
            input.form[ind].focus();
            return;
        }else if (input.form[ind].type == "select-one"){
            input.form[ind].focus();
        }else if (input.form[ind + 1].type == "select-one" && input.form[ind].readOnly){
            if (input.form[ind + 1].disabled){
                ind++;
            }
            input.form[ind + 1].focus();
        }else if (input.form[ind].type == 'text') {
            input.form[ind].select();
            input.form[ind].focus();
        }else {
            input.form[ind].focus();
        }
    }
}   

function getIndex(input)
{
    var index = -1, i = 0, found = false;
    while (i < input.form.length && index == -1)
        if (input.form[i] == input) {
            index = i;
            if (i < (input.form.length -1)) {
                if (input.form[i+1].type == 'hidden') {
                    index++;
                }
                if (input.form[i+1].type == 'button' && input.form[i+1].id == 'tabstopfalse') {
                    index++;
                }
            }
        }
        else
            i++;
    return index;
}


function codigoBarra(codigoBarra,campo, e)
{
    var codigo = form[codigoBarra].value;
    var isNN = (navigator.appName.indexOf("Netscape")!=-1);
    var keyCode = (isNN) ? e.which : e.keyCode;
    var nKeyCode = e.keyCode;
    var tam = codigo.length;

    if(keyCode == 8 || keyCode == 9 || keyCode == 46 || (keyCode>=37 && keyCode<=40)){
        return true;
    }
    //if(keyCode != 13 && (keyCode <48 || keyCode>57) && (keyCode < 96 || keyCode > 105 )){
    //    if(keyCode != 13){
    //        return false;
    //    }
    if(keyCode == 13 && tam < 13 && codigo != 0){
        return false;
    }
    if(tam==14){
        form[codigoBarra].focus();
        fireElement(campo);
        //executarFuncaoCodigoBarra(campo);
        form[codigoBarra].value = "";
    }

    form[codigoBarra].focus();
    if(keyCode == 13 && codigo == 0){
        return true;
    }
    if(keyCode == 13){
        return false;
    }
    

}

function zerarCodigoBarra(codigoBarra)
{
    form[codigoBarra].value = "";
    form[codigoBarra].focus();
}


function fireElement(objID)
{
    var target=document.getElementById(objID);
    if (document.dispatchEvent) { // W3C
        var oEvent = document.createEvent( "MouseEvents" );
        oEvent.initMouseEvent("click", true, true,window, 1, 1, 1, 1, 1, false, false, false, false, 0, target);
        target.dispatchEvent( oEvent );
    } else {
        if(document.fireEvent) { // IE
            target.fireEvent("onclick");
        }
    }
}
  

function fecharJanelaDetalhamento()
{
    var commandLinkHidden = window.document.getElementById("form:submeterItemCarregamentoVO");
    if (commandLinkHidden) {
        commandLinkHidden.fireEvent("onclick");
    }
    window.close();
}

function liberarBackingBeanMemoria()
{
    var hiddenCommandLink = window.document.getElementById("formCadastro:idLiberarBackingBeanMemoria");
    if (hiddenCommandLink) {
        document.getElementById('formCadastro:idLiberarBackingBeanMemoria').click();
    } else {
        hiddenCommandLink = window.document.getElementById("form:idLiberarBackingBeanMemoria");
        if (hiddenCommandLink) {
            window.document.getElementById("form").target = "";
            window.document.getElementById('form:idLiberarBackingBeanMemoria').click();
        } 
    }
}

function ativarBotaoLimparDados(e){
    window.close();
//location.reload(true);
}


function movimentarComSetas(e)
{
    var keyCode = e.keyCode;

    if(e.ctrlKey && keyCode == 40){
        document.getElementById('form:moverBaixo').click();
        return false;
    }
    if(e.ctrlKey && keyCode == 38){
        document.getElementById('form:moverAcima').click();
        return false;
    }
    if(e.ctrlKey && keyCode == 13){
        document.getElementById('form:abrirModalItemCarregamento').click();
        return false;
    }

    if(keyCode == 119){
        document.getElementById('form:extornarCaixa').click();
        return false;
    }

    if(keyCode == 117){
        document.getElementById('form:abrirModalDescontoPreco').click();
        return false;
    }
 

    var keychar;
    // Internet Explorer
    try {
        keychar = String.fromCharCode(event.keyCode);
        e = event;
    }
    // Firefox, Opera, Chrome, etc...
    catch(err) {
        keychar = String.fromCharCode(e.keyCode);
    }
    if (e.ctrlKey && keychar == '1') {
        document.getElementById('form:mudarAba1').click();
        return false;
    }
    if (e.ctrlKey && keychar == '2') {
        document.getElementById('form:mudarAba2').click();
        return false;
    }
    if (e.ctrlKey && keychar == '3') {
        document.getElementById('form:mudarAba3').click();
        return false;
    }
    if (e.ctrlKey && keychar == '4') {
        document.getElementById('form:mudarAba4').click();
        return false;
    }

}

function eventosTeclas(e)
{
    var keyCode = e.keyCode;

    if(e.ctrlKey && keyCode == 40){
        document.getElementById('form:moverBaixo').click();
        return false;
    }
    if(e.ctrlKey && keyCode == 38){
        document.getElementById('form:moverAcima').click();
        return false;
    }

    if (keyCode == 119) {
        document.getElementById('form:linkAbrirLeituraCarcaca').click();
        return false;
    }
    if (keyCode == 120) {
        document.getElementById('form:linkAbrirDetalhamento').click();
        return false;
    }
    if(keyCode == 121){
        document.getElementById('form:linkAbrirModalLeitura').click();
        return false;
    }
    
}

function fecharModal(panel, e)
{
    var keyCode = e.keyCode;

    if(keyCode == 27){
        Richfaces.hideModalPanel(panel);
        return false;
    }
}
function fecharModalDetalhamento(panel, e)
{
    var keyCode = e.keyCode;

    if(keyCode == 27){
        Richfaces.hideModalPanel(panel);
        document.getElementById('form:redesenharForm').click();
        document.getElementById("form:valorCarga").focus();
        return false;
    }
}

function SomenteNumeros(input)
{
    if ((event.keyCode<48)||(event.keyCode>57))
        event.returnValue = false;
}
//-------------------------------

function FormataValor(campo,tammax,teclapres) {

    var tecla = teclapres.keyCode;
    var vr = campo.value;
    vr = vr.replace( "/", "" );
    vr = vr.replace( "/", "" );
    vr = vr.replace( ",", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    tam = vr.length;

    if (tam < tammax && tecla != 8){
        tam = vr.length + 1 ;
    }

    if (tecla == 8 ){
        tam = tam - 1 ;
    }
		
    if ( tecla == 8 || (tecla >= 48 && tecla <= 57) || (tecla >= 96 && tecla <= 105) ){
        if ( tam <= 2 ){
            campo.value = vr ;
        }
        tam = tam - 1;
        if ( (tam > 2) && (tam <= 5) ){
            campo.value = vr.substr( 0, tam - 2 ) + ',' + vr.substr( tam - 2, tam ) ;
        }
        if ( (tam >= 6) && (tam <= 8) ){
            campo.value = vr.substr( 0, tam - 5 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;
        }
        if ( (tam >= 9) && (tam <= 11) ){
            campo.value = vr.substr( 0, tam - 8 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;
        }
        if ( (tam >= 12) && (tam <= 14) ){
            campo.value = vr.substr( 0, tam - 11 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;
        }
        if ( (tam >= 15) && (tam <= 17) ){
            campo.value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;
        }
    }
}
function FormataValorQuatroCasas(campo,tammax,teclapres) {

    var tecla = teclapres.keyCode;
    var vr = campo.value;
    vr = vr.replace( "/", "" );
    vr = vr.replace( "/", "" );
    vr = vr.replace( ",", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    tam = vr.length;

    if (tam < tammax && tecla != 8){ 
        tam = vr.length + 1 ;
    }

    if (tecla == 8 ){	
        tam = tam - 1 ;
    }

    if ( tecla == 8 || (tecla >= 48 && tecla <= 57) || (tecla >= 96 && tecla <= 105) ){
        if ( tam <= 4 ){
            campo.value = vr ;
        }
        tam = tam - 1;
        if ( (tam > 4) && (tam <= 7) ){
            campo.value = vr.substr( 0, tam - 4 ) + ',' + vr.substr( tam - 4, tam ) ;
        }
        if ( (tam >= 8) && (tam <= 10) ){
            campo.value = vr.substr( 0, tam - 7 ) + '.' + vr.substr( tam - 7, 3) + ',' + vr.substr( tam - 4, tam ) ;
        }
        if ( (tam >= 11) && (tam <= 13) ){
            campo.value = vr.substr( 0, tam - 10 ) + '.' + vr.substr( tam - 10, 3 ) + '.' + vr.substr( tam - 7, 3 ) + ',' + vr.substr( tam - 4, tam ) ;
        }
        if ( (tam >= 14) && (tam <= 16) ){
            campo.value = vr.substr( 0, tam - 13 ) + '.' + vr.substr( tam - 13, 3 ) + '.' + vr.substr( tam - 10, 3 ) + '.' + vr.substr( tam - 7, 3 ) + ',' + vr.substr( tam - 4, tam ) ;
        }
        if ( (tam >= 17) && (tam <= 19) ){
            campo.value = vr.substr( 0, tam - 16 ) + '.' + vr.substr( tam - 16, 3 ) + '.' + vr.substr( tam - 13, 3 ) + '.' + vr.substr( tam - 10, 3 ) + '.' + vr.substr( tam - 7, 3 ) + ',' + vr.substr( tam - 4, tam ) ;
        }
    }
}


function FormataValorTresCasas(campo,tammax,teclapres) {

    var tecla = teclapres.keyCode;
    var vr = campo.value;
    vr = vr.replace( "/", "" );
    vr = vr.replace( "/", "" );
    vr = vr.replace( ",", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    tam = vr.length;

    if (tam < tammax && tecla != 8){
        tam = vr.length + 1 ;
    }

    if (tecla == 8 ){
        tam = tam - 1 ;
    }

    if ( tecla == 8 || (tecla >= 48 && tecla <= 57) || (tecla >= 96 && tecla <= 105) ){
        if ( tam <= 3 ){
            campo.value = vr ;
        }
        tam = tam - 1;
        if ( (tam > 3) && (tam <= 6) ){
            campo.value = vr.substr( 0, tam - 3 ) + ',' + vr.substr( tam - 3, tam ) ;
        }
        if ( (tam >= 7) && (tam <= 9) ){
            campo.value = vr.substr( 0, tam - 6 ) + '.' + vr.substr( tam - 6, 3) + ',' + vr.substr( tam - 3, tam ) ;
        }
        if ( (tam >= 10) && (tam <= 12) ){
            campo.value = vr.substr( 0, tam - 9 ) + '.' + vr.substr( tam - 9, 3 ) + '.' + vr.substr( tam - 6, 3 ) + ',' + vr.substr( tam - 3, tam ) ;
        }
        if ( (tam >= 13) && (tam <= 15) ){
            campo.value = vr.substr( 0, tam - 12 ) + '.' + vr.substr( tam - 12, 3 ) + '.' + vr.substr( tam - 9, 3 ) + '.' + vr.substr( tam - 6, 3 ) + ',' + vr.substr( tam - 3, tam ) ;
        }
        if ( (tam >= 16) && (tam <= 18) ){
            campo.value = vr.substr( 0, tam - 15 ) + '.' + vr.substr( tam - 15, 3 ) + '.' + vr.substr( tam - 12, 3 ) + '.' + vr.substr( tam - 9, 3 ) + '.' + vr.substr( tam - 6, 3 ) + ',' + vr.substr( tam - 3, tam ) ;
        }
    }
}

function windowUnloadedAtalho(event) {
    //ALT + F4
    if ((event.altKey) && (event.keyCode == 115)) {
        liberarBackingBeanMemoria();
    }
    //CRTL + W
    if ((event.ctrlKey) && (event.keyCode == 87)) {
        liberarBackingBeanMemoria();
    }
}

function windowUnloaded(event) {
    if (window.event.clientY < 0) {
        liberarBackingBeanMemoria();
    }
}

function setFocus(form, campo) {
    form[campo].focus();
}

function testarCodigofecharJanela(form) {
    var codigo = form["form:codigo"].value;
    if (codigo != 0) {
        window.close();
    }
}

function abrirPopup(URL, nomeJanela, comprimento, altura) {
    alert(URL + '-' + nomeJanela);
    var posTopo = ((screen.height / 2) - (altura / 2));
    var posEsquerda = ((screen.width / 2) -(comprimento / 2));
    var atributos = 'left=' + posEsquerda + ', screenX=' + posEsquerda + ', top=' + posTopo + ', screenY=' + posTopo + ', width=' + comprimento + ", height=" + altura + ' dependent=yes, menubar=no, toolbar=no, resizable=yes , scrollbars=yes' ;
    window.open(URL,nomeJanela,atributos);
    return false;
}
function abrirPopupMaximizada(URL, nomeJanela) {
    var comprimento = screen.width;
    var altura = screen.height;
    var atributos = 'width=' + comprimento + ", height=" + altura + ', dependent=yes, menubar=no, toolbar=no, resizable=yes , scrollbars=yes' ;
    window.open(URL,nomeJanela,atributos);

    return false;
}

function alterarPopupParaTelaCheia(URL, nomeJanela) {
    var comprimento = screen.width;
    var altura = screen.height;
    window.moveTo(0,0);
    window.resizeTo(comprimento,altura);
    return false;
}

function abrirPopupSemScrolBar(URL, nomeJanela, comprimento, altura) {
    var posTopo = ((screen.height / 2) - (altura / 2));
    var posEsquerda = ((screen.width / 2) -(comprimento / 2));
    var atributos = 'left=' + posEsquerda + ', screenX=' + posEsquerda + ', top=' + posTopo + ', screenY=' + posTopo + ', width=' + comprimento + ", height=" + altura + ' dependent=yes, menubar=no, toolbar=no, resizable=yes , scrollbars=yes' ;
    window.open(URL,nomeJanela,atributos);
    return false;
}

function fecharJanela() {
    window.close();
}

function Tecla(e) {
    TeclasAtalho(e);
    if (document.all) // Internet Explorer
        var tecla = event.keyCode;
    else if(document.layers) // Nestcape
        var tecla = e.which;
    if (tecla > 47 && tecla < 58) // numeros de 0 a 9
        return true;
    else {
        if (tecla != 8) { // backspace
            event.keyCode = 0;
        } else {
            return true;
        }
    }
}

function SomenteLetra(e) {
    TeclasAtalho(e);
    if (document.all) {
        // Internet Explorer
        var tecla = event.keyCode;
    } else if(document.layers) {
        // Nestcape
        var tecla = e.which;
    }
    if (tecla > 47 && tecla < 58) {
        // numeros de 0 a 9
        return false;
    } else{
        return true;
    }
}

function SomenteLetraABCDE(e) {
    TeclasAtalho(e);
    if (document.all) {
        // Internet Explorer
        var tecla = event.keyCode;
    }else if(document.layers) {
        // Nestcape
        var tecla = e.which;
    }
    if (tecla > 47 && tecla < 58) {
        // numeros de 0 a 9
        return false;
    } else{
        if (tecla > 96 && tecla < 102) {
            return true;
        }
        else if (tecla > 64 && tecla < 70) {
            return true;
        } else {
            return false;
        }
    }
}

// Se o valor selecionado na combo for NÚMERO, não deixar o USUÁRIO digitar letras
function TeclaSelectApenasNumeros(e,form,nomeForm,nomeValorCombo){
    var valorSelecionadoCombo = form[form.id + nomeForm].value;
    if(valorSelecionadoCombo == nomeValorCombo){
        Tecla(e);
    }
}
    
function TeclasAtalho(e) {
    if (e.shiftKey) {
        if (e.keyCode == 43) {
            event.keyCode = 0;
            document.forms[0].incluir.click();
        } else if (e.keyCode == 45) {
            event.keyCode = 0;
            document.forms[0].excluir.click();
        } else if (e.keyCode == 46) {
            event.keyCode = 0;
            document.forms[0].gravar.click();
        } else if (e.keyCode == 42) {
            event.keyCode = 0;
            document.forms[0].consultar.click();
        }
    }
}

function getSelText() {
    var txt = '';
    if (window.getSelection) {
        txt = window.getSelection();
    } else if (document.getSelection) {
        txt = document.getSelection();
    } else if (document.selection) {
        txt = document.selection.createRange().text;
    } else {
        return;
    }
    return txt;
}

//onkeypress="return mascara(document.rcfDownload, 'str_cep', '99999-999', event);">
function mascara(objForm, strField, sMask, evtKeyPress) {
    //    alert('objForm = ' + objForm);
    //    alert('strField = ' + strField);
    //    alert('sMask = ' + sMask);
    //    alert('evtKeyPress = ' + evtKeyPress);
    var i, nCount, sValue, fldLen, mskLen,bolMask, sCod, nTecla;
    var textoSel = getSelText();
    //    alert('akiiiiiiiii 1111');
    if (textoSel != '') {
        //        alert('aki 1');
        return true;
    }
    TeclasAtalho(evtKeyPress);
    //    alert('akiiiiiiiii 2222');
    if(document.all) { // Internet Explorer
        nTecla = evtKeyPress.keyCode;
    }else if(document.layers) { // Nestcape
        nTecla = evtKeyPress.which;
    } else {
        nTecla = evtKeyPress.which;
        if (nTecla == 8) {
            //            alert('aki 2');
            return true;
        }
    }
    //    alert('akiiiiiiiii 3333');
    //    alert('objForm[strField].value = ' + objForm[strField].value);
    sValue = objForm[strField].value;
    //    alert('valor = ' + sValue);
    
    // Limpa todos os caracteres de formatação que
    // já estiverem no campo.
    sValue = sValue.toString().replace( "-", "" );
    sValue = sValue.toString().replace( "-", "" );
    sValue = sValue.toString().replace( ".", "" );
    sValue = sValue.toString().replace( ".", "" );
    sValue = sValue.toString().replace( "/", "" );
    sValue = sValue.toString().replace( "/", "" );
    sValue = sValue.toString().replace( "(", "" );
    sValue = sValue.toString().replace( "(", "" );
    sValue = sValue.toString().replace( ")", "" );
    sValue = sValue.toString().replace( ")", "" );
    sValue = sValue.toString().replace( ":", "" );
    sValue = sValue.toString().replace( ":", "" );
    sValue = sValue.toString().replace( " ", "" );
    sValue = sValue.toString().replace( " ", "" );
    fldLen = sValue.length;
    mskLen = sMask.length;
	
    i = 0;
    nCount = 0;
    sCod = "";
    mskLen = fldLen;

    while (i <= mskLen) {
        bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ":") || (sMask.charAt(i) == ".") || (sMask.charAt(i) == "/"))
        bolMask = bolMask || ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")") || (sMask.charAt(i) == " "))

        if (bolMask) {
            sCod += sMask.charAt(i);
            mskLen++;
        }else {
            sCod += sValue.charAt(nCount);
            nCount++;
        }
        i++;
    }

    if (sMask.length == sCod.length) {
        event.keyCode = 0;
    }
    objForm[strField].value = sCod;
    if (nTecla != 8) { // backspace
        if (sMask.charAt(i-1) == "9") { // apenas NÚMEROs...
            return ((nTecla > 47) && (nTecla < 58));
        } // NÚMEROs de 0 a 9
        else { // qualquer caracter...
            return true;
        }
    }else {
        return true;
    }
}

//* Observação: As mêscaras podem ser representadas como os exemplos abaixo:
//* CEP -> 99.999-999
//* CPF -> 999.999.999-99
//* CNPJ -> 99.999.999/9999-99
//* Data -> 99/99/9999
//* Tel Resid -> (99) 999-9999
//* Tel Cel -> (99) 9999-9999
//* Processo -> 99.999999999/999-99
//* C/C -> 999999-!
//* Hora -> 99:99:99 
//* Placa -> xxx - 9999
//***/



function mascaraTodos(objForm, strField, sMask, evtKeyPress) {
    var i, nCount, sValue, fldLen, mskLen,bolMask, sCod, nTecla;
    nTecla = (evtKeyPress.which) ? evtKeyPress.which : evtKeyPress.keyCode;
    sValue = objForm[strField].value;
    // Limpa todos os caracteres de formatação que
    // já estiverem no campo.
    expressao = /[\.\/\-\(\)\,\;\: ]/gi;
    sValue = sValue.toString().replace(expressao, '');
    fldLen = sValue.length;
    mskLen = sMask.length;
    
    i = 0;
    nCount = 0;
    sCod = "";
    mskLen = fldLen;

    while (i <= mskLen) {
        bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask.charAt(i) == "/") || (sMask.charAt(i) == ",") || (sMask.charAt(i) == ";") || (sMask.charAt(i) == ":"))
        bolMask = bolMask || ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")") || (sMask.charAt(i) == " "))

        if (bolMask) {
            sCod += sMask.charAt(i);
            mskLen++;
        }
        else {
            sCod += sValue.charAt(nCount);
            nCount++;
        }

        i++;
    }

    objForm[strField].value = sCod;

    if (nTecla != 8 && nTecla != 13)
    { // backspace enter
        if (sMask.charAt(i-1) == "9")
        { // apenas NÚMEROs...
            return ((nTecla > 47) && (nTecla < 58));
        } // NÚMEROs de 0 a 9
        else
        {
            if (sMask.charAt(i-1) == "x")
            { // apenas letras... Sem espaco
                return ((nTecla > 64) && (nTecla < 123));
            } // maiusculas e minusculas de A a z sem acentos
            else
            { // qualquer caracter...
                return true;
            }
        }
    }
    else
    {
        return true;
    }
}

function mascaraCNPJ(objForm, strField, sMask, evtKeyPress) {
    var i, nCount, sValue, fldLen, mskLen,bolMask, sCod, nTecla;
    nTecla = (evtKeyPress.which) ? evtKeyPress.which : evtKeyPress.keyCode;
    sValue = objForm[strField].value;
    // Limpa todos os caracteres de formatação que
    // já estiverem no campo.
    expressao = /[\.\/\-\(\)\,\;\: ]/gi;
    sValue = sValue.toString().replace(expressao, '');
    fldLen = sValue.length;
    mskLen = sMask.length;
    if (fldLen > 13){
        return false;
    }
    i = 0;
    nCount = 0;
    sCod = "";
    mskLen = fldLen;

    while (i <= mskLen) {
        bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask.charAt(i) == "/") || (sMask.charAt(i) == ",") || (sMask.charAt(i) == ";") || (sMask.charAt(i) == ":"))
        bolMask = bolMask || ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")") || (sMask.charAt(i) == " "))

        if (bolMask) {
            sCod += sMask.charAt(i);
            mskLen++;
        }
        else {
            sCod += sValue.charAt(nCount);
            nCount++;
        }

        i++;
    }

    objForm[strField].value = sCod;

    if (nTecla != 8 && nTecla != 13)
    { // backspace enter
        if (sMask.charAt(i-1) == "9")
        { // apenas NÚMEROs...
            return ((nTecla > 47) && (nTecla < 58));
        } // NÚMEROs de 0 a 9
        else
        {
            if (sMask.charAt(i-1) == "x")
            { // apenas letras... Sem espaco
                return ((nTecla > 64) && (nTecla < 123));
            } // maiusculas e minusculas de A a z sem acentos
            else
            { // qualquer caracter...
                return true;
            }
        }
    }
    else
    {
        return true;
    }
}

function mascaraCPF(objForm, strField, sMask, evtKeyPress) {
    var i, nCount, sValue, fldLen, mskLen,bolMask, sCod, nTecla;
    nTecla = (evtKeyPress.which) ? evtKeyPress.which : evtKeyPress.keyCode;
    sValue = objForm[strField].value;
    // Limpa todos os caracteres de formatação que
    // já estiverem no campo.
    expressao = /[\.\/\-\(\)\,\;\: ]/gi;
    sValue = sValue.toString().replace(expressao, '');
    fldLen = sValue.length;
    mskLen = sMask.length;
    if (fldLen > 10){
        return false;
    }
    i = 0;
    nCount = 0;
    sCod = "";
    mskLen = fldLen;

    while (i <= mskLen) {
        bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask.charAt(i) == "/") || (sMask.charAt(i) == ",") || (sMask.charAt(i) == ";") || (sMask.charAt(i) == ":"))
        bolMask = bolMask || ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")") || (sMask.charAt(i) == " "))

        if (bolMask) {
            sCod += sMask.charAt(i);
            mskLen++;
        }
        else {
            sCod += sValue.charAt(nCount);
            nCount++;
        }

        i++;
    }

    objForm[strField].value = sCod;

    if (nTecla != 8 && nTecla != 13)
    { // backspace enter
        if (sMask.charAt(i-1) == "9")
        { // apenas NÚMEROs...
            return ((nTecla > 47) && (nTecla < 58));
        } // NÚMEROs de 0 a 9
        else
        {
            if (sMask.charAt(i-1) == "x")
            { // apenas letras... Sem espaco
                return ((nTecla > 64) && (nTecla < 123));
            } // maiusculas e minusculas de A a z sem acentos
            else
            { // qualquer caracter...
                return true;
            }
        }
    }
    else
    {
        return true;
    }
}


function abrirPopup(URL, nomeJanela, comprimento, altura) {
    var posTopo = ((screen.height / 2) - (altura / 2));
    var posEsquerda = ((screen.width / 2) -(comprimento / 2));
    var atributos = 'left=' + posEsquerda + ', screenX=' + posEsquerda + ', top=' + posTopo + ', screenY=' + posTopo + ', width=' + comprimento + ", height=" + altura + ' dependent=yes, menubar=no, toolbar=no, resizable=yes , scrollbars=yes' ;
    window.open(URL,nomeJanela,atributos);
    return false;
}

function abrirPopupMaximizada(url,janela) {
    var params = 'directories=no';
    params += ', location=no';
    params += ', menubar=no';
    params += ', resizable=false';
    params += ', scrollbars=yes';
    params += ', status=no';
    params += ', toolbar=no';
    params += ', maximize=yes';
    params += ', width='+screen.width;
    params += ', height='+screen.height;
    params += ', width='+screen.width;
    params += ', height='+screen.height;
    newwin=window.open(url,janela, params);
}

function formatarDuasCasasDecimais(valor){
    var valorTemp = valor;
    if(valorTemp == null || valorTemp == "" || valorTemp == "0" || valorTemp == "0.0" || valorTemp == "0.00" || valorTemp == "0,0"){
        valorTemp = "0,00";
    }else{
        valorTemp = new String(valorTemp.replace('.', ',').replace(',0', ',00').replace(',000', ',00'));
    }
    return valorTemp;
}

function verificarEnter(form, event) {
    if (event.keyCode == 13) {
        document.getElementById("form:leitura").focus();
        return false;
    }
    return true;
}

function setarFocoTelaPesagemAbate(e) {
    if(e.keyCode == 13){
        document.getElementById("form:adicionarPeso").focus();
        return false;
    }
    return true;
}

function setarFocoPesagemBalancaProducao(e) {
    if(e.keyCode == 13){
        document.getElementById("form:gravarEtiqueta").focus();
    }
}

function setarFocoCampoPesoBruto(e) {
    if(e.keyCode == 13) {
        document.getElementById("form:pesoBruto").focus();
        document.getElementById("form:pesoBruto").select();
        return false;
    }
    return true;
}

function checa_seguranca(pass, campo){
    var senha = document.getElementById(pass).value;
    var entrada = 0;
    var resultadoado;
    if(senha.length < 7){
        entrada = entrada - 1;
    }
    if(!senha.match(/[a-z_]/i) || !senha.match(/[0-9]/)){
        entrada = entrada - 1;
    }
    if(!senha.match(/\W/)){
        entrada = entrada - 1;
    }
    if(entrada == 0){
        resultado = 'Segurança: <font color=\'#99C55D\'>EXCELENTE</font>';
    } else if(entrada == -1){
        resultado = 'Segurança: <font color=\'#7F7FFF\'>BOM</font>';
    } else if(entrada == -2){
        resultado = 'Segurança: <font color=\'#FF5F55\'>BAIXA</font>';
    } else if(entrada == -3){
        resultado = 'Segurança: <font color=\'#A04040\'>MUITO BAIXA</font>';
    }
    document.getElementById(campo).innerHTML = resultado;
    return;
}
