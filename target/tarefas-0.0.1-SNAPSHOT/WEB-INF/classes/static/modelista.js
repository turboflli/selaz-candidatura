const SENHA_INPUT_ID = "senha_input"
const SEGUNDA_SENHA_INPUT_ID = "senha_input2"

var modelistaModule = (function() {

    var modelistaModule = function () {};

    modelistaModule.prototype = {

        limpar : () => {
            if(confirm("Isso apagará os dados preenchidos, \nTem certeza?")) {
                var input = document.getElementById(SEGUNDA_SENHA_INPUT_ID);
                input.value = "";
                base.clearFormData();
            }
        },

        validarSenha : (input, segundoInputId) => {
            const segundoInput= document.getElementById(segundoInputId);
            if (segundoInput.value == input.value) {
                input.valid = true;
                segundoInput.valid = true;
            } else {
                input.valid = false;
                segundoInput.valid = false;
            }
        },

        save : () => {
            const input= document.getElementById(SENHA_INPUT_ID);
            const segundoInput= document.getElementById(SEGUNDA_SENHA_INPUT_ID);
            if (input.value === segundoInput.value) {
                var dataPost = base.getFormData();
                var dataText = JSON.stringify(dataPost);
                var chave = document.getElementById(CHAVE_INPUT_ID).value;
                var chaveArray = CryptoJS.enc.Hex.parse(chave);
                var iv   = document.getElementById(IV_INPUT_ID).value
                var ivArray   = CryptoJS.enc.Hex.parse(iv);
                dataText = {"modelista":CryptoJS.enc.Base64.stringify(CryptoJS.AES.encrypt(dataText, chaveArray , {
                    iv:ivArray}).ciphertext)};
                if (dataPost != null) {
                    $.ajax({
                        method: "POST",
                        url: "../rest/saveModelista",
                        data:JSON.stringify(dataText),
                        dataType : "json",
                        contentType: "application/json; charset=utf-8",
                        success: function(data) {
                            alert(data.responseText);
                            window.location.href = "./"
                        },
                        error:  function(data) {
                            if(data.status == 200) {
                                alert(data.responseText);
                                window.location.href = "./"
                            }
                        },
                    })
                }
            } else {
                alert("Confirme a senha! \nOs campos de senha devem ser idênticos");
            }


        },

        alterarSenha : () => {
            const input= document.getElementById(SENHA_INPUT_ID);
            const segundoInput= document.getElementById(SEGUNDA_SENHA_INPUT_ID);
            if (input.value === segundoInput.value) {
                var dataPost = base.getFormData();
                var dataText = JSON.stringify(dataPost);
                var chave = document.getElementById(CHAVE_INPUT_ID).value;
                var chaveArray = CryptoJS.enc.Hex.parse(chave);
                var iv   = document.getElementById(IV_INPUT_ID).value
                var ivArray   = CryptoJS.enc.Hex.parse(iv);
                dataText = {"credenciais":CryptoJS.enc.Base64.stringify(CryptoJS.AES.encrypt(dataText, chaveArray , {
                    iv:ivArray}).ciphertext)};
                if (dataPost != null) {
                    $.ajax({
                        method: "POST",
                        url: "../rest/alterarSenha",
                        data:JSON.stringify(dataText),
                        dataType : "json",
                        contentType: "application/json; charset=utf-8",
                        success: function(data) {
                            alert(data.responseText);
                            window.location.href = "./"
                        },
                        error:  function(data) {
                            if(data.status == 200) {
                                alert(data.responseText);
                                window.location.href = "./"
                            }
                        },
                    })
                }
            } else {
                alert("Confirme a senha! \nOs campos de senha devem ser idênticos");
            }


        },



    }
	    return modelistaModule;
 })();

 var modelista = new modelistaModule();
