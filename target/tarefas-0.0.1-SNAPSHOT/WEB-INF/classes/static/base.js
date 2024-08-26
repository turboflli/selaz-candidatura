const DIORAMA_TEXT = "diorama";
const EXTRA_FIELDS_ID = "extra_fields"
const ESCALAS_OPCOES_ID = "escalas_list"
const FABRICANTE_INPUT_ID = "fabricante_input"
const CATEGORIA_SELECT_ID = "categoria_select"
const FABRICANTE_LABEL_ID = "fabricante_label"
const FABRICANTE_UNICO_TEXT = "Fabricante"
const FABRICANTE_VARIOS_TEXT = "Fabricantes"
const EXTRA_FIELDS_AVI_ID = "extra_fields_avi"
const LISTA_MODELOS_ID = "lista_modelos"
const CHAVE_INPUT_ID = "chave_input"
const IV_INPUT_ID = "iv_input"
const VALUE_OUTROS = "Outros"
const VALUE_OUTRAS = "Outras"

var baseModule = (function() {

    var baseModule = function () {};

    baseModule.prototype = {
        limpar : (auto) => {
            if(auto || confirm("Isso apagará os dados preenchidos, \nTem certeza?")) {
                var div = document.getElementById(EXTRA_FIELDS_ID);
                div.innerHTML = "";
                base.clearFormData();
                var selectCategoria = document.getElementById(CATEGORIA_SELECT_ID);
                selectCategoria.value = "";
                base.alterarFabricantes(false);
            }
        },

        limparModelista : () => {

        },

        alterarFabricantes : (varios) => {
            var inputFabricante = document.getElementById(FABRICANTE_INPUT_ID);
            var labelFabricante = document.getElementById(FABRICANTE_LABEL_ID);
            if (varios) {
                inputFabricante.maxlength = 100
                labelFabricante.innerHTML = FABRICANTE_VARIOS_TEXT
            } else {
                inputFabricante.maxlength = 25
                labelFabricante.innerHTML = FABRICANTE_UNICO_TEXT;
            }
        },

        save : () => {
            var dataPost = base.getFormData();
            if (dataPost != null) {
                $.ajax({
                    method: "POST",
                    url: "../rest/save",
                    data:JSON.stringify(dataPost),
                    dataType : "json",
                    contentType: "application/json; charset=utf-8",
                    success: function(data) {
                        alert(data);
                        base.recarregarTabela();
                        base.limpar(true);
                    },
                    error:  function(data) {
                        alert(data.responseText);
                        if(data.status == 200) {
                            window.location.href = "./"
                        }
                    },
                })
            }

        },

        recarregarTabela : () => {
            $.ajax({
                method: "GET",
                url: "../jsp/recarregarModelos" ,
                success: function(data) {
                    var div = document.getElementById(LISTA_MODELOS_ID);
                   div.innerHTML = data
                },
            })
        },

        getFormData : (ignorarVazios) => {
            const elements = document.querySelectorAll('[name]');
            var jsonData = {};
            for (element of elements) {
                if(element.tagName === 'INPUT' || element.tagName === 'TEXTAREA' || element.tagName === 'SELECT') {
                    if ( element.value == '') {
                        if (ignorarVazios !== true) {
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
                    }else if (element.type !== "radio" || element.checked) {
                        jsonData[element.name] = element.value;
                    }
                }
            }
            return jsonData;
        },

        clearFormData : () => {
            const elements = document.querySelectorAll('[name]');
            for (element of elements) {
                if(element.value && (element.type !== 'hidden' && element.type !== 'radio')) {
                    element.value = '';
                }
            }
        },

        adicionarCampos : (categoriaSelect) => {
            var categoria = categoriaSelect.value;
            if (categoria !== DIORAMA_TEXT) { //Diorama é a base
                base.alterarFabricantes(false);
                $.ajax({
                    method: "GET",
                    url: "../jsp/opcoes/"+categoria,
                    success: function(data) {
                        var div = document.getElementById(EXTRA_FIELDS_ID);
                       div.innerHTML = data
                       var inputs = div.querySelectorAll("input[type='hidden']");
                       base.evaluateInputs(inputs);
                    },
                })
            } else {
                var div = document.getElementById(EXTRA_FIELDS_ID);
                div.innerHTML = "";
                base.alterarFabricantes(true);
            }
        },

        adicionarCamposAvi : (utilizacaoSelect) => {
            var utilizacao = utilizacaoSelect.value;
            $.ajax({
                method: "GET",
                url: "../jsp/opcoesavi/"+utilizacao,
                success: function(data) {
                    var div = document.getElementById(EXTRA_FIELDS_AVI_ID);
                   div.innerHTML = data
                   var inputs = div.querySelectorAll("input[type='hidden']");
                   base.evaluateInputs(inputs);
                },
            })
        },

        carregarEscalas : (categoriaSelect) => {
            var categoria = categoriaSelect.value;
            $.ajax({
                method: "GET",
                url: "../jsp/escalas/"+categoria,
                success: function(data) {
                    var dataList = document.getElementById(ESCALAS_OPCOES_ID);
                    dataList.innerHTML = data
                }
            })
        },

        carregarValores : (selectName, selectId) => {
            $.ajax({
                method: "GET",
                url: "../jsp/subopcoes/"+selectName,
                success: function(data) {
                    var select = document.getElementById(selectId);
                    select.innerHTML = data
                }
            })
        },

        esconderSelects : (tipo) => {
            var escondidos = document.getElementsByClassName("is-hidden");
            while (escondidos.length > 0) {
                escondidos[0].classList.add("is-block")
                escondidos[0].classList.remove("is-hidden")
            }
            var selectCategoria = document.getElementById(CATEGORIA_SELECT_ID);
            var categoria = selectCategoria.value;
            $.ajax({
                method: "GET",
                url: "../rest/esconder",
                data:{
                    "categoria" : categoria,
                    "tipo" : tipo,
                },
                dataType: "text",
                success: function(data) {
                    if(data.length > 0) {
                        for (elementId of data.split(',')) {
                            var element = document.getElementById(elementId);
                            if (element) {
                                var divField = element.closest('div.field');
                                divField.classList.remove("is-block");
                                divField.classList.add("is-hidden");
                            }
                        }
                    }
                }
            })
        },

        evaluateInputs : (inputs) => {
            for (input of inputs) {
                const textos = input.value.split("/");
                switch (textos[0]) {
                    case "add":
                        base.carregarValores(textos[1], textos[2]);
                        break;
                    case "change":
                        var element = document.getElementById(textos[1]);
                        element.addEventListener("change", (event) => {
                          base.carregarValores(event.target.value, textos[2]);
                        });
                        break;
                    case "hide":
                        var element = document.getElementById(textos[1]);
                        element.addEventListener("change", (event) => {
                          base.esconderSelects(event.target.value);
                        });
                        break;
                }
            }
        },

        login : () => {
            const login= document.getElementById("login_input").value;
            const senha= document.getElementById("senha_input").value;
            var dataText = JSON.stringify({
                                                "login" : login,
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
                url: "../rest/login",
                data:JSON.stringify(dataText),
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                success: function(data) {
                    if (data) {
                        window.location.href = "./"
                    } else {
                        alert("Usuário ou senha inválido")
                    }
                },
                error:  function(data) {
                    alert(data);
                },
            })


        },

        logout : () => {
                $.ajax({
                    method: "POST",
                    url: "../rest/logout",
                    dataType: "text",
                    success: function(data) {
                        if (data == "true") {
                            window.location.href = "./"
                        } else {
                            alert("Erro ao finalizar Sessão")
                        }
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

        deletar: (idKit) => {
            $.ajax({
                method: "DELETE",
                url: "../rest/deletar/" + idKit,
                success: function(data) {
                    if (data == "true") {
                        alert("Modelo removido com sucesso")
                        base.recarregarTabela()
                    } else {
                        alert("Erro ao remover modelo")
                    }
                },
                error:  function(data) {
                    alert(data);
                },
            })
        },

        handleOutros : (select) => {
            if (select.value.startsWith(VALUE_OUTROS) || select.value == VALUE_OUTRAS) {
                const input_outros = document.createElement("input")
                    input_outros.name = select.name;
                    input_outros.id = select.name+"_outros";
                    input_outros.className = "input is-rounded outrosInput";
                    input_outros.required = "required";
                const Ppai = select.parentElement.parentElement
                Ppai.appendChild(input_outros);
            } else {
                input_outros = document.getElementById(select.name+"_outros");
                if (input_outros) {
                    input_outros.remove();
                }

            }
        },

        buscar : () => {
            var dataPost = base.getFormData(true);
            $.ajax({
                method: "POST",
                url: "../jsp/buscar" ,
                data:JSON.stringify(dataPost),
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                success: function(data) {
                    var div = document.getElementById(LISTA_MODELOS_ID);
                   div.innerHTML = data
                },
                error:  function(data) {
                    if(data.status == 200) {
                         var div = document.getElementById(LISTA_MODELOS_ID);
                         div.innerHTML = data.responseText
                    }
                },
            })
        },

        ExportToExcel : (type, fn, dl) => {
//           var elt = document.getElementById('tbl_exporttable_to_xls');
//           var wb = XLSX.utils.table_to_book(elt, { sheet: "sheet1" });
//           return dl ?
//             XLSX.write(wb, { bookType: type, bookSST: true, type: 'base64' }):
//             XLSX.writeFile(wb, fn || ('MOELOS.' + (type || 'xlsx')));



           var rows = document.querySelectorAll('table#' + 'tbl_exporttable_to_xls' + ' tr');
           // Construct csv
           var csv = [];
           for (var i = 0; i < rows.length; i++) {
               var row = [], cols = rows[i].querySelectorAll('td, th');
               for (var j = 0; j < cols.length; j++) {
                   // Clean innertext to remove multiple spaces and jumpline (break csv)
                   var data = "";
                   var text = cols[j].innerText;
                   if (text == "") {
                       for (index in cols[j].childNodes) {
                          var node = cols[j].childNodes[index];
                          if (node.className && node.className.includes("has-text-success")) {
                            data= "sim";
                            break;
                          }
                          if (node.className && node.className.includes("has-text-danger")) {
                            data= "Não";
                            break;
                          }
                          if (node.className && node.className.includes("tooltip")) {
                            for (index2 in node.childNodes) {
                                var subNode = node.childNodes[index2]
                                if (subNode.className && subNode.className.toString().includes("tooltiptext")) {
                                    var valores = subNode.innerHTML.replace("<p>", "").substring(0, subNode.innerHTML.lastIndexOf('</p>')-3).split("</p><p>");
                                    for (index3 in valores) {
                                            row.push('"' + valores[index3] + '"');
                                    }
                                    break;
                                }
                            }
                            break;
                          }

                       }
                  } else {
                       data = text.replace(/(\r\n|\n|\r)/gm, '').replace(/(\s\s)/gm, ' ')
                  }



                   // Escape double-quote with double-double-quote (see https://stackoverflow.com/questions/17808511/properly-escape-a-double-quote-in-csv)
                   data = data.replace(/"/g, '""');
                   // Push escaped string
                   row.push('"' + data + '"');

               }
               csv.push(row.join(";"));
           }
           var csv_string = csv.join('\n');
           // Download it
           var filename = 'modelos' + '_' + new Date().toLocaleDateString() + '.xlsx';
           var link = document.createElement('a');
           link.style.display = 'none';
           link.setAttribute('target', '_blank');
           link.setAttribute('href', 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv_string));
           link.setAttribute('download', filename);
           document.body.appendChild(link);
           link.click();
           document.body.removeChild(link);


        },

    }
	    return baseModule;
 })();

 var base = new baseModule();
