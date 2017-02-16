/**
 * 角色管理
 *
 * */
(function() {
    var docDOM = $(document),
        tempData = null,
        curPopDom = null; //当前点击的popover弹窗dom

    //点击新增打开编辑框
    $('#btn-add').click(function (e) {
        e.preventDefault();

        $('#editForm').modal('show');
    });

    //点击列表中的编辑按钮
    docDOM.on('click', '.btn-edit', function(e){
        e.preventDefault();

        var idx = $(this).data('idx'),
            d = tempData.content[idx];

        if(!d){
            d = {};
        }

        $('#editForm input[name="id"]').val(d.id);
        $('#editForm input[name="name"]').val(d.name);
        $('#editForm input[name="desc"]').val(d.desc);
        $('#editForm').modal('show');
    });

    //每次关闭弹窗都重置表单
    $('#editForm').on('hide.bs.modal', function(e){
        $('#editForm form')[0].reset();
        $('#editForm input[name="id"]').val(0);//隐藏域总是重置不了
    });

    // 点击保存按钮，保存表单
    $('.btn-save').click(function(e) {

        $('#editForm form').trigger('submit');
    });

    // 表单提交数据
    $('#editForm form').submit(function(e) {
        e.preventDefault();

        var formData = $('#editForm form').serializeArray();
        var obj = {};

        for (var i in formData) {
            obj[formData[i].name] = formData[i].value;
        }

        if (!obj.name) {
            Message.warning('请填写角色名称');
            return;
        }

        if (!obj.desc) {
            Message.warning('请填写角色说明');
            return;
        }

        Ajax.custom({
            url: Ajax.javaPath + '/role/save',
            data: obj,
            type: 'POST'
        }, function(response) {
            $('#editForm').modal('hide');
            Message.success('保存成功');
            getList(1);
        })
    });

    // 点击列表中的删除显示确认弹窗
    docDOM.on('click', '.btn-delete', function(e){
        e.preventDefault();

        if(curPopDom){
            //若确认弹框存在则先取消
            curPopDom.popover('hide');
        }

        curPopDom = $(this);
        $(this).popover('show');
    });

    // 取消确认弹窗，点击空白处也取消
    docDOM.click(function(e) {
        if(!curPopDom || e.target.className == 'btn-delete'){
            return;
        }

        curPopDom.popover('hide');
        curPopDom = null;
    });


    // 确认删除
    docDOM.on('click', '.ok', function(e) {
        e.preventDefault();

        var id = $(this).data('id');

        Ajax.custom({
            url: Ajax.javaPath + '/role/' + id + '/remove',
            type: 'POST',
            contentType: 'application/json'
        }, function(response) {
            Message.success('删除成功');
            getList(1);
        }, function(data) {
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
            url: Ajax.javaPath + '/role/findAll',
            data: obj,
            showLoader: true
        }, function(response) {
            tempData = response.body;
            Ajax.render("#peach-page", "peach-page-tpl", tempData);
            Ajax.render("#peach-list", "peach-list-tpl", tempData.content);


        }, function(data) {
        })
    }

    // 设置到分页事件上
    Ajax.getNext = getList;

    getList(1); //默认加载第一页


})()
