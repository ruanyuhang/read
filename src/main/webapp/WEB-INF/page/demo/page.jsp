<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
    <title>Demo</title>
    <script type="text/javascript" src="<%=path%>/js/jquery/jquery-3.1.1.min.js"></script>
</head>

<body>
<input type="button" name="submit" id="submit" value="点我">
<ul>
    <li id="id"></li>
    <li id="name"></li>
    <li id="createTime"></li>
</ul>
<script>
$(function () {
    $("#submit").bind("click", function(){
        $.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : '<%=path%>/demo/getDetail',
            processData : false,
            dataType : 'json',
            data : {},
            success : function(data) {
                $('#id').text(data.id);
                $('#name').text(data.name);
                $('#createTime').text(data.createTime);
            },
            error : function() {
                alert('Err...');
            }
        });
    });
});
</script>
</body>
</html>