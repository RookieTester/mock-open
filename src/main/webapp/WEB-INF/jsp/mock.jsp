<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/11
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>mock</title>
    <link href="<%=path%>/resource/css/animate.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/font-awesome.min.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/style.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/base.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/jquery.numberedtextarea.css" rel="stylesheet">

    <script src="<%=path%>/resource/js/jquery.min.js"></script>
    <script src="<%=path%>/resource/js/bootstrap.min.js"></script>
    <script src="<%=path%>/resource/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
    <script src="<%=path%>/resource/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
    <script src="<%=path%>/resource/js/content.js"></script>

    <script>
        /**
         * 初始化jqgrid
         */
        $(function () {

            $.jgrid.defaults.styleUI = "Bootstrap";
            $("#table_list_1").jqGrid({
                url: "<%=path%>/mock/queryAll",
                subGridUrl: "/mock/execute",
                datatype: "json",
                height: "auto",
                autowidth: true,
                shrinkToFit: true,
                rowNum: 100,
                rowList: [10, 20, 30],
                colNames: ["主键", "名称", "协议", "域名", "链接", "内容", "文件名", "状态码", "", ""],
                colModel: [{
                    name: "id",
                    index: "id",
                    width: 60,
                    hidden: true,
                    editable: false,
                }, {
                    name: "alias",
                    index: "alias",
                    width: 60,
                    editable: true,
                }, {
                    name: "proto",
                    index: "proto",
                    width: 90,
                    editable: false,
                }, {
                    name: "domain",
                    index: "domain",
                    width: 100,
                    editable: false,
                }, {
                    name: "url",
                    index: "url",
                    width: 80,
                    editable: false,
                }, {
                    name: "json",
                    index: "json",
                    width: 80,
                    editable: false,
                }, {
                    name: "fileName",
                    index: "fileName",
                    width: 80,
                    hidden: true
                }, {
                    name: "status",
                    index: "status",
                    width: 80,
                    editable: false,
                }, {
                    name: "edit",
                    index: "id",
                    width: 40,
                    editable: false,
                }, {
                    name: "del",
                    index: "id",
                    width: 40,
                    hidden: false
                }],

                pager: "#pager_list_1",
                viewrecords: true,
                caption: "已有mock接口",
                hidegrid: false,
                scroll: true,
                gridComplete: function () {  //在此事件中循环为每一行添加修改和删除链接
                    var ids = jQuery("#table_list_1").jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var id = ids[i];
                        var rowDatas = $("#table_list_1").jqGrid('getRowData', id);
                        edit = "<a id='edit' href='<%=path%>/page/modal-edit?id="+id+"' style='color:#2E8B57' data-toggle='modal' data-target='#modal-form'>编辑</a>";
                        del = "<a style='color:#2E8B57' onclick='deleteMock(" + id + ")'>删除</a>";
                        jQuery("#table_list_1").jqGrid('setRowData', ids[i], {
                            edit:edit,del: del
                        });
                    }
                },

                onSelectRow: function (id) {

                }

            });

            $("#table_list_1").setSelection(4, true);
            $("#table_list_1").jqGrid("navGrid", "#pager_list_1", {
                edit: false,
                add: false,
                del: false,
                search: false,
            }, {
                //定义编辑属性
                url: "<%=path%>/mock/edit",
                height: "auto",
                reloadAfterSubmit: true,
                closeAfterEdit: true,
            }, {
                //定义添加属性
                url: "<%=path%>/mock/insert",
                closeAfterAdd: true,
            }, {
                //定义删除属性
                url: "<%=path%>/mock/delete",
            }, {
                //定义查询属性
                url: "<%=path%>/mock/query",
            });
            $(window).bind("resize", function () {
                var width = $(".jqGrid_wrapper").width();
                $("#table_list_1").setGridWidth(width);
            })
        });

        function deleteMock(id) {
            $(function () {
                $("#table_list_1").jqGrid("delGridRow", id, {
                    url: "<%=path%>/mock/delete",
                    width: 500,
                    closeAfterEdit: true
                });
            });
        }
    </script>
    <script src="<%=path%>/resource/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="<%=path%>/resource/js/plugins/iCheck/icheck.min.js"></script>
    <!-- Switchery -->
    <script src="<%=path%>/resource/js/plugins/switchery/switchery.js"></script>
</head>

<body class="gray-bg">
<!-- 点击编辑按钮时，弹出的模态窗-->
<div id="modal-form" class="modal fade" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">

            </div>
        </div>
    </div>
</div>

