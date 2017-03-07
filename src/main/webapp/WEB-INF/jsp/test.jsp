<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/23
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>测试页面</title>
    <link href="<%=path%>/resource/css/base.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/jquery.numberedtextarea.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<main class="row-fluid">
    <div class="col-md-5" style="padding:0;">
        <textarea id="json-src" placeholder="在此输入json字符串或XML字符串..."   class="form-control"  style="height:553px;padding:0 10px 10px 20px;border:0;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none; outline:none;">{&#13"Json解析":"支持格式化高亮折叠",&#13"支持XML转换":"支持XML转换Json,Json转XML",&#13"Json格式验证":"更详细准确的错误信息"</textarea>
    </div>
    <div class="col-md-7" style="padding:0;">
        <div style="padding:10px;font-size:16px;border-bottom:solid 1px #ddd;" class="navi">
            <a href="#" class="tip zip" title="压缩"  data-placement="bottom"><i class="fa fa-database"></i></a>
            <a href="#" class="tip xml" title="转XML"  data-placement="bottom"><i class="fa fa-file-excel-o"></i></a>
            <a href="#" class="tip shown"  title="显示行号"  data-placement="bottom"><i class="glyphicon glyphicon-sort-by-order"></i></a>
            <a href="#" class="tip clear" title="清空"  data-placement="bottom"><i class="fa fa-trash"></i></a>
            <a href="#" class="tip save" title="保存"  data-placement="bottom"><i class="fa fa-floppy-o"></i></a>
        </div>
        <div id="right-box"  style="height:510px;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none;overflow-y:scroll; outline:none;position:relative;">
            <div id="line-num" style="background-color:#fafafa;padding:0px 8px;float:left;border-right:dashed 1px #eee;display:none;z-index:-1;color:#999;position:absolute;text-align:center;over-flow:hidden;">
                <div>0</div>
            </div>
            <div class="ro" id="json-target" style="padding:0px 25px;over">
            </div>
        </div>
        <form id="form-save" method="POST"><input type="hidden" value="" id="txt-content" name="content"></form>
    </div>
    <br style="clear:both;" />
</main>
<!-- JSON CHECK -->
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.min.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.json.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.json2xml.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.message.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.numberedtextarea.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.xml2json.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/json2.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jsonlint.js"></script>
<script src="<%=path%>/resource/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $('textarea').numberedtextarea();
    var current_json = '';
    var current_json_str = '';
    var xml_flag = false;
    var zip_flag = false;
    var shown_flag = false;
    function init(){
        xml_flag = false;
        zip_flag = false;
        shown_flag = false;
        renderLine();
        $('.xml').attr('style','color:#999;');
        $('.zip').attr('style','color:#999;');

    }
    $('#json-src').keyup(function(){
        init();
        var content = $.trim($(this).val());
        var result = '';
        if (content!='') {
            //如果是xml,那么转换为json
            if (content.substr(0,1) === '<' && content.substr(-1,1) === '>') {
                try{
                    var json_obj = $.xml2json(content);
                    content = JSON.stringify(json_obj);
                }catch(e){
                    result = '解析错误：<span style="color: #f1592a;font-weight:bold;">' + e.message + '</span>';
                    current_json_str = result;
                    $('#json-target').html(result);
                    return false;
                }

            }
            try{
                current_json = jsonlint.parse(content);
                current_json_str = JSON.stringify(current_json);
                //current_json = JSON.parse(content);
                result = new JSONFormat(content,4).toString();
            }catch(e){
                result = '<span style="color: #f1592a;font-weight:bold;">' + e + '</span>';
                current_json_str = result;
            }

            $('#json-target').html(result);
        }else{
            $('#json-target').html('');
        }

    });
    $('.xml').click(function(){
        if (xml_flag) {
            $('#json-src').keyup();
        }else{
            var result = $.json2xml(current_json);
            $('#json-target').html('<textarea style="width:100%;height:100%;border:0;resize:none;">'+result+'</textarea>');
            xml_flag = true;
            $(this).attr('style','color:#15b374;');
        }

    });
    $('.shown').click(function(){
        if (!shown_flag) {
            renderLine();
            $('#json-src').attr("style","height:553px;padding:0 10px 10px 40px;border:0;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none; outline:none;");
            $('#json-target').attr("style","padding:0px 50px;");
            $('#line-num').show();
            $('.numberedtextarea-line-numbers').show();
            shown_flag = true;
            $(this).attr('style','color:#15b374;');
        }else{
            $('#json-src').attr("style","height:553px;padding:0 10px 10px 20px;border:0;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none; outline:none;");
            $('#json-target').attr("style","padding:0px 20px;");
            $('#line-num').hide();
            $('.numberedtextarea-line-numbers').hide();
            shown_flag = false;
            $(this).attr('style','color:#999;');
        }
    });
    function renderLine(){
        var line_num = $('#json-target').height()/20;
        $('#line-num').html("");
        var line_num_html = "";
        for (var i = 1; i < line_num+1; i++) {
            line_num_html += "<div>"+i+"<div>";
        }
        $('#line-num').html(line_num_html);
    }
    $('.zip').click(function(){
        if (zip_flag) {
            $('#json-src').keyup();
        }else{
            $('#json-target').html(current_json_str);
            zip_flag = true;
            $(this).attr('style','color:#15b374;');
        }

    });
    $('.clear').click(function(){
        $('#json-src').val('');
        $('#json-target').html('');
    });
    $('.save').click(function(){
        var content = JSON.stringify(current_json);
        $('#txt-content').val(content);
        $("#form-save").submit();
    });
    $('#json-src').keyup();
</script>
</body>
</html>
