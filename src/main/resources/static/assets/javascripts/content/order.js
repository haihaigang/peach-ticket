/**
 * 订单管理
 *
 * */
(function () {
    var docDOM = $(document),
        tempData = null,
        curPopDom = null; //当前点击的popover弹窗dom

    //提交form表单
    $('.peach-search').submit(function (e) {
        e.preventDefault();

        getList(1);
    });

    //点击新增打开编辑框
    $('#btn-add').click(function (e) {
        e.preventDefault();

        $('#editForm').modal('show');
    });

    //点击列表中的编辑按钮
    docDOM.on('click', '.btn-edit', function (e) {
        e.preventDefault();

        var idx = $(this).data('idx'),
            d = tempData.content[idx];

        $('#oid').text(d.id);
        $('#userId').text(d.userId);
        $('#startStationId').text(d.startStationId);
        $('#endStationId').text(d.endStationId);
        $('#fangan').text(d.fangan);
        $('#totalAmount').text(d.totalAmount);
        $('#payAmount').text(d.payAmount);
        $('#payStatus').text(d.payStatus);
        $('#status').text(d.status);
        $('#createAt').text(Tools.formatDate(d.createAt, 'yyyy-MM-dd hh:mm:ss'));
        $('#editForm').modal('show');
    });

    //每次关闭弹窗都重置表单
    $('#editForm').on('hide.bs.modal', function (e) {
        $('#editForm form')[0].reset();
        $('#editForm input[name="id"]').val(0);//隐藏域总是重置不了
    });

    // 点击保存按钮，保存表单
    $('.btn-save').click(function (e) {

        $('#editForm form').trigger('submit');
    });

    // 表单提交数据
    $('#editForm form').submit(function (e) {
        e.preventDefault();

        var formData = $('#editForm form').serializeArray();
        var obj = {};

        for (var i in formData) {
            obj[formData[i].name] = formData[i].value;
        }

        if (!obj.username) {
            Message.warning('请填写登录名');
            return;
        }

        if (!obj.password) {
            Message.warning('请填写密码');
            return;
        }

        if (!obj.password2) {
            Message.warning('请填写确认密码');
            return;
        }

        if (obj.password != obj.password2) {
            Message.warning('请确认两次输入密码一致');
            return;
        }

        if (!obj.nickname) {
            Message.warning('请填写昵称');
            return;
        }

        if (!obj.roleId) {
            Message.warning('请选择角色');
            return;
        }

        Ajax.custom({
            url: Ajax.javaPath + '/order/save',
            data: obj,
            type: 'POST'
        }, function (response) {
            $('#editForm').modal('hide');
            Message.success('保存成功');
            getList(1);
        })
    });

    // 点击列表中的删除显示确认弹窗
    docDOM.on('click', '.btn-delete', function (e) {
        e.preventDefault();

        if (curPopDom) {
            //若确认弹框存在则先取消
            curPopDom.popover('hide');
        }

        curPopDom = $(this);
        $(this).popover('show');
    });

    // 取消确认弹窗，点击空白处也取消
    docDOM.click(function (e) {
        if (!curPopDom || e.target.className == 'btn-delete') {
            return;
        }

        curPopDom.popover('hide');
        curPopDom = null;
    });

    // 点击列表中的删除启用禁用按钮
    docDOM.on('click', '.btn-enabled', function (e) {
        e.preventDefault();

        var idx = $(this).data('idx'),
            d = tempData.content[idx];

        Ajax.custom({
            url: Ajax.javaPath + '/order/enabled/' + d.id,
            data: {
                id: d.id,
                status: d.status ? 0 : 1
            },
            type: 'POST'
        }, function (response) {
            Message.success((d.status ? '禁用' : '启用') + '成功');
            getList(1);
        });
    });

    // 确认删除
    docDOM.on('click', '.ok', function (e) {
        e.preventDefault();

        var id = $(this).data('id');

        Ajax.custom({
            url: Ajax.javaPath + '/order/' + id + '/remove',
            type: 'POST',
            contentType: 'application/json'
        }, function (response) {
            Message.success('删除成功');
            getList(1);
        }, function (data) {
        })
    });

    /**
     * 搜索商品
     */
    function getList(page) {
        var formData = $('.peach-search').serializeArray();
        var obj = {};

        for (var i in formData) {
            obj[formData[i].name] = formData[i].value;
        }
        obj.page = page;
        obj.size = Ajax.pageSize;

        Ajax.custom({
            url: Ajax.javaPath + '/order/findAll',
            data: obj,
            showLoader: true
        }, function (response) {
            tempData = processData(response.body);
            Ajax.render("#peach-page", "peach-page-tpl", tempData);
            Ajax.render("#peach-list", "peach-list-tpl", tempData.content);


        }, function (data) {
        })
    }

    /**
     * 处理数据
     * */
    function processData(data){
        for(var i in data.content){
            var fanganArr = data.content[i].fanganCN.split('-');

            if(fanganArr.length == 0){
                continue;
            }
            data.content[i].startStationName = fanganArr[0];
            data.content[i].endStationName = fanganArr[fanganArr.length - 1];
        }

        return data;
    }

    // 设置到分页事件上
    Ajax.getNext = getList;

    getList(1); //默认加载第一页


})()