<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>Mock</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <strong>注意事项：</strong>
                    </p>
                    <ol>
                        <li>需要将mock接口的域名指向Nginx服务的IP</li>
                        <li>目前mock接口数据不支持参数区分（入参无论怎样赋值，返回的数据都是相同的）</li>
                    </ol>
                    <br>

                    <div class="form-group" style="margin: 50px 10px 20px 5px;">
                        <div class="input-group">
                            <input class="form-control" placeholder="此处填写接口mock的名称" style="display: block" id="alias"/>
                        </div>
                        <br>

                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="在此输入想要mock的接口URL，然后点击确认按钮即可" id="url">
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-primary" id="confirm">确认添加</button>
                            </span>
                        </div>
                    </div>


                    <!-- 状态码部分 -->
                    <label id="status">
                        状态码配置开关：
                        <input type="checkbox" class="js-switch" name="status" id="statusSwitch"/>
                    </label>
                    <input placeholder="此处填写想要模拟的状态码" style="display: none" id="statusValue"/>

                    <!-- Json填写框部分 -->
                    <header class="header">
                        <div class="row-fluid">
                            <div class="col-md-5" style="position:relative;">
                                <a class="logo" href="#">Json<span style="color:#555;">填写框</span></a>
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
                            <textarea id="json-src" placeholder="在此输入json字符串或XML字符串..." class="form-control"
                                      style="height:553px;padding:0 10px 10px 20px;border:0;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none; outline:none;">{&#13"Json解析":"支持格式化高亮折叠",&#13"支持XML转换":"支持XML转换Json,Json转XML",&#13"Json格式验证":"更详细准确的错误信息"</textarea>
                        </div>
                        <div class="col-md-7" style="padding:0;">
                            <div id="right-box"
                                 style="height:553px;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none;overflow-y:scroll; outline:none;position:relative;">
                                <div id="line-num"
                                     style="background-color:#fafafa;padding:0px 8px;float:left;border-right:dashed 1px #eee;display:none;z-index:-1;color:#999;position:absolute;text-align:center;over-flow:hidden;">
                                    <div>0</div>
                                </div>
                                <div class="ro" id="json-target" style="padding:0px 25px;over:0">
                                </div>
                            </div>
                            <form id="form-save" method="POST">
                                <input type="hidden" value="" id="txt-content"
                                       name="content">
                            </form>
                        </div>

                        <br style="clear:both;"/>
                    </main>

                    <!-- 查询框 -->
                    <div class="ibox" style="margin-left:10px;margin-right:40px;">
                        <div class="form-group" style="margin: 50px 10px 20px 5px;">
                            <div class="input-group">
                                <div style="float: left">
                                    <select class="form-control" id="queryCondition" >
                                        <option>按URL查询</option>
                                        <option>按接口名称查询</option>
                                    </select>
                                </div>
                                <div style="float: left;width: 500px">
                                    <input type="text" class="form-control" placeholder="查询已经存在的mock服务，在此输入关键词"
                                           id="searchKey">
                                </div>
                                <div style="float: left">
                                    <span class="input-group-btn">
                                       <button type="button" class="btn btn-primary" id="search">
                                           搜索
                                       </button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- jqgrid显示部分 -->
                    <div class="jqGrid_wrapper">
                        <table id="table_list_1"></table>
                        <div id="pager_list_1"></div>
                        <hr>


                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


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

    $('#json-src').keyup(function () {
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
                    $('#json-target').html(result);
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

            $('#json-target').html(result);
        } else {
            $('#json-target').html('');
        }
    });

    $('#json-src').click(function () {
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
                    $('#json-target').html(result);
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

            $('#json-target').html(result);
        } else {
            $('#json-target').html('');
        }
    });

    function renderLine() {
        var line_num = $('#json-target').height() / 20;
        $('#line-num').html("");
        var line_num_html = "";
        for (var i = 1; i < line_num + 1; i++) {
            line_num_html += "<div>" + i + "<div>";
        }
        $('#line-num').html(line_num_html);
    }

    $('#json-src').keyup();
</script>

<!-- ajax添加一个mock服务 -->
<script type="text/javascript">
    $("#confirm").click(function () {
        var alias = document.getElementById("alias").value;
        var url = document.getElementById("url").value;
        var json = current_json_str;//压缩后的JSON
        var statusValue = document.getElementById("statusValue").value;
        var switchVar = $('input:checkbox[name="status"]:checked').val();
        if (switchVar == 'on') {
            $.ajax({
                url: "<%=path%>/mock/execute",
                data: {
                    alias: alias, url: url, json: "", statusSwitch: 1, statusValue: statusValue
                },
                type: "post",
                async: false,
                success: successFuc
            })
        } else {
            $.ajax({
                url: "<%=path%>/mock/execute",
                data: {
                    alias: alias, url: url, json: json, statusSwitch: 0, statusValue: ""
                },
                type: "post",
                async: false,
                success: successFuc
            })
        }
        function successFuc(data) {
            var json = eval(data);
            var result = json.errorMsg;
            alert(result);
            window.location.reload();
        }

    })

</script>

<!-- ajax查询mock服务 -->
<script type="text/javascript">
    $("#search").click(function () {
        var queryCondition=document.getElementById("queryCondition").value;
        var searchKey = document.getElementById("searchKey").value;
        successFuc();
        function successFuc() {
            if (queryCondition=='按URL查询'){
                $("#table_list_1").jqGrid("clearGridData", true).jqGrid("setGridParam", {
                    url: "<%=path%>/mock/queryByUrl?url=" + searchKey,
                    datatype: "json",
                }).trigger("reloadGrid");
            }else if (queryCondition=='按接口别名查询'){
                $("#table_list_1").jqGrid("clearGridData", true).jqGrid("setGridParam", {
                    url: "<%=path%>/mock/queryByAlias?alias=" + searchKey,
                    datatype: "json",
                }).trigger("reloadGrid");
            }
        }

    })

</script>

<!-- 由于存在Js冲突，将部分变量的初始化放在这里 -->
<script>
    $(document).ready(function () {
        var elem = document.querySelector('.js-switch');
        var switchery = new Switchery(elem, {
            color: '#1AB394'
        });
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });

    });
</script>

<!-- 选填状态码时隐藏Json填写框 -->
<script type="text/javascript">
    $("#status").click(function () {
        var switchVar = $('input:checkbox[name="status"]:checked').val();
        if (switchVar == 'on') {
            alert("配置状态码情况下将不支持填写json数据");
            $("#json-src").attr("disabled", true);
            document.getElementById("statusValue").style.display = "block";
        } else {
            $("#json-src").attr("disabled", false);
            document.getElementById("statusValue").style.display = "none";
        }
    })
</script>


</body>
</html>
