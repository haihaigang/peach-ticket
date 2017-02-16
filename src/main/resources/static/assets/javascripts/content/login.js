/**
 * 登录
 *
 * */
(function () {
    var docDOM = $(document),
        isLogin = false;

    //提交form表单
    $('#loginForm').submit(function (e) {
        e.preventDefault();

        if(isLogin){
            return;
        }

        var formData = $(this).serializeArray();
        var obj = {};

        for (var i in formData) {
            obj[formData[i].name] = formData[i].value;
        }

        if(!obj.username){
            Message.warning('请输入用户名');
            return;
        }

        if(!obj.password){
            Message.warning('请输入密码');
            return;
        }

        isLogin = true;
        $('#btnSubmit').addClass('disabled').text('登录中');

        Ajax.custom({
            url: Ajax.javaPath + '/account/login',
            data: formData,
            type: 'POST'
        }, function (response) {
            Message.success('登录成功');

            Cookie.setCookie('UserId', response.body.id);
            Cookie.setCookie('UserName', response.body.nickname);
            Cookie.setCookie('RoleId', response.body.role.id);

            location.href = 'dashboard.html';
        }, function () {
            resetLoginStatus();
        });
    });

    //重置登录的一些状态
    var inte = null;
    function resetLoginStatus(){
        isLogin = false;
        if(inte){
            clearTimeout(inte);
        }
        inte = setTimeout(function(){
            $('#btnSubmit').removeClass('disabled').text('登 录');
        },200)
    }

})()
