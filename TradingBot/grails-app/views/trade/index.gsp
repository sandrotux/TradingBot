<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'trade.label', default: 'Trade')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>

        <style>
            .toggle.btn {
                width: 100%!important;
                height: 30px!important;
            }
        </style>

        <a href="#list-trade" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<!--        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>-->

        <div class="row" style="background-color: #ddd;margin-bottom: 5px;">
            <div class="col-sm-3"></div>
            <div class="col-sm-6 text-center">
                <div class="row">
                    <div class="col-sm-6 col-xs-6">
                        <div class="col-sm-4 col-xs-6"><label>STATUS: </label></div>
                        <div class="col-sm-4 col-xs-6"><span style="font-weight: bold; color: #5cb85c;">RUNNING</span></div>
                    </div>
                    <div class="col-sm-6 col-xs-6">
                        <div class="col-sm-4 col-xs-6">
                            <label for="inAmount">AMOUNT:</label>
                        </div>
                        <div class="col-sm-4 col-xs-6">
                            <span>${config?.amountTrade}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="padding:0;">

            <div class="col-sm-2">
                <div class="panel panel-default">
                    <div class="panel-heading"><strong>CONFIG</strong></div>
                    <div class="panel-body" style="background-color: #eee;">
                        <form>
                            <div class="form-group" style="margin-top: 10px;">
                                <input type="checkbox" checked data-toggle="toggle" data-on="RUNNING" data-off="STOPED" data-onstyle="success" data-offstyle="danger">
                            </div>
                            <div class="form-group">
                                <input type="number" id="inAmount" class="form-control"/>
                            </div>
                            <button type="button" class="btn btn-primary" style="float:right">Save</button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-sm-10">
                <div id="list-trade" class="content scaffold-list" role="main" >
                    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <f:table collection="${tradeList}" />

                    <div class="pagination">
                        <g:paginate total="${tradeCount ?: 0}" />
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>