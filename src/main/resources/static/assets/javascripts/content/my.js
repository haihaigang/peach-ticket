/**
 * 订单管理
 *
 * */
(function () {
    var docDOM = $(document),
        tempData = null,
        curPopDom = null; //当前点击的popover弹窗dom

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

    // 点击列表中的支付按钮
    docDOM.on('click', '.btn-pay', function (e) {
        e.preventDefault();

        var idx = $(this).data('idx'),
            d = tempData.content[idx];

        if($(this).hasClass('disabled')){
            return;
        }

        Ajax.custom({
            url: Ajax.javaPath + '/order/' + d.id + '/pay',
            type: 'POST'
        }, function (response) {
            Message.success('支付成功，请等到售票人员审核');
            getList(1);
        });
    });

    // 点击列表中的使用按钮
    docDOM.on('click', '.btn-use', function (e) {
        e.preventDefault();

        var idx = $(this).data('idx'),
            d = tempData.content[idx];

        if($(this).hasClass('disabled')){
            return;
        }

        Ajax.custom({
            url: Ajax.javaPath + '/order/' + d.id + '/use',
            type: 'POST'
        }, function (response) {
            Message.success('使用成功');
            getList(1);
        });
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
            url: Ajax.javaPath + '/order/findByUser',
            data: obj,
            showLoader: true
        }, function (response) {
            tempData = response.body;
            Ajax.render("#peach-page", "peach-page-tpl", tempData);
            Ajax.render("#peach-list", "peach-list-tpl", tempData.content);


        }, function (data) {
        })
    }

    // 设置到分页事件上
    Ajax.getNext = getList;

    getList(1); //默认加载第一页


})()
