/**
 * 默认页
 *
 * */
(function () {
    var docDOM = $(document),
        tempData = null;

    //角色菜单对应表，这里的key需要和后台数据对应，如果后台修改了这里也需要对应更新
    var ROLE_MENUS = {
        1: {
            id: 1,
            name: '管理员',
            menus: [{
                name: '角色',
                path: './role.html'
            }, {
                name: '用户',
                path: './user.html'
            }, {
                name: '站台',
                path: './station.html'
            }, {
                name: '线路',
                path: './line.html'
            }, {
                name: '订单',
                path: './order.html'
            }]
        },
        2: {
            id: 2,
            name: '售票人员',
            menus: [{
                name: '订单',
                path: './ticket.html'
            }]
        },
        3: {
            id: 3,
            name: '普通用户',
            menus: [{
                name: '购票',
                path: './index.html'
            }, {
                name: '订单',
                path: './my.html'
            }]
        }
    }

    //初始化菜单
    function initMenus() {
        var result = '',
            pathname = location.pathname,
            key = Cookie.getCookie('RoleId');

        if (!key) {
            return;
        }

        var menus = ROLE_MENUS[key].menus;

        for (var i in menus) {
            var action = menus[i].path,
                isActive = false;

            if (pathname == action || (pathname == '/' && action == '/index.html')) {
                isActive = true;
            }
            result += '<li' + (isActive ? ' class="active"' : '') + '><a href="' + action + '">' + menus[i].name + '</a></li>'
        }

        $('#peach-menus').html(result);
    }


    initMenus();
})()
