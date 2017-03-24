/**
 * 线路管理
 *
 * */
(function () {
    var docDOM = $(document),
        tempData = null,
        curPopDom = null, //当前点击的popover弹窗dom
        tempId = null;//点击操作的信息ID

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

        $('#editForm input[name="id"]').val(d.id);
        $('#editForm input[name="code"]').val(d.code);
        $('#editForm input[name="name"]').val(d.name);
        $('#editForm input[name="price"]').val(d.price);
        $('#editForm input[name="miles"]').val(d.miles);
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

        if (!obj.code) {
            Message.warning('请填写编码');
            return;
        }

        if (!obj.name) {
            Message.warning('请填写名称');
            return;
        }

        Ajax.custom({
            url: Ajax.javaPath + '/line/save',
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

    // 确认删除
    docDOM.on('click', '.ok', function (e) {
        e.preventDefault();

        var id = $(this).data('id');

        Ajax.custom({
            url: Ajax.javaPath + '/line/' + id + '/remove',
            type: 'POST',
            contentType: 'application/json'
        }, function (response) {
            Message.success('删除成功');
            getList(1);
        }, function (data) {
        })
    });

    //点击列表中查看站台按钮
    docDOM.on('click', '.btn-station', function (e) {
        e.preventDefault();

        var idx = $(this).data('idx'),
            d = tempData.content[idx];

        tempId = d.id;

        getStationsByLineId(tempId);
        $('#stationForm').modal('show');
    });

    //点击弹窗框中的增加站台按钮，显示表单
    $('#stationForm .btn-add-station').click(function (e) {
        e.preventDefault();

        $(this).hide();
        $('#addStationForm').show();
    });

    //取消增加站台
    $('#addStationForm .btn-cancel').click(function (e) {
        e.preventDefault();
        $('#addStationForm').hide();
        $('#stationForm .btn-add-station').show();
    });

    //提交保存站台的表单
    $('#addStationForm').submit(function (e) {
        e.preventDefault();

        var stationId = $('#stationId').val();

        if (!tempId || !stationId) {
            Message.warning('信息不完整，请确认');
            return;
        }

        Ajax.custom({
            url: Ajax.javaPath + '/line/' + tempId + '/addStation',
            data: {
                stationId: stationId
            },
            type: 'POST'
        }, function (reponse) {
            Message.success('增加站台成功');
            getStationsByLineId(tempId);
            $('#addStationForm')[0].reset();
        });
    });

    //点击站台列表中的移除按钮
    $('#peach-station-list').on('click', '.btn-remove', function (e) {
        e.preventDefault();

        var that = $(this),
            stationId = $(this).data('id');

        Ajax.custom({
            url: Ajax.javaPath + '/line/' + tempId + '/removeStation',
            data: {
                stationId: stationId
            },
            type: 'POST'
        }, function (reponse) {
            Message.success('移除站台成功');
            that.parents('tr').remove();

            if($('#peach-station-list tr').length == 0){
                $('#peach-station-list').html('<tr><td colspan="6">没有数据。</td></tr>');
            }
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
            url: Ajax.javaPath + '/line/findAll',
            data: obj,
            showLoader: true
        }, function (response) {
            tempData = response.body;
            Ajax.render("#peach-page", "peach-page-tpl", tempData);
            Ajax.render("#peach-list", "peach-list-tpl", tempData.content);


        }, function (data) {
        })
    }

    /**
     * 获取某个线路的所有站台信息，并追加到dom中
     * */
    function getStationsByLineId(lineId){
        Ajax.custom({
            url: Ajax.javaPath + '/line/' + lineId + '/getStations'
        }, function(response){
            Ajax.render("#peach-station-list", "peach-station-list-tpl", response.body);
        });
    }

    // 设置到分页事件上
    Ajax.getNext = getList;

    getList(1); //默认加载第一页


})()
