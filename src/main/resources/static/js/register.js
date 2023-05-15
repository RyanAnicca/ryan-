window.onload = function () {

    $("#register").click(function (e) {
        e.preventDefault();
        console.log("/register");

        register();
    });


    $("#login").click(function (e) {
        e.preventDefault();
        console.log("/login");

        login();
    });


    function register() {
        // 取得表單資料
        var raw2 = JSON.stringify({
            email: $("#email").val(),
            password: $("#password").val(),
            username: $("#username").val(),
            userphone: $("#userphone").val()
        });
        console.log(raw2);
        $.ajax({
            type: 'post',
            url: '/userdemo/api/v1/auth/register',
            dataType: 'text',
            contentType: 'application/json',
            data: raw2,
            success: function (data) {
                alert(data);
                window.location.href = "/userdemo/api/web/html-controller/login";
            }
        });

    }


    function login() {
        // 取得表單資料
        var raw1 = JSON.stringify({
            email: $("#user").val(),
            password: $("#pass").val()
        });
        console.log(raw1);
        $.ajax({
            type: 'post',
            url: '/userdemo/api/v1/auth/authenticate',
            dataType: 'text',
            contentType: 'application/json',
            data: raw1,
            success: function (data) {
                alert(data);
                window.location.href = "/userdemo/api/web/html-controller/home";
            }
        });

    }
};