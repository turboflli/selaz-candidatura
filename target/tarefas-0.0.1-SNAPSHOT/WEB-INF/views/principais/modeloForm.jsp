<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <div  class= "field">
        <label class="label">Nome do trabalho</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="nome" type="text" placeholder="Nome do trabalho" required="required">
            <span class="icon is-small is-left">
              <i class="fas fa-signature"></i>
            </span>
        </div>
        <!--p class="help">This is a help text</p-->
    </div>
    <div  class= "field">
        <label class="label">Nível</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="nivel" required="required">
               <option selected disabled hidden value="">Escolha o nível ao qual se considera</option>
              <option value="Principal">Principal</option>
              <option value="Iniciante">Iniciante</option>
              <option value="KIDS">KIDS</option>
          </select>
        </span>
            <span class="icon is-small is-left">
          <i class="fas fa-user"></i>
        </span>
        </p>
    </div>
    <div  class= "field">
        <label class="label">Modalidade</label>
        <div class="control has-icons-left">
            <span class="select is-rounded">
                <select name="categoria" id="categoria_select"
                        onchange="base.adicionarCampos(this);base.carregarEscalas(this)" required="required">
                     <option selected disabled hidden value="">Escolha a modalidade na qual o modelo se enquadra</option>
                    <option value="diorama">Diorama</option>
                    <option value="astronautica">Astronautica</option>
                    <option value="autos">Autos</option>
                    <option value="aviacao">Aviação</option>
                    <option value="ficcao">Ficção</option>
                    <option value="figuras">Figuras</option>
                    <option value="militaria">Militaria</option>
                    <option value="naval">Naval</option>
                    <c:if test="${param.permiteTodasCategorias}">
                     <option value="">Todas</option>
                    </c:if>
                </select>
                <span class="icon is-small is-left">
                  <i class="fas fa-tag"></i>
                </span>
            </span>
        </div>
    </div>
    <div  class= "field">
        <label class="label">Escala  (Digite ou escolha)</label>
        <p class="control has-icons-left">
          <input class="input is-rounded" type="text" list="escalas_list" name="escala" autocomplete="on"
                 required="required" placeholder="Escolha a escala do modelo" maxlength="50"/>
        <span class="icon is-small is-left">
          <i class="fas fa-sort-numeric-down-alt"></i>
        </span>
        </p>
    </div>
    <datalist id="escalas_list">
        <option value="1/16">1/16</option>
        <option value="1/32">1/32</option>
        <option value="1/48">1/48</option>
    </datalist>

    <div id="extra_fields">
    </div>

    <div  class= "field">
        <label class="label" id="fabricante_label">Fabricante</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="fabricante" type="text" placeholder="Fabricante do modelo"
                   required="required"  maxlength="25" id="fabricante_input">
            <span class="icon is-small is-left">
              <i class="fas fa-gavel"></i>
            </span>
        </div>
    </div>

    <div   class= "field is-rounded">
        <label class="label">Possui Base?</label>
        <div class="control">
            <label class="radio">
                <input type="radio" name="temBase" value="true">
                Sim
            </label>
            <label class="radio">
                <input type="radio" name="temBase" value="false">
                Não
            </label>
        </div>
    </div>

    <div   class= "field is-rounded">
        <label class="label">Temática?</label>
        <div class="control">
            <label class="radio">
                <input type="radio" name="tematica" value="true">
                Sim
            </label>
            <label class="radio">
                <input type="radio" name="tematica" value="false">
                Não
            </label>
        </div>
    </div>

    <div  class= "field">
        <label class="label">Informações Adicionais</label>
        <div class="control">
            <textarea class="textarea" name="melhorias" placeholder="Informações Adicionais"></textarea>
        </div>
    </div>