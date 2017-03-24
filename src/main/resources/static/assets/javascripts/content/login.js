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

            if(_GET().from){
                location.href = _GET().from;
            }else{
                location.href = 'dashboard.html';
            }
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

    function _GET() {
        var e = location.search,
            o = {};
        if ("" === e || void 0 === e) return o;
        e = e.substr(1).split("&");
        for (var n in e) {
            var t = e[n].split("=");
            o[t[0]] = decodeURI(t[1])
        }
        if (o.from) {
            delete o.code
        } //o.from得到的是什么值(类型)
        return o
    }

})()
