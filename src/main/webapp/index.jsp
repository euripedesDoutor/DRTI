<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>


<f:view>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><h:outputText value="DRTI - #{LoginControle.versaoSistema}"/></title>
        <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.2.0/css/all.css'>
        <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.2.0/css/fontawesome.css'>
        <link rel="stylesheet" href="./login/style.css">
    </head>
    <style>
        .label {
            font-family: Raleway, sans-serif;
            font-size: 11px;
            color: #555555;
            font-weight: bold;
            display: block;
            width: 100%;
            background: transparent;
            position: relative;
            top: 10px;
        }

        .labelMensagem {
            font-family: Raleway, sans-serif;
            font-size: 11px;
            color: #D70000;
            background-color: #eeeeee;
            box-shadow: 0px 2px 2px #97B4DA;
            font-weight: bold;
            display: block;
            width: 100%;
            position: relative;
            top: -30px;
            border-radius: 5px;
            padding-left: 5px;
        }

        .login__input {
            border: none !important;
            border-bottom: 2px solid #647188 !important;
            background: none !important;
            padding: 10px !important;
            padding-left: 24px !important;
            font-weight: 700 !important;
            width: 75% !important;
            transition: .2s !important;
        }

        .login__input:active,
        .login__input:focus,
        .login__input:hover {
            outline: none;
            border-bottom-color: #6A679E;
        }

        .login__submit {
            background: #fff !important;
            font-size: 14px !important;
            margin-top: 30px !important;
            padding: 16px 20px !important;
            border-radius: 26px !important;
            border: 1px solid #6A679E !important;
            text-transform: uppercase !important;
            font-weight: 700 !important;
            display: flex !important;
            align-items: center !important;
            width: 100% !important;
            color: #4D6BCC !important;
            box-shadow: 0px 2px 2px #97B4DA !important;
            cursor: pointer !important;
            transition: .2s !important;
            position: relative;
            top: -20px;
        }

        .button__text {
            padding: 15px;
            position: relative;
            top: -60px;
        }
        .button__icon {
            position: relative;
            top: -55px;
            left: 235px;
        }

        .logo {
            width: 180px;
            position: relative;
            top: 60px;
            left: 80px;
            z-index: 99;
            padding-bottom: 30px !important;
        }


    </style>
    <body>
    <div class="container">
        <div class="screen">
            <img src="./login/imagens/logo.png" class="logo"/>
            <div class="screen__content">
                <h:form id="form" styleClass="login">
                    <div class="login__field">
                        <span class="label" style="position: relative; top: -5px !important; left: 15px !important;">Empresa</span>
                        <i class="login__icon fas fa-building" style="top: 40px !important;"></i>
                        <h:selectOneMenu  id="empresa" style="background-color:transparent;" styleClass="login__input" value="#{LoginControle.empresaLogin}" >
                            <f:selectItems  value="#{LoginControle.listaSelectItemEmpresa}" />
                        </h:selectOneMenu>
                    </div>
                    <div class="login__field">
                        <i class="login__icon fas fa-user"></i>
                        <h:inputText styleClass="login__input" id="username" value="#{LoginControle.username}"/>
                    </div>
                    <div class="login__field">
                        <i class="login__icon fas fa-lock"></i>
                        <h:inputSecret styleClass="login__input" id="senha" value="#{LoginControle.senha}"/>
                    </div>

                    <h:commandButton id="login" type="submit" value="Acessar" styleClass="login__submit" action="#{LoginControle.login}">
                        <i class="button__icon fas fa-chevron-right"></i>
                    </h:commandButton>
                    <span class="labelMensagem"><h:outputText escape="false" value="#{LoginControle.mensagemDetalhada}"/></span>
                </h:form>
                <div class="social-login">
                    <h5>Develop by NEVERA</h5>
                    <div class="social-icons">
                        <a href="#" class="social-login__icon fab fa-instagram"></a>
                        <a href="#" class="social-login__icon fab fa-facebook"></a>
                        <a href="#" class="social-login__icon fab fa-twitter"></a>
                    </div>
                </div>
            </div>
            <div class="screen__background">
                <span class="screen__background__shape screen__background__shape4"></span>
                <span class="screen__background__shape screen__background__shape3"></span>
                <span class="screen__background__shape screen__background__shape2"></span>
                <span class="screen__background__shape screen__background__shape1"></span>
            </div>
        </div>
    </div>
    </body>
</f:view>
<script>
    document.getElementById("form:username").focus();
    document.getElementById("form:username").placeholder = "Usuário";
    document.getElementById("form:senha").placeholder = "Senha";
</script>
