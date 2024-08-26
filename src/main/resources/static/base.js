const CHAVE_INPUT_ID = "chave_input"
const IV_INPUT_ID = "iv_input"

const SENHA_INPUT_ID = "senha_input"
const SECOND_SENHA_INPUT_ID = "senha_input2"
const USER_SELECT_ID = "user_select"

var baseModule = (function() {

    var baseModule = function () {};

    baseModule.prototype = {

        /**
        Funções de utilidade
        */

        clearFormData : () => {
            const elements = document.querySelectorAll('[name]');
            for (element of elements) {
                if(element.value && (element.type !== 'hidden' && element.type !== 'radio')) {
                    element.value = '';
                }
            }
        },

        clearFormUser : () => {
            var input = document.getElementById(SECOND_SENHA_INPUT_ID);
            input.value = "";
            base.clearFormData();
        },

        getFormData : () => {
            const elements = document.querySelectorAll('[name]');
            var jsonData = {};
            for (element of elements) {
                if(element.tagName === 'INPUT' || element.tagName === 'TEXTAREA' || element.tagName === 'SELECT') {
                    if (element.value != '') {
                        if ( element.type == 'date') {
                            var stringDate = element.value;
                            jsonData[element.name] = stringDate.substring(8,10)+"/"+stringDate.substring(5,7)+"/"+stringDate.substring(0,4);
                        } else if (element.type !== "radio" || element.checked) {
                            jsonData[element.name] = element.value;
                        }
                    } else if (element.required) {
                        if (element.tagName === 'INPUT') {
                            alert("Por favor, Preencha todos os campos");
                            return null;
                        } else if(element.tagName === 'SELECT') {
                            var divField = element.closest('div.field');
                            if (!divField.classList.contains("is-hidden")) {
                                alert("Por favor, Preencha todos os campos");
                                return null;
                            }
                        }
                    }

                }
            }
            return jsonData;
        },

        /**
        Funcções de usuário
        */

        checkPassword : (input, secondInputId) => {
            const secondInput= document.getElementById(secondInputId);
            if (secondInput.value == input.value) {
                input.valid = true;
                secondInput.valid = true;
            } else {
                input.valid = false;
                secondInput.valid = false;
            }
        },

        saveUser : () => {
            const input= document.getElementById(SENHA_INPUT_ID);
            const secondInput= document.getElementById(SECOND_SENHA_INPUT_ID);
            if (input.value === secondInput.value) {
                var dataPost = base.getFormData();
                if (dataPost != null) {
                    var dataText = JSON.stringify(dataPost);
                    var chave = document.getElementById(CHAVE_INPUT_ID).value;
                    var chaveArray = CryptoJS.enc.Hex.parse(chave);
                    var iv   = document.getElementById(IV_INPUT_ID).value
                    var ivArray   = CryptoJS.enc.Hex.parse(iv);
                    dataText = {"user":CryptoJS.enc.Base64.stringify(CryptoJS.AES.encrypt(dataText, chaveArray , {
                            iv:ivArray}).ciphertext)};
                    $.ajax({
                        method: "POST",
                        url: "./api/usersSafe",
                        data:JSON.stringify(dataText),
                        dataType : "json",
                        contentType: "application/json; charset=utf-8",
                        success: function(data) {
                            alert(data.responseText);
                            base.clearFormUser
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

        editUser : (userId) => {
            const input= document.getElementById(SENHA_INPUT_ID);
            const secondInput= document.getElementById(SECOND_SENHA_INPUT_ID);
            if (input.value === secondInput.value) {
                var dataPost = base.getFormData();
                if (dataPost != null) {
                    var dataText = JSON.stringify(dataPost);
                    var chave = document.getElementById(CHAVE_INPUT_ID).value;
                    var chaveArray = CryptoJS.enc.Hex.parse(chave);
                    var iv   = document.getElementById(IV_INPUT_ID).value
                    var ivArray   = CryptoJS.enc.Hex.parse(iv);
                    dataText = {"user":CryptoJS.enc.Base64.stringify(CryptoJS.AES.encrypt(dataText, chaveArray , {
                            iv:ivArray}).ciphertext)};
                    $.ajax({
                        method: "PUT",
                        url: "../api/usersSafe/" + userId,
                        data:JSON.stringify(dataText),
                        dataType : "json",
                        contentType: "application/json; charset=utf-8",
                        success: function(data) {
                            alert(data.responseText);
                        },
                        error:  function(data) {
                            if(data.status == 200) {
                                alert(data.responseText);
                            }
                        },
                    })
                }
            } else {
                alert("Confirme a senha! \nOs campos de senha devem ser idênticos");
            }
        },


        deleteUser: () => {
            const userId = document.getElementById(USER_SELECT_ID).value;
            $.ajax({
                method: "DELETE",
                url: "./api/users/" + userId,
                success: function(data) {
                    alert("Usuário Deletado.")
                },
                error:  function(data) {
                    alert(data);
                },
            })
        },

        openUserEditWindow: () => {
            const userId = document.getElementById(USER_SELECT_ID).value;
            if (userId != "") {
                window.open("./editUser/" + userId);
            }
        },

        /**
        Funcções de Tarefas
        */

        saveTask : () => {
            var dataPost = base.getFormData();
            if (dataPost != null) {
                $.ajax({
                    method: "POST",
                    url: "./api/tasks",
                    data:JSON.stringify(dataPost),
                    dataType : "json",
                    contentType: "application/json; charset=utf-8",
                    success: function(data) {
                        alert("Tarefa criada");
                        base.clearFormData();
                    },
                    error:  function(data) {
                        alert(data.responseText);
                    },
                })
            }

        },

        deleteTask: (taskId) => {
            $.ajax({
                method: "DELETE",
                url: "./api/tasks/" + taskId,
                success: function(data) {
                    alert("Tarefa Deletada. Recarregue página para ver alteração")
                },
                error:  function(data) {
                    alert(data);
                },
            })
        },

        editTask: (taskId) => {
            var dataPost = base.getFormData();
            if (dataPost != null) {
                $.ajax({
                    method: "PUT",
                    url: "../api/tasks/" + taskId,
                    data:JSON.stringify(dataPost),
                    dataType : "json",
                    contentType: "application/json; charset=utf-8",
                    success: function(data) {
                        alert("Tarefa Alterada com sucesso");
                    },
                    error:  function(data) {
                        alert(data.responseText);
                    },
                })
            }
        },

        /**
        Funções de login
        */

        login : () => {
            const username= document.getElementById("username_input").value;
            const senha= document.getElementById("senha_input").value;
            var dataText = JSON.stringify({
                                                "username" : username,
                                                "senha" : senha
                                            });
            var chave = document.getElementById(CHAVE_INPUT_ID).value;
            var chaveArray = CryptoJS.enc.Hex.parse(chave);
            var iv   = document.getElementById(IV_INPUT_ID).value
            var ivArray   = CryptoJS.enc.Hex.parse(iv);
            dataText = {"credentials":CryptoJS.enc.Base64.stringify(CryptoJS.AES.encrypt(dataText, chaveArray , {
                iv:ivArray}).ciphertext)};
            $.ajax({
                method: "POST",
                url: "./api/loginSafe",
                data:JSON.stringify(dataText),
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                success: function(data) {
                    if (data.status == 200) {
                        alert(data.responseText)
                        window.location.href = "./"
                    }
                },
                error:  function(data) {
                    if (data.status == 200) {
                        alert(data.responseText)
                        window.location.href = "./"
                    } else if (data.responseJSON.status == 500) {
                        alert("Usuário ou senha inválido")
                    }
                },
            })


        },

        logout : () => {
                $.ajax({
                    method: "POST",
                    url: "./api/logout",
                    dataType: "text",
                    success: function(data) {
                        alert(data)
                        window.location.href = "./"
                    },
                    error:  function(data) {
                        alert(data);
                    },
                })
        },

        sendLogin : () => {
            if (event.code == 'Enter') {
                base.login();
            }
        },

    }
	    return baseModule;
 })();

 var base = new baseModule();
