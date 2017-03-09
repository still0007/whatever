$('#loginbtn').click(function() {
    var param = {
        openid: $("#openid").val(),
        username : $("#username").val(),
        password : $("#password").val(),
        rememberMe: $("#rememberMe").val()=="on"
    };
    $.ajax({
        type: "post",
        url: "/api/user/checkLogin.json",
        data: param,
        dataType: "json",
        success: function(data) {
            if(data.success == false){
                alert(data.errorMsg);
            }else{
                window.location.href = "/index.html";
            }
        },
        error: function(data) {
            alert("调用失败...."+data.responseText);
        }
    });
});

$(document).ready(function(){
    $.ajax({
        type: "get",
        url: "/api/user/loginStatus.json",
        dataType: "json",
        success: function(data){
            if(data.status == true){
                window.location.href = "/index.html";
            }
            else{
                $.ajax({
                    type: "POST",
                    url: "/api/user/autologin.json",
                    dataType: "json",
                    success: function(data){
                        if(data.success == true){
                            window.location.href = "/index.html";
                        }
                    },
                    error: function(data) {
                        alert("调用失败...."+data.responseText);
                    }
                });
            }
        },
        error: function(data) {
            alert("调用失败...."+data.responseText);
        }
    });
});