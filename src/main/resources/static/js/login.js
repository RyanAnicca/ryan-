// window.onload = function () {
//
//     $("#login").click(function (e) {
//         e.preventDefault();
//         console.log("/login");
//
//         login();
//     });
//
//     function login() {
//         // 取得表單資料
//         var raw1 = JSON.stringify({
//             email: $("#user").val(),
//             password: $("#pass").val()
//         });
//         console.log(raw1);
//         $.ajax({
//             type: 'post',
//             url: '/userdemo/api/v1/auth/authenticate',
//             dataType: 'text',
//             contentType: 'application/json',
//             data: raw1,
//             success: function (data) {
//                 alert(data);
//                 window.location.href = "/userdemo/api/web/html-controller/home";
//             }
//         });
//
//     }
// };