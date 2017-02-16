/*cookie*/
(function () {
    function cookie(option) {

    }

    cookie.prototype.getCookie = function (name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }
    cookie.prototype.setCookie = function (name, value, tick) {
        var Days = 30 * 24 * 60 * 60 * 1000;
        var exp = new Date();
        tick = tick || Days;
        exp.setTime(exp.getTime() + tick);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }

    cookie.prototype.removeCookie = function (name) {
        this.setCookie(name, '', -1);
    }
    window.Cookie = new cookie();
})();

/*message*/
(function () {
    function message(option) {
        this.startTimeOut = null;
        this.endTimeOut = null;
    }

    message.prototype.error = function (text) {
        var that = this;
        that.bulidMessage(text, "alert-danger");
        that.showMessage();
    };
    message.prototype.success = function (text) {
        var that = this;
        that.bulidMessage(text, "alert-success");
        that.showMessage();
    };
    message.prototype.warning = function (text) {
        var that = this;
        that.bulidMessage(text, "alert-warning");
        that.showMessage();
    };
    message.prototype.bulidMessage = function (text, type) {
        var that = this;

        $(".top-message").remove()
        clearTimeout(that.startTimeOut)
        clearTimeout(that.endTimeOut)

        var html = '<div class="top-message"><div class="alert ' + type + '" role="alert">' + text + '</div></div>';
        $("body").append(html)
    };
    message.prototype.showMessage = function () {
        var that = this;
        that.startTimeOut = setTimeout(function () {
            $(".top-message").addClass("active");
        }, 500)
        that.endTimeOut = setTimeout(function () {
            $(".top-message").removeClass("active");
        }, 5000)


    };
    window.Message = new message();
})();
(function(){

    function tools(){}

    //按指定格式格式化日期
    function format(date, pattern) {
        var that = date;
        var o = {
            "M+": that.getMonth() + 1,
            "d+": that.getDate(),
            "h+": that.getHours(),
            "m+": that.getMinutes(),
            "s+": that.getSeconds(),
            "q+": Math.floor((that.getMonth() + 3) / 3),
            "S": that.getMilliseconds()
        };
        if (/(y+)/.test(pattern)) {
            pattern = pattern.replace(RegExp.$1, (that.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(pattern)) {
                pattern = pattern.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return pattern;
    };

    //时间戳格式化
    tools.prototype.formatDate = function(content, type, defaultValue) {
        if (content == 0) {
            return '--';
        }
        var pattern = type || "yyyy-MM-dd hh:mm";
        if (isNaN(content) || content == null) {
            return defaultValue || content;
        } else if (typeof(content) == 'object') {
            var y = dd.getFullYear(),
                m = dd.getMonth() + 1,
                d = dd.getDate();
            if (m < 10) {
                m = '0' + m;
            }
            var yearMonthDay = y + "-" + m + "-" + d;
            var parts = yearMonthDay.match(/(\d+)/g);
            var date = new Date(parts[0], parts[1] - 1, parts[2]);
            return format(date, pattern);
        } else {
            if (typeof content == 'string') {
                content = content * 1;
            }
            if (content < 9999999999) {
                content = content * 1000;
            }
            var date = new Date(parseInt(content));
            return format(date, pattern);
        }
    };

    window.Tools = new tools();
})();
/*ajax*/
(function () {
    function ajax() {
        this.pageSize = 100; //分页大小
        this.pageCount = 10; //显示分页数量
        this.loaderDom = $(".loader");
        this.setTokenDom = $("#set-token");
        this.backTokenDom = $("#back-token");
        this.phpPath = '/p'; //php接口的路径前缀，以斜杠开头，结尾不要
        this.javaPath = '/api'; //java接口的路径前缀，以斜杠开头，结尾不要
    };

    ajax.prototype.custom = function (options, success, failed) {
        if (!options) return;
        var that = this;
        if (options.showLoader) {
            that.loaderDom.fadeIn()
        }

        $.ajax({
            url: options.url,
            data: options.data,
            type: options.type || 'GET',
            dataType: 'json',
            beforeSend: function (request) {
                var user = Cookie.getCookie("User"),
                    mockToken = Cookie.getCookie('MockAccessToken');

                if (user) {
                    try {
                        user = JSON.parse(user);
                    } catch (e) {
                        user = {};
                    }
                } else {
                    user = {};
                    if (mockToken) {
                        // 若模拟token存在则使用
                        user.accessToken = mockToken;
                    } else {
                        that.redirectToLogin();
                        return;
                    }
                }
                request.setRequestHeader("X-Auth-Token", user.accessToken);
                request.setRequestHeader("AccessToken", user.accessToken);
            },
            processData: options.contentType ? false : true,
            contentType: options.contentType ? options.contentType : "application/x-www-form-urlencoded",
            success: function (response, textStatus, jqXHR) {
                if (response.status == 200 || response.status == 204) {
                    if (success && typeof success == "function") {
                        if (response.body) {
                            if (response.body.totalPages) {
                                response.body.number = parseInt(response.body.number, 10) // 转换数据类型，以便后面能正常做运算
                                response.body.pagerInfor = that.getAllPager(response.body.number, response.body.totalPages)
                            }
                        }
                        success(response)
                    }
                } else if (response.status == 401) { //未登录状态
                    Message.error(response.message)
                    that.redirectToLogin();
                } else {
                    Message.error(response.message)
                    if (failed && typeof failed == "function") {
                        failed(response)
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                Message.error("服务器" + errorThrown)
                if (failed && typeof failed == "function") {
                    failed();
                }
            },
            complete: function () {
                if (options.showLoader) {
                    that.loaderDom.fadeOut()
                }
            }
        });
    }

    /**
     * template方法
     * @param: ele:容器  id:需要渲染的id data:数据
     * */
    ajax.prototype.render = function (ele, id, data) {
        if (!template) return;
        var html = "";
        if (data instanceof Array) {
            var obj = {list: data}
            html = template(id, obj);
        } else {
            html = template(id, data);
        }
        $(ele).html(html)
    }

    ajax.prototype.getAllPager = function (page, totalPages) {
        if (!page || !totalPages) return [];
        var pagesize = this.pageCount > totalPages ? totalPages : this.pageCount
        var numberLeft = Math.ceil((pagesize - 1) / 2);
        var numberright = pagesize - numberLeft;
        var startNum = 1;
        var result = [];

        if (page <= numberLeft) {
            startNum = 1
        } else if (page > totalPages - numberright) {
            startNum = totalPages - pagesize + 1
        } else {
            startNum = page - numberLeft
        }
        ;
        for (var i = 0; i < pagesize; i++) {
            result.push(startNum + i)
        }
        return result

    }

    /**
     * 设置模拟的token
     * @param accessToken 模拟的token
     */
    ajax.prototype.setMockToken = function (accessToken) {
        Cookie.setCookie('MockUser', accessToken)
    }

    /**
     * 跳转到登录页
     * @return
     */
    ajax.prototype.redirectToLogin = function () {
        var mockUser = Cookie.getCookie("MockUser");
        if (location.pathname == '/abc.html' || location.pathname == '/login.html' || mockUser) {
            // 设置某个特定页面不验证用户身份，或者已经设置了模拟token
            return
        }
        location.href = "./login.html?from=" + location.href;
    }

    window.Ajax = new ajax();
})();

(function () {
    // 校验用户身份并初始化用户昵称
    var userName = Cookie.getCookie("UserName");

    if (userName) {
        $('#username').text('欢迎您，' + userName);
        $('#logout').show();
    } else {
        Ajax.redirectToLogin();
    }

    //点击退出登录
    $('#logout').click(function (e) {
        e.preventDefault();

        Ajax.custom({
            url: Ajax.javaPath + '/account/logout',
            type: 'POST'
        }, function (response) {
            clearAndRedirect();
        }, function () {
            clearAndRedirect();
        });

        function clearAndRedirect() {
            Cookie.removeCookie('UserId');
            Cookie.removeCookie('UserName');
            Cookie.removeCookie('RoleId');
            location.href = './login.html';
        }
    });

    /*
     * 分页
     * */
    $(document).on("click", ".pagination a", function (e) {
        e.preventDefault();

        var obj = $(this);
        if (obj.hasClass("disabled")) {
            return;
        }

        var value = Number(obj.attr("data-value"))
        Ajax.getNext(value)
    });

    /**
     * 截取字符串并添加省略号
     * @param txt 原始字符串
     * @param len 截取的长度
     * @return {[type]}        [description]
     */
    template.helper('$splitStr', function (txt, len) {
        var zhReg = /[\u4e00-\u9fa5]/;
        var oldLen = txt.length;
        var newTxt = txt.replace(zhReg, '__'); //中文计算为两个字符
        var zhCount = (newTxt.length - oldLen) / 2; //中文的个数

        if (newTxt.length > len) {
            return txt.substring(0, len - zhCount) + '...';
        } else {
            return txt;
        }
    });

    /**
     * 自增序号，转换类型并+1
     */
    template.helper('$i', function (i) {
        return parseInt(i) + 1;
    });

    /**
     * 获取文字形式的订单状态
     * 0未支付、1已支付、2已使用、3已取消、4已作废
     */
    template.helper('$getOrderStatus', function (status) {
        var text = '未知';
        switch (status) {
            case 0: {
                text = '未支付';
                break;
            }
            case 1: {
                text = '已支付';
                break;
            }
            case 2: {
                text = '已使用';
                break;
            }
            case 3: {
                text = '已取消';
                break;
            }
            case 4: {
                text = '已作废';
                break;
            }
        }

        return text;
    });

    /**
     * 格式化时间
     */
    template.helper('$formatDate', function(content, type){
        return Tools.formatDate(content, type);
    });

})();
