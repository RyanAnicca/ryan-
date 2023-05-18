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
        let raw2 = JSON.stringify({
            email: $("#email").val(),
            password: $("#password").val(),
            username: $("#username").val(),
            userphone: $("#userphone").val()
        });
        if ($("#email").val() === "") {
            alert('帳號未填寫');
            return;
        }
        if ($("#password").val() === "") {
            alert('密碼未填寫');
            return;
        }
        if ($("#username").val() === "") {
            alert('名稱未填寫');
            return;
        }
        if ($("#userphone").val() === "") {
            alert('電話未填寫');
            return;
        }
        if ($("#password").val() != $("#password2").val()) {
            alert('兩次密碼不相符');
            return;
        }
        if (confirm("確定創建帳號?")) {
            $.ajax({
                type: 'post',
                url: '/userdemo/api/v1/auth/register',
                dataType: 'text',
                contentType: 'application/json',
                data: raw2,
                success: function (data) {
                    alert("註冊成功");
                    window.location.href = "/userdemo/api/web/html-controller/login";
                }
            });

        } else {
        }
    }


    function login() {
        // 取得表單資料
        let raw1 = JSON.stringify({
            email: $("#user").val(),
            password: $("#pass").val()
        });
        if ($("#user").val() === "") {
            alert('帳號未填寫');
            return;
        }
        if ($("#pass").val() === "") {
            alert('密碼未填寫');
            return;
        }
        $.ajax({
            type: 'post',
            url: '/userdemo/api/v1/auth/authenticate',
            dataType: 'text',
            contentType: 'application/json',
            data: raw1,
            success: function (data) {
                // 解析 JSON 字符串为 JavaScript 对象
                const parsedData = JSON.parse(data);
                // 获取 access_token 的值
                const accessToken = parsedData.access_token;
                sessionStorage.setItem('jwtToken', accessToken);
                window.location.href = "/userdemo/api/web/html-controller/home";
            }
        });

    }
};