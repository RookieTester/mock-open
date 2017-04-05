<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/22
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="<%=path%>/resource/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/font-awesome.min.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/style.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/base.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/jquery.numberedtextarea.css" rel="stylesheet">

    <script src="<%=path%>/resource/js/jquery.min.js"></script>
    <script src="<%=path%>/resource/js/bootstrap.min.js"></script>
</head>
<body>
<header class="header">
    <div class="row-fluid">
        <div class="col-md-5" style="position:relative;">
            <div class="logo">
                <input class="form-control" placeholder="此处填写接口mock的名称" style="display: block" id="alias" value="${requestScope.alias}"/>
            </div>
        </div>

        <nav class="col-md-7" style="padding:10px 0;" align="right">
            <div class="navi">

            </div>
        </nav>
        <br style="clear:both;"/>
    </div>
</header>
<main class="row-fluid">
    <div class="col-md-5" style="padding:0;">
                            <textarea id="json-src-modal" placeholder="在此输入json字符串或XML字符串..." class="form-control"
                                      style="height:553px;padding:0 10px 10px 20px;border:0;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none; outline:none;">${requestScope.json}</textarea>
    </div>
    <div class="col-md-7" style="padding:0;">
        <div id="right-box"
             style="height:553px;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none;overflow-y:scroll; outline:none;position:relative;">
            <div id="line-num"
                 style="background-color:#fafafa;padding:0px 8px;float:left;border-right:dashed 1px #eee;display:none;z-index:-1;color:#999;position:absolute;text-align:center;over-flow:hidden;">
                <div>0</div>
            </div>
            <div class="ro" id="json-target-modal" style="padding:0px 25px;over:0">
            </div>
        </div>
        <form id="form-save-modal" method="POST">
            <input type="hidden" value="" id="txt-content-modal"
                   name="content">
        </form>
    </div>

    <center><a class="btn btn-primary" id="update"> 更新Json </a></center>
    <input id="data_id" value="${requestScope.id}" type="hidden"/>
    <br style="clear:both;"/>
</main>

<!-- JSON 染色与格式化 -->
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.json.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.json2xml.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.message.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.numberedtextarea.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.xml2json.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/json2.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jsonlint.js"></script>
<script type="text/javascript">
    $('textarea').numberedtextarea();
    var current_json = '';
    var current_json_str = '';
    var xml_flag = false;
    var zip_flag = false;
    var shown_flag = false;
    function init() {
        xml_flag = false;
        zip_flag = false;
        shown_flag = false;
        renderLine();
        $('.xml').attr('style', 'color:#999;');
        $('.zip').attr('style', 'color:#999;');

    }

    $('#json-src-modal').keyup(function () {
        init();
        var content = $.trim($(this).val());
        var result = '';
        if (content != '') {
            //如果是xml,那么转换为json
            if (content.substr(0, 1) === '<' && content.substr(-1, 1) === '>') {
                try {
                    var json_obj = $.xml2json(content);
                    content = JSON.stringify(json_obj);
                } catch (e) {
                    result = '解析错误：<span style="color: #f1592a;font-weight:bold;">' + e.message + '</span>';
                    current_json_str = result;
                    $('#json-target-modal').html(result);
                    return false;
                }

            }
            try {
                current_json = jsonlint.parse(content);
                current_json_str = JSON.stringify(current_json);
                //current_json = JSON.parse(content);
                result = new JSONFormat(content, 4).toString();
            } catch (e) {
                result = '<span style="color: #f1592a;font-weight:bold;">' + e + '</span>';
                current_json_str = result;
            }

            $('#json-target-modal').html(result);
        } else {
            $('#json-target-modal').html('');
        }
    });

    $('#json-src-modal').click(function () {
        init();
        var content = $.trim($(this).val());
        var result = '';
        if (content != '') {
            //如果是xml,那么转换为json
            if (content.substr(0, 1) === '<' && content.substr(-1, 1) === '>') {
                try {
                    var json_obj = $.xml2json(content);
                    content = JSON.stringify(json_obj);
                } catch (e) {
                    result = '解析错误：<span style="color: #f1592a;font-weight:bold;">' + e.message + '</span>';
                    current_json_str = result;
                    $('#json-target-modal').html(result);
                    return false;
                }

            }
            try {
                current_json = jsonlint.parse(content);
                current_json_str = JSON.stringify(current_json);
                //current_json = JSON.parse(content);
                result = new JSONFormat(content, 4).toString();
            } catch (e) {
                result = '<span style="color: #f1592a;font-weight:bold;">' + e + '</span>';
                current_json_str = result;
            }

            $('#json-target-modal').html(result);
        } else {
            $('#json-target-modal').html('');
        }
    });

    function renderLine() {
        var line_num = $('#json-target-modal').height() / 20;
        $('#line-num').html("");
        var line_num_html = "";
        for (var i = 1; i < line_num + 1; i++) {
            line_num_html += "<div>" + i + "<div>";
        }
        $('#line-num').html(line_num_html);
    }

    $('#json-src-modal').keyup();
</script>
<script type="text/javascript">
    $("#update").click(function () {
        var id=document.getElementById("data_id").value;
        var json=$("#json-src-modal").val();
        var alias=$("#alias").val();
        $.ajax({
            url: "<%=path%>/mock/update",
            data: {
                id: id, alias: alias, json: json
            },
            type: "post",
            async: false,
            success: successFuc
        })
    })

    function successFuc() {
        alert("更新成功");
        window.location.reload();
    }
</script>
</body>
</html>
